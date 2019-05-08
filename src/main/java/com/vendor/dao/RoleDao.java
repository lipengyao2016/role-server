package com.vendor.dao;

import com.vendor.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface  RoleDao  extends JpaRepository<Roles, Long>,JpaSpecificationExecutor<Roles> {

}
