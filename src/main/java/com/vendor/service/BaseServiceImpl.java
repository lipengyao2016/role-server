package com.vendor.service;

import com.google.gson.reflect.TypeToken;
import com.vendor.controller.RoleController;
import com.vendor.entity.Roles;
import com.vendor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T, QY_T> implements IBaseService {

    private Log log = LogFactory.getLog(BaseServiceImpl.class);
    private JpaRepository m_jpaRepository;
    private JpaSpecificationExecutor m_jpaSpecificationExecutor;
    private Class entityClass;

    public BaseServiceImpl() {

    }

    public BaseServiceImpl(Object obj, Class entityClass) {
        m_jpaRepository = (JpaRepository) obj;
        m_jpaSpecificationExecutor = (JpaSpecificationExecutor) obj;
        this.entityClass = entityClass;
    }


    public Object create(Object obj) {

        try {
            ReflectUtils.setField(obj, "uuid", UUIDUtils.createUUID());
            ReflectUtils.setField(obj, "createdAt", new Date());
            ReflectUtils.setField(obj, "modifiedAt", new Date());
            ReflectUtils.setField(obj, "status", "enabled");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return m_jpaRepository.save(obj);
    }


    public Object get(String uuid) {
        Object queryObj = null;
        try {
            queryObj = ReflectUtils.createInstance(this.entityClass);
            try {
                ReflectUtils.setField(queryObj, "uuid", uuid);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        ExampleMatcher matcher = ExampleMatcher.matching();
        //创建实例
        Example ex = Example.of(queryObj, matcher);
        List findRoles = m_jpaRepository.findAll(ex);
        if(findRoles.size() > 0)
        {
            return findRoles.get(0);
        }
        else
        {
            return  null;
        }

    }


    public List list(Object queryObj) {

        Specification<T> specification = new Specification<T>() {
            //toPredicate就是查询条件
            //root根对象表示实体类,要查询的类型,query所需要添加查询条件,cb构建Predicate
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicatesList = new ArrayList<>();

                Field[] fieldArray = queryObj.getClass().getDeclaredFields();
                for (Field f : fieldArray) {

                    try {
                        String value = null;
                        try {
                            value = (String) ReflectUtils.getField(queryObj,f.getName());
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        log.info("field name:" + f.getName() + ",value:" + value);
                        if(value != null)
                        {
                            if (value.contains("[")) {
                                List<Object> valueList = GsonUtils.ToObjectList( value);

                                Object firstValue = valueList.get(0);
                                StrUtils.emObjType objType = StrUtils.getObjectType(firstValue);
                                if (valueList.size() == 2 &&  objType == StrUtils.emObjType.Obj_Str
                                && StrUtils.isValidDate((String) valueList.get(0))
                                        && StrUtils.isValidDate((String) valueList.get(1))) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        Date beginDate = sdf.parse((String)valueList.get(0));
                                        Date endDate = sdf.parse((String)valueList.get(1));
                                        predicatesList.add(cb.and(cb.between(root.get(f.getName()), beginDate, endDate)));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    CriteriaBuilder.In<Object> in = cb.in(root.get(f.getName()));
                                    for (Object valSta : valueList) {
                                        log.info("valSta  :" + valSta);
                                        in.value(valSta);
                                    }
                                    predicatesList.add(cb.and(in));
                                }
                            } else if (value.contains("*")) {
                                value = value.replace("*", "%");
                                predicatesList.add(cb.and(cb.like(root.get(f.getName()), value)));
                            } else {
                                predicatesList.add(cb.and(cb.equal(root.get(f.getName()), value)));
                            }
                        }



                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return cb.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
            }
        };
        List findRoles = m_jpaSpecificationExecutor.findAll(specification);
        return findRoles;
    }


    public Object update(String uuid, Object updateObj) {
        Object oldRole = this.get(uuid);
        if(oldRole != null)
        {
            BeanHelper.copyPropertiesIgnoreNull(updateObj,oldRole);
            try {
                ReflectUtils.setField(oldRole, "modifiedAt", new Date());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            m_jpaRepository.save(oldRole);
        }
        else
        {
           throw new DataNotFoundException("5000","update db not found uuid:" + uuid);
        }

        return oldRole;
    }


    public Object delete(String uuid) {
        Object oldRole = this.get(uuid);
        if(oldRole != null)
        {
            m_jpaRepository.delete(oldRole);
        }
        else
        {
            throw new DataNotFoundException("5001","delete db not found uuid:" + uuid);
        }
         return  oldRole;
    }
}
