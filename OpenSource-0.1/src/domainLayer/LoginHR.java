package domainLayer;

import java.util.Scanner;

import forms.LoginForm;
import repositories.Repository;

/**
 * Implements Login interface for HR.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class LoginHR implements Login<HR> {
	
	private Repository<HR> hRRepository;
	private LoginForm loginForm;
	
	public LoginHR(Repository<HR> hRRepository) {
		this.hRRepository = hRRepository;
		this.loginForm = new LoginForm(new Scanner(System.in));
	}

	@Override
	public HR login() {
		int counter = 0;
		boolean isLoggedIn = false, passwordReset = false;
		String[] form = loginForm.form();
		String username = form[0];
		String password = form[1];
		
		HR hR = getHR(username);
		
		while(counter < 10 && !isLoggedIn) {
			if (hR == null) {
				System.out.println("There is no user with the name: " + username);
				form = loginForm.form();
				username = form[0];
				password = form[1];
				hR = getHR(username);
			} else if (hR.getUser().getPassword().equals(password)) {
					LoginHelper.logIn(hR.getUser());
					isLoggedIn = true;
					break;
			} else {
				boolean[] result = loginHelper(password, hR, isLoggedIn, passwordReset);
				if (result[0]) {
					break;
				} 
				if (result[1]) {
					form = loginForm.form();
					username = form[0];
					password = form[1];

					hR = getHR(username);
										
					passwordReset = false;
				}
			}
			counter++;
		
		}
		return hR;
	}
	
	@Override
	public void logout(HR hR) {
		LoginHelper.logOut(hR.getUser());
		saveHR(hR);
	}
	
	/**
	 * To be called in case the HR forgets their password or reenter one if the entered one was wrong.
	 * @param password The entered password.
	 * @param hR the HR trying to login.
	 */
	private boolean[] loginHelper(String password, HR hR, boolean isLoggedIn, boolean passwordReset) {
		System.out.println("Wrong password or username");
		if (loginForm.isPasswordForgotten()) {
			resetPassword(hR);
			saveHR(hR);
			passwordReset = true;
		} else {
			password = loginForm.passwordForm();
			if (hR.getUser().getPassword().equals(password)) {
				LoginHelper.logIn(hR.getUser());
				isLoggedIn = true;
			}
		}
		boolean[] result = { isLoggedIn, passwordReset };
		return result;
	}
	
	/**
	 * A helper method to change the password of the HR.
	 * Not that the password is taken form the HR by calling the login form method reponsible for that.
	 * @param HR the HR whose password is to be resetted.
	 */
	private void resetPassword(HR hR) {
		String newPassword = loginForm.getNewPassword();
		hR.getUser().setPassword(newPassword);
	}
	
	private HR getHR(String username) {
		try {
			HR hR = hRRepository.get(username);
			return hR;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private void saveHR(HR hR) {
		try {
			hRRepository.update(hR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
 }
