package domainLayer;

import java.util.Arrays;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Levenshtein{
	
	public static Integer distanceComputer(String source, String field) {
		
		if(source.isEmpty()) {
			return source.length();
		}
		
		if(field.isEmpty()) {
			return field.length();
		}
		
		int substitution = distanceComputer(source.substring(1), field.substring(1))
				+ costFinder(source.charAt(0), field.charAt(0));
		int insertion = distanceComputer(source, field.substring(1)) + 1;
		int deletion = distanceComputer(source.substring(1), field) + 1;
		
		return minFinder(substitution, insertion, deletion);
	}
	
	//------------------------------Helper Methods ------------------------------------------\\
	private static int costFinder(char a, char b) {
		return a == b ? 0 : 1;
	}
	
	public static int minFinder(int...numbers) {
		return Arrays.stream(numbers)
				.min().orElse(Integer.MAX_VALUE);
	}
	
}
