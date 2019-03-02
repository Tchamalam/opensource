package domainLayer;

/**
 * This class is to enforce the state of an Interview: 
 * either scheduled or attained. No other possibilities.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public enum InterviewStatus {
	
	SCHEDULED("scheduled"),
	ATTENDED("attended");
	
	private String value;
	
	InterviewStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
