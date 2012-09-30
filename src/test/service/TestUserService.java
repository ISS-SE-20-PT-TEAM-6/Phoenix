package test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.exceptions.UserAlreadyExistsException;
import sg.edu.nus.iss.phoenix.user.exceptions.UserNotFoundException;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 * Class to test the User Service
 * 
 * @author AJ
 *
 */
public class TestUserService {
	private User presenter, admin;
	private UserService userService;
	

	@Before
	public void setUp() throws Exception {
		userService = new UserService();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteDummyUser();
	}
	
	/**
	 * Finally delete the dummy user that was created
	 */
	private void deleteDummyUser() {
		try {
			System.out.println("Deleting dummy user");
			User dummyUser = new User();
			dummyUser.setId("alfred");
			userService.deleteUser(dummyUser);
		} catch (UserNotFoundException e) {
			//Nothing to do
		}
	}
	
	@Test
	public void testGetUserDetail() {
		try {
			admin = userService.getUser("catbert");
			
			Assert.assertNotNull(admin);
			Assert.assertEquals("catbert", admin.getId());
			
			Assert.assertNotNull(admin.getRoles());
			Assert.assertTrue(admin.getRoles().size() > 0);
		} catch (UserNotFoundException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testModifyUser() {
		try {
			presenter = userService.getUser("dogbert");
			
			Assert.assertNotNull(presenter);
			Assert.assertEquals("dogbert", presenter.getId());
			
			String randomCity = String.valueOf((new Random()).nextInt(1000000));
			presenter.setCity(randomCity);
			
			userService.modifyUser(presenter);
			
			presenter = userService.getUser("dogbert");
			Assert.assertNotNull(presenter);
			Assert.assertEquals("dogbert", presenter.getId());
			Assert.assertEquals(randomCity, presenter.getCity());
		
		} catch (UserNotFoundException e) {
			Assert.fail(e.getMessage());
		}
	}	

	@Test
	public void testCreateUser() {
		try {
			User user = new User();
			user.setId("alfred");
			user.setName("Alfred");
			user.setPassword("password");
			user.setAddress1("Address 1");
			user.setAddress2("Address 2");
			user.setAddress3("Address 3");
			user.setHomePhone("+65 11111111");	
			user.setMobile("+65 22222222");	
			user.setEmailAddress("alfred.jayaprakash@nus.edu.sg");
			user.setCity("City");
			user.setCountry("SG");
			
			List<Role> roles = new ArrayList<Role>();
			roles.add(new Role("admin"));
			
			user.setRoles(roles);
			
			userService.createUser(user);
			user = null;
			
			user = userService.getUser("alfred");
			
			Assert.assertNotNull(user);
			Assert.assertEquals("alfred", user.getId());
			Assert.assertEquals("Alfred", user.getName());
			Assert.assertEquals("password", user.getPassword());
			Assert.assertEquals("Address 1", user.getAddress1());
			Assert.assertEquals("Address 2", user.getAddress2());
			Assert.assertEquals("Address 3", user.getAddress3());
			Assert.assertEquals("+65 11111111", user.getHomePhone());
			Assert.assertEquals("+65 22222222", user.getMobile());
			Assert.assertEquals("alfred.jayaprakash@nus.edu.sg", user.getEmailAddress());
			Assert.assertEquals("City", user.getCity());
			Assert.assertEquals("SG", user.getCountry());
			Assert.assertNotNull(user.getRoles());
			Assert.assertTrue(user.getRoles().size() > 0);
			Assert.assertEquals("admin", user.getRoles().get(0).getRole());
		
		} catch (UserNotFoundException e) {
			Assert.fail(e.getMessage());
		} catch (UserAlreadyExistsException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateExistingUser() {
		try {
			User user = new User();
			user.setId("dogbert");
			user.setName("Dogbert 2");
			user.setPassword("password");
			user.setAddress1("My dummy address");
			user.setCity("City");
			user.setCountry("SG");
			

			List<Role> roles = new ArrayList<Role>();
			roles.add(new Role("admin"));
			
			user.setRoles(roles);
			
			userService.createUser(user);
			Assert.fail("Duplicate user was created.");
		
		} catch (UserAlreadyExistsException e) {
			//This means successful
		}
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> userList = userService.getAllUsers();
		
		Assert.assertNotNull(userList);
	}
}
