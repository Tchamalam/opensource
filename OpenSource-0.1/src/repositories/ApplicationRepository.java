package repositories;

import java.util.List;

import dataAccessLayer.Reader;
import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class ApplicationRepository implements Repository<Application> {
	
	@SuppressWarnings("unused")
	private User user;
	private Reader<Application> reader;

	public ApplicationRepository(User user, Reader<Application> reader) {
		CheckPreconditions.ensureIsAdmin(user);
		this.user = user;
		this.reader = reader;
	}
	@Override
	public Application get(String name) throws Exception {
		try {
			return reader.get(name);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void delete(String name) throws Exception {
		try {
			reader.delete(name);
		} catch(Exception e) {
			throw new Exception (e);
		}
	}

	@Override
	public void update(Application element) throws Exception {
		try {
			reader.update(element);
		} catch(Exception e) {
			throw new Exception (e);
		}
	}

	@Override
	public void create(Application element) throws Exception {
		try {
			reader.create(element);
		} catch(Exception e) {
			throw new Exception (e);
		}
	}

	@Override
	public List<Application> fetchAll() throws Exception {
		try {
			return reader.fetchAll();
		} catch(Exception e) {
			throw new Exception (e);
		}
	}
	
}
