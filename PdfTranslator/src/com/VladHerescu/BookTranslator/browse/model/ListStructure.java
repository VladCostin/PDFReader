package com.VladHerescu.BookTranslator.browse.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * contains the data of all List
 * @author Vlad Herescu
 *
 */
public class ListStructure
{
	
	
	/**
	 * the key is the index of the section and the value is data about
	 * the Section
	 */
	List<Section> m_Sections;

	
	
	/**
	 * key = the index of the item in the list
	 * 
	 */
	List<ItemList> m_items;
	
	
	/**
	 * contains the values used for populating the list
	 */
	List<String> m_itemValues;
	
	
	/**
	 * storing the list of items so they will never change
	 */
	List<ItemList> m_permanentItems;
	
	
	
	
	/**
	 * constructor, without setting the value for the members
	 */
	public ListStructure() 
	{
		
		m_Sections = new ArrayList<Section>();
		m_items = new ArrayList<ItemList>();
		
	}
	
	/**
	 * @param sections
	 * @param items
	 * @param _itemValues : the items shown in the list, updated in case of a search
	 */
	public ListStructure(List<Section> sections, List<ItemList> items, List<String> _itemValues)
	{
		m_Sections = sections;
		m_items = items;
		m_itemValues = _itemValues;
		m_permanentItems = new ArrayList<ItemList>(items);
	} 

	/**
	 * method used for testing
	 */
	public void printSections() {
		Log.i("message", "datele despre sections sunt : ");
		
	//	for(Section section :m_Sections)
	//		Log.i("message",  section.m_value + " " + section.m_position);
		
	//	for(ItemList item : m_items)
	//	{
	//		Log.i("message",   item.getM_value() + " " + item.getM_Section());
	//	}
		
	/*	for(Integer key : m_Section.keySet())
		{
			Log.i("message",  key + " " + m_Section.get(key).m_position + " " + m_Section.get(key).m_value);
		}
	*/
		
		/*for(Integer key : m_item.keySet())
		{
			Log.i("message",  key + " " + m_item.get(key).m_value);
		}*/
		
	}
	
	
	/**
	 * gets all the values associated with each item
	 * <br> which will be passed as an argument to the ItemAdapter
	 * @return : all the values associated with each item
	 */
	public List<String> getAllItemValues()
	{
		List<String> items = new ArrayList<String>();
		for(ItemList item : m_items)
			items.add(item.m_value);
		return items;
		
	}
	
	/**
	 * 
	 * @return  the values shown in the fast scroll
	 */
	public List<String> getSectionsValues()
	{
		List<String> values = new ArrayList<String>();
		
		for(Section section : m_Sections)
		{
			if(section.isM_shown() == true)
				values.add(section.m_value);
		}
		
		return values;
		
	}
	
	

	
	/**
	 * @return the size of the list shown in listview
	 */
	public int getSize()
	{
		return m_items.size();
	}
	
	/**
	 * checks if the item is header or a simple item
	 * @param _position : the position of the list
	 * @return : true if it is a header
	 */
	public boolean isHeader(int _position)
	{
		if( m_items.get(_position).m_type == TypeItem.HEADER)
			return true;
		
		return false;
	}

	/**
	 * @return the m_Sections
	 */
	public List<Section> getM_Sections() {
		return m_Sections;
	}

	/**
	 * @param m_Sections the m_Sections to set
	 */
	public void setM_Sections(List<Section> m_Sections) {
		this.m_Sections = m_Sections;
	}

	/**
	 * @return the m_items
	 */
	public List<ItemList> getM_items() {
		return m_items;
	}

	/**
	 * @param m_items the m_items to set
	 */
	public void setM_items(List<ItemList> m_items) {
		this.m_items = m_items;
	}

	/**
	 * @return the m_itemValues
	 */
	public List<String> getM_itemValues() {
		return m_itemValues;
	}

	/**
	 * @param m_itemValues the m_itemValues to set
	 */
	public void setM_itemValues(List<String> m_itemValues) {
		this.m_itemValues = m_itemValues;
	}

	/**
	 * @return the m_permanentItems
	 */
	public List<ItemList> getM_permanentItems() {
		return m_permanentItems;
	}

	/**
	 * @param m_permanentItems the m_permanentItems to set
	 */
	public void setM_permanentItems(List<ItemList> m_permanentItems) {
		this.m_permanentItems = m_permanentItems;
	}

	
	
}
