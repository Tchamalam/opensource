package domainLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Resume {
	
	private User user;
	private List<String> educationalInformation;
	private List<String> workExperience;
	private List<String> fieldsOfInterest;
	private String[][] skills;
	 
	public Resume(User user, List<String> educationalInformation, List<String> workExperience,
			List<String> fieldsOfInterest, String[][] skills) {
		this.educationalInformation = educationalInformation;
		this.workExperience = workExperience;
		this.fieldsOfInterest = fieldsOfInterest;
		setUser(user);
		this.skills = new String[skills.length][2];
		setSkills(skills);
	}
	
	public Resume(User user) {
		this(user, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new String[10][2]);
	}
	
	public User getUser() {
		return user;
	}
	
	
	public List<String> getEducationalInformation() {
		return educationalInformation;
	}
	
	public List<String> getWorkExperience() {
		return workExperience;
	}
	
	private void setUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Invalid input for the user.");
		}
		this.user = user;
	}
	
	public Resume getResume() {
		return this;
	}
	
	public List<String> getFieldsOfInterest() {
		return fieldsOfInterest;
	}
	
	public String[][] getSkills() {
		return skills;
	}
	
	public void setSkills(String[][] skills) {
		for (int i = 0; i < skills.length; i++) {
			this.skills[i][0] = skills[i][0];
			this.skills[i][1] = skills[i][1];
 		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n    RESUME     \n");
		sb.append(".....Educational Informaion......\n");
		for (String info : educationalInformation) {
			sb.append(info + " \n");
		}
		sb.append("\n.....WorkExperience........\n");
		for (String work : workExperience) {
			sb.append(work + "\n");
		}
		sb.append("\n.......Fields Of Interest......\n");
		for (String field : fieldsOfInterest) {
			sb.append(field + "\n");
		}
		sb.append("\n..........Skills...........\n");
		String string = ToStringHelper.convert2DStringToString(skills);
		sb.append(string);
		
		return sb.toString();
	}
	
}
