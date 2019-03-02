package controllers;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import domainLayer.Candidate;
import domainLayer.Company;
import domainLayer.HR;
import domainLayer.IdGenerator;
import domainLayer.Interview;
import domainLayer.InterviewPortal;
import domainLayer.InterviewStatus;
import domainLayer.Message;
import domainLayer.Resume;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class HRController extends Controller {

	private AdminController adminController;
	private HR hR;
	private InterviewPortal interviewPortal;

	
	public HRController(AdminController adminController, HR hR) {
		this.adminController = adminController;
		this.hR = hR;
		interviewPortal = new InterviewPortal(this.adminController);
	}
	
	@Override
	public void viewMyProfile() {
		System.out.println("----------" + hR.getUser().getName() + "- HR Profile----------");
		System.out.println(hR.toString());
	}
	
	public void viewInterviews() {
		System.out.println("------------------All Interviews-------------------");
		List<Interview> interviews = hR.getInterviews();
		if (interviews.isEmpty()) {
			System.out.println("Opps ! Dear " + hR.getUser().getName() + ", you don't have any interviews for the moment.\n");
		} else {
			int counter = 1;
			System.out.println("Interview No:" + counter + "\n");
			for (Interview interview: interviews) {
				System.out.println(interview.toString() + "\n");
				counter++;
			}
		}
	}
	
	public void viewInterviewsOf(LocalDate date) {
		System.out.println("----------------Interviews Scheduled for -" + date.toString() + "--------------\n");
		List<Interview> result = new ArrayList<>();
		for (Interview interview: hR.getInterviews()) {
			if (interview.getDate().equals(date)) {
				System.out.println(interview.toString());
				result.add(interview);
			}
		}
		if (result.isEmpty()) {
			System.out.println("Opss! There is no interview scheduled for" + date.toString());
		}
		
	}
	
	public void viewMyMessages() {
		System.out.println("----------Message Box Content----------  \n");
		List<Message> messages = hR.getAdminMessages(); 
		if (messages.isEmpty()) {
			System.out.println("Dear " + hR.getUser().getName() + ", you message box is empty for now\n");
		} else {
			for (Message message : messages) {
				System.out.println(message.toString());
			}
		}
	}
	
	public void scheduleInterview(LocalDate date, String candidateName) {
		
		String id = IdGenerator.generateInterviewId();
		String companyName = hR.getCompanyName();
		Company company = adminController.provideCompany(companyName);
		Candidate candidate = adminController.provideCandidate(candidateName);
		 
		Interview interview = new Interview(id, companyName, candidateName, candidate, company, date, InterviewStatus.SCHEDULED);
		
		adminController.saveInterview(interview);
		hR.addToInterviews(interview);
		Message message = adminController.interviewScheduledMessageGenerator(companyName, candidateName, date);
		
		try {
			adminController.sendMessage(candidate, message);
			System.out.println("The interview with " + candidateName + " has been successfully scheduled for " + date);
		} catch (Exception e) {
			System.out.println("Error scheduling an interview\n");
			System.out.println(e.getMessage());
		}
	}
	
	public void viewCandidateInformation(String candidateName) {
		Candidate candidate = adminController.provideCandidate(candidateName);
		if (candidate != null) {
			candidateName = candidate.getUser().getName();
			System.out.println("----------------About " + candidateName + "----------------\n");

			System.out.println(candidate.toString());
		} else {
			System.out.println("Opss! There is no candidate with the name " + candidateName);
		}
		
	}
	
	public void viewResumeOfApplicant(String candidateName) {
		System.out.println("------------------Resume of " + candidateName + "------------------\n");
		Resume result = null;
		List<Resume> resumes = hR.getApplicantsResumes();
		for (Resume resume : resumes) {
			if (resume.getUser().getName().equals(candidateName)) {
				result = resume;
				break;
			}
		}
		
		if (result == null) {
			System.out.println("No candidate is found in your applicants list with the name " + candidateName);
		} else {
			System.out.println(result.toString());
		}
	}
	
	public void viewCandidateResume(String candidateName) {
		Candidate candidate = adminController.provideCandidate(candidateName);
		
		if (candidate == null) {
			System.out.println("Opss! There is no candidate with the name " + candidateName);
		} else {
			candidateName = candidate.getUser().getName();
			System.out.println("------------------Resume of " + candidateName + "------------------\n");
			System.out.println(candidate.toString());
		}
	}
	
	public void viewInterviewPortal() {
		System.out.println("----------------------- Interview Portal ----------------------\n");
		
		interviewPortal.displayScheduledInterviews();

		interviewPortal.displayAttainedInterviews();
	}
 	
	public void logMeOut() {
		adminController.logHROut(hR);
	}

}
