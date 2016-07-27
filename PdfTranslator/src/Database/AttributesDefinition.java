package Database;

/**
 * the attributes of the definition
 * @author Vlad Herescu
 *
 */
public interface AttributesDefinition{
	
	   /**
	 *  the id of the definition
	 */
	String ID    	= "id";
	   /**
	 *  the definition of the word
	 */
	String VALUE  	= "value";
	   /**
	 *  the id of the word
	 */
	String WORD  	= "word";
	
	
	/**
	 * if it is a verb, noun , adjective, etc
	 */
	String PART_SPEECH	= "partSpeech";
	    
	   /**
	 *  how to be recognized in database
	 */
	String table_name	= "Definitions";

}
