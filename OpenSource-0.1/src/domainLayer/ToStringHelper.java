package domainLayer;

/**
 * 
 * @author Ben-Malik TCHAMLAM
 *
 */
public class ToStringHelper {
	
	public static String convert2DStringToString(String[][] string) {
		String result = "";
		String requirement;
		String current;
		for (int i = 0; i < string.length; i++) {
			
			current = string[i][0];
			if (!current.equals(null)) {
				requirement = string[i][0] + "->" + string[i][1] + "\n";
				result += requirement;
			} else {
				break;
			}
		}
		return result;
	}

}
