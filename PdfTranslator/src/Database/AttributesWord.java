package Database;

/**
 * the attributes of the tables Word
 * @author Vlad Herescu
 *
 */
public interface AttributesWord 
{
	/**
	 * the id of the word
	 */
	String ID			= 			"id";
	
	/**
	 * its value
	 */
	String VALUE		= 			"value";
	
	
	String TRANSLATION	=			"translation";
	
	/**
	 * how the table is recognized in the database
	 */
	String table_name	=			"word";
}
