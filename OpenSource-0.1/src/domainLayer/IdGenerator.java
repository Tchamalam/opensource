package domainLayer;


import dataAccessLayer.IdsReader;

/**
 * This class generates id for HRs, candidates, and admins.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class IdGenerator {
	
	public static  String generateCandidateId() {
		IdsReader reader = new IdsReader();
		return String.valueOf(reader.getLastIdForCandidate());
	}
	
	public static String generateHRId() {
		IdsReader reader = new IdsReader();
		return String.valueOf(reader.getLastIdForHR());
	}
	
	public static String generateAdminId() {
		IdsReader reader = new IdsReader();
		return String.valueOf(reader.getLastIdForAdmin());
	}

	public static String generateInterviewId() {
		IdsReader reader = new IdsReader();
		return String.valueOf(reader.getLastIdForInterview());
	}
	
	public static String generateApplicationId() {
		IdsReader reader = new IdsReader();
		return String.valueOf(reader.getLastIdForApplication());
	}
}
