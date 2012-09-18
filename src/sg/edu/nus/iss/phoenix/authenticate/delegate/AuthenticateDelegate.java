package sg.edu.nus.iss.phoenix.authenticate.delegate;

import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateService;
import sg.edu.nus.iss.phoenix.user.entity.User;

public class AuthenticateDelegate {
	private AuthenticateService service;

	public AuthenticateDelegate() {
		super();
		service = new AuthenticateService();
	}

	public User validateUserIdPassword(User user) {
		return service.validateUserIdPassword(user);
	}

	public User evaluateAccessPreviledge(User user) {
		return service.evaluateAccessPreviledge(user);
	}
	
}
