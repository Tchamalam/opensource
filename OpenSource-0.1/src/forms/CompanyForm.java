package forms;

import java.util.Scanner;

import domainLayer.Company;
import domainLayer.CheckPreconditions;
import repositories.Repository;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CompanyForm implements Form<Company> {
	
	private Repository<Company> companyRepository;
	private Scanner scanner;

	public CompanyForm(Repository<Company> companyRepository, Scanner scanner) {
		this.companyRepository = companyRepository;
		this.scanner = scanner;
	}
	
	@Override
	public Company form() {
		
		System.out.println("\n***************** Company Registration From *****************\n");
				
		System.out.println("What's the name of your company ?\n");
		String name = getUsername();
		
		System.out.println("What's the type of your company?\n");
		String type = scanner.nextLine();
		
		System.out.println("Enter your company email please: \n");
		String email = getEmail();
		
		System.out.println("Write a bit about your company: \n");
		String about = scanner.nextLine();
		
		String[][] requirements = getRequirements();
		
		return new Company(name, email, type, about, requirements);
	}
	
	private String[][] getRequirements() {
		System.out.println("Enter the type of the requirement: \"end\" to quit. "
				+ "\n Beware that you can enter at most 20 requirements.\n");
		String[][] requirements = new String[20][2];
		String input;
		String type = "";
		int i = 0;
		while(!type.equals("end") && i < 20) {
			System.out.println("The type or name of the requirement:\n");
			type = scanner.nextLine();
			if (type.equals("end")) 
				break;
			requirements[i][0] = type;
			System.out.println("What is the requirement for " + type + "?");
			input = scanner.nextLine();
			requirements[i][1] = input;
			i++;
		}
		if (i == 20) {
			System.out.println("You have entered all the 20 requirements!");
		}
		return requirements;
	}
	
	private String getUsername() {
		String username = scanner.nextLine();
		try {
			while(!CheckPreconditions.isCompanyNameAvailable(username, companyRepository)) {
				System.out.println(username + " already taken. Retry a new one: ");
				username = scanner.nextLine();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	
}
