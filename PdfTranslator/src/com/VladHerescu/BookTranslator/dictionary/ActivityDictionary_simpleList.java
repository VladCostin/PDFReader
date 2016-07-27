package com.VladHerescu.BookTranslator.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.ItemAdaptors.ItemAdaptorDictionary;
import com.VladHerescu.BookTranslator.ItemAdaptors.ItemAdaptorWord;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerLogicItf;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerWordsDictionary;
import com.VladHerescu.BookTranslator.Translate.ActivityTranslation;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.dictionary.controller.ControllerDictionary;
import com.VladHerescu.BookTranslator.dictionary.model.CoreDictionary;
import com.VladHerescu.BookTranslator.dictionary.model.ListStructureWord;
import com.VladHerescu.BookTranslator.R;
import com.VladHerescu.BookTranslator.R.id;

import Database.Appeareance;
import Database.AttributesAppearences;
import Database.AttributesBook;
import Database.AttributesWord;
import Database.Book;
import Database.PartSpeech;
import Database.Word;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;

public class ActivityDictionary_simpleList extends Activity implements OnItemClickListener, OnQueryTextListener {
	
	
	static Book m_book;
	
	
	ListView m_listFilesItems;
	
	
	ItemAdaptorWord m_adapter;
	
	ListStructureWord m_structure;
	
	
	SearchView m_searchView;
	
	Bundle m_savedInstanceState;
	
	
	String M_QUERY = "KEY_QUERY_SAVE_STATE";
	
	
	/**
	 * the logic that creates the list structure
	 */
	SectionIndexerWordsDictionary m_structure_itf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_activity_dictionary_simple_list);
		
		m_structure_itf = new SectionIndexerWordsDictionary();
		m_listFilesItems = (ListView) findViewById(R.id.listviewBooksDictionary);
		
		String book = getIntent().getStringExtra(ConstantsTabs.titleBook);
		m_book = MainActivity.m_database.getBook(AttributesBook.TITLE, book);
		setTitle( book.substring(0, Math.min(book.length(), 30)));
		m_structure = m_structure_itf.getListStructure(book);
		m_adapter = new ItemAdaptorWord(this, R.layout.item_list_word_dictionary, m_structure,  R.color.darkRed );
		
		
		m_listFilesItems.setAdapter(m_adapter);
		m_listFilesItems.setOnItemClickListener(this); 
		
		m_savedInstanceState = savedInstanceState;
        getActionBar().setHomeButtonEnabled(true);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dictionary_simple_list, menu);
		
		SearchManager searchManager =
	            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	     m_searchView =
	             (SearchView) menu.findItem(R.id.action_search).getActionView();
	     m_searchView.setSearchableInfo(
	             searchManager.getSearchableInfo(getComponentName()));
	     m_searchView.setOnQueryTextListener(this);
		
	     
	     int searchPlateId = m_searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
	     View searchPlate = m_searchView.findViewById(searchPlateId);
	     searchPlate.setBackgroundColor (Color.TRANSPARENT);
	     
	     
	     
	     if(m_savedInstanceState != null)
	     {
	    	 String text = m_savedInstanceState.getString(M_QUERY);
	    	 if(text.equals("") == false)
	    	 {
	    		 
	    		 menu.findItem(R.id.action_search).expandActionView();
	    		 m_searchView.setQuery(text, false);
	    	 }
	     }
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		m_structure.getM_items().get(position).getM_value();
		Intent intent = new Intent(this, ActivityTranslation.class);
		intent.putExtra("word", m_structure.getM_items().get(position).getM_file());
		intent.putExtra(Constants.translation_before_activity, Constants.translation_before_dictionary);
		intent.putExtra("book", m_book.getId());
		startActivity(intent);
		
	}


	@Override
	public boolean onQueryTextSubmit(String query) {
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = this.getCurrentFocus();
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	    return true;
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		if(newText.equals("")== true)
		 {
			 m_listFilesItems.setFastScrollEnabled(true);
		//	 m_listFilesItems.setFastScrollAlwaysVisible(true);
		 }
		 else
		 {
			 m_listFilesItems.setFastScrollEnabled(false);
			 m_listFilesItems.setFastScrollAlwaysVisible(false);
		 }
		 m_structure_itf.updateListStructureSearch(m_structure, newText);
		 m_adapter.notifyDataSetChanged();
		 
		 return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current game state
	    savedInstanceState.putString(this.M_QUERY, m_searchView.getQuery().toString());
	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	public void onStart()
	{
		super.onStart();
		m_adapter.notifyDataSetChanged();
	}
}
