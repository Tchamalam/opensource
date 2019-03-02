package controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import domainLayer.*;
import forms.ResumeForm;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 */
public class CandidateController extends Controller {
	
	private AdminController adminController;
	private Candidate candidate;
	private Scanner scanner;
	
	public CandidateController(AdminController adminController, Candidate candidate) {
		this.adminController = adminController;
		this.candidate = candidate;
		scanner = new Scanner(System.in);
	}
	
	@Override
	public void viewMyProfile() {
		System.out.println("------------" + candidate.getUser().getName() + "-Candidate Profile----------");
		String profile = adminController.provideCandidateProfile(candidate.getUser().getName());
		System.out.println(profile);
	}
	
	public void viewMyMessages() {
		System.out.println("----------Message Box Content----------  \n");
		List<Message> messages = candidate.getAdminMessages();
		if (messages.isEmpty()) {
			System.out.println("Dear " + candidate.getUser().getName() + ", your messages box seems to be empty for now");
		} else {
			for (Message message : messages) {
				System.out.println(message.toString());
			}
		}
	}
	
	public void applyTo(String companyName) {
		adminController.applyCandidateTo(candidate, companyName);
	}
	
	public void viewMyApplications() {
		adminController.provideApplications(candidate.getUser().getName());
	}
	
	public void updateMyResume() {
	
		Resume resume = ResumeForm.form(candidate.getUser(), scanner);
		candidate.setResume(resume);
		adminController.saveCandidate(candidate);
		System.out.println("Dear " + candidate.getUser().getName() + ", you Resume has successfully been updated\n");
	}
	
	public void viewMyResume() {
		System.out.println("\n---------------Resume of " + candidate.getUser().getName() + "------------\n");
		System.out.println(candidate.toString());
	}
	
	public void viewCompanyRequirements(String name) {
	
		Company company = adminController.provideCompany(name);
		if (company == null) {
			System.out.println("\n Opps! There is no company with the name " + name);
		} else {
			String[][] requirements = company.getRequirements();
			name = company.getName();
			System.out.println("\n-----------------Requirements of the company " + name + ":-----------------\n");
			for (int i = 0; i < requirements.length; i++) {
				System.out.println(requirements[i][0] + "   ->   " + requirements[i][1]);
			}
			System.out.println("\n");
		}
		
	}
	
	public void viewMyInterviews() {
		List<Interview> interviews = adminController.provideInterviewsOf(candidate.getUser().getName());
		System.out.println("--------All your Interviews ---------\n");
		if (interviews.isEmpty()) {
			System.out.println("\n Opps!You haven't got any interview for the moment\n");
		} else {
			int counter = 1;
			for (Interview interview : interviews) {
				System.out.println("       " + counter + " . Interview \n");
				System.out.println(interview.toString());
				counter++;
			}
		}
	}
	
	public void viewInterviewsOf(LocalDate date) {
		List<Interview> interviewsOf = adminController.provideInterviewsOf(date, candidate.getUser().getName());
		System.out.println("--------Interviews Scheduled for " + date.toString() + "--------\n");
		
		if (interviewsOf.isEmpty()) {
			System.out.println("\n Opps! There is no interview scheduled for the date: " + date.toString() + "\n");
		} else {
			for (Interview interview : interviewsOf) {
				System.out.println(interview.toString());
			}
		}
	}
	
	public void viewHROf(String companyName) {
		HR hr = adminController.provideHROfCompany(companyName);
		if (hr == null) {
			System.out.println("Opss! The is no company with the name " + companyName);
		} else {
			companyName = hr.getCompanyName();
			System.out.println("---------------------HR Of " + companyName + "-------------------\n");
			System.out.println(hr.toString());
		}
		
	}
	
	public void viewJobsRecommendedForMe() {
		adminController.recommendJobs(candidate);
	}
	
	public void logMeOut() {
		adminController.logCandidateOut(candidate);
	}
	
}
