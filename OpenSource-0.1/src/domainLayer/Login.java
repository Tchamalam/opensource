package domainLayer;

/**
 * The interface defining all the methods during login and logouts.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 * @param <T> The type of user logging in.
 */
public interface Login<T> {
	
	/**
	 * Logs a candidate, an HR, or an Admin to the system.
	 * With their LogState changed to LogState.LOGGED_OUT.
	 * 
	 * @return The object of the user loggin in.
	 */
	public T login();
	
	/**
	 * Given a user, the latter is logged out.
	 * With their LogState changed to LogState.LOGGED_OUT.
	 * 
	 * @param element The user to be logged out.
	 */
	public void logout(T user);
	
}
