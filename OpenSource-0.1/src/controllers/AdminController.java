package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dataAccessLayer.*;
import domainLayer.*;
import forms.*;
import repositories.*;

/**
 * This controller is the one responsible for controlling any actions made by all users
 * including candidates, HRs and their companies.
 * It also provides all necessary informations to the latter.
 * 
 * @author Ben-Malik TCHAMALAM
 * Since 2019
 *
 */
public class AdminController extends Controller{
	
	@SuppressWarnings("unused")
	private User user;
	
	private Reader<User> reader;
	
	private Reader<Candidate> candidateReader;
	
	private Reader<HR> hrReader;
	
	private Reader<Company> companyReader;
	
	private Reader<Interview> interviewReader;
	
	private Reader<Application> applicationReader;
	
	private Repository<User> userRepository;
	
	private Repository<Candidate> candidateRepository;
	
	private Repository<HR> hrRepository;
	
	private Repository<Company> companyRepository;
	
	private Repository<Interview> interviewRepository;
	
	private Repository<Application> applicationRepository;
	
	private Scanner scanner;
	
	private CandidateForm candidateForm;
	
	private UserForm userForm;
	
	private CompanyForm companyForm;
	
	private HRForm hrForm;
	
	private Recommendation recommendation;
	
	private Application application;
	
	private Login<Candidate> loginCandidate;
	
	private Login<HR> HRLogin;
	
	private Login<User> loginAdmin;

	public AdminController(User user) {
		/** Ensure the user making use of this controller is an Admin. */
		CheckPreconditions.ensureIsAdmin(user);
		this.user = user;
		this.scanner = new Scanner(System.in);
		this.reader = new UserReader();
		candidateReader = new CandidateReader();
		companyReader = new CompanyReader();
		interviewReader = new InterviewReader();
		hrReader = new HRReader();
		applicationReader = new ApplicationReader();
		
		userRepository = new UserRepository(reader); 
		candidateRepository = new CandidateRepository(user, candidateReader);
		hrRepository = new HRRepository(user, hrReader);
		companyRepository = new CompanyRepository(user, companyReader);
		interviewRepository = new InterviewRepository(user, interviewReader);
		applicationRepository = new ApplicationRepository(user, applicationReader);
		
		userForm = new UserForm(userRepository, scanner);
		candidateForm = new CandidateForm(userForm, scanner);
		companyForm = new CompanyForm(companyRepository, scanner);
		hrForm = new HRForm(userForm, scanner);
		
		loginCandidate = new LoginCandidate(candidateRepository);
		HRLogin = new LoginHR(hrRepository);
		loginAdmin = new LoginAdmin(userRepository);
		
		setRecommendations();
	}
	
