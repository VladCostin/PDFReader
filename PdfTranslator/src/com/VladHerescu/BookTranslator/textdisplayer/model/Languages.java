package com.VladHerescu.BookTranslator.textdisplayer.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.memetix.mst.language.Language;


public class Languages{
	
	static List<String> m_languages;
	
	public Languages() {
//		m_languages = new LinkedHashMap<>();
		m_languages = new ArrayList<String>();
		addLanguages();
		
	}

	private void addLanguages() {
		
		m_languages.add(Language.ARABIC.toString());
		m_languages.add(Language.BULGARIAN.toString());
		m_languages.add(Language.GERMAN.toString());
		m_languages.add(Language.ROMANIAN.toString());
		m_languages.add(Language.RUSSIAN.toString());
		m_languages.add(Language.SPANISH.toString());
		m_languages.add(Language.SWEDISH.toString());
		
		/*
		m_languages.put(Language.ARABIC.toString(), Language.ARABIC);
		m_languages.put(Language.FRENCH.toString(), Language.FRENCH);
		m_languages.put(Language.GERMAN.toString(), Language.GERMAN);
		m_languages.put(Language.ROMANIAN.toString(), Language.ROMANIAN);
		m_languages.put(Language.RUSSIAN.toString(), Language.RUSSIAN);
		m_languages.put(Language.SPANISH.toString(), Language.SPANISH);
		m_languages.put(Language.SWEDISH.toString(), Language.SWEDISH);
		*/
	}
	
	public static List<String> getListLanguages()
	{
		
		return m_languages;
	}
	
	/**
	 * @param _language : the language to translate to
	 * @return : the index of the language
	 * 
	 */
	public static int getIndexSpinner(String _language)
	{
		return m_languages.indexOf(_language);
	}

}
