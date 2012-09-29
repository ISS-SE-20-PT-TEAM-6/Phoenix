package sg.edu.nus.iss.phoenix.user.exceptions;

@SuppressWarnings("serial")
public class DAOException extends Exception {
	public DAOException() 
	{
			super();
		}

		public DAOException(String message) 
	{
			super(message);
		}

		public DAOException(Throwable cause) 
	{
			super(cause);
		}

		public DAOException(String message, Throwable cause) 
	{
			super(message, cause);
		}

}
