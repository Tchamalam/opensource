package dataAccessLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import domainLayer.Interview;

/**
 * This class implements the Reader interface for an Interview.
 * Meaning that the T in the Reader is replaced here by Interview.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class InterviewReader implements Reader<Interview> {

	/**This is the entire object of all the HRs in the database. 
	 * In this manner, all operations on done on the HRs reflected this object first
	 * then the database. */
	private JSONObject interviews;
	
	//The path of the interviews in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/interviews.json";

	public InterviewReader() {
		interviews = ReaderHelper.parse(path);
	}
	
	@Override
	public Interview get(String name) {
		JSONObject object = getObject(name);
		if (!object.equals(null)) {
			return ReaderHelper.objectToInterview(object);
		}
		return null;
	}

	@Override
	public List<Interview> fetchAll() {
		List<Interview> allInterviews = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> objects = interviews.values().iterator();
		while(objects.hasNext()) {
			allInterviews.add(ReaderHelper.objectToInterview(objects.next()));
		}
		return allInterviews;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Interview item) {
		JSONObject object = ReaderHelper.interviewToJSONObject(item);
		String id = item.getId();
		interviews.put(id, object);
		
		ReaderHelper.save(interviews, path);
	}

	@Override
	public void delete(String id) {
		JSONObject object = getObject(id);
		if (!object.equals(null)) {
			interviews.remove(object);
			
			ReaderHelper.save(interviews, path);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create(Interview item) {
		JSONObject object = ReaderHelper.interviewToJSONObject(item);
		String id = item.getId();
		interviews.put(id, object);
		
		ReaderHelper.save(interviews, path);

	}
	
	/**
	 * A helper method to find the Interview whose candidate/companh name corresponds with a given name.
	 * @param name The name whose interview object is sought.
	 * @return The <tt>JSONObject<tt> of the interview if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(String name) {
		String nameLookedFor = name.toLowerCase(), currentName;
		
		Iterator<JSONObject> values = interviews.values().iterator();
		JSONObject current = null, result = null;
		
		while(values.hasNext()) {
			current = values.next();
			currentName = current.get("companyName").toString().toLowerCase();
			
			if (currentName.equals(nameLookedFor) 
					|| current.get("candidateName").toString().equals(nameLookedFor)) {
				result = current;
				break;
			}
		}
		return result;
	}

}
