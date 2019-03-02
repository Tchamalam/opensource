package dataAccessLayer;

import java.util.List;

/**
 * This very interface invokes the methods for all possible operations doable on
 * the objects: User, Application, Company, HR and Interviews;
 * 
 * @author Ben-Malik TCHAMALAM
 *
 * @param <T> The type of object to be read.
 */
public interface Reader<T> {
	
	/**
	 * Given a particular name, this method reads the T object from the database 
	 * and if any of the read objects' name corresponds with the given name, the object is returned.
	 * @param name The name of the objcect sought.
	 * @return The T object if found and null otherwise;
	 */
	public T get(String name);
	
	/**
	 * Reads the database for the T type objects and fetches them all.
	 * @return All the T objects available in the database.
	 */
	public List<T> fetchAll();
	
	/**
	 * Takes a particular item and updates its values with the older one in the database.
	 * In case no object's name in the database matches with the given item's name, the item is saved to the database.
	 * @param item The T type object to be udpated.
	 */
	public void update(T item);
	
	/**
	 * Delete the object from the database whose name corresponds with the given one.
	 * Beaware that nothing is done if no object matches the given name.
	 * @param name The name whose object is to be deleted.
	 */
	public void delete(String name);
	
	/**
	 * given a T type item, this one is saved to the database.
	 * @param item The item to be saved to the database.
	 */
	public void create(T item);
	
}
