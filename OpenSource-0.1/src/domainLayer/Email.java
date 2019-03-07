package domainLayer;

import java.util.regex.Pattern;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Email {
	
	private String receipient;
	private String domain;
	
	public Email(String receipient, String domain) {
		ensureEmailValidity(receipient + "@" + domain);
		this.receipient = receipient;
		this.domain = domain;
	}
	
	public String toString() {
		return receipient + "@" + domain;
	}
	
	private void ensureEmailValidity(String email) {
		boolean flag = isDomainValide(email);
		if (!flag) {
			throw new IllegalArgumentException("Invalid Email address.");
		}
	}
	
	private boolean isDomainValide(String domain) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		if (domain == null) {
			return false;
		}
		return pattern.matcher(domain).matches();
	}
	
}
