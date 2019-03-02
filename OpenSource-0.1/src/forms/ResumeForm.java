package forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domainLayer.Resume;
import domainLayer.User;

public class ResumeForm {


	public static Resume form(User user, Scanner scanner) {
		System.out.println("\n******************Resume Form******************\n");
		
		List<String> educationalInformation = new ArrayList<>();
		List<String> workExperience = new ArrayList<>();
		List<String> fieldsOfInterest = new ArrayList<>();
		
		System.out.println("Enter your education informations one by one. \"end\" to quit.");
		get(educationalInformation, scanner);
		
		
		System.out.println("What is your work experience. \"end\" to quit");
		get(workExperience, scanner);
		
		System.out.println("What are your fields of interests? \"end\" to quit");
		get(fieldsOfInterest, scanner);
		
		String[][] skills = getSkills(scanner);
		
		return new Resume(user, educationalInformation, workExperience, fieldsOfInterest, skills);
	}
	
	/** A helper method to get multiple inputs form user. */
	private static void get(List<String> attribute, Scanner scanner) {
		String input = scanner.nextLine();
		while(!input.equals("end")) {
			attribute.add(input);
			input = scanner.nextLine();
		}
	}
	
	private static String[][] getSkills(Scanner scanner) {
		String[][] skills = new String[20][2];
		System.out.println("Enter your the name of the skills, push enter, then enter the skill. end to quite");
		System.out.println("Beware that you can enter at most 20 skills.\n");
		String type = "", skill;
		int index = 0;
		while(!type.equals("end")) {
			if (index == 20) {
				System.out.println("You have entered all the required 20 skills");
				break;
			}
			System.out.println("Enter skill name:");
			type = scanner.nextLine();
			if (type.equals("end")) {
				break;
			}
			System.out.println("Enter your skill for " + type);
			skill = scanner.nextLine();
			skills[index][0] = type;
			skills[index][1] = skill;
			index++;
		}
		
		return skills;
	}
	
}
