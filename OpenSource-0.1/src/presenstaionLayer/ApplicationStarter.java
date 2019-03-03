package presenstaionLayer;

import java.time.LocalDate;
import java.util.Scanner;

import controllers.*;
import dataAccessLayer.ReaderHelper;
import dataAccessLayer.UserReader;
import domainLayer.Candidate;
import domainLayer.CheckPreconditions;
import domainLayer.HR;
import domainLayer.User;
import domainLayer.UserType;

/**
 * This class is responsible for starting the Application.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class ApplicationStarter {
	
	private static Scanner scanner = new Scanner(System.in);
	private static AdminController controller = new AdminController(getAdmin());

	/**
	 * Start the application.
	 */
	public static int start() {
		int operationType = takeUserType();
		int action = 0;
		String name = "";
		if (operationType == 1) {
			User admin = controller.logAdminIn();
			while (true) {
				action = adminMenu();
				if (action == 1) {
					controller.viewAdminProfile(admin.getName());
				} else if (action == 2) {
					controller.logAdminOut(admin);
					System.out.println("It was nice seeing you around " + admin.getName() 
							+ "\nSee you soon!\n");
					break;
				} else {
					System.out.println("Unknown Operation\n");
				}
			}
			
		} else if (operationType == 2) {
			Candidate candidate = logOrRegisterCandidate();
			if (candidate == null) {
				System.out.println("We are sorry. No candidate found!");
			}
			while (true) {
				action = candidateActions(candidate, action, name);
				if (action == 12) {
					System.out.println("It was nice seeing you around " + candidate.getUser().getName() 
							+ "\nSee you soon!\n");
					break;
				}
			}
		} else if (operationType == 3){
			HR hr = logOrRegisterHR();
			if (hr == null) {
				System.out.println("We are sorry. No HR found!");
			}
			while (true) {
				action = HRActions(hr, action, name);
				if (action == 10) {
					System.out.println("It was nice seeing you around " + hr.getUser().getName() 
							+ " \nSee you soon!\n");
					break;
				}
			}
		} else if (operationType == 4) {
			System.out.println("\nBye Bye...\n");
		} else {
			System.out.println("Unknow operation");
		}
		return operationType;
			
	}
	
	private static int HRActions(HR hr, int action, String name) {
		HRController hrController = new HRController(controller, hr);
		
		action = HRMenu();
		switch(action) {
		case 1:
			hrController.viewMyProfile();
			break;
		case 2:
			LocalDate date = getDateToScheduleInterview();
			name = getNameOfCandidateToScheduleInterview();
			hrController.scheduleInterview(date, name);
			break;
		case 3:
			name = getNameOfCandidateToViewInformation();
			hrController.viewCandidateInformation(name);
			break;
		case 4:
			name = getNameOfCandidateToViewInformation();
			hrController.viewCandidateResume(name);
			break;
		case 5:
			hrController.viewInterviews();
			break;
		case 6:
			name = getNameOfCandidateToViewInformation();
			hrController.viewResumeOfApplicant(name);
			break;
		case 7:
			hrController.viewInterviewsOf(getDateToShowInterviews());
			break;
		case 8:
			hrController.viewMyMessages();
			break;
		case 9: 
			hrController.viewInterviewPortal();
			break;
		case 10:
			hrController.logMeOut();
			break;
		default:
			System.out.println("Unknown Operation");
		} 
		return action;
	}
	
	private static int candidateActions(Candidate candidate, int action, String name) {
		CandidateController candidateController = new CandidateController(controller, candidate);

		action = candidateMenu();
		switch(action) {
		case 1:
			candidateController.viewMyProfile();
			break;
		case 2:
			candidateController.viewMyMessages();
			break;
		case 3:
			candidateController.updateMyResume();
			break;
		case 4:
			candidateController.viewMyResume();
			break;
		case 5:
			name = getNameOfCompanyForRequirements();
			candidateController.viewCompanyRequirements(name);
			break;
		case 6:
			candidateController.viewMyInterviews();
			break;
		case 7:
			candidateController.viewInterviewsOf(getDateToShowInterviews());
			break;
		case 8:
			candidateController.viewHROf(getNameOfCompanyForHR());
			break;
		case 9:
			candidateController.viewJobsRecommendedForMe();
			break;
		case 10:
			name = getNameOfCompanyToApplyTo();
			candidateController.applyTo(name);
			break;
		case 11: 
			candidateController.viewMyApplications();
			break;
		case 12:
			candidateController.logMeOut();
			break;
		default:
			System.out.println("Unknow Operation\n");
		}
		return action;
	}
	
	//------------------------------Log-Register Helper---------------------------------\\
	
	private static Candidate logOrRegisterCandidate() {
		int logInOrRegister = logInOrRegisterGetter();
		Candidate candidate = null;
		if (logInOrRegister == 1) {
			candidate = controller.logCandidateIn();
		} else {
			controller.registerCandidate();
			candidate = controller.logCandidateIn();
		}
		return candidate;
	}
	
	private static HR logOrRegisterHR() {
		int logInOrRegister = logInOrRegisterGetter();
		HR hr = null;
		if (logInOrRegister == 1) {
			hr = controller.logHRIn();
		} else {
			controller.registerHR();
			hr = controller.logHRIn();
		}
		return hr;
	}
	
	//---------------------------------------Menus---------------------------------------\\
	private static int candidateMenu() {
		System.out.println("\n ***************** Candidate Menu *****************\n");
		candidateOptionGetter();
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputInteger(input)) {
			System.out.println("Invalid input: You need to enter a number for an operation\n");
			candidateOptionGetter();
			input = scanner.nextLine();
		}
		
		return Integer.valueOf(input);
	}
	
	private static void candidateOptionGetter() {
		System.out.println("1. To view your profile");
		System.out.println("2. To view your messages");
		System.out.println("3. To update your resume");
		System.out.println("4. To view your resume");
		System.out.println("5. To view the requirements of a company");
		System.out.println("6. To view all your interviews");
		System.out.println("7. To view the interviews of a given date");
		System.out.println("8. To view the Human Resources of a company");
		System.out.println("9. To view the Jobs recommended for you");
		System.out.println("10.To apply to a company");
		System.out.println("11.To view all your applications");
		System.out.println("12.To Securely log out");
	}
	
	private static int HRMenu() {
		System.out.println("\n ********************* HR Menu **********************\n");
		HROptionGetter();
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputInteger(input)) {
			System.out.println("Invalid input. You need to enter a number for an operation\n");
			HROptionGetter();
			input = scanner.nextLine();
		}
		
		return Integer.valueOf(input);
	}
	
	private static void HROptionGetter() {
		System.out.println("1. To see your profile");
		System.out.println("2. To schedule a candidate");
		System.out.println("3. To view a candidate's information");
		System.out.println("4. To view the resume of a candidate");
		System.out.println("5. To view the interviews");
		System.out.println("6. To view the informantion of an applicant");
		System.out.println("7. To view the interviews of a given date");
		System.out.println("8. To view all messages");
		System.out.println("9. To view the Interview Portal");
		System.out.println("10.To Securely log out");
	}
	
	private static int adminMenu() {
		System.out.println("\n ********************* Admin Menu **********************\n");
		 adminOptionGetter();
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputInteger(input)) {
			System.out.println("Invalid input. You need to enter the number of an option\n");
			 adminOptionGetter();
			input = scanner.nextLine();
		}
		return Integer.valueOf(input);
	}
	
	private static void adminOptionGetter() {
		System.out.println("1. To view your profile");
		System.out.println("2. To log securely out");
	}
	
	//-----------------------------------Getter - Helpers------------------------------------------\\
	private static String getNameOfCompanyForRequirements() {
		System.out.println("Enter the name of the company to see its requirements");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputString(input)) {
			System.out.println("Invalid input. You need to enter a name with at least 2 characters");
			input = scanner.nextLine();
		}
		return input;
	}
	
	private static String getNameOfCompanyToApplyTo() {
		System.out.println("Enter the name of the company you'd like to apply to:");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputString(input)) {
			System.out.println("Invalid input: You need to enter the name of the name of the company");
			input = scanner.nextLine();
		}
		return input;
	}
	
	private static String getNameOfCompanyForHR() {
		System.out.println("Enter the name of the company to see its HR.");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputString(input)) {
			System.out.println("Invalid input. You need to enter a name with at least 2 characters");
			input = scanner.nextLine();
		}
		return input;
	}
	
	private static String getNameOfCandidateToScheduleInterview() {
		System.out.println("Enter the name of the candidate you'd like to schedule an interview for");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputString(input)) {
			System.out.println("Invalid input. You need to enter a name with at least 2 characters");
			input = scanner.nextLine();
		}
		return input;
	}
	
	private static String getNameOfCandidateToViewInformation() {
		System.out.println("Enter the name of the candidate whose information you want");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputString(input)) {
			System.out.println("Invalid input. You need to enter a name with at least 2 characters");
			input = scanner.nextLine();
		}
		return input;
	}
	
	private static LocalDate getDateToScheduleInterview() {
		System.out.println("Enter the date you'd like to schedule the interview\n"
				+ "Caution: Format for date yyyy-mm-dd");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputDate(input)) {
			System.out.println("Invalid input. Enter the date in the format: yyyy-mm-dd");
			input = scanner.nextLine();
		}
		return ReaderHelper.stringToLocalDate(input);
	}
	
	private static LocalDate getDateToShowInterviews() {
		System.out.println("Enter the date to see the interviews scheduled on the date"
				+ "Caution: Format for date yyyy-mm-dd");
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputDate(input)) {
			System.out.println("Invalid input. Enter the date in the format: yyyy-mm-dd");
			scanner.nextLine();
		}
		return ReaderHelper.stringToLocalDate(input);
	}
	
	private static int takeUserType() {
		System.out.println("What would you like to operate as? Enter the corresponding number:\n");
		userTypeGetter();
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputInteger(input)) {
			System.out.println("Invalid input: You need to enter the number of an option\n");
			System.out.println("What would like to operate as? Enter the corresponding number:\n");
			userTypeGetter();
			input = scanner.nextLine();
		}
		return Integer.valueOf(input);
	}
	
	private static void userTypeGetter() {
		System.out.println("1. Admin\n"
				+ "2. Canidate \n"
				+ "3. HumanResources\n"
				+ "4. To Quit the app\n");
	}
	
	private static void logInOrRegisterOPtionGetter() {
		System.out.println("1. Login");
		System.out.println("2. Register\n");
	}
	private static int logInOrRegisterGetter() {
		System.out.println("\n\nSelect one of the options below by entering its number.\n");
		logInOrRegisterOPtionGetter();
		String input = scanner.nextLine();
		while (!CheckPreconditions.isInputInteger(input)) {
			System.out.println("Invlid input. You need to enter the number of an operation.");
			logInOrRegisterOPtionGetter();
			input = scanner.nextLine();
		}
		return Integer.parseInt(input);
	}
	
	/**
	 * A helper method to provide the admin user for the AdminController.
	 * 
	 * @return one User whose type is admin.
	 */
	private static User getAdmin() {
		UserReader reader = new UserReader();
		User admin = null;
		for (User user : reader.fetchAll()) {
			if (user.getUserType().equals(UserType.ADMIN)) {
				admin = user;
				break;
			}
		}
		return admin;
	}

}
