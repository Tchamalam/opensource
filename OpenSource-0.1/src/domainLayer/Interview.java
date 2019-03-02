package domainLayer;

import java.time.LocalDate;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Interview {
	
	private String id;
	private String companyName;
	private String candidateName;
	private Candidate candidate;
	private Company company;
	private LocalDate date;
	private InterviewStatus status;
	
	public Interview(String id, String companyName, String candidateName, Candidate candidate, Company company, LocalDate date, InterviewStatus status) {
		this.id = id;
		this.companyName = companyName;
		this.candidateName = candidateName;
		this.candidate = candidate;
		this.company = company;
		this.date = date;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String name) {
		this.companyName = name;
	}
	
	public String getCandidateName() {
		return candidateName;
	}
	
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void getDate(LocalDate date) {
		this.date = date;
	}
	
	public InterviewStatus getInterviewStatus() {
		return status;
	}
	
	public void setInterviewStatus(InterviewStatus status) {
		this.status = status;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nCompany Name: " + companyName);
		sb.append("\nCandidate Name: " + candidateName);
		sb.append("\nInterview Date: " + date); 
		sb.append("\nInterview Status: " + status.getValue());
		
		return sb.toString();
	}
	
}
