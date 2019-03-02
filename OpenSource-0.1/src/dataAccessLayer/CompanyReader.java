package dataAccessLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import domainLayer.Company;

/**
 * This class implements the Reader interface for a Company.
 * Meaning that the T in the Reader is replaced here by Company.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CompanyReader implements Reader<Company> {

	/**This is the entire object of all the companies in the database. 
	 * In this manner, all operations on done on the applications reflected this object first
	 * then the database*/
	private JSONObject companies;
	
	//The path of the companies in the database.
	private static final String path = 	System.getProperty("user.dir") + "/src/database/companies.json";

	public CompanyReader() {
		companies = ReaderHelper.parse(path);
	}
	
	@Override
	public Company get(String name) {
		JSONObject object = getObject(name);
		if (object != null) {
			return ReaderHelper.objectToCompany(object);
		}
		return null;
	}

	@Override
	public List<Company> fetchAll() {
		
		List<Company> allCompanies = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> objects = companies.values().iterator();
		JSONObject current;
		while(objects.hasNext()) {
			current = objects.next();
			allCompanies.add(ReaderHelper.objectToCompany(current));
		}
		
		return allCompanies;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Company item) {
		
		String name = item.getName();
		companies.put(name, ReaderHelper.companyToJSONObject(item));
		
		ReaderHelper.save(companies, path);
	}

	@Override
	public void delete(String name) {
		
		JSONObject object = getObject(name);
		
		if (!object.equals(null)) {
			companies.remove(object);
			ReaderHelper.save(companies, path);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create(Company item) {
		
		JSONObject object = ReaderHelper.companyToJSONObject(item);
		String companyName = item.getName();
		
		companies.put(companyName, object);
		ReaderHelper.save(companies, path);

	}
	

	/**
	 * A helper method to find the company whose candidate name corresponds with a given name.
	 * @param name The name whose application object is sought.
	 * @return The <tt>JSONObject<tt> of the company if found and null otherwise.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getObject(String name) {
		String theName = name.toLowerCase();
		Iterator<String> keys = companies.keySet().iterator();
		Iterator<JSONObject> values = companies.values().iterator();
		
		JSONObject current = null, result = null;
		
		String key;
		while(keys.hasNext() && values.hasNext()) {
			current = values.next();
			key = keys.next().toLowerCase();
			if (key.equals(theName)) {
				result = current;
				break;
			}
		}
		
		return result;
	}

}
