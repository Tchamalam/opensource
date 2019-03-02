package domainLayer;

import java.util.Scanner;

import forms.LoginForm;
import repositories.Repository;

/**
 * Implementation of Login for Candidate's
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class LoginCandidate implements Login<Candidate> {
	private Repository<Candidate> candidateRepository;
	private LoginForm loginForm;
	
	public LoginCandidate(Repository<Candidate> candidateRepository) {
		this.candidateRepository = candidateRepository;
		this.loginForm = new LoginForm(new Scanner(System.in));
	}
	
	@Override
	public Candidate login() {
		boolean isLoggedIn = false, passwordReset = false;
		int counter = 0;
		String[] form = loginForm.form();
		String username = form[0];
		String password = form[1];
		Candidate candidate = candidateGetter(username);
		while(counter < 10 && !isLoggedIn) {
			if (candidate != null && password.equals(candidate.getUser().getPassword())) {
				LoginHelper.logIn(candidate.getUser());
				isLoggedIn = true;
				break;
				
			} else if(candidate == null) {
				System.out.println("There is no candidate with the name :" + username);
				form = loginForm.form();
				username = form[0];
				password = form[1];
				candidate = candidateGetter(username);
			} else {
				boolean[] result = loginHelper(password, candidate, isLoggedIn, passwordReset);
				if (result[1]) {
					break;
				}
				if (result[0]) {
					System.out.println("Your password has successfully been reset. Login now!");
					form = loginForm.form();
					username = form[0];
					password = form[1];
					candidate = candidateGetter(username);
										
					passwordReset = false;
				}
			}
			counter++;
		}
		if (!isLoggedIn && counter == 10) {
			System.out.println("You have tried 10 times with wrong input. Try 20 minutes later please!");
		}
		return candidate;
	}
	
	@Override
	public void logout(Candidate candidate) {
		LoginHelper.logOut(candidate.getUser());
		saveCandidate(candidate);
	}
	
	/**
	 * A helper method to be called when the entered password is wrong.
	 * To let the user either reenter the password or resest it.
	 * @param password The password entered.
	 * @param candidate The candidate whose being logged in.
	 */
	private boolean[] loginHelper(String password, Candidate candidate, boolean isLoggedIn, boolean passwordReset) {
		System.out.println("Wrong password or username");
		if (loginForm.isPasswordForgotten()) {
			candidate = resetPassword(candidate);
			saveCandidate(candidate);
			passwordReset = true;
		} else {
			password = loginForm.passwordForm();
			if (candidate.getUser().getPassword().equals(password)) {
				LoginHelper.logIn(candidate.getUser());
				isLoggedIn = true;
			}
		}
		boolean[] result = { passwordReset, isLoggedIn };
		return result;
	}
	

	/**
	 * A helper method to change the password of the candidate.
	 * Not that the password is taken form the user by calling the login form method reponsible for that.
	 * @param candidate the candidate whose password is to be resetted.
	 */
	private Candidate resetPassword(Candidate candidate) {
		String newPassword = loginForm.getNewPassword();
		candidate.getUser().setPassword(newPassword);
		return candidate;
	}
	
	private Candidate candidateGetter(String username) {
		try {
			Candidate candidate = candidateRepository.get(username);
			return candidate;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private void saveCandidate(Candidate candidate) {
		try {
			candidateRepository.update(candidate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
