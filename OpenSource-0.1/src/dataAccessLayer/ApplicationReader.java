package dataAccessLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import domainLayer.Application;

/**
 * This class implements the Reader interface for an Application.
 * Meaning that the T in the reader is replaced here by Application.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class ApplicationReader implements Reader<Application> {

	/**This is the entire object of all the applications in the database. 
	 * In this manner, all operations on done on the applications reflected this object first
	 * then the database. */
	private JSONObject applications;
	
	//The path of the applications in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/applications.json";

	public ApplicationReader() {
		applications = ReaderHelper.parse(path);
	}
	
	@Override
	public Application get(String name) {
		
		JSONObject object = getObject(name);
		if (object != null) {
			return ReaderHelper.objectToApplication(object);
		}
		return null;
	}

	@Override
	public List<Application> fetchAll() {
		List<Application> allApplications = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> objects = applications.values().iterator();
		while(objects.hasNext()) {
			allApplications.add(ReaderHelper.objectToApplication(objects.next()));
		}
		
		return allApplications;
	}

	@Override
	public void update(Application item) {
		create(item);
		
	}

	@Override
	public void delete(String name) {
		JSONObject object = getObject(name);
		if (!object.equals(null)) {
			applications.remove(object);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create(Application item) {
		JSONObject object = ReaderHelper.applicationToJSONObject(item);
		String name = item.getName();
		applications.put(name, object);
		ReaderHelper.save(applications, path);
		
	}
	
	/**
	 * A helper method to find the application whose candidate name corresponds with a given name.
	 * @param name The name whose application object is sought.
	 * @return The <tt>JSONObject<tt> of the application if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(String name) {
		String theName = name.toLowerCase(), currentKey;

		Iterator<String> keys = applications.keySet().iterator();
		Iterator<JSONObject> values = applications.values().iterator();
		JSONObject current = null, result = null;
	
		while(keys.hasNext() && values.hasNext()) {
			current = values.next();
			currentKey = keys.next().toLowerCase();
			if (currentKey.equals(theName)) {
				result = current;
				break;
			}
		}
		
		return result;
	}

}
