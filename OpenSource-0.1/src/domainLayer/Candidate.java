package domainLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ben-Malik 
 *
 */
public class Candidate {
	private User user;
	private CandidateType candidateType;
	private CandidateStatus status;
	private Resume resume;
	private List<Message> adminMessages;
	
	public Candidate(User user, CandidateType candidateType, CandidateStatus status, Resume resume) {
		CheckPreconditions.ensureIsCandidate(user);
		setUser(user);
		setCandidateType(candidateType);
		setCandidateStatus(status);
		setResume(resume);
		adminMessages = new ArrayList<>();
		
	}
	
	public Candidate(User user, CandidateType candidateType, CandidateStatus status, Resume resume,
			List<Message> messages) {
		this.user = user;
		this.candidateType = candidateType;
		this.status = status;
		this.resume = resume;
		this.adminMessages = messages;
		
	}
	
	public void setResume(Resume resume) {
		this.resume = resume;
	}
	
	private void setUser(User user) {
		if (user == null) {
			throw  new IllegalArgumentException("Enter a valid user pleae!");
		}
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setCandidateStatus(CandidateStatus status) {
		this.status = status;
	}
	
	public CandidateStatus getCandidateStatus() {
		return status;
	}
	
	public void setCandidateType(CandidateType type) {
		this.candidateType = type;
	}
	
	public CandidateType getCandidateType() {
		return candidateType;
	}
	
	public Resume getResume() {
		return resume;
	}
	
	public List<Message> getAdminMessages() {
		return adminMessages;
	}
	
	public void addToAdminMessages(Message message) {
		adminMessages.add(message);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(user.toString() + " \n\nCandidate Status: " + status + ". Candidate Type: " + candidateType);
		
		sb.append("\n" + resume.toString());
		return sb.toString();
	}
	
} //end Candidate

