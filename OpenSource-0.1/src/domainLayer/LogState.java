package domainLayer;

/**
 * The possible types of Log state of any user. LoggedIn or LoggedOut.
 * This class is aimed at enforcing the states so that nothing irrelevant get entered.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public enum LogState {
	
	LOGGED_IN("loggedIn"),
	LOGGED_OUT("loggedOut");
	
	private String value;
	
	LogState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
