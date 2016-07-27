package com.VladHerescu.BookTranslator.browse.model;

import java.io.File;
import java.util.List;

import Database.Book;

/**
 * structure containing the data about a section in the list
 * @author Vlad Herescu
 *
 */
public class Section 
{
	
	/**
	 * the position of the item associated with the header
	 */
	int m_position;
	
	/**
	 * the files contained in the Section
	 */
	List<Book> m_files; 
	
	/**
	 * the value of the header
	 */
	String m_value; 
	
	
	/**
	 * if it's shown or not
	 * it is not shown if it is a header without items under it
	 */
	boolean m_shown;

	
	/**
	 * as written above
	 * @param _position
	 * @param _files
	 * @param _value
	 * @param _shown 
	 */
	public Section(int _position, List<Book> _files, String _value, boolean _shown) {

		this.m_position = _position;
		this.m_files = _files;
		this.m_value = _value;
		m_shown = _shown;
	}


	/**
	 * @return the m_position
	 */
	public int getM_position() {
		return m_position;
	}


	/**
	 * @param m_position the m_position to set
	 */
	public void setM_position(int m_position) {
		this.m_position = m_position;
	}


	/**
	 * @return the m_files
	 */
	public List<Book> getM_files() {
		return m_files;
	}


	/**
	 * @param m_files the m_files to set
	 */
	public void setM_files(List<Book> m_files) {
		this.m_files = m_files;
	}


	/**
	 * @return the m_value
	 */
	public String getM_value() {
		return m_value;
	}


	/**
	 * @param m_value the m_value to set
	 */
	public void setM_value(String m_value) {
		this.m_value = m_value;
	}


	/**
	 * @return the m_shown
	 */
	public boolean isM_shown() {
		return m_shown;
	}


	/**
	 * @param m_shown the m_shown to set
	 */
	public void setM_shown(boolean m_shown) {
		this.m_shown = m_shown;
	}
	
}
