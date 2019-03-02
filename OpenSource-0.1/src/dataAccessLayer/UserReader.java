package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

import domainLayer.User;

/**
 * This class implements the Reader interface for a User.
 * Meaning that the T in the Reader is replaced here by User.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class UserReader implements Reader<User> {
	
	/**This is the entire object of all the HRs in the database. 
	 * In this manner, all operations on done on the HRs reflected this object first
	 * then the database. */
	private JSONObject users;
	
	//The path of the users in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/users.json";

	public UserReader() {
		users = ReaderHelper.parse(path);
	}
	
	@Override
	public User get(String name) {
		JSONObject object = getObject(users, name);
		if (object != null) {
			User user = ReaderHelper.objectToUser(object);
			return user;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> fetchAll() {
		List<User> users = new ArrayList<>();
		Iterator<JSONObject> iterator = this.users.values().iterator();
		while(iterator.hasNext()) {
			users.add(ReaderHelper.objectToUser(iterator.next()));
		}
		return users;
	}

	@Override
	public void update(User item) {
		create(item);
		ReaderHelper.save(users, path);
	}

	@Override
	public void delete(String name) {
		JSONObject object = getObject(users, name);
		if (!object.equals(null)) {
			users.remove(ReaderHelper.objectToUser(object).getID());
			ReaderHelper.save(users, path);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create(User item) {
		JSONObject object = ReaderHelper.userToJSONObject(item);
		users.put(item.getID(), object);
		ReaderHelper.save(users, path);
	}
	

	/**
	 * A helper method to find the user whose name corresponds with a given name.
	 * @param name The name whose user object is sought.
	 * @return The <tt>JSONObject<tt> of the interview if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(JSONObject object, String name)  {
		String nameLookedFor = name.toLowerCase(), currentName;
		
		Iterator<JSONObject> iterator = users.values().iterator();
		Iterator<JSONObject> keys = users.keySet().iterator();
		JSONObject result = null, current = null;
		
		while(iterator.hasNext() && keys.hasNext()) {
			current = iterator.next();
			currentName = current.get("name").toString().toLowerCase();
			if (currentName.equals(nameLookedFor)) {
				result = current;
				break;
			}
		}
		
		return result;
	}
	
}
