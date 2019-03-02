package domainLayer;

import java.util.List;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Recommendation {
	
	private List<Company> allCompanies;
	
	public Recommendation(List<Company> allCompanies) {
		this.allCompanies = allCompanies;
	}
	
	public List<Company> recommendJobs(Candidate candidate) {
		return RecommendationHelper.findAllCompanies(allCompanies, candidate);
	}
	
}
