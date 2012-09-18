package sg.edu.nus.iss.phoenix.authenticate.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.dao.RoleDao;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

public class AuthenticateService {
	DAOFactoryImpl factory;
	UserDao udao;
	RoleDao rdao;

	public AuthenticateService() {
		super();
		// TODO Auto-generated constructor stub
		factory = new DAOFactoryImpl();
		udao = factory.getUserDAO();
		rdao = factory.getRoleDAO();

	}

	public User validateUserIdPassword(User user) {
		ArrayList<User> userList = new ArrayList<User>();
		try {
			userList = (ArrayList<User>) udao.loadAll();

			for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
				User user1 = (User) iterator.next();
				if (user.getId().equalsIgnoreCase(user1.getId()) && user.getPassword().equalsIgnoreCase(user1.getPassword()) ) {
					return user1;
				}
				//System.out.println(user1.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public User evaluateAccessPreviledge(User user) {
		try {
			Role role = rdao.getObject(user.getRoles().get(0).getRole());
			//user.setAccessPrivilege(role.getAccessPrivilege());
			return user;
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
