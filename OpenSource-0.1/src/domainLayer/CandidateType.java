package domainLayer;
/**
 * This class is to enforce the type of Candidate 's.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public enum CandidateType {
	
	EXPERIENCED("experienced"),
	FRESH("fresh");
	
	private String value;
	
	CandidateType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
