package com.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vendor.controller", "com.vendor.dao","com.vendor.entity","com.vendor.service"})
public class RoleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleServerApplication.class, args);
	}

}
