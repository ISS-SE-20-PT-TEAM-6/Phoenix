package test.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;


public class TestUserDAO {
	private User user, user1, uniqueUser, uniqueUser1 ;
;
	private  UserDao userDao;
	

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
		
		uniqueUser = new User();
		uniqueUser.setId(UUID.randomUUID().toString());
		uniqueUser.setName("Test U");
		uniqueUser.setPassword("password");
		uniqueUser.setAddress1("Address1");
		uniqueUser.setAddress2("Address2");
		uniqueUser.setAddress3("Address3");
		uniqueUser.setCity("SG City");
		uniqueUser.setState("SG State");
		uniqueUser.setCountry("SG");
		uniqueUser.setMobile("Mobile");
		uniqueUser.setHomePhone("HomePhoneNo");
		uniqueUser.setEmailAddress("emailAddress");
		ArrayList<Role> ulist = new ArrayList<Role>();
		ulist.add(new Role("presenter") );
		ulist.add(new Role("producer") );
		uniqueUser.setRoles(ulist);

		uniqueUser1 = new User();
		uniqueUser1.setId(UUID.randomUUID().toString());
		uniqueUser1.setName("Test U1");
		uniqueUser1.setPassword("password");
		uniqueUser1.setAddress1("Address1");
		uniqueUser1.setAddress2("Address2");
		uniqueUser1.setAddress3("Address3");
		uniqueUser1.setCity("SG City");
		uniqueUser1.setState("SG State");
		uniqueUser1.setCountry("SG");
		uniqueUser1.setMobile("Mobile");
		uniqueUser1.setHomePhone("HomePhoneNo");
		uniqueUser1.setEmailAddress("emailAddress");
		ArrayList<Role> ulist1 = new ArrayList<Role>();
		ulist1.add(new Role("presenter") );
		ulist1.add(new Role("producer") );
		uniqueUser1.setRoles(ulist);

	}

	@After
	public void tearDown() throws Exception {
		try
		{
		userDao = new UserDaoImpl();
		userDao.delete(uniqueUser);
		userDao.delete(uniqueUser1);
		}
		catch(Exception ex){}
	}

	@Test
	public void testLoad() {
		userDao = new UserDaoImpl();
		user1 = new User();
		user1.setId("catbert");
		
		try{
		userDao.load(user1);
		List<Role> roles = user1.getRoles();
		Assert.assertTrue(user1.getId().equals(user.getId()));
		Assert.assertTrue(user1.getPassword().equals(user.getPassword()));
		Assert.assertTrue(user1.getName().equals(user.getName()));
		Assert.assertTrue(roles.size()==user1.getRoles().size());
		//notfound
		try{
			User tUser = new User();
			tUser.setId("nobody");
			userDao.load(tUser);
			Assert.fail();
		}
		catch(NotFoundException ex) {
			Assert.assertTrue(true);
		}
		}
		catch(Exception ex) {
			Assert.fail();
		}
				
	}
	@Test
	public void testLoadAll() {
		userDao = new UserDaoImpl();
		
		try{
				List<User> users = userDao.loadAll();
				int rows=userDao.countAll();
				Assert.assertEquals(users.size(),rows);
		}
		catch(Exception ex) {
			Assert.fail();
		}				
	}

	@Test
	public void testCreateUser() {
		userDao = new UserDaoImpl();
		
		try{
				userDao.create(uniqueUser);
				User luser = userDao.getObject(uniqueUser.getId());
				Assert.assertEquals(luser.getId(), uniqueUser.getId());
				Assert.assertEquals(luser.getName(), uniqueUser.getName());
				Assert.assertEquals(luser.getPassword(), uniqueUser.getPassword());
				Assert.assertEquals(luser.getAddress1(), uniqueUser.getAddress1());
				Assert.assertEquals(luser.getAddress2(), uniqueUser.getAddress2());
				Assert.assertEquals(luser.getAddress3(), uniqueUser.getAddress3());
				Assert.assertEquals(luser.getCity(), uniqueUser.getCity());
				Assert.assertEquals(luser.getState(), uniqueUser.getState());
				Assert.assertEquals(luser.getCountry(), uniqueUser.getCountry());
				Assert.assertEquals(luser.getMobile(), uniqueUser.getMobile());
				Assert.assertEquals(luser.getHomePhone(), uniqueUser.getHomePhone());
				Assert.assertEquals(luser.getEmailAddress(), uniqueUser.getEmailAddress());
				
				List<Role> lRoles = luser.getRoles();
				List<Role> uRoles = uniqueUser.getRoles();
				
				Assert.assertEquals(lRoles.size(), uRoles.size());
				boolean isRolesSame=false;
				for(Role role : uRoles)
				{
					for(Role lrole : lRoles)
					{
						isRolesSame=false;
						if(lrole.getRole().equals(role.getRole()))
						{
							isRolesSame=true;
							break;
						}
					}
					if(!isRolesSame)
						break;
				}
				Assert.assertTrue(isRolesSame);
		}
		catch(Exception ex) {
			Assert.fail();
		}				
	}

	@Test
	public void testSaveUser() {
		userDao = new UserDaoImpl();
		
		try{
			
				userDao.create(uniqueUser1);
				
				User luser = userDao.getObject(uniqueUser1.getId());
				luser.setPassword("NewPassword");
				luser.setName("new Name");
				luser.setAddress1("New Address1");
				luser.setAddress2("New Address2");
				luser.setAddress3("New Address3");
				luser.setCity("New City");
				luser.setState("New State");
				luser.setCountry("ID");
				luser.setEmailAddress("new email");
				luser.setHomePhone("new Homephone No");
				luser.setMobile("new Mobile");
				List<Role> roles = luser.getRoles();
				roles.clear();
				roles.add(new Role("admin"));
				luser.setRoles(roles);
				
				userDao.save(luser);
				
				User uuser = userDao.getObject(uniqueUser1.getId());
				
				Assert.assertEquals(luser.getId(), uuser.getId());
				Assert.assertEquals(luser.getPassword(), uuser.getPassword());
				Assert.assertEquals(luser.getName(), uuser.getName());
				Assert.assertEquals(luser.getAddress1(), uuser.getAddress1());
				Assert.assertEquals(luser.getAddress2(), uuser.getAddress2());
				Assert.assertEquals(luser.getAddress3(), uuser.getAddress3());
				Assert.assertEquals(luser.getCity(), uuser.getCity());
				Assert.assertEquals(luser.getState(), uuser.getState());
				Assert.assertEquals(luser.getCountry(), uuser.getCountry());
				Assert.assertEquals(luser.getMobile(), uuser.getMobile());
				Assert.assertEquals(luser.getHomePhone(), uuser.getHomePhone());
				Assert.assertEquals(luser.getEmailAddress(), uuser.getEmailAddress());
				
				List<Role> lRoles = luser.getRoles();
				List<Role> uRoles = uuser.getRoles();
				
				Assert.assertEquals(lRoles.size(), uRoles.size());
				boolean isRolesSame=false;
				for(Role role : uRoles)
				{
					for(Role lrole : lRoles)
					{
						isRolesSame=false;
						if(lrole.getRole().equals(role.getRole()))
						{
							isRolesSame=true;
							break;
						}
					}
					if(!isRolesSame)
						break;
				}
				Assert.assertTrue(isRolesSame);
		}
		catch(Exception ex) {
			Assert.fail();
		}				
	}

	@Test
	public void testDeleteUser() {
		userDao = new UserDaoImpl();
		
		try{
			
				String strIDSave= uniqueUser.getId();
				String newID = strIDSave + "1";
				uniqueUser.setId(newID);
				userDao.create(uniqueUser);
				//restore
				uniqueUser.setId(strIDSave);
				
				User luser = userDao.getObject(newID);
				
				Assert.assertTrue(luser!=null);
				userDao.delete(luser);
				try
				{
					luser = userDao.getObject(newID);	
					Assert.fail();
				}
				catch(NotFoundException ex){
					Assert.assertTrue(true);
				}
		}
		catch(Exception ex) {
			Assert.fail();
		}				
	}
	
	@Test
	public void testSearch() {
		userDao = new UserDaoImpl();
		
		try{
				User lUser = new User();
				lUser.setId("");
				ArrayList<Role> sroles = new ArrayList<Role>();
				sroles.add(new Role("presenter"));
				lUser.setRoles(sroles);
				List<User> sUsers=userDao.searchMatching(lUser);
				
				List<User> users = userDao.loadAll();
				int recs=0;	
				for(User user : users){
						List<Role> roles = user.getRoles();
						for(Role role : roles){
							if(role.getRole().equals("presenter"))
								recs++;
						}
				}
				Assert.assertEquals(sUsers.size(),recs);
		}
		catch(Exception ex) {
			Assert.fail();
		}				
	}	
	
}

