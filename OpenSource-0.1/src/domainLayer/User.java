package domainLayer;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class User {
	
	private String name;
	private String ID;
	private String email;
	private int age;
	private UserType type;
	private String password;
	private LogState logState;
	
	public User() {
		
	}
	
	public User(User user) {
		this(user.name, user.ID, user.email, user.age, user.type, user.password, user.logState);
	}
	
	public User(String name, String ID, String email, int age, UserType type, String password, LogState logState) {
		setName(name);
		setID(ID);
		setEmail(email);
		setAge(age);
		setType(type);
		setPassword(password);
		setLogState(logState);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name.length() > 100 || name == null) {
			throw new IllegalArgumentException("Invalid input for name");
		}
		else {
			this.name = name;
		}
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		if (ID == null) {
			throw new IllegalArgumentException("Invalid input for ID");
		}
		this.ID = ID;
	}
	private void setEmail(String email) {
		if (!email.contains("@")) {
			throw new IllegalArgumentException("Enter a valid email please");
		}
		this.email = email;
	}
	
	public int getAge() {
		return age;
	}
	
	private void setAge(int age) {
		this.age = age;
	}
	public UserType getUserType() {
		return type;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	private void setType(UserType type) {
		this.type = type;
	}
	
	public LogState getLogState() {
		return logState;
	}
	
	public void setLogState(LogState logState) {
		this.logState = logState;
	}
	
	/** The password should never be seen by other users, companies or HRs.
	 *  So not included in toString() */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n Name: " + name 
				+ "\n Age: " + age 
				+ "\n Email: " + email);
		if (type.equals(UserType.HR)) {
			sb.append("\n      Human Resources");
		} else sb.append("\n         " + type.getValue());
		
		return sb.toString();
	}
	
} // end User.
