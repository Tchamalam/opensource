package repositories;

import java.util.List;

import dataAccessLayer.*;
import domainLayer.*;

/**
 * 
 * @author Ben-Malik TCHAMALAM
 *
 */
public class CandidateRepository implements Repository<Candidate> {

	private User user;
	private Reader<Candidate> reader;
	
	public CandidateRepository(User user, Reader<Candidate> reader) {
		this.user = user;
		this.reader = reader;
	}
	
	
	public Candidate get(String name) throws Exception {
		CheckPreconditions.ensureIsAdmin(user);
		try {
			return reader.get(name);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void update(Candidate candidate) {
		CheckPreconditions.ensureIsAdmin(user);
		try {
			reader.update(candidate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void create(Candidate candidate) {
		CheckPreconditions.ensureIsAdmin(user);
		reader.create(candidate);
		
	}
	
	public List<Candidate> fetchAll() throws Exception {
		CheckPreconditions.ensureIsAdmin(user);
		try {
			List<Candidate> candidates = reader.fetchAll();
			return candidates;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void delete(String name) throws Exception {
		CheckPreconditions.ensureIsAdmin(user);
		try {
			reader.delete(name);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
