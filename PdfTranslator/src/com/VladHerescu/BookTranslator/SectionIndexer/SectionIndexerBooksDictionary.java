package com.VladHerescu.BookTranslator.SectionIndexer;


import java.util.ArrayList;
import java.util.List;

import Database.Book;

import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;

public class SectionIndexerBooksDictionary  extends SectionIndexerLogic 
{
	@Override
	public ListStructure getListStructure() {
		
		List<Book> files = getAllFiles();
		sortFiles(files);
		
		return createStructure(files);
	}

	
	
	@Override
	public ArrayList<Book> getAllFiles() {
		
		final List<Book> books = MainActivity.m_database.getAllBooks();
		return new ArrayList<>(books);
	}

}
