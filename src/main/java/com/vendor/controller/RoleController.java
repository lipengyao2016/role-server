package com.vendor.controller;

import com.vendor.dao.RoleDao;
import com.vendor.entity.Roles;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.service.IRoleService;
import com.vendor.service.RoleServiceImpl;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//@RequestMapping("/order")
public class RoleController {

    private Log log = LogFactory.getLog(RoleController.class);
  //  @Autowired
   // roleService roleService;

    @Autowired
    IRoleService rolexxService;

    @Autowired
    RoleDao roleDao;

    RoleServiceImpl roleService = null;

    public RoleServiceImpl getRoleService() {
        if(roleService == null)
        {
            roleService = new RoleServiceImpl(roleDao);
        }
        return roleService;
    }

    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Roles createRole(@PathVariable("ver") String version, @RequestBody Roles role) {

        log.info("role:" + role.getUuid() + " name:" + role.getName() + ",version:" + version);

        System.out.println(role.getName());
      //  Roles newRole = (Roles) this.getRoleService().create(role);

       Roles newRole = (Roles) rolexxService.create(role);
       return newRole;
        //return  null;
    }


    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Roles getRoles(@PathVariable("ver") String version, @PathVariable("roleUUID") String roleUUID) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);
        if(roleUUID.contentEquals("2") )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return rolexxService.get(roleUUID);
    }

    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.GET})
    @ResponseBody
    public List<Roles> listRoles(@PathVariable("ver") String version, RoleQueryVo role) {
        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, RoleQueryVo.class));
        return rolexxService.list(role);
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public Roles updateRoles(@PathVariable("ver") String version, @PathVariable("roleUUID") String roleUUID,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);


        return rolexxService.update(roleUUID,updateRole);
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Roles deleteRoles(@PathVariable("ver") String version, @PathVariable("roleUUID") String roleUUID) {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);

        return rolexxService.delete(roleUUID);
    }

    /*@RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.GET})
    @ResponseBody
    public List<Roles> listRoles(@PathVariable("ver") String version, RoleQueryVo role) {
        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, RoleQueryVo.class));
        List<Roles> findRoles = null;

*//*        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<Roles> ex = Example.of(role, matcher);
        findRoles = roleService.findAll(ex);*//*

        Specification<Roles> specification = new Specification<Roles>() {
            //toPredicate就是查询条件
            //root根对象表示实体类,要查询的类型,query所需要添加查询条件,cb构建Predicate
            @Override
            public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub

                List<Predicate> predicatesList = new ArrayList<>();

                if (role.getName() != null) {
                    predicatesList.add(cb.and(cb.like(root.get("name"), "%" + role.getName() + "%")));
                }

                if (role.getUuid() != null) {
                    predicatesList.add(cb.and(cb.equal(root.get("uuid"), role.getUuid())));
                }

                if (role.getStatus() != null) {
                    String status  =role.getStatus();
                    List<String>  statuss= (List<String>) GsonUtils.ToObject(status,List.class);
                    log.info("statuss  :" + GsonUtils.ToJson(statuss, List.class));
                    CriteriaBuilder.In<String> in = cb.in(root.get("status"));
                    for (String valSta:statuss) {
                        log.info("valSta  :" + valSta);
                        in.value(valSta);
                    }
                    predicatesList.add(cb.and(in));
                }

                if (role.getCreatedAt() != null) {
                    String createAtStr  =role.getCreatedAt();
                    List<String>  createAts= (List<String>) GsonUtils.ToObject(createAtStr,List.class);
                    log.info("createAtStr  :" + GsonUtils.ToJson(createAts, List.class));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date beginDate = sdf.parse(createAts.get(0));
                        Date endDate = sdf.parse(createAts.get(1));
                        predicatesList.add(cb.and(cb.between(root.get("createdAt"),beginDate ,endDate )));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                return cb.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
            }
        };
        findRoles = roleService.findAll(specification);

        return findRoles;
    }





    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public Roles updateRoles(@PathVariable("ver") String version, @PathVariable("roleUUID") String roleUUID,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);

        Roles role = new Roles();
        role.setUuid(roleUUID);

        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<Roles> ex = Example.of(role, matcher);

        List<Roles> findRoles = roleService.findAll(ex);

        Roles oldRole = findRoles.get(0);
        RoleController.copyPropertiesIgnoreNull(updateRole,oldRole);
        roleService.save(oldRole);

        return oldRole;
    }
  */


}
