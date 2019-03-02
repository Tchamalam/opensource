package forms;

/**
 * An interface for the the forms.
 * 
 * @author Ben-Malik TCHAMALAM
 *
 * @param <T> The type of user whose form is to be taken.
 */
public interface Form<T> {
	
	/**
	 * Take inputs form uses and create an object with them.
	 * @return The created object.
	 */
	T form();

}
