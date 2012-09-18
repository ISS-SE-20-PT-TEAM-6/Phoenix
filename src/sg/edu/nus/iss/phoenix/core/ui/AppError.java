package sg.edu.nus.iss.phoenix.core.ui;

public class AppError {
	public static final int SUCCESS = 0;
	public static final int INFO = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 3;

	private String code;
	private String message;
	private int type;

	public AppError(String code, String message) {
		this(ERROR, code, message);
	}
	
	public AppError(int type, String code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
