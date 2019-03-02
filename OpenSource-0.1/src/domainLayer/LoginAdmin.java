package domainLayer;
import java.util.Scanner;

import forms.LoginForm;
import repositories.Repository;

/**
 * Implements the methods of Login interface.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class LoginAdmin implements Login<User> {
	
	private Repository<User> userRepository;
	private LoginForm loginForm;
	
	public LoginAdmin(Repository<User> userRepository) {
		this.userRepository = userRepository;
		this.loginForm = new LoginForm(new Scanner(System.in));
	}
	
	@Override
	public User login() {
		
		int counter = 0;
		boolean isLoggedIn = false, passwordReset = false;
		String[] form = loginForm.form();
		String username = form[0];
		String password = form[1];
		User user = adminGetter(username);
		
		while(counter < 10 && !isLoggedIn) {
			
			if ((user != null) && user.getUserType().equals(UserType.ADMIN) && user.getPassword().equals(password)) {
				LoginHelper.logIn(user);
				isLoggedIn = true;
				break;
			} else if (user == null || !user.getUserType().equals(UserType.ADMIN)) {
				System.out.println("There is no admin with the name: " + username);
				form = loginForm.form();
				username = form[0];
				password = form[1];
				user = adminGetter(username);
			} else {
				
				boolean[] result = loginHelper(password, user, isLoggedIn, passwordReset);
				if (result[1]) 
					break;
				if (result[0]) {
					System.out.println("Your password has successfully been reset! Login now");
					form = loginForm.form();
					username = form[0];
					password = form[1];
					
					user = adminGetter(username);
										
					passwordReset = false;
				}
			}
			counter++;
		}

		if (!isLoggedIn && counter == 10) {
			System.out.println("You have tried 10 times with wrong input. Try 20 minutes later please!");
		}
		return user;
	}
	
	@Override
	public void logout(User user) {
		LoginHelper.logOut(user);
		saveAdmin(user);
		
	}
	
	/**
	 * A helper method to be called when the entered password is wrong.
	 * To let the user either reenter the password or resest it.
	 * @param password The password entered.
	 * @param user The user whose being logged in.
	 */
	private boolean[] loginHelper(String password, User user, boolean isLoggedIn, boolean passwordReset) {
		System.out.println("Wrong password or username");
		if (loginForm.isPasswordForgotten()) {
			user = resetUserPassword(user);
			saveAdmin(user);
			passwordReset = true;
		} else {
			password = loginForm.passwordForm();
			if (user.getPassword().equals(password)) {
				LoginHelper.logIn(user);
				isLoggedIn = true;
			}
		}
		boolean[] result = { passwordReset, isLoggedIn};
		return result;
	}
	
	/**
	 * A helper method to change the password of the Admin.
	 * Not that the password is taken form the Admin by calling the login form method reponsible for that.
	 * @param user the Admin whose password is to be resetted.
	 */
	public User resetUserPassword(User user) {
		String newPassword = loginForm.getNewPassword();
		user.setPassword(newPassword);
		return user;
	}
	
	private User adminGetter(String username) {
		try {
			User user = userRepository.get(username);
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private void saveAdmin(User user) {
		try {
			userRepository.update(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
	
