package repositories;

import java.util.List;

import dataAccessLayer.Reader;

import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class UserRepository implements Repository<User> {
	
	private Reader<User> reader;
	
	public UserRepository(Reader<User> reader) {
		this.reader = reader;
	}
	
	public User get(String name) {
		return reader.get(name);
	}

	@Override
	public void delete(String name) {
		reader.delete(name);
	}


	@Override
	public List<User> fetchAll() {
		return reader.fetchAll();
	}

	@Override
	public void update(User element) {
		reader.update(element);
	}

	@Override
	public void create(User element) {
		reader.create(element);
	}
	
}
