package forms;

import java.util.Scanner;

import domainLayer.*;
import repositories.Repository;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class UserForm {
	
	private Repository<User> userRepository;
	private Scanner scanner;
	
	public UserForm(Repository<User> userRepository, Scanner scanner) {
		this.userRepository = userRepository;
		this.scanner = scanner;
	}
	
	public User form(UserType userType) {
		
		scanner = new Scanner(System.in);
		String username = null, id, password, email;
		int age;
		
		try {
			System.out.println("Enter your username: ");
			username = getUsername();
		} catch(Exception e) {
			
		}
		
		System.out.println("Enter your password:");
		password = scanner.nextLine();
		
		System.out.println("Enter your email address: ");
		email = getEmail();
		
		System.out.println("How old are you " + username + " ?");
		age = getAge();

		id = findProperId(userType);
		
		return new User(username, id, email, age, userType, password, LogState.LOGGED_OUT);
	}
	
	//----------------------------------Helper Methods -------------------------------------\\
	private String getUsername() throws Exception {
		String username = scanner.nextLine();
		while(!CheckPreconditions.isUsernameAvailable(username, userRepository)) {
			System.out.println(username + " already taken. Retry a new one: ");
			username = scanner.nextLine();
		}
		return username;
	}
	
	private String getEmail() {
		String email = scanner.nextLine();
		while (!CheckPreconditions.isEmail(email)) {
			System.out.println("Invalid email. Retry please: ");
			email = scanner.nextLine();
		}
		return email;
	}
	
	private int getAge() {
		int age = scanner.nextInt();
		while (!CheckPreconditions.isAgeValid(age)) {
			System.out.println("To get registered, you should be older than 18 and less older than 60"
					+ "\n Try again: ");
			age = scanner.nextInt();
		}
		return age;
	}
	
	private String findProperId(UserType userType) {
		
		if (userType.equals(UserType.ADMIN))
			return IdGenerator.generateAdminId();
		else if (userType.equals(UserType.CANDIDATE))
			return IdGenerator.generateCandidateId();
		else if (userType.equals(UserType.HR))
			return IdGenerator.generateHRId();
		else 
			throw new IllegalArgumentException("Invalid input for the user type");
	}

}
