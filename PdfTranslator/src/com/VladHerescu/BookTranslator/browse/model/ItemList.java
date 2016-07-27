package com.VladHerescu.BookTranslator.browse.model;

import java.io.File;

import Database.Book;

/**
 * contains the data about an item in the list shown
 * @author Vlad Herescu
 *
 */
public class ItemList
{
	/**
	 * the value written in the item
	 */
	String m_value;
	
	/**
	 * the type of the item: header or a simple item
	 */
	TypeItem m_type;

	
	/**
	 * the index of the section it depends on
	 */
	Integer m_Section;
	
	/**
	 * the File containing the path and the name
	 */
	Book m_file;

	/**
	 * as written above
	 * @param m_value
	 * @param m_type
	 * @param m_shown
	 * @param m_Section
	 * @param m_file
	 */
	public ItemList(String m_value, TypeItem m_type,
			Integer m_Section, Book m_file) {
		this.m_value = m_value;
		this.m_type = m_type;
		this.m_Section = m_Section;
		this.m_file = m_file;
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
	 * @return the m_type
	 */
	public TypeItem getM_type() {
		return m_type;
	}

	/**
	 * @param m_type the m_type to set
	 */
	public void setM_type(TypeItem m_type) {
		this.m_type = m_type;
	}


	/**
	 * @return the m_Section
	 */
	public Integer getM_Section() {
		return m_Section;
	}

	/**
	 * @param m_Section the m_Section to set
	 */
	public void setM_Section(Integer m_Section) {
		this.m_Section = m_Section;
	}

	/**
	 * @return the m_file
	 */
	public Book getM_file() {
		return m_file;
	}

	/**
	 * @param m_file the m_file to set
	 */
	public void setM_file(Book m_file) {
		this.m_file = m_file;
	}
	
	
}
