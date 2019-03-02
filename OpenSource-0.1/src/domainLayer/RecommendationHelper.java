package domainLayer;

import java.util.List;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class RecommendationHelper {
	
	public static List<Company> findAllCompanies(List<Company> elements, Candidate candidate) {
		
		//TODO In Review. to be updated very soon.
		List<Company> result = elements;
		/*
		Integer distance, i = 0;
		Integer[] distances = new Integer[elements.size()];
		
		for (Company company : elements) {
			String[][] skills = company.getRequirements();
			String[][] requirements = candidate.getResume().getSkills();
			System.out.println("Company" + i);

			distance = getDistanceOfCompany(requirements, skills);
			distances[i] = distance;
			i++;
		}
		
		int maxNumberOfCompany = 5;
		int current = 0;
		while ((current < maxNumberOfCompany) && !elements.isEmpty()) {
			int min = findMinsIndex(distances);
			result.add(elements.get(min));
			elements.remove(min);
		}*/
		
		return result ;
	}
	
	@SuppressWarnings("unused")
	private static Integer getDistanceOfCompany(String[][] requirements, String[][] skills) {
		String[] requirementValues = new String[requirements.length];
		Integer result = 0;

		for (int i = 0; i < requirements.length; i++) {
			requirementValues[i] = requirements[i][1];
		}
		
		String[] toBeCompared = new String[skills.length];
		for (int i = 0; i < requirements.length; i++) {
			toBeCompared[i] = skills[i][1];
		}
		
		for (int i = 0; i < requirements.length; i++) {
			result += Levenshtein.distanceComputer(toBeCompared[i], requirementValues[i]);
		}
		System.out.println(result + " is result");
		
		return result;
	}
	
	
	@SuppressWarnings("unused")
	private static String getClosest(String[] requirements, String skillType) {
		Integer[] distances = new Integer[requirements.length];
		

		Integer distance;
		for (int i = 0; i < requirements.length; i++) {
			distance = Levenshtein.distanceComputer(requirements[i], skillType);

			distances[i] = distance;
		}
		
		int minIndex = findMinsIndex(distances);
		return requirements[minIndex];
	}
	
	public static int removeMinimumFromList(Integer[] elements) {
		int result = findMinsIndex(elements);
		elements[result] = null;
		for (int i = result; i < elements[i]; i++) {
			elements[i] = elements[i + 1];
		}
		
		return result;
	}
	
	private static int findMinsIndex(Integer[] elements) {
		int min = elements[0];
		int index = 0;

		for (int i = 0; i < elements.length; i++) {
			if(min > elements[i]) {
                min = elements[i];
                index=i;
            }
	    }
		return index;
		
	}

}
