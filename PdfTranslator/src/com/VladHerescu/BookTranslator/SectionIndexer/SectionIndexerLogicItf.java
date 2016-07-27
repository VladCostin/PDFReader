package com.VladHerescu.BookTranslator.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import Database.Book;

import com.VladHerescu.BookTranslator.browse.model.ListStructure;

/**
 * @author Vlad Herescu
 *
 */
public interface SectionIndexerLogicItf 
{

	/**
	 * @return : all the list of PDF files in the storage
	 * 
	 */
	public ArrayList<Book> getAllFiles();
	
	
	/**
	 * 
	 * @return : the list structure containing the headers and the items
	 */
	public ListStructure getListStructure();
	
	
	
	/**
	 * t
	 * @param _files the list of files found in the storage
	 * @return : the list of files sorted
	 */
	public List<Book> sortFiles( List<Book> _files);
	
	
	/**
	 * @param _file : the sorted files from the storage
	 * @return : the structure of headers and items
	 * 
	 */
	public ListStructure createStructure(List<Book> _file);


	/**
	 * @param _structure 
	 * @param _searchFile 
	 * 
	 */
	void updateListStructureSearch(ListStructure _structure, String _searchFile);


}
