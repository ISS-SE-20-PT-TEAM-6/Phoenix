package sg.edu.nus.iss.phoenix.core.dao;

import sg.edu.nus.iss.phoenix.radioprogram.dao.RadioProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.dao.impl.RadioProgramDAOImpl;
import sg.edu.nus.iss.phoenix.user.dao.RoleDao;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.dao.impl.RoleDaoImpl;
import sg.edu.nus.iss.phoenix.user.dao.impl.UserDaoImpl;

public class DAOFactoryImpl {
	private UserDao userDAO = new UserDaoImpl();
	private RoleDao roleDAO = new RoleDaoImpl();
	private RadioProgramDAO rpdao = new RadioProgramDAOImpl();

	public UserDao getUserDAO() {
		// TODO Auto-generated method stub
		return userDAO;
	}

	public RoleDao getRoleDAO() {
		// TODO Auto-generated method stub
		return roleDAO;
	}

	public RadioProgramDAO getRadioProgramDAO() {
		// TODO Auto-generated method stub
		return rpdao;
	}

}
