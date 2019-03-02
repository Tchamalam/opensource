package forms;

import java.util.Scanner;

import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class HRForm implements Form<HR> {
	
	private UserForm userForm;
	private Scanner scanner;
	
	public HRForm(UserForm userForm, Scanner scanner) {
		this.userForm = userForm;
		this.scanner = scanner;
	}
	
	@Override
	public HR form() {
		
		System.out.println("************* Registration form for Human Resources *************\n");
		
		User user = userForm.form(UserType.HR);
		String companyName = getCompanyName();
		System.out.println(companyName);
		HR hr = new HR(user, companyName);
		return hr;
	}
	
	//------------------------------Helper Methods-------------------------------------\\
	
	private String getCompanyName() {
		System.out.println("What is the name of the company?");
		return  scanner.nextLine();
	}
	
}
