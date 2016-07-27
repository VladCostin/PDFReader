package Database;

import java.util.ArrayList;
import java.util.List;









import com.VladHerescu.BookTranslator.MainActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AttributeSet;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper
{

	static String name = "Dictionary";
	
	static int version = 4;
	
	
	public DatabaseHandler(Context context) {
		super(context, name, null, version);
	
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String create_table_Book, create_table_appearences, create_table_word, create_table_definition, create_table_example;
		
		create_table_Book = "Create table " + AttributesBook.table_name + " ( " +
							AttributesBook.ID + " INTEGER PRIMARY KEY, " +
							AttributesBook.TITLE + " TEXT, " +
							AttributesBook.DIFFICULTY + " TEXT,  "+
							AttributesBook.NR_PAGES + " TEXT,  " +
							AttributesBook.PATH +")";
		create_table_appearences = "Create table " + AttributesAppearences.table_name + " ( " +
							AttributesAppearences.ID + " INTEGER PRIMARY KEY, " +
							AttributesAppearences.BOOK + " INTEGER, " +
							AttributesAppearences.WORD + " INTEGER, " +
							AttributesAppearences.PAGE + " INTEGER) ";
		
		create_table_word = "Create table " + AttributesWord.table_name + " ( " +
							AttributesWord.ID + " INTEGER PRIMARY KEY, " +
							AttributesWord.VALUE +  " TEXT, " +
							AttributesWord.TRANSLATION + " TEXT)";
		
		create_table_definition = "Create table " + AttributesDefinition.table_name + " ( " +
				AttributesDefinition.ID + " INTEGER PRIMARY KEY, " +
				AttributesDefinition.VALUE +  " TEXT, " +
				AttributesDefinition.PART_SPEECH + " TEXT, " +
				AttributesDefinition.WORD + " INTEGER) ";
		
		create_table_example = "Create table " + AttributesExample.table_name + " ( " +
				AttributesExample.ID + " INTEGER PRIMARY KEY, " +
				AttributesExample.VALUE +  " TEXT, " +
				AttributesExample.WORD + " INTEGER) ";
		
		
		db.execSQL(create_table_Book);
		db.execSQL(create_table_word);
		db.execSQL(create_table_appearences);
		db.execSQL(create_table_definition);
		db.execSQL(create_table_example);
		

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + AttributesBook.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesWord.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesAppearences.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesDefinition.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesExample.table_name);
		
		onCreate(db);
		
		
	}
	
	
	/**
	 * adding the data about a book
	 * @param book : contains the values about the book
	 */
	public void addBook(Book book)
	{
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesBook.TITLE, book.getTitle());
		values.put(AttributesBook.DIFFICULTY, book.getDifficulty());
		values.put(AttributesBook.NR_PAGES, book.getNr_pages());
		values.put(AttributesBook.PATH, book.getPath());

		db.insert(AttributesBook.table_name, null, values);
		db.close();
	}
	
	
	/**
	 * 
	 * @param word : contains the data about the word to be inserted
	 */
	public void addWord(Word word)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesWord.VALUE, word.getM_value());
		values.put(AttributesWord.TRANSLATION, word.getM_translation());
		db.insert(AttributesWord.table_name, null, values);
		
	}
	
	
	/**
	 * adding the definition of a word in the database
	 * @param _definition 
	 * 
	 */
	public void addDefinition(Definition _definition)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesDefinition.VALUE, _definition.getM_value());
		values.put(AttributesDefinition.PART_SPEECH, _definition.getM_partSpeech());
		values.put(AttributesDefinition.WORD, _definition.getM_idWord()); 
		db.insert(AttributesDefinition.table_name, null, values);
	}
	
	
	/**
	 * adding the example of a word in the database
	 * @param _example 
	 * 
	 */
	public void addExample(Example _example)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesExample.VALUE, _example.getM_value());
		values.put(AttributesExample.WORD, _example.getM_idWord()); 
		db.insert(AttributesExample.table_name, null, values);
	}
	
	
	/**
	 * @param appeareance : the appearance of a word in a book at a certain page
	 * 
	 */
	public void addAppeareance(Appeareance appeareance)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesAppearences.BOOK, appeareance.getIdBook());
		values.put(AttributesAppearences.WORD, appeareance.getIdWord());
		values.put(AttributesAppearences.PAGE, appeareance.getPage());
		
		
		db.insert(AttributesAppearences.table_name, null, values);
	}
	
	/**
	 * adding the word in the table Word, the examples in the table examples, and definitions in the table definitions
	 * @param _word : the data about a word to be inserted
	 */
	public int add_Word_Def_Ex(Word _word)
	{
		addWord(_word);
		int idWord = MainActivity.m_database.getWordId(_word.getM_value());
		int i;
		
		for(String ex : _word.getM_example())
			addExample(new Example( ex, idWord));
		
		for(i = 0; i < _word.getM_definition().size(); i++)
			addDefinition(new Definition( _word.getM_definition().get(i), _word.getM_partSpeech().get(i), idWord));
		
		return idWord;
		
		
	}
	
	
	
	
	
	
	public Book getBook(String fieldName, String value) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(AttributesBook.table_name, new String[] 
	    		{ AttributesBook.ID,AttributesBook.TITLE, AttributesBook.DIFFICULTY, AttributesBook.NR_PAGES, AttributesBook.PATH },
	    		fieldName + "=?",
	            new String[] { value }, null, null, null, null);
	    if(cursor == null || cursor.getCount() == 0)
	    	return null;
	   
	    cursor.moveToFirst();
	   
	 
	    Book book = new Book(Integer.parseInt(cursor.getString(0)),
	            							   cursor.getString(1),
	            							   Integer.parseInt(cursor.getString(2)),
	            							   cursor.getInt(3),
	            							   cursor.getString(4));
	    // return contact
	    return book;
	}
	
	public List<Book> getAllBooks() {
	    List<Book> bookList = new ArrayList<Book>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesBook.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Book book = new Book();
	            book.setId(Integer.parseInt(cursor.getString(0)));
	            book.setTitle(cursor.getString(1));
	            book.setDifficulty( Integer.parseInt(cursor.getString(2)));
	            book.setNr_pages(cursor.getInt(3));
	            book.setPath(cursor.getString(4));
	            // Adding contact to list
	            bookList.add(book);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return bookList;
	}
	
	public Word getWord(String fieldName, String value)
	{
	    SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(AttributesWord.table_name, new String[] 
	    		{ AttributesWord.ID,
	    		  AttributesWord.VALUE,
	    		  AttributesWord.TRANSLATION
	    		},
	    		fieldName + "=?",
	            new String[] { value }, null, null, null, null);
	    
	    if(cursor.getCount() == 0 || cursor == null)
	    	return null;
	    
	    cursor.moveToFirst();
	 
	    List<String> examples = getExamples(AttributesExample.WORD, cursor.getString(0));
	    List<Definition> definitions = getDefinition(AttributesDefinition.WORD, cursor.getString(0));
	    
	
	    
	    Word word = new Word(Integer.parseInt(cursor.getString(0)),  cursor.getString(1), cursor.getString(2), definitions, examples);

	    return word;
	//    Word word = new  Word(Integer.parseInt(cursor.getString(0)),
	 ///           cursor.getString(1), cursor.getString(2),cursor.getString(3),
	   //         cursor.getString(4), cursor.getString(5));
	    // return contact
	 //   return word;
	}
	
	public ArrayList<Word> getAllWords() {
	    ArrayList<Word> wordList = new ArrayList<Word>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesWord.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	/*
	            Word word = new Word();
	            word.setM_id(Integer.parseInt(cursor.getString(0)));
	            word.setM_value(cursor.getString(1));
	            word.setM_translation(cursor.getString(2));
	            
	            
	            List<String> examples = getExamples(AttributesExample.WORD, cursor.getString(0));
	    	    List<Definition> definitions = getDefinition(AttributesDefinition.WORD, cursor.getString(0));
	    	    
	            
	        //    word.setM_definition(null);
	       //     word.setM_example(null);
	       //     word.setPart(cursor.getString(5)); 
	      //      word.setDefinition(cursor.getString(3));
	      //      word.setExample(cursor.getString(4));
	       //     word.setPart(cursor.getString(5));
	            // Adding contact to list
	             * 
	             */
	        	
	        	List<String> examples = getExamples(AttributesExample.WORD, cursor.getString(0));
	    	    List<Definition> definitions = getDefinition(AttributesDefinition.WORD, cursor.getString(0));
	            wordList.add( new Word(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), definitions, examples));
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return wordList;
	}
	
	
	/**
	 * gets all the words searched in a book
	 */
	public ArrayList<Word> getWordsFromBook(String _bookTitle)
	{
		ArrayList<Word> words = new ArrayList<Word>();
		
		Book book = getBook(AttributesBook.TITLE, _bookTitle);

		List<Appeareance> app = getAppearances(AttributesAppearences.BOOK, Integer.toString( book.getId()));
		for(Appeareance ap : app)
			words.add(getWord(AttributesWord.ID, Integer.toString(ap.idWord)));
		
		return words;
	}
	
	
	public List<Appeareance> getAllAppearances() {
	    List<Appeareance> appearanceList = new ArrayList<Appeareance>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesAppearences.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	          Appeareance appeareance = new Appeareance();
	          appeareance.setId(cursor.getInt(0));
	          appeareance.setIdBook(cursor.getInt(1));
	          appeareance.setIdWord(cursor.getInt(2));
	          appeareance.setPage(cursor.getInt(3));
	          appearanceList.add(appeareance);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return appearanceList;
	}
	
	public boolean WordAppearsinBook(String _idWord, String _book)
	{
		Log.i("message", "id-ul cuvantului este: " + _idWord);
		List<Appeareance> apps =  getAppearances(AttributesAppearences.WORD, _idWord);

		
		
		for(Appeareance app : apps)
		{
			Book book = getBook(AttributesBook.ID, Integer.toString(app.idBook));
			Log.i("message", book.getTitle() + " " + _book);
			
			if(book.getTitle().equals(_book))
				return true;

		}
		return false;
		
	}
	
	/**
	 * 
	 */
	public List<Appeareance> getAppearances(String fieldName, String value) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Appeareance> appearanceList = new ArrayList<Appeareance>();
	 
		Cursor cursor = db.query(AttributesAppearences.table_name, new String[] 
				{ AttributesAppearences.ID,
				AttributesAppearences.BOOK, 
				AttributesAppearences.WORD,
				AttributesAppearences.PAGE},
				fieldName + "=?",
				new String[] { value }, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
 
		if (cursor.moveToFirst()) {
		        do {
		          Appeareance appeareance = new Appeareance();
		          appeareance.setId(cursor.getInt(0));
		          appeareance.setIdBook(cursor.getInt(1));
		          appeareance.setIdWord(cursor.getInt(2));
		          appeareance.setPage(cursor.getInt(3));
		          appearanceList.add(appeareance);
		        } while (cursor.moveToNext());
		    }
		 
		 // return contact list
		 return appearanceList;

	}
	
	
	/**
	 * @param fieldName : the attribute where the condition is tested
	 * @param value : the value of the field in order for the condition to be reached
	 * @return : the list of examples for which the condition is true, usually the id of the word must be true
	 * 
	 */
	public List<String> getExamples(String fieldName, String value)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		List<String> examplesList = new ArrayList<String>();
		
		
		Cursor cursor = db.query(AttributesExample.table_name, new String[] 
				{ AttributesExample.ID,
				AttributesExample.VALUE, 
				AttributesExample.WORD},
				fieldName + "=?",
				new String[] { value }, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
		
		
		if (cursor.moveToFirst()) {
	        do {
	        	examplesList.add(cursor.getString(1));
	        } while (cursor.moveToNext());
	    }
	 
		return examplesList;
	}
	
	/**
	 * @param fieldName : the attribute where the condition is tested
	 * @param value : the value of the field in order for the condition to be reached
	 * @return : the list of examples for which the condition is true, usually the id of the word must be true
	 * 
	 */
	public List<Definition> getDefinition(String fieldName, String value)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		List<Definition> definitionList = new ArrayList<Definition>();
		
		
		Cursor cursor = db.query(AttributesDefinition.table_name, new String[] 
				{ AttributesDefinition.ID,
				AttributesDefinition.VALUE,
				AttributesDefinition.PART_SPEECH,
				AttributesDefinition.WORD},
				fieldName + "=?",
				new String[] { value }, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
		
		
		if (cursor.moveToFirst()) {
	        do {
	        	definitionList.add(new Definition( cursor.getString(1), cursor.getString(2)));
	        } while (cursor.moveToNext());
	    }
	 
		return definitionList;
	}
	
	
	
	
	
	
	public void deleteAllWords()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesWord.table_name, null, null);
		   db.close();
	}
	
	public void deleteAllApp()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesAppearences.table_name, null, null);
		   db.close();
	}
	
	public void deleteAllBooks()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesBook.table_name, null, null);
		   db.close();
	}
	
	public void deleteAllDefinitions()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesDefinition.table_name, null, null);
		   db.close();
	}
	
	public void deleteAllExamples()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesExample.table_name, null, null);
		   db.close();
	}
	
	
	
	public void deleteBook(int _id)
	{
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		String condition = AttributesBook.ID + "=" + _id;
		db.delete(AttributesBook.table_name, condition, null);
		deleteAppearance(AttributesAppearences.BOOK, Integer.toString(_id));
		db.close();
	}
	
	
	public void deleteAppearance(String _fieldName, String _value)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String condition = _fieldName + "=" + _value;
		db.delete(AttributesAppearences.table_name, condition, null);
		db.close();
	}
	
	
	public void deleteWord(String _fieldName, String _value)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String condition = _fieldName + "=" + _value;
		db.delete(AttributesWord.table_name, condition, null);
		db.close();
	}
	
	
	/**
	 * @param titleBook : the title of the 
	 * @return : returns the id of a book 
	 */
	public int getBookId(String titleBook)
	{
		 return getBook(AttributesBook.TITLE, titleBook).id;
	}
	
	/**
	 * @param wordValue : the word searched in database
	 * @return : the id of the word searched
	 */
	public Integer getWordId(String wordValue)
	{
		Word word = getWord(AttributesWord.VALUE, wordValue);
		if(word == null)
			return null;
		
		return word.m_id;
	}
	
	
	
}
