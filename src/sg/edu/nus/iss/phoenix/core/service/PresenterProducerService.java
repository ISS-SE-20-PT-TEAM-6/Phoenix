package sg.edu.nus.iss.phoenix.core.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;

import sg.edu.nus.iss.phoenix.user.dao.RoleDao;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.User;

public class PresenterProducerService {
	private DAOFactoryImpl factory;
	
	private UserDao userDao;
	private RoleDao roleDao;
	
	public PresenterProducerService(){
		factory=new DAOFactoryImpl();
		userDao= (UserDao) factory.getUserDAO();
		roleDao=(RoleDao) factory.getRoleDAO();
		
	}
	public List <User> searchPresenterProducer()throws ParseException, SQLException {
			return userDao.loadAll();
		
	}
	
}
