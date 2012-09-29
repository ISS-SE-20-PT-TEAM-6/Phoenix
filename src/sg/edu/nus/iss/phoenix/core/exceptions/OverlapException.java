package sg.edu.nus.iss.phoenix.core.exceptions;

/**
 * OverlapException exception. This exception will be thrown from if 
 * there is existing schedule which is overlap with the new or modified schedule.
 **/

public class OverlapException extends Exception {
	/**
	 * For eclipse based unique identity
	 */
	private static final long serialVersionUID = -8937329631346507675L;

	/**
	 * Constructor for OverlapException. The input message is returned in
	 * toString() message.
	 */
	public OverlapException(String msg) {
		super(msg);
	}

}
