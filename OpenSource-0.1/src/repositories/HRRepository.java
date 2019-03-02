package repositories;

import java.util.List;

import dataAccessLayer.Reader;
import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class HRRepository implements Repository<HR> {
	
	private Reader<HR> reader;
	
	public HRRepository(User user, Reader<HR> reader) {
		this.reader = reader;
		CheckPreconditions.ensureIsAdmin(user);
	}
	
	public HR get(String name) {
		return reader.get(name);
	}
	
	public List<HR> fetchAll() {
		return reader.fetchAll();
	}
	
	
	public void update(HR hR) throws Exception {
		try {
			reader.update(hR);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void delete(String name) throws Exception {
		try {
			reader.delete(name);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void create(HR element) {
		reader.create(element);
	}

}
