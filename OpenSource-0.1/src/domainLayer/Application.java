package domainLayer;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Application {
	
	private String id;
	private String name;
	private Integer numberOfApplications;
	private List<String> appliedCompaniesNames;
	
	public Application(String name, Integer numberOfApplications, List<String> appliedCompaniesNames) {
		id = IdGenerator.generateApplicationId();

		this.name = name;
		this.numberOfApplications = numberOfApplications;
		this.appliedCompaniesNames = appliedCompaniesNames;
	}
	
	public Application (String id, String name, Integer numberOfApplications, List<String> appliedCompaniesNames) {
		this.id = id;
		this.name = name;
		this.numberOfApplications = numberOfApplications;
		this.appliedCompaniesNames = appliedCompaniesNames;
	}
	
	public String getId() {
		return id;
	}
	
	public void incrementNumberOfApplications() {
		numberOfApplications++;
	}
	
	public Integer getNumberOfApplications() {
		return numberOfApplications;
	}
	
	public List<String> getAppliedCompaniesNames() {
		return appliedCompaniesNames;
	}
	
	public void addToAppliedCompaniesNames(String name) {
		appliedCompaniesNames.add(name);
	}
	
	public String getName() {
		return name;
	}
	
	public Application apply(String companyName, Candidate candidate, HR hR) throws Exception {
		
		name = candidate.getUser().getName();
		hR.addToApplicantsResumes(candidate.getResume());
		candidate.setCandidateStatus(CandidateStatus.APPLIED);
		
		appliedCompaniesNames.add(companyName);
		incrementNumberOfApplications();
		
		return this;
	}
	
	@Override
	public String toString() {
		return "------------------------Application------------------------\n"
				+ "Candidate Name: " + name
				+ "\nNumber of Applications: " + numberOfApplications
				+ "\nNames Of Applied Companies: " 
				+ Arrays.toString(appliedCompaniesNames.toArray());
	}
	
} // end Application.
