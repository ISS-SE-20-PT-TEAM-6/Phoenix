package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.core.exceptions.SystemException;
import sg.edu.nus.iss.phoenix.user.dao.RoleDao;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.exceptions.UserAlreadyExistsException;
import sg.edu.nus.iss.phoenix.user.exceptions.UserNotFoundException;

/**
 * User Business Service
 * 
 * @author AJ
 *
 */
public class UserService {
	DAOFactoryImpl factory;
	UserDao udao;
	RoleDao roleDao;
	Logger log = Logger.getLogger(getClass().getName());
	
	public UserService() {
		factory = new DAOFactoryImpl();
		udao = factory.getUserDAO();
		roleDao = factory.getRoleDAO();
	}
	
	/**
	 * Get user with the given user id
	 * 
	 * @param userId	User id of the user that needs to be found
	 * @return
	 * @throws UserNotFoundException	If the given user is not found in the system
	 */
	public User getUser(String userId) throws UserNotFoundException {
		try {
			return udao.getObject(userId);
		} catch (SQLException e) {
			log.severe("Error occured while getting all users :"+ e.getMessage());
			throw new SystemException(e);
		} catch (NotFoundException e) {
			throw new UserNotFoundException("User not found in the system");
		}
	}
	
	/**
	 * Create user with the given data
	 * 
	 * @param user	Data of the given user
	 * @throws UserAlreadyExistsException	If a user with the given id already exists in the system
	 */
	public void createUser(User user) throws UserAlreadyExistsException {
		try {
			try {
				User existingUser = udao.getObject(user.getId());
				if(existingUser != null) {
					throw new UserAlreadyExistsException("User already exists in the system");
				}
			} catch (NotFoundException e) {
				//This means User not found. Then we are good.
			}
			udao.create(user);
		} catch (SQLException e) {
			log.severe("Error occured while getting all users :"+ e.getMessage());
			throw new SystemException(e);
		}
	}
	
	/**
	 * Modify user with given user data
	 * 
	 * @param user	Data of the modified user
	 * @throws UserNotFoundException
	 */
	public void modifyUser(User user) throws UserNotFoundException {
		try {
			udao.save(user);
		} catch (SQLException e) {
			log.severe("Error occured while getting all users :"+ e.getMessage());
			throw new SystemException(e);
		} catch (NotFoundException e) {
			throw new UserNotFoundException("User not found in the system");
		}
	}
	
	/**
	 * Delete the given user from the system
	 * 
	 * @param userId	User id of the user that needs to be deleted from the system
	 * @throws UserNotFoundException
	 */
	public void deleteUser(User user) throws UserNotFoundException{
		try {
			udao.delete(user);
		} catch (SQLException e) {
			log.severe("Error occured while getting all users :"+ e.getMessage());
			throw new SystemException(e);
		} catch (NotFoundException e) {
			throw new UserNotFoundException("User not found in the system");
		}
	}

	/**
	 * Get all the list of users in the system
	 * 
	 * @return	List of users
	 */
	public List<User> getAllUsers() {
		try {
			return udao.loadAll();
		} catch (SQLException e) {
			log.severe("Error occured while getting all users :"+ e.getMessage());
			throw new SystemException(e);
		}
	}

	/**
	 * Get all the list of roles available in the system
	 * 
	 * @return	List of roles
	 */
	public List<Role> getAllRoles() {
		try {
			return roleDao.loadAll();
		} catch (SQLException e) {
			log.severe("Error occured while getting all roles :"+ e.getMessage());
			throw new SystemException(e);
		}
	}
}
