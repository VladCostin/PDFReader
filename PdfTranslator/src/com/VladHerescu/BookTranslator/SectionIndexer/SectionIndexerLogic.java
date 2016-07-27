package com.VladHerescu.BookTranslator.SectionIndexer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.VladHerescu.BookTranslator.browse.model.ItemList;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.browse.model.Section;
import com.VladHerescu.BookTranslator.browse.model.TypeItem;

import Database.Book;
import android.os.Environment;
import android.util.Log;

/**
 * contains the methods to create the List Structure
 * @author Vlad Herescu
 *
 */
public class SectionIndexerLogic implements SectionIndexerLogicItf{

	

	@Override
	public ListStructure getListStructure() {
		
		List<Book> files = getAllFiles();
		sortFiles(files);
		Log.i("message", "dupa sortare");
	//	printFiles(files);
		return createStructure(files);
	}

	
	
	@Override
	public ArrayList<Book> getAllFiles() {
		
		File m_root = new File(Environment.getExternalStorageDirectory().toString() );
		ArrayList<Book> allFiles = getFilesFromDirectory(m_root, null);
		return allFiles;
	}
	

	
	
	/**
	 * gets recursively all the files files from folders
	 * @param _currentFile : the current folder the search is done
	 * @param _searchName : in case there is a condition regarding the name of the file
	 * @return : all the pdfs from the root and obtained recursively
	 */
	public ArrayList<Book> getFilesFromDirectory(File _currentFile, String _searchName)
	{
		ArrayList<Book> files = new ArrayList<Book>();
		if( _currentFile.isFile() == true ) 
		{
			
			if(_searchName != null)
			{
				if(_currentFile.getName().endsWith("pdf") == true && _currentFile.getName().toLowerCase().contains(_searchName.toLowerCase() ) == true)
				{
					files.add( new Book(_currentFile));
				}
			}
			else
				if(_currentFile.getName().endsWith("pdf") == true)
					files.add(new Book(_currentFile));
					
			return files;
		}
		
		
		for(File file : _currentFile.listFiles())
			files.addAll( getFilesFromDirectory(file, _searchName));
		return files;
		
		
	}


	@Override
	public List<Book> sortFiles(List<Book> _files) {
		Collections.sort(_files, new Comparator<Book>() {

			@Override
			public int compare(Book lhs, Book rhs) {
					
				String nameLHS = lhs.getTitle().toLowerCase().replaceAll("\\W", "");
				String nameRHS = rhs.getTitle().toLowerCase().replaceAll("\\W", "");
				

				
				return nameLHS.compareTo(nameRHS);
					
			}
		});
			
		return _files;
	}



	@Override
	public ListStructure createStructure(List<Book> _file) {
		
		
		List<Section> sections = new ArrayList<Section>();
		List<ItemList> items = new ArrayList<ItemList>();
		List<String> values = new ArrayList<String>();
		List<String> alphabet = this.getAllLettersAlphabet();
		
		
		int indexItem= 0;
		int indexSection = 0;
	     int i, indexLeft = 0;
	//	 printFiles( _file);
		
		
		for(String letter : alphabet)
		{
			Section section = new Section(indexItem, null, letter, true);
			ItemList itemHeader = new ItemList(letter, TypeItem.HEADER, indexSection, null);
		//	Log.i("message", "litera este : " + letter);
			
			
			
			//items.put(indexItem++, itemHeader);
			//sections.put(indexSection, section);
			sections.add(section);
			items.add(itemHeader);
			values.add(letter);
			indexItem++;
			for(i = indexLeft; i < _file.size(); i++)
			{
				Book f = _file.get(i);
				String name = f.getTitle();
			//	Log.i("message", "numele fisierului este : " + name.toLowerCase().replaceAll("\\W", ""));
				
				
				
				if(name.toLowerCase().replaceAll("\\W", "").startsWith(letter.toLowerCase()) == false &&
					startsWithNumber(letter, name) == false)	
					
					break;
				
				ItemList item = new ItemList(name, TypeItem.ITEM, indexSection, f);
				items.add(item);
				values.add(name);
				indexItem++;
			}
			
			
			if(i == indexLeft)
			{
				sections.remove(section);
				items.remove(itemHeader);
				indexItem--;
				values.remove(letter);
			}
			else
			{
		
			indexLeft = i;
			indexSection++;
			}
			 
			
		}
		
		
		
		return new ListStructure(sections, items,values);
			
	}
	
	

	@Override
	public void updateListStructureSearch(ListStructure _structure, String _searchFile)
	{
		
		
		List<ItemList> itemsUpdate = new ArrayList<ItemList>();
		List<String> values = new ArrayList<String>();
		for(ItemList item : _structure.getM_permanentItems())
		{
			if(_searchFile == null || _searchFile.equals("") == true)
			{
				itemsUpdate.add(item);
				values.add(item.getM_value());
			}
			else
			{
			if(item.getM_file() != null)
			{
				if(item.getM_file().getTitle().toLowerCase().contains(_searchFile.toLowerCase()) == true)
				{
					itemsUpdate.add(item);
					values.add(item.getM_value());
				}
			}
			}

		}

		
		_structure.setM_items(itemsUpdate);
		_structure.setM_itemValues(values);
		
	}
	
	
	
	
	
	/**
	 * used to print the name of the files shown in the lis
	 * @param _files : the files to be printed
	 */
	public void printFiles(List<Book> _files)
	{	
		for(Book _file : _files)
		{
			Log.i("message",_file.getTitle().replaceAll("\\W", ""));
		}
		
	}
	
	/**
	 * used to create all the headers, made of simple letters
	 * @return list containing all the letter of the alphabet
	 */
	public List<String> getAllLettersAlphabet() {
		
		ArrayList<String> m_letters = new ArrayList<String>();
		
		m_letters.add("#");
		m_letters.add("A");
		m_letters.add("B");
		m_letters.add("C");
		m_letters.add("D");
		m_letters.add("E");
		m_letters.add("F");
		m_letters.add("G");
		m_letters.add("H");
		m_letters.add("I");
		m_letters.add("J");
		m_letters.add("K");
		m_letters.add("L");
		m_letters.add("M");
		m_letters.add("N");
		m_letters.add("O");
		m_letters.add("P");
		m_letters.add("Q");
		m_letters.add("R");
		m_letters.add("S");
		m_letters.add("T");
		m_letters.add("U");
		m_letters.add("V");
		m_letters.add("W");
		m_letters.add("X");
		m_letters.add("Y");
		m_letters.add("Z");
		
		return m_letters;
	}
	
	/**
	 * checks if the file name starts with a number 
	 * @param _letter 
	 * @param _name 
	 * @return : true if the file starts with a number
	 */
	public boolean startsWithNumber(String _letter, String _name)
	{
		
		String name = _name.replace("[", "");
		String letter = name.substring(0, 1).toUpperCase();
		
	//	Log.i("message", name + " " + letter);
		
		if(letter.matches("[0-9]") && _letter=="#")
			return true;
		
		
		return false;
	}


}
