package domainLayer;
/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public enum UserType {
	
	ADMIN("admin"), 
	CANDIDATE("candidate"),
	HR("hr");
	
	private String value;
	
	UserType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
