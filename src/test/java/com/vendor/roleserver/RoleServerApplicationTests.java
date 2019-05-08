package com.vendor.roleserver;

import com.vendor.dao.RoleDao;
import com.vendor.entity.Roles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServerApplicationTests {

	@Autowired
	private RoleDao roleDao;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAddRole() {
		Roles role = new Roles();
		role.setType("merchant");
		role.setUuid("gmFTHz79zBlWyJ0M4h3MLQ");
		role.setName("testRole");
		role.setOwnerUuid("Qb6bKJ2COuIPQwFSwXzV2Q");
		role.setStatus("enabled");
	//	role.setPermission(new Character('a'));
		role.setCreatedAt(new Date());
		role.setModifiedAt(new Date());
		roleDao.save(role);
	}
}
