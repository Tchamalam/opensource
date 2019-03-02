package dataAccessLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import domainLayer.*;

/**
 * This class implements the Reader interface for an HR.
 * Meaning that the T in the Reader is replaced here by an HR.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class HRReader implements Reader<HR> {

	/**This is the entire object of all the HRs in the database. 
	 * In this manner, all operations on done on the HRs reflected this object first
	 * then the database. */
	private JSONObject hrs;
	
	//The path of the HRs in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/HRs.json";
	
	public HRReader() {
		hrs = ReaderHelper.parse(path);
	}
	
	@SuppressWarnings("unchecked")
	public List<HR> fetchAll() {
		List<HR> companiesList = new ArrayList<>();
		Iterator<JSONObject> iterator = hrs.values().iterator();
		while(iterator.hasNext()) {
			companiesList.add(ReaderHelper.objectToHR(iterator.next()));
		}
		return companiesList;
	}
	
	@SuppressWarnings("unchecked")
	public void update(HR hR) {
		
		JSONObject object = ReaderHelper.HRToJSONObject(hR);
		
		String id = hR.getUser().getID();
		hrs.put(id, object);
		
		ReaderHelper.save(hrs, path);
	}
	
	@SuppressWarnings("unchecked")
	public void create(HR hR) {
		JSONObject object = ReaderHelper.HRToJSONObject(hR);
		String id = hR.getUser().getID();
		hrs.put(id, object);
		ReaderHelper.save(hrs, path);
	}

	@Override
	public HR get(String name) {
		JSONObject object = getObject(name, false);
		if (object != null) {
			HR hr = ReaderHelper.objectToHR(object);
			return hr;
		}
		return null;
	}
	
	public HR getHROfCompany(String companyName) {
		
		JSONObject object = getObject(companyName, true);
		if (object != null) {
			return ReaderHelper.objectToHR(object);
		}
		return null;
	}

	@Override
	public void delete(String name) {
		JSONObject object = getObject(name, false);
		if (object != null) {
			hrs.remove(object);
			ReaderHelper.save(hrs, path);
		}
		
	}

	/**
	 * A helper method to find the HR whose candidate name corresponds with a given name.
	 * @param name The name whose application object is sought.
	 * @param forCompany If the hr is searched for a company or for an hr user.
	 * 		 true for company and false for hr user.
	 * @return The <tt>JSONObject<tt> of the HR if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(String name, boolean forCompany) {
		Iterator<JSONObject> iterator = hrs.values().iterator();
		JSONObject current = null, result = null;
		String nameLookedFor = name.toLowerCase(), currentName;
		if(forCompany) {
			while(iterator.hasNext()) {
				current = iterator.next();
				currentName = current.get("companyName").toString().toLowerCase();
				if (currentName.equals(nameLookedFor)) {
					result = current;
					break;
				}
			}
		} else {
			while(iterator.hasNext()) {
				JSONObject user;
				current = iterator.next();
				user = (JSONObject) current.get("user");
				currentName = user.get("name").toString().toLowerCase();
				
				if (currentName.equals(nameLookedFor)) {
					result = current;
					break;
				}
			}
		}
		
		return result;
	}
	
}
