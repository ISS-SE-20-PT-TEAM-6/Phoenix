package test.service;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateService;

public class TestAuthenicateService {
	private User user, user1;
	

	@Before
	public void setUp() throws Exception {
		user =new User();
		user.setId("catbert");
		user.setName("catbert, the hr");
		user.setPassword("catbert");
		Role e = new Role("admin");
		ArrayList<Role> list = new ArrayList<Role>();
		list.add(e);
		user.setRoles(list);
		//user.setAccessPrivilege("");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		user1 = new User();
		user1.setId("catbert");
		user1.setPassword("catbert");
		AuthenticateService service = new AuthenticateService();
		user1 = service.validateUserIdPassword(user1);
		Assert.assertEquals(user1.getRoles().get(0).getRole(), "admin");
	}

}
