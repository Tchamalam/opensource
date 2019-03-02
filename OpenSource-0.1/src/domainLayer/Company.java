package domainLayer;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Company {
	
	private String name;
	private String about;
	private String email;
	private String type;
	private String[][] requirements;
	
	public Company(String name, String email, String type, String about, String[][] requirements) {
		this.name = name;
		this.about = about;
		this.email = email;
		this.type = type;
		this.requirements = requirements;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		if (!email.contains("@")) {
			throw new IllegalArgumentException("Invalid input for the email");
		}
		this.email = email;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
 	
	public String[][] getRequirements() {
		return requirements;
	}
	
	public void setRequirements(String[][] requirements) {
		for (int i = 0; i < requirements.length; i++) {
			this.requirements[i][0] = requirements[i][0];
			this.requirements[i][1] = requirements[i][1];
		}
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append( "Company Name: " + name
				+ "\nEmail: " + email
				+ "\nAbout " + name + ": " + about
				+ "\nType: " + type);
		sb.append("\n\n...........Requirements..........\n");
		String string = ToStringHelper.convert2DStringToString(requirements);
		sb.append(string);
		
		return sb.toString();
	}

}
