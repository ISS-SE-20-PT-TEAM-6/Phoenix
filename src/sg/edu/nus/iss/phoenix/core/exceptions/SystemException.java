package sg.edu.nus.iss.phoenix.core.exceptions;

public class SystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4295564610865303183L;
	
	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable t) {
		super(t);
	}
}
