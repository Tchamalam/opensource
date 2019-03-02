package domainLayer;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import repositories.Repository;

/**
 * CheckPreconditions is a class with static methods to be called to ensure
 * inputs are the ones expected.
 * Such as for registering an HR, checks if it is the admin that wants to do that and so on.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CheckPreconditions {

	/**
	 * To be called when a candidate, an HR, a company's information are taken for registration.
	 * As the usernames are unique, if the entered username was already entered by another user, a different name should be required.
	 * @param username The username whose availability is being checked.
	 * @param userRepository The repository to check in the database whether the name is already used.
	 * @return true if available the is not used yet and false otherwise.
	 */
	public static  boolean isUsernameAvailable(String username, Repository<User> userRepository) throws Exception {
		for (User user : userRepository.fetchAll()) {
			if (user.getName().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isCompanyNameAvailable(String companyName, Repository<Company> companyRepository) throws Exception {
		for (Company company : companyRepository.fetchAll()) {
			if (company.getName().equals(companyName)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the given email has an '@' in it.
	 * @param email The email to be checked.
	 * @return true if email contains '@' and false otherwise.
	 */
	public static boolean isEmail(String email) {
		String afterAt = email.substring(email.lastIndexOf("@") + 1);
		return email.contains("@") && afterAt.contains(".");
	}
	
	/**
	 * See if the age of the user is between the range. [18, 60].
	 * @param age The age to be checked.
	 * @return true if the age is in the range and false otherwise.
	 */
	public static boolean isAgeValid(int age) {
		return (age >= 18 && age <= 60);
	}
	
	/**
	 * Ensure that the given user is an HR.
	 * @param user The user to be checked if he/she is an HR.
	 */
	public static void ensureIsHR(User user) {
		if (!user.getUserType().equals(UserType.HR)) {
			throw new IllegalArgumentException("Dear user, the type of the user should be company");
		}
	} 
	
	/**
	 * Ensure that the given user is a candidate.
	 * @param user The user to be checked.
	 */
	public static void ensureIsCandidate(User user) {
		if (!user.getUserType().equals(UserType.CANDIDATE)) {
			throw new IllegalArgumentException("Dear user, to carry out this operation, the user type should be candidate");
		}
	}
	
	/**
	 * Enusre that the given user is an Admin.
	 * So to make sure that no user apart form the admin does admin-allowed actions.
	 * @param user The user to be checked.
	 */
	public static void ensureIsAdmin(User user) {
		if (!user.getUserType().equals(UserType.ADMIN)) {
			throw new IllegalArgumentException("Dear user, to carry out this operation you need to be admin");
		}
	}
 
	public static boolean isInputInteger(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isInputString(String input) {
		return input.length() > 1;
	}
	
	public static boolean isInputChar(String input) {
		return input.length() == 1;
	}
	
	public static boolean isInputDate(String input) {
		StringTokenizer stringTokenizer = new StringTokenizer(input, "-");
		List<String> tokens = new ArrayList<>();
		int counter = 0;
		while (stringTokenizer.hasMoreElements()) {
			tokens.add(stringTokenizer.nextToken());
			counter++;
		}
		return counter == 3;
	}
	
	public static boolean isLoggedIn(User user) {
		return user.getLogState().equals(LogState.LOGGED_IN);
	}
	
}
