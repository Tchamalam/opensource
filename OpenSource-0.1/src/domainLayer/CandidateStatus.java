package domainLayer;
/*
 * @author: Ben-Malik
 */
public enum CandidateStatus {
	
	REGISTERED("registered"),
	APPLIED("applied"),
	INTERVIEWED("interviewed"),
	REJECTED("rejected"),
	HIRED("hired");
	
	private String value;
	
	CandidateStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
