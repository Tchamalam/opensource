package forms;

import java.util.Scanner;

import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CandidateForm implements Form<Candidate> {
	
	private UserForm userForm;
	private Scanner scanner;
	
	public CandidateForm(UserForm userForm, Scanner scanner) {
		this.userForm = userForm;
		this.scanner = scanner;
	}
	
	@Override
	public Candidate form() {
		System.out.println("\n **************** Candidate Registration Form *******************\n");
		User user = userForm.form(UserType.CANDIDATE);
		CandidateStatus status = CandidateStatus.REGISTERED;
		Resume resume = ResumeForm.form(user, scanner);

		System.out.println("Have you got any work experience? y/n");
		String input = scanner.nextLine();
		CandidateType candidateType = specifyCandidateType(input);
		
		return new Candidate(user, candidateType, status, resume);
	}
	
	private CandidateType specifyCandidateType(String input) {
		if (input.toLowerCase().equals("y")) {
			return CandidateType.EXPERIENCED;
		} 
		return CandidateType.FRESH;
	}
}