	public void viewAdminProfile(String name) {
		System.out.println("\n********************* ADMIN PROFILE **********************");
		
		try {
			User admin = userRepository.get(name);
			System.out.println(admin.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//--------------------------------Messages Senders --------------------------------------\\
	public void sendMessage(HR hR, Message message) throws Exception {
		hR.addToAdminMessages(message);
		hrRepository.update(hR);
	}
	
	public void sendMessage(Candidate candidate, Message message) throws Exception {
		candidate.addToAdminMessages(message);
		candidateRepository.update(candidate);
	}
	
	public void sendResume(Resume resume, HR hR) {
		hR.addToApplicantsResumes(resume);
		try {
			hrRepository.update(hR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//------------------------------Profile Providers------------------------------------------\\
	public String provideCandidateProfile(String name) {
		
		try {
			Candidate candidate = candidateRepository.get(name);
			if (candidate == null) {
				return "Opss! There is no candidate with the name " + name;
			}
			return candidate.toString();
		} catch (Exception e) {
			return "There is no such candidate named: " + name + e.getMessage();
		}
	}
	
	public String provideHRProfile(String name) {
		try {
			HR hr = hrRepository.get(name);
			if (hr == null) {
				return "Opps there is no HR with the name " + name;
			}
			return hr.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public String provideCompanyProfile(String name) {
		try {
			Company company = companyRepository.get(name);
			if (company == null) {
				return "Opss! There is no company with the name " + name;
			}
			return company.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//--------------------------------------Registrars------------------------------------------\\

	public Candidate registerCandidate() {
		Candidate candidate = candidateForm.form();
		
		try {
			candidateRepository.create(candidate);
			System.out.println("\n Congratulations! You have successfully been registered to the system");
			return candidate;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public HR registerHR() {
		
		HR hR = hrForm.form();
		Company company = companyForm.form();

		try {
			hrRepository.create(hR);
			companyRepository.create(company);
			System.out.println("\n Congratulations! You have successfully been registered to the system");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hR;
	}
	
	public User registerAdmin() {
		
		User user = userForm.form(UserType.ADMIN);
		
		try {
			userRepository.create(user);
			System.out.println("\n Congratulations! You have successfully been registered to the system");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return user;
	}
	
	//-------------------------------------Savers---------------------------------------------\\
	public void saveInterview(Interview interview) {
		try {
			interviewRepository.create(interview);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void saveCandidate(Candidate candidate) {
		try {
			candidateRepository.create(candidate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Candidate provideCandidate(String name) {
		try {
			return candidateRepository.get(name);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//----------------------------------Providers ------------------------------------------\\
	public void provideResume(String candidateName) {
		try {
			Candidate candidate = candidateRepository.get(candidateName);
			System.out.println(candidate.getResume().toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<Interview> provideAllInterviews() {
		List<Interview> interviews;
		try {
			interviews = interviewRepository.fetchAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			/** Return an empty list */
			interviews = new ArrayList<>();
		}
		
		return interviews;
	}
	
	public Company provideCompany(String companyName) {
		try {
			return companyRepository.get(companyName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public List<Interview> provideInterviewsOf(String candidateOrCompanyName) {
		candidateOrCompanyName = candidateOrCompanyName.toLowerCase();
		List<Interview> interviews = new ArrayList<>();
		try {
			List<Interview> allInterviews = interviewRepository.fetchAll();
			for (Interview interview : allInterviews) {
				if (interview.getCompanyName().toLowerCase().equals(candidateOrCompanyName) ||
						interview.getCandidateName().toLowerCase().equals(candidateOrCompanyName)) {
					interviews.add(interview);
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return interviews;
	}
	
	public List<Interview> provideInterviewsOf(LocalDate date, String candidateOrCompanyName) {
		List<Interview> interviewsOf = new ArrayList<>();
		List<Interview> allInterviews = provideInterviewsOf(candidateOrCompanyName);
		
		for (Interview interview : allInterviews) {
			if (interview.getDate().equals(date)) {
				interviewsOf.add(interview);
			}
		}
		
		return interviewsOf;
	}
	
	public HR provideHROfCompany(String companyName) {
		String nameLookedFor = companyName.toLowerCase(), currentName;
		HR hR = null;

		try {
			List<HR> allHRs = hrRepository.fetchAll();
			
			for (HR hr : allHRs) {
				currentName = hr.getCompanyName().toLowerCase();
				if (currentName.equals(nameLookedFor)) {
					hR = hr;
					break;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return hR;
	}
	
	//------------------------------------Loggers-In-----------------------------------------\\
	public Candidate logCandidateIn() {
		System.out.println("\n***************Login For Candidate ****************\n");
		return loginCandidate.login();
	}
	
	public HR logHRIn() {
		System.out.println("\n*****************Login For HR ******************\n");
		return HRLogin.login();
	}
	
	public User logAdminIn() {
		System.out.println("\n*****************Login For Admin ******************\n");
		return loginAdmin.login();
	}
	
	//----------------------------------Loggers-Out-----------------------------------------\\
	public void logCandidateOut(Candidate candidate) {
		loginCandidate.logout(candidate);
	}
	
	public void logHROut(HR hR) {
		HRLogin.logout(hR);
	}
	
	public void logAdminOut(User user) {
		loginAdmin.logout(user);
	}
	
	public void recommendJobs(Candidate candidate) {
		List<Company> companies = recommendation.recommendJobs(candidate);
		System.out.println("-------------Jobs Recommendations Made For You-------------------\n");
		int count = 1;
		for (Company company : companies) {
			System.out.println(count + ". " + company.toString() + "\n");
			count++;
		}
	}
	
	public void applyCandidateTo(Candidate candidate, String companyName) {
		HR hr = provideHROfCompany(companyName);
		if (hr == null) {
			System.out.println("\n Opps! There is no company with the name " + companyName + ".");
		} else {
			try {
				application = applicationRepository.get(candidate.getUser().getName());

				if (application == null) {
					application = new Application(candidate.getUser().getName(), 0, new ArrayList<>());
				}
				List<String> previouslyApplied = application.getAppliedCompaniesNames();
				boolean alreadyApplied = false;
				for (String name: previouslyApplied) {
					if (name.toLowerCase().equals(companyName.toLowerCase())) {
						alreadyApplied = true;
						break;
					}
				}
				if (alreadyApplied) {
					System.out.println("     Dear " + candidate.getUser().getName()
							+ "\nYou have already applied to " + companyName
							+ "\n Note that you can apply to a company only once.");
				} else {
					Application app = application.apply(companyName, candidate, hr);

					applicationRepository.create(app);
					Message messageToHR = applicationMessageGenerator(candidate.getUser().getName());
					sendMessage(hr, messageToHR);
					
					hrRepository.update(hr);
					candidateRepository.update(candidate);
					
					System.out.println("\nDear " + candidate.getUser().getName() + "\n Your application to " + companyName + " has successfully been taken");
					System.out.println("You'll be notified as an interview is scheduled for you."
							+ "Keep in touch by checking you message box");
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
 		}
	}
	
	public void provideApplications(String candidateName) {
		System.out.println("-----------------Applications for " + candidateName + "----------------");
		String nameLookedFor = candidateName.toLowerCase(), currentName;
		try {
			List<Application> applications = applicationRepository.fetchAll();
			int counter = 1;
			for (Application application: applications) {
				currentName = application.getName().toLowerCase();
				if (currentName.equals(nameLookedFor)) {
					System.out.println(counter + ".\n " + application.toString());
					counter++;
				}
			}
			if (counter == 1) {
				System.out.println("\n Opps! You haven't applied to any company yet.\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//-------------------------------------Messages Generators-----------------------------------\\
	public Message applicationMessageGenerator(String applicantName) {
		
		String sender = "Admin";
		String topic = "Job application";
		String content = "The candiate named " + applicantName
				+ " has applied to your job. \n"
				+ "You can view the resume of the applicant using the name: " + applicantName;
		
		return new Message(sender, topic, content);
	}
	
	public Message interviewScheduledMessageGenerator(String companyName, String candidateName, LocalDate date) {
	
		String sender = " Admin";
		String topic = " Interview Scheduled For You!\n";
		String content = "\t\t\t\t\t Dear " + candidateName
				+ "\nThe company of the name " + companyName
				+ " has scheduled your interview on the date " + date
				+ "\n \t\t\t Good luck!";
		
		return new Message(sender, topic, content);
	}
		
	private void setRecommendations() {
		try {
			recommendation = new Recommendation(companyRepository.fetchAll());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}