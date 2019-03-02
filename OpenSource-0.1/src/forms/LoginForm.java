package forms;

import java.util.Scanner;

/**
 * All forms needed when logging a candidate, admin or company in
 * are provided by this very class.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class LoginForm {
	
	private Scanner scanner;
	
	public LoginForm(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public String[] form() {
		String[] loginCredentials = new String[2];
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		loginCredentials[0] = username;
		loginCredentials[1] = password;
		return loginCredentials;
	}

	/**
	 * Takes the password from the user when the given one is wrong.
	 * @return The String value of the entered password. */
	public String passwordForm() {
		System.out.println("Re-enter the password");
		return scanner.nextLine();
	}
	
	/**
	 * Asks if the password was forgotten or not.
	 * @return <tt>true</tt> if the user enter y or Y.
	 * Note that the given input is converted to lower case.
	 */
	public boolean isPasswordForgotten() {
		System.out.println("Did you forget your password? y/n");
		String input = scanner.nextLine();
		if (input.toLowerCase().equals("y")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Takes a new password when user forgets their password.
	 * @return The String value of the new password entered by the user.
	 */
	public String getNewPassword() {
		String newPassword = null;
		while(newPassword == null) {
			System.out.println("Enter your new password: ");
			newPassword = scanner.nextLine();
		}
		return newPassword;
	}

}
