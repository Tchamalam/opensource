package presenstaionLayer;
/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class OpenSourceApp {

	
	public static void main(String[] args) {
		
		System.out.println(".....................Very Warm Welcome  ......................\n\n");
		
		while (true) {
			int operation = ApplicationStarter.start();
			if (operation == 4) {
				break;
			}
		}
		
		System.out.println(" \n \n   ****************  Open Source 0.1  ****************  \n\n");
	}

}
