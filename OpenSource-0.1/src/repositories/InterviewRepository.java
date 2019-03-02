package repositories;

import java.util.List;

import dataAccessLayer.Reader;
import domainLayer.Interview;
import domainLayer.CheckPreconditions;
import domainLayer.User;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class InterviewRepository implements Repository<Interview> {

	private Reader<Interview> reader;
	
	public InterviewRepository(User user, Reader<Interview> reader) {
		CheckPreconditions.ensureIsAdmin(user);
		this.reader = reader;
	}
	
	@Override
	public Interview get(String name) throws Exception {
		return reader.get(name);
	}

	@Override
	public void delete(String name) throws Exception {
		reader.delete(name);
	}

	@Override
	public void update(Interview element) throws Exception {
		reader.update(element);
	}

	@Override
	public void create(Interview element) throws Exception {
		reader.create(element);
	}

	@Override
	public List<Interview> fetchAll() throws Exception {
		return reader.fetchAll();
	}

}
