package com.VladHerescu.BookTranslator.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Database.Appeareance;
import Database.AttributesAppearences;
import Database.AttributesBook;
import Database.AttributesWord;
import Database.Book;
import Database.Word;
import android.util.Log;

import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.browse.model.ItemList;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.browse.model.Section;
import com.VladHerescu.BookTranslator.browse.model.TypeItem;
import com.VladHerescu.BookTranslator.dictionary.model.ItemListWord;
import com.VladHerescu.BookTranslator.dictionary.model.ListStructureWord;
import com.VladHerescu.BookTranslator.dictionary.model.Section_Word;

public class SectionIndexerWordsDictionary{
	
	
	public ListStructureWord getListStructure(String _title) {
		
		List<Word> files = getWordsFromBook( _title);
		sortFiles(files);
		
		return createStructure(files);
	}
	
	public ArrayList<Word> getWordsFromBook(String _title) {
		
		ArrayList<Word> words = new ArrayList<Word>();
		Book book = MainActivity.m_database.getBook(AttributesBook.TITLE, _title);
		
		
		List<Appeareance> apps = MainActivity.m_database.getAppearances(
				 AttributesAppearences.BOOK, Integer.toString( book.getId()));
			
			
			if(apps == null)
				Log.i("message", "este app null");
			else{
				for(Appeareance app : apps )
				{
					
				//	Log.i("message","ActivityDictionary - getBookData" + app.getId() + " " + app.getIdBook() + " " + app.getIdWord() + " " + app.getPage());
					
					Word word = MainActivity.m_database.getWord(AttributesWord.ID, Integer.toString( app.getIdWord()) );
					words.add(word);
					
				}

		}
			
	    return words;
	}
	

	public List<Word> sortFiles(List<Word> _words) {
		Collections.sort(_words, new Comparator<Word>() {

			@Override
			public int compare(Word lhs, Word rhs) {
					
				String nameLHS = lhs.getM_value().replace("[", "").toLowerCase();
				String nameRHS = rhs.getM_value().replace("[", "").toLowerCase();
				
				return nameLHS.compareTo(nameRHS);
					
			}
		});
			
		return _words;
	}
	
	

	public ListStructureWord createStructure(List<Word> _file) {
		
		
		List<Section_Word> sections = new ArrayList<Section_Word>();
		List<ItemListWord> items = new ArrayList<ItemListWord>();
		List<String> values = new ArrayList<String>();
		List<String> alphabet = getAllLettersAlphabet();
		
		
		int indexItem= 0;
		int indexSection = 0;
		int i, indexLeft = 0;
		
		
		for(String letter : alphabet)
		{
			Section_Word section = new Section_Word(indexItem, null, letter, true);
			ItemListWord itemHeader = new ItemListWord(letter, TypeItem.HEADER, indexSection, null);
		//	Log.i("message", "litera este : " + letter);
			
			
			
			//items.put(indexItem++, itemHeader);
			//sections.put(indexSection, section);
			sections.add(section);
			items.add(itemHeader);
			values.add(letter);
			indexItem++;
			for(i = indexLeft; i < _file.size(); i++)
			{
				Word f = _file.get(i);
				String name = f.getM_value();
		
				if(name.toLowerCase().startsWith(letter.toLowerCase()) == false)	
						
						break;
				
				
				ItemListWord item = new ItemListWord(name, TypeItem.ITEM, indexSection, f);
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
		
		
		
		return new ListStructureWord(sections, items,values);
			
	}
	
	

	public void updateListStructureSearch(ListStructureWord _structure, String _searchFile)
	{
		
		
		List<ItemListWord> itemsUpdate = new ArrayList<ItemListWord>();
		List<String> values = new ArrayList<String>();
		for(ItemListWord item : _structure.getM_permanentItems())
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
				if(item.getM_file().getM_value().toLowerCase().contains(_searchFile.toLowerCase()) == true)
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

}
