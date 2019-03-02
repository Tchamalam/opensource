package domainLayer;

import java.util.List;

import controllers.AdminController;

/**
 * The interviews are displayed by this class.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class InterviewPortal {
	
	private List<Interview> interviews;
	@SuppressWarnings("unused")
	private AdminController adminController;

	public InterviewPortal(AdminController adminController) {
		this.adminController = adminController;
		interviews = adminController.provideAllInterviews();
	}
	
	
	public void displayScheduledInterviews() {
		System.out.println("-----------All Scheduled Interviews--------------\n");
		int count = 0;
		for (Interview interview : interviews) {
			if (interview.getInterviewStatus().equals(InterviewStatus.SCHEDULED)) {
				System.out.println(interview.toString());
				count++;
			}
		}
		if (count == 0) {
			System.out.println("There are not interviews scheduled\n");
		}
	}
	
	public void displayAttainedInterviews() {
		System.out.println("-------------Interviews Already Attended -----------\n");
		int count = 0;
		for (Interview interview : interviews) {
			if (interview.getInterviewStatus().equals(InterviewStatus.ATTENDED)) {
				System.out.println(interview.toString());
				count++;
			}
		}
		if (count == 0) {
			System.out.println("None of the interviews have been attended yet.\n");
		}
	}
	
} // end InterviewPortal.
