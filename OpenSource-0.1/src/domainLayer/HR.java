package domainLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class HR {
	
	private User user;
	private String companyName;
	private List<Message> adminMessages;
	private List<Interview> interviews;
	private List<Resume> applicantsResumes;
	
	public HR(User user, String companyName) {
		CheckPreconditions.ensureIsHR(user);
		this.user = user;
		this.companyName = companyName;
		adminMessages = new ArrayList<>();
		interviews = new ArrayList<>();
		applicantsResumes = new ArrayList<>();
		System.out.println("fomr HR: " + user.toString());
	}
	
	public HR(User user, String name, List<Resume> resume,
			 List<Message> messages, List<Interview> interviews) {
		this.user = user;
		this.companyName = name;
		this.applicantsResumes = resume;
		this.adminMessages = messages;
		this.interviews = interviews;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;;
	}
	
	public void setUser(User user) {
		if (user == null) {
			throw new SecurityException("The entered user seems to be null! Do check and try again please");
		}
		CheckPreconditions.ensureIsHR(user);
		this.user = user;
	}
	
	public User getUser() { return user; }

	public List<Interview> getInterviews() {
		return interviews;
	}
	
	public void addToInterviews(Interview interview) {
		this.interviews.add(interview);
	}
	
	
	public List<Message> getAdminMessages() {
		return adminMessages;
	}
	
	public void addToAdminMessages(Message message) {
		adminMessages.add(message);
	}
	
	public List<Resume> getApplicantsResumes() {
		return applicantsResumes;
	}
	
	public void addToApplicantsResumes(Resume resume) {
		applicantsResumes.add(resume);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n.......PersonalInformation.......");
		sb.append(user.toString());
		sb.append("\nCompany Name: " + companyName);

		return sb.toString();
	}

}
