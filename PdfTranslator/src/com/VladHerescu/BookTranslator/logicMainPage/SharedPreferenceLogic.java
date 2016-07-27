package com.VladHerescu.BookTranslator.logicMainPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.VladHerescu.BookTranslator.Constants;
import com.fasterxml.jackson.databind.util.Comparators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;

/**
 * the logic that gets data from the shared preferences
 * @author Vlad Herescu
 *
 */
public class SharedPreferenceLogic{
	
	
	/**
	 * associated to the list of books starte to be read and the page
	 */
	static String keySPGetPdgAndPage = "KEY_STARTED_PDF";
	
	/**
	 * 
	 */
	String m_nameSharedPreferences = "SharedPreferences";
	
	
	/**
	 * the shared preferences associated with the activity
	 * <br> from which the books are retrieved
	 */
	static SharedPreferences m_prefs; 
	
	
	/**
	 * @param _activity : the activity associated with shared preferences
	 */
	public SharedPreferenceLogic(Activity _activity) {

		m_prefs = _activity.getSharedPreferences(m_nameSharedPreferences,  Context.MODE_PRIVATE);
		Log.i("message", "intra iar aici");
	}
	
	/**
	 * getting the set of titles of the books opened
	 * @return : the titles of the books opened
	 */
	public List<String> getSetDataBookSharedPreferences()
	{

		Set<String> openedPdf;
		openedPdf = m_prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		ArrayList<String> list = new ArrayList<String>(openedPdf);
		
		Collections.sort(list,  new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				
				int endLHS = lhs.indexOf("pdf");
				int endRHS = rhs.indexOf("pdf");
				
				int startLHS = lhs.lastIndexOf("/");
				int startRHS = rhs.lastIndexOf("/");
				
				
				return lhs.substring(startLHS, endLHS).compareTo(rhs.substring(startRHS, endRHS));
			}
		});
		
		Log.i("message", "Cartile din sharedPreferences sunt : "  + openedPdf.toString());
		
		return list;
	}
	
	
	
	
	/**
	 * deletes all the books from shared preferences
	 */
	public void deleteAllBooks()
	{
		Set<String> booksStarted;
		booksStarted = m_prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		
		Editor prefsEditor = m_prefs.edit();
		prefsEditor.clear();
		
		booksStarted.clear();	
		prefsEditor.putStringSet(keySPGetPdgAndPage,  booksStarted).commit();
	}
	
	
	/**
	 * retrieves data about the books started
	 * @param _nameBook : the name of the book where the bookmark is left
	 * @param _progress : the progress the user has done so far
	 * @param _nrPages : the number of pages of the book
	 */
	public void addDataAboutStartedPDF(String _nameBook, Integer _progress, Integer _nrPages) {
		
		
		Set<String> booksStarted;
		String returnedData;
		String bookName;
		String book_from_Set;
		
		booksStarted = m_prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		
		
		returnedData = _nameBook.replace
					   (Environment.getExternalStorageDirectory().toString() + "/", ""); 
		bookName = returnedData.split(Constants.separatorNamePage)[0];
		
		
		Editor prefsEditor = m_prefs.edit();
		prefsEditor.clear();
		
		for(String book_page : booksStarted)
		{
			book_from_Set = book_page.split(Constants.separatorNamePage)[0];
			if(book_from_Set.equals(bookName))
			{
				booksStarted.remove(book_page);
				break;
			}
		}
		
		booksStarted.add(returnedData+ Constants.separatorNamePage+  _progress + Constants.separatorNamePage + _nrPages);
		prefsEditor.putStringSet(keySPGetPdgAndPage,  booksStarted).commit();
		
		
	}
	
	/**
	 * remove a book from shared preferences
	 * @param _title : the value of the item to be deleted
	 */
	public static void deleteBook(String _title)
	{
		Set<String> openedPdf;
		openedPdf = m_prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		
		Log.i("message", "lista de carti este : " + openedPdf);
		Log.i("message", "cartea de sters este : " + _title);
		
		for(String book : openedPdf)
			if(book.contains(_title))
			{
				openedPdf.remove(book);
				break;
			}
		
		Log.i("message", "lista de carti este : " + openedPdf);
		
		Editor prefsEditor = m_prefs.edit();
		prefsEditor.clear();

		prefsEditor.putStringSet(keySPGetPdgAndPage, openedPdf).commit();
		
		
		openedPdf = m_prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		Log.i("message", "lista de carti dupa este : " + openedPdf);
	}
	

}
