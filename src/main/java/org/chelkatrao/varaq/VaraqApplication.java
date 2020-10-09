package org.chelkatrao.varaq;

import org.chelkatrao.varaq.security.AuthorityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VaraqApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(VaraqApplication.class, args);
		run.getBean(AuthorityService.class).createDepartment();// create department
		run.getBean(AuthorityService.class).createPermission();// create permission
		run.getBean(AuthorityService.class).createRoleAdmin();// create admin role
		run.getBean(AuthorityService.class).createRoleUser();// create user role
		run.getBean(AuthorityService.class).createRoleTeacher();// create teacher role
		run.getBean(AuthorityService.class).createUser();// create user
	}

}
