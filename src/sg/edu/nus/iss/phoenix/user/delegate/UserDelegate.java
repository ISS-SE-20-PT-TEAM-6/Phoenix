package sg.edu.nus.iss.phoenix.user.delegate;

import java.util.List;

import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.exceptions.UserAlreadyExistsException;
import sg.edu.nus.iss.phoenix.user.exceptions.UserNotFoundException;
import sg.edu.nus.iss.phoenix.user.service.UserService;

public class UserDelegate {
	UserService service;
	
	public UserDelegate() {
		service = new UserService();
	}
	
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}
	
	public User getUser(String userId) throws UserNotFoundException {
		return service.getUser(userId);
	}

	public void createUser(User user) throws UserAlreadyExistsException {
		service.createUser(user);
	}
	
	public void modifyUser(User user) throws UserAlreadyExistsException, UserNotFoundException {
		service.modifyUser(user);
	}


}
