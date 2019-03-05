
package repositories;

import java.util.List;

/**
 * An interface defining all possible operations on any object. 
 * Including Candidate, Company, HR and Interview.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 * @param <T> The type of the object.
 */
public interface Repository<T> {
	
	/**
	 * Finds the object corresponding with a given name.
	 * @param name The name whose object is sought.
	 * @return The object of the object.
	 * @throws Exception in case an unexpected error occurs while getting the object form the database.
	 */
	public T get(String name) throws Exception;
	
	/**
	 * Give a name , the corresponding object is searched and if found, gets deleted.
	 * @param name The of the object to be deleted.
	 * @throws Exception in case an in case an unexpected error occurs while
	 * getting the object form the database.
	 */
	public void delete(String name) throws Exception;
	
	/**
	 * Given an element of type T(to be specified during implementation), update its contents.
	 * Beware that if no matching is found in the database, it get saved.
	 * @param element The object of the element to be saved.
	 * @throws Exception in case an unexpected error occurs while 
	 * getting the object form the database.
	 */
	public void update(T element) throws Exception;
	
	/**
	 * Create an object and saves it to the database.
	 * @param element The element to be saved
	 * @throws Exception in case an unexpected error occurs while
	 *  getting the object form the database.
	 */
	public void create(T element) throws Exception;
	
	/**
	 * Fetches all of the objects of type T form the database.
	 * @return a list of all the objects.
	 * @throws Exception in case an unexpected error occurs while
	 *  getting the objects form the database.
	 */
	public List<T> fetchAll() throws Exception;
	
}
