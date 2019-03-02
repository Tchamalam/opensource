package domainLayer;

/**
 * LoginHelper class is the one responsible for updating the user
 * when the latter logs in or out of the system.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class LoginHelper {
	
	/**
	 * When as user is logged in, this method is called to update their LogState.
	 * @param user The user being logged in.
	 */
	public static void logIn(User user) {
		if (user.getLogState().equals(LogState.LOGGED_IN)) {
			System.out.println("\nYou are already logged in as " + user.getName());
		} else {
			user.setLogState(LogState.LOGGED_IN);
			System.out.println("\nVery warm welcome " + user.getName()
			+ "\n Have Fun\n");
		}
	}
	
	/**
	 * A method to be called when a user logged out to change their LogState.
	 * @param user The user being logged out.
	 */
	public static void logOut(User user) {
		if (user.getLogState().equals(LogState.LOGGED_OUT)) {
			System.out.println(("You're already logged out"));
		} else {
			user.setLogState(LogState.LOGGED_OUT);
			System.out.println("You just got logged out\n");
		}
	}
	
}
