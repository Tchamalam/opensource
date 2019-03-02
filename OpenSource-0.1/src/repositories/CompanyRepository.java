package repositories;

import java.util.List;

import dataAccessLayer.Reader;
import domainLayer.Company;
import domainLayer.CheckPreconditions;
import domainLayer.User;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CompanyRepository implements Repository<Company> {
	
	private Reader<Company> reader;
	
	public CompanyRepository(User user, Reader<Company> reader) {
		this.reader = reader;
		CheckPreconditions.ensureIsAdmin(user);
	}

	@Override
	public Company get(String name) {
		return reader.get(name);
	}

	@Override
	public void delete(String name) {
		reader.delete(name);
	}

	@Override
	public void update(Company element) {
		reader.update(element);
	}

	@Override
	public void create(Company element) {
		reader.create(element);
	}

	@Override
	public List<Company> fetchAll() {
		return reader.fetchAll();
	}

	
}
