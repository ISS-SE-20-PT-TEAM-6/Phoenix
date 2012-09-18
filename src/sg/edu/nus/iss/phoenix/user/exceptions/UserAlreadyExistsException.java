package sg.edu.nus.iss.phoenix.user.exceptions;

public class UserAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9077590954639505730L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
