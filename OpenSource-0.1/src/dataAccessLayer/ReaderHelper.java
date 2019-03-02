package dataAccessLayer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 * This class is responsible for providing necessary methods when fetching or saving Users,
 * Candidates and Companies.
 */
public class ReaderHelper {

	/**
	 * Given a path, this method reads the content of the file and parses it to a JSONObject object.
	 * @param path The path of the file to be read.
	 * @return the <tt>JSONObject<tt> version of the contenet of the file.
	 */
	public static JSONObject parse(String path) {
		
		JSONObject object = null;
		try {
			FileReader file = new FileReader(path);
			JSONParser parser = new JSONParser();
			object = (JSONObject) parser.parse(file);
			
		} catch (IOException e) {
			System.out.println(e.getMessage() + "not found");
		} catch (ParseException e) {
			System.out.println(e.getMessage() + " dont know");
		}
		
		return object;
	}
	
	/**
	 * 
	 * @param users
	 * @param path
	 */
	public static void save(JSONObject users, String path) {
		
		try (FileWriter file = new FileWriter(path)) {
			file.append(users.toString());
			file.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//---------------------------------JSONObject to Type Converters-----------------------------\\

	@SuppressWarnings("unchecked")
	public static Resume objectToResume(JSONObject object) {
		
		User user = objectToUser((JSONObject) object.get("user"));
		List<String> educationalInformation = new ArrayList<>();
		List<String> fieldsOfInterest = new ArrayList<>();
		List<String> workExperience = new ArrayList<>();

		JSONArray info = new JSONArray();
		
		
		info = (JSONArray) object.get("educationalInformation");
		for (int i = 0; i < info.size(); i++) {
			educationalInformation.add(info.get(i).toString());
		}
		
		info = (JSONArray) object.get("fieldsOfInterest");
		for(int i = 0; i < info.size(); i++) {
			fieldsOfInterest.add(info.get(i).toString());
		}
		
		info = (JSONArray) object.get("workExperience");
		for(int i = 0; i < info.size(); i++) {
			workExperience.add(info.get(i).toString());
		}
		
		JSONObject skillsObject = (JSONObject) object.get("skills");
		String[][] skills = new String[skillsObject.size()][2];
		Iterator<String> keys = skillsObject.keySet().iterator();
		Iterator<String> values = skillsObject.values().iterator();
		int index =  0;
		while(keys.hasNext() && values.hasNext()) {
			skills[index][0] = keys.next();
			skills[index][1] = values.next();
			index++;
		}
				
		return new Resume(user, educationalInformation, workExperience,
				fieldsOfInterest, skills);
	}
	
	public static Candidate objectToCandidate(JSONObject object) {
		
		User user = ReaderHelper.objectToUser((JSONObject)object.get("user"));
		CandidateStatus status = ReaderHelper.candidateStatusFinder(object.get("status").toString());
		CandidateType type = ReaderHelper.candidateTypeFinder(object.get("type").toString());
		Resume resume = ReaderHelper.objectToResume((JSONObject)object.get("resume"));
		
		List<Message> messages = new ArrayList<>();
		
		JSONArray array = (JSONArray) object.get("messages");
		for(int i = 0; i < array.size(); i++) {
			messages.add(ReaderHelper.objectToMessage((JSONObject)array.get(i)));
		}
		
		return new Candidate(user, type, status, resume, messages);
	}
	
	public static User objectToUser(JSONObject object) {
		
		String name = object.get("name").toString();
		String id = object.get("id").toString();
		String email = object.get("email").toString();
		Integer age = Integer.valueOf(object.get("age").toString());
		UserType type = userTypeFinder(object.get("userType").toString());
		String password = object.get("password").toString();
		LogState state = logStateFinder(object.get("logState").toString());
		
		return new User(name, id , email, age, type, password, state);
	}
	
	public static HR objectToHR(JSONObject object) {
		
		List<Message> messages = new ArrayList<>();
		List<Resume> resumes = new ArrayList<>();
		List<Interview> interviews = new ArrayList<>();
		
		JSONArray array = (JSONArray) object.get("messages");
		for(int i = 0; i < array.size(); i++) {
			messages.add(objectToMessage((JSONObject) array.get(i)));
		}
		
		array = (JSONArray) object.get("applicantsResumes");
		for (int i = 0; i < array.size(); i++) {
			resumes.add(objectToResume((JSONObject) array.get(i)));
		}
		
		String name = object.get("companyName").toString();
		
		User user = objectToUser((JSONObject)object.get("user"));
		
		array = (JSONArray) object.get("interviews");
		for(int i = 0; i < array.size(); i++) {
			interviews.add(objectToInterview((JSONObject) array.get(i)));
		}
		
		return new HR(user, name, resumes, messages, interviews);
	}
	
	public static Application objectToApplication(JSONObject object) {
		
		String id = object.get("id").toString();
		String name = object.get("name").toString();
		Integer numberOfApplications = Integer.valueOf(object.get("numberOfApplications").toString());
		List<String> appliedCompaniesNames = new ArrayList<>();
		JSONArray array = (JSONArray) object.get("appliedCompaniesNames");
		for (int i = 0; i < array.size(); i++) {
			appliedCompaniesNames.add(array.get(i).toString());
		}
		
		return new Application(id, name, numberOfApplications, appliedCompaniesNames);
		
	}

	public static Interview objectToInterview(JSONObject object) {
		
		Candidate candidate = objectToCandidate((JSONObject) object.get("candidate"));
		Company company = objectToCompany((JSONObject) object.get("company"));
		LocalDate date = stringToLocalDate(object.get("date").toString());
		String id = object.get("id").toString();
		String companyName = object.get("companyName").toString();
		String candidateName = object.get("candidateName").toString();
		InterviewStatus status = interviewStatusFinder(object.get("status").toString());
		
		return new Interview(id, companyName, candidateName, candidate, company, date, status);
	}
	
	@SuppressWarnings("unchecked")
	public static Company objectToCompany(JSONObject object) {
		
		String name = object.get("name").toString();
		String email = object.get("email").toString();
		String about = object.get("about").toString();
		String type = object.get("type").toString();
		
		JSONObject subObject = (JSONObject) object.get("requirements");
		String[][] requirements = new String[subObject.size()][2];
		Iterator<String> keys = subObject.keySet().iterator();
		Iterator<String> values = subObject.values().iterator();
		int index = 0;
		while(keys.hasNext() && values.hasNext()) {
			requirements[index][0] = keys.next();
			requirements[index][1] = values.next();
			index++;
		}
		
		return new Company(name, email, type, about, requirements);
	}
	public static Message objectToMessage(JSONObject object) {
		return new Message(object.get("sender").toString(),
				object.get("topic").toString(),
				object.get("content").toString());
	}
	
	//----------------------------Type to JSONObject Converters---------------------------------\\
	@SuppressWarnings("unchecked")
	public static JSONObject interviewToJSONObject(Interview interview) {
		
		JSONObject object = new JSONObject();
		object.put("companyName", interview.getCompanyName());
		object.put("candidateName", interview.getCandidateName());
		object.put("id", interview.getId());
		object.put("candidate", candidateToJSONObject(interview.getCandidate()));
		object.put("company", companyToJSONObject(interview.getCompany()));
		object.put("date", interview.getDate().toString());
		object.put("status", interview.getInterviewStatus().getValue());
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject applicationToJSONObject(Application application) {
		
		JSONObject object = new JSONObject();
		object.put("id", application.getId());
		object.put("name", application.getName());
		object.put("numberOfApplications", application.getNumberOfApplications());
		JSONArray array = new JSONArray();
		for (String name : application.getAppliedCompaniesNames()) {
			array.add(name);
		}
		object.put("appliedCompaniesNames", array);
		
		return object;
		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject userToJSONObject(User user) {
		JSONObject object = new JSONObject();
		
		object.put("name", user.getName());
		object.put("email", user.getEmail());
		object.put("password", user.getPassword());
		object.put("age", user.getAge());
		object.put("id", user.getID());
		object.put("userType", user.getUserType().getValue());
		object.put("logState", user.getLogState().getValue());
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject HRToJSONObject(HR hR) {
		JSONObject object = new JSONObject();
		object.put("user", ReaderHelper.userToJSONObject(hR.getUser()));
		object.put("companyName", hR.getCompanyName());
		JSONArray array = new JSONArray();
		if (!hR.getApplicantsResumes().isEmpty()) {
			for (Resume resume : hR.getApplicantsResumes()) {
				array.add(ReaderHelper.resumeToJSONObject(resume));
			}
		}
		object.put("applicantsResumes", array);
		
		array  = new JSONArray();
		if (!hR.getInterviews().isEmpty()) {
			for (Interview interview : hR.getInterviews()) {
				array.add(interviewToJSONObject(interview));
			}
		}
		object.put("interviews", array);
		
		JSONArray messages = new JSONArray();
		if (!hR.getAdminMessages().isEmpty()) {
			for(Message message : hR.getAdminMessages()) {
				messages.add(ReaderHelper.messageToJSONObject(message));
			}
		}
		object.put("messages", messages);
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject companyToJSONObject(Company company) {
		JSONObject object = new JSONObject();
		
		object.put("name", company.getName());
		object.put("email", company.getEmail());
		object.put("about", company.getAbout());
		object.put("type", company.getType());
		
		JSONObject requirements = new JSONObject();
		String[][] required = company.getRequirements();
		for(int i = 0; i < required.length; i++) {
			requirements.put(required[i][0], required[i][1]);
		}
		object.put("requirements", requirements);
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject candidateToJSONObject(Candidate candidate) {
		JSONObject object = new JSONObject();
		object.put("user", ReaderHelper.userToJSONObject(candidate.getUser()));
		object.put("status", candidate.getCandidateStatus().getValue());
		object.put("type", candidate.getCandidateType().getValue());
		object.put("resume", ReaderHelper.resumeToJSONObject(candidate.getResume()));
		JSONArray array = new JSONArray();
		for (Message message : candidate.getAdminMessages()) {
			array.add(ReaderHelper.messageToJSONObject(message));
		}
		object.put("messages", array);
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject resumeToJSONObject(Resume resume) {
		JSONObject object = new JSONObject();
		object.put("user", userToJSONObject(resume.getUser()));
		JSONArray array = new JSONArray();
		for(String info : resume.getEducationalInformation()) {
			array.add(info);
		}
		object.put("educationalInformation", array);
		
		
		JSONArray workExperience = new JSONArray();
		for (String work : resume.getWorkExperience()) {
			workExperience.add(work);
		}
		object.put("workExperience", workExperience);
				
		array = new JSONArray();
		for(String field : resume.getFieldsOfInterest()) {
			array.add(field);
		}
		object.put("fieldsOfInterest", array);
		
		JSONObject skills = new JSONObject();
		String[][] resumeSkills = resume.getSkills();
		for (int i = 0; i < resumeSkills.length; i++) {
			skills.put(resumeSkills[i][0], resumeSkills[i][1]);
		}
		object.put("skills", skills);
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject messageToJSONObject(Message message) {
		JSONObject object = new JSONObject();
		object.put("sender", message.getSender());
		object.put("topic", message.getTopic());
		object.put("content", message.getContent());
		return object;
	}
	
	//----------------------------------String to Types Convertors-------------------------------\\
	/**
	 * Converts a given date as String to LocalDate.
	 * @param date The String version of the date.
	 * @return The localDate version of the given string date.
	 */
	public static LocalDate stringToLocalDate(String date) {
		StringTokenizer tokenizer = new StringTokenizer(date, "-");
		int year, month, day;
		year = Integer.valueOf(tokenizer.nextToken());
		month = Integer.valueOf(tokenizer.nextToken());
		day = Integer.valueOf(tokenizer.nextToken());
		return LocalDate.of(year, month, day);
	}
	
	public static LogState logStateFinder(String state) {
		if (state.equals("loggedIn")) {
			return LogState.LOGGED_IN;
		}
		return LogState.LOGGED_OUT;
	}
	
	/**
	 * A method to find the UserType version of a user given the String version.
	 * @param type The String value of the type whose 
	 * @return the UserType version of the given string type.
	 */
	public static UserType userTypeFinder(String type) {
		switch(type.toLowerCase()) {
		case "admin":
			return UserType.ADMIN;
		case "hr":
			return UserType.HR;
		default:
			return UserType.CANDIDATE;
		}
	}
	
	/**
	 * A method to find the CandidateType version given a String type.
	 * @param type The string value of the type whose CandidateType is sought.
	 * @return The CandidateType version of the given type.
	 */
	public static CandidateType candidateTypeFinder(String type) {
		if(type.equals("experienced")) {
			return CandidateType.EXPERIENCED;
		} else {
			return CandidateType.FRESH;
		}
	}
	
	/**
	 * A method that would get the status of a candidate as a <tt>String</tt>
	 * and find its <tt>CandidateStatus</tt> version.
	 * @param status The string value of the status.
	 * @return The CandidateStatus version of the given status.
	 */
	public static CandidateStatus candidateStatusFinder(String status) {
		switch(status) {
		case "registered":
			return CandidateStatus.REGISTERED;
		case "interviewed":
			return CandidateStatus.INTERVIEWED;
		case "rejected":
			return CandidateStatus.REJECTED;
		case "hired":
			return CandidateStatus.HIRED;
		default:
			return CandidateStatus.REGISTERED;
		}
	}
	
	/**
	 * This method finds the InterviewStatus version of a given string one. 
	 * @param status The string version.
	 * @return The corresponding InterviewStatus of the string.
	 */
	public static InterviewStatus interviewStatusFinder(String status) {
		if(status.equals("scheduled")) {
			return InterviewStatus.SCHEDULED;
		}
		return InterviewStatus.ATTENDED;
	}
	
}
