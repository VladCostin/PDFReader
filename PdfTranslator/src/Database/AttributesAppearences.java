package Database;

/**
 * contains the attributes of the table Attributes
 * @author Vlad Herescu
 *
 */
public interface AttributesAppearences {

    /**
     * the id of the appearence
     */
    String ID    = "id";
    /**
     * the id of the book where the word was found
     */
    String BOOK  = "book";
    /**
     * the id of the word found
     */
    String WORD  = "word";
    /**
     * the page where the word was found
     */
    String PAGE	 = "page";
    
    /**
     * how to be known when creating the database
     */
    String table_name	= "Appereances";
	
}
