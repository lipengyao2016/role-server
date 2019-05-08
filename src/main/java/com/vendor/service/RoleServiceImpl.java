package com.vendor.service;

import com.vendor.dao.RoleDao;
import com.vendor.entity.Roles;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl   implements  IRoleService{

    @Autowired
    private RoleDao roleDao;

    private IBaseService<Roles,RoleQueryVo> baseService;

    public  RoleServiceImpl()
    {
        System.out.println("RoleServiceImpl init");
    }

    @Autowired(required = true)
    public  RoleServiceImpl(RoleDao roleDao)
    {
        this.roleDao = roleDao;
        this.baseService = new BaseServiceImpl(roleDao,Roles.class);
    }


    @Override
    public Roles create(Roles obj) {
      /*  obj.setUuid(UUIDUtils.createUUID());
        obj.setCreatedAt(new Date());
        obj.setModifiedAt(new Date());
        if(StringUtils.isEmpty(obj.getStatus()))
        {
            obj.setStatus("enabled");
        }*/
        return this.baseService.create(obj);
    }

    @Override
    public Roles get(String uuid) {
        return this.baseService.get(uuid);
    }

    @Override
    public List<Roles> list(RoleQueryVo queryObj) {
        return this.baseService.list(queryObj);
    }

    @Override
    public Roles update(String uuid, Roles updateObj) {
        return this.baseService.update(uuid,updateObj);
    }

    @Override
    public Roles delete(String uuid) {
        return this.baseService.delete(uuid);
    }
}
