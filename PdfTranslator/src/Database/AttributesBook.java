package Database;

/**
 * the attributes of the table Book
 * @author Vlad Herescu
 *
 */
public interface AttributesBook {

	/**
	 * the id of the book in database
	 */
	String ID	= "id";
	
	/**
	 * the title of the book
	 */
	String TITLE = "title";
	
	/**
	 * its difficulty
	 */
	String DIFFICULTY = "difficulty";
	
	
	/**
	 * how to be recognized when creatin the database
	 */
	String table_name	= "Book";
	
	/**
	 * how many pages it has
	 */
	String NR_PAGES		= "NR_PAGES";
	
	/**
	 * the path of the book
	 */
	String PATH			= "Path";
	
}
