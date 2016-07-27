package com.VladHerescu.BookTranslator.exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.VladHerescu.BookTranslator.MainActivity;

import Database.Word;

/**
 * 
 * class that deals with data managing retrieved from database, that checks values, etc 
 * @author Vlad Herescu
 *
 */
public class ExerciseModel implements ExerciseModelInterface{

	/**
	 * the words to be tested by the user
	 */
	static ArrayList<Word> m_AllWords;
	
	
	/**
	 * the word to be checked by the application
	 */
	static Word m_currentWordChecked;
	
	
	/**
	 * the list of words to which the translation was correct
	 */
	static ArrayList<Integer> m_indexWordsCorrect;
	
	/**
	 * the words to be tested in the activity
	 */
	static ArrayList<Word> m_testWords;
	
	
	
	
	/**
	 * initializing the data
	 */
	public ExerciseModel() {
		m_AllWords = new ArrayList<Word>();
		m_indexWordsCorrect = new ArrayList<Integer>();
		m_testWords = new ArrayList<Word>();
	}
	
	
	/**
	 * initializing the data
	 * @param _bookTitle : the book from which to retrieve the words searched in dictionary
	 */
	public ExerciseModel(String _bookTitle) 
	{
		m_AllWords = MainActivity.m_database.getWordsFromBook(_bookTitle);
		m_indexWordsCorrect = new ArrayList<Integer>();
		m_testWords = new ArrayList<Word>();
	}
	
	@Override
	public void setTestWords(int _wordsLength) {
		
		m_testWords.clear();
		
		if(_wordsLength > m_AllWords.size())
		{
			
			m_testWords.addAll(m_AllWords);
			
		}
		else
		{
			List<Word> copy = m_AllWords.subList(0, m_AllWords.size() - 1);
			Collections.shuffle(copy);
			for(int i = _wordsLength -1; i>= 0; i--)
			{
				Log.i("message","aaaaaa " + copy.get(i).getM_value());
				m_testWords.add(copy.get(i));
			}
		
		}
		
		
		
		
		//if(words.size() - _wordsLength >= 0)
		//	for(int i = words.size() - _wordsLength; i < words.size(); i++)
		//		m_AllWords.add(words.get(i));
		
		

	}

	@Override
	public boolean checksValue( String _valueFromUser) {
		
		Log.i("message", m_currentWordChecked.getM_translation());
		
		if(m_currentWordChecked.getM_translation().equals(_valueFromUser))
			return true;
		
		return false;
	}
	
	/**
	 * get a word to be tested
	 * @param _position : the ndex associated with the word to be tested
	 * @return : the word to be tested
	 */
	public static Word getWordAtPosition(int _position) 
	{

		m_currentWordChecked = m_testWords.get(_position);
		
	//	Log.i("message", "cuvintele sunt: " + m_testWords.get(0).getM_value() + " " + m_testWords.get(1).getM_value() + " " + m_testWords.get(2).getM_value());
		
		return m_testWords.get(_position);

	}
	
	/**
	 * @param _index : the id of the word to which the user has responded correctly
	 * 
	 */
	public static void addIfCorrect(int _index)
	{
		if(m_indexWordsCorrect.contains(_index) == false)
			m_indexWordsCorrect.add(_index);
	}
	
	public static int getNrSlides()
	{
		return m_testWords.size();
	}
	
	public static ArrayList<String> getValuesWord()
	{
		ArrayList<String> values = new ArrayList<String>();
		for(Word word :m_testWords)
			values.add(word.getM_value());
		return values;
	}
	
	public static ArrayList<String> getTranslationWord()
	{
		ArrayList<String> values = new ArrayList<String>();
		for(Word word :m_testWords)
			values.add(word.getM_translation());
		return values;
	}
	

}
