package com.VladHerescu.BookTranslator.textdisplayer.logic;

import java.util.HashSet;

import com.memetix.mst.language.Language;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @author Vlad Herescu
 *
 */
public class ShPref_TextDisplayer{
	
	/**
	 * associated to the list of books starte to be read and the page
	 */
	static String keySPGetPdgAndPage = "KEY_LAN";
	
	/**
	 * 
	 */
	String m_nameSharedPreferences = "SharedPreferencesDisplayer";
	
	
	/**
	 * the shared preferences associated with the activity
	 * <br> from which the books are retrieved
	 */
	static SharedPreferences m_prefs;
	
	
	/**
	 * @param _activity : the activity associated with shared preferences
	 */
	public ShPref_TextDisplayer(Activity _activity) {

		m_prefs = _activity.getSharedPreferences(m_nameSharedPreferences,  Context.MODE_PRIVATE);
		Log.i("message", "intra iar aici");
	}
	
	
	/**
	 * getting the language the user wants to translate to
	 * @return : the language preferred saved
	 */
	public static String getLanguage()
	{
		String language  = m_prefs.getString(keySPGetPdgAndPage, Language.ARABIC.toString());
		return language;
	}
	
	
	/**
	 * @param _language : the language the user wants to translate to from now on
	 * 
	 */
	public static void setLanguage(String _language)
	{
		Editor prefsEditor = m_prefs.edit();
		prefsEditor.clear();
		prefsEditor.putString(keySPGetPdgAndPage,  _language).commit();
	}

}
