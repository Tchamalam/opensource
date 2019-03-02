package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

import domainLayer.*;

/**
 * This class implements the Reader interface for a Candidate.
 * Meaning that the T in the Reader is replaced here by Candidate.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CandidateReader implements Reader<Candidate> {
	
	/**This is the entire object of all the HRs in the database. 
	 * In this manner, all operations on done on the HRs reflected this object first
	 * then the database. */
	private JSONObject candidates;
	
	// The path of all the candidates in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/candidates.json";

	public CandidateReader() {
		candidates = ReaderHelper.parse(path);
	}
	
	public Candidate get(String name) {
		JSONObject object = getObject(name);
		return  ReaderHelper.objectToCandidate(object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> fetchAll() {
		List<Candidate> candidatesList = new ArrayList<>();
		JSONObject current;
		Iterator<JSONObject> iterator = candidates.values().iterator();
		while(iterator.hasNext()) {
			current = iterator.next();
			candidatesList.add( ReaderHelper.objectToCandidate(current));
		}
		return candidatesList;
	}

	@Override
	public void update(Candidate item) {
		create(item);
	}

	@Override
	public void delete(String item) {
		JSONObject object = getObject(item);
		if(!object.equals(null)) {
			candidates.remove(object);
			ReaderHelper.save(candidates, path);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create(Candidate item) {
		JSONObject object = ReaderHelper.candidateToJSONObject(item);
		candidates.put(item.getUser().getID(), object);
		
		ReaderHelper.save(candidates, path);
	}
	
	/**
	 * A helper method to get the JSONObject that matches with the given name.
	 * @param name the name whose object is sought.
	 * @return The <tt>JSONObject<tt> of the given name if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(String name) {
		String theName = name.toLowerCase();
		
		Iterator<JSONObject> iterator = candidates.values().iterator();
		JSONObject current = null, result = null, user = null;
		while(iterator.hasNext()) {
			current = (JSONObject) iterator.next();
			user = (JSONObject) current.get("user");
			if ( user.get("name").toString().toLowerCase().equals(theName)) {
				result = current;
				break;
			}
		}
		
		return result;
	}
	
}
