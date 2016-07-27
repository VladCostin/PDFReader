package com.VladHerescu.BookTranslator.BookList;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.ItemAdaptors.ItemAdaptorDictionary;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerBooksDictionary;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerLogic;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerLogicItf;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.dictionary.ActivityDictionary;
import com.VladHerescu.BookTranslator.dictionary.ActivityDictionary_simpleList;
import com.VladHerescu.BookTranslator.dictionary.ConstantsTabs;
import com.VladHerescu.BookTranslator.exercise.ActivityExercise;
import com.VladHerescu.BookTranslator.textdisplayer.view.ActivityTextDisplayer;
import com.VladHerescu.BookTranslator.R;
import com.VladHerescu.BookTranslator.R.id;
import com.VladHerescu.BookTranslator.R.layout;
import com.VladHerescu.BookTranslator.R.menu;

import Database.Book;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class ActivityBookList extends Activity implements OnQueryTextListener, OnItemClickListener
 {
	
	String m_theme;
	
	ListStructure m_structure;

	ItemAdaptorDictionary m_adapter;
	
	
	ListView m_listFilesItems;
	
	int m_colorHeader;
	
	
	String M_QUERY = "KEY_QUERY_SAVE_STATE";
	
	SearchView m_searchView;
	
	/**
	 * the logic that creates the list structure
	 */
	SectionIndexerLogicItf m_structure_itf;
	
	Bundle m_savedInstanceState;
	
	ProgressDialog m_progress;
	
	Activity m_activity;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setTheme();
		setContentView(R.layout.activity_activity_book_list);
        getActionBar().setHomeButtonEnabled(true);

	
	
		
		m_structure = m_structure_itf.getListStructure();
		m_listFilesItems = (ListView) findViewById(R.id.listviewBooksDictionary);

		
		m_adapter = new ItemAdaptorDictionary(this, R.layout.item_list_dictionary, m_structure,  m_colorHeader );
		



		m_listFilesItems.setAdapter(m_adapter);
		m_listFilesItems.setOnItemClickListener(this); 

		m_savedInstanceState = savedInstanceState;
		m_activity = this;
		
	}

	private void setTheme() 
	{
		Intent intent = getIntent();
		m_theme = intent.getStringExtra(Constants.m_activityBookList);
		if(m_theme == null)
			m_theme = Constants.m_activityDictionary;
		
		if(m_theme.equals(Constants.m_activityExercise))
		{
			setTheme(R.style.MyCustomThemeExercise);
			
			setTitle("Exercise vocabulary");
			m_colorHeader = R.color.darkPurple;
			m_structure_itf = new SectionIndexerBooksDictionary();
		}
		if(m_theme.equals(Constants.m_activityDictionary))
		{
			setTheme(R.style.MyCustomTheme);
			setTitle("See dictionary");
			m_colorHeader = R.color.darkRed;
			m_structure_itf = new SectionIndexerBooksDictionary();
		}
		if(m_theme.equals(Constants.m_activityOpenPDF))
		{
			
			
			setTheme(R.style.MyCustomThemeBrowser);
			setTitle("Open a book");
			m_colorHeader = R.color.darkBlue;
			m_structure_itf = new SectionIndexerLogic();
		}
		
	}
	
	public void onResume()
	{
		super.onResume();
		Intent intent = getIntent();
		Log.i("message", "in onResume: " + intent.getStringExtra(Constants.m_activityERROROpenPDF));
		if(intent.getStringExtra(Constants.m_activityERROROpenPDF) != null)
		if(intent.getStringExtra(Constants.m_activityERROROpenPDF).equals(Constants.m_activityERROROpenPDF))
		{
			intent.removeExtra(Constants.m_activityERROROpenPDF);
			Log.i("message", "intra si aici in if-ul asta");
			CustomDialog dialog = new CustomDialog(this, R.color.darkBlue);
			dialog.setTitle(Constants.error_parsing);
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.show();
		}
		if(m_listFilesItems.getCount() == 0 && m_theme.equals(Constants.m_activityOpenPDF) == false)
			finish();
		if(m_listFilesItems.getCount() == 0 && m_theme.equals(Constants.m_activityOpenPDF) == true)
		{
			CustomDialog dialog = new CustomDialog(this, R.color.darkBlue);
			dialog.setTitle("There are no pdf files stored in your devices. Please store the files you want to open");
			dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					m_activity.finish();
					
				}
			});
			dialog.setOnDismissListener( new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					m_activity.finish();
					
				}
			});
			dialog.show();
		}
		
		Log.i("message", "dimensiunea lsitei este :" + m_listFilesItems.getCount());


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_list, menu);
		
	    SearchManager searchManager =
	            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	     m_searchView =
	             (SearchView) menu.findItem(R.id.action_search).getActionView();
	     m_searchView.setSearchableInfo(
	             searchManager.getSearchableInfo(getComponentName()));
	     m_searchView.setOnQueryTextListener(this);
	     
	     if(m_savedInstanceState != null)
	     {
	    	 String text = m_savedInstanceState.getString(M_QUERY);
	    	 if(text.equals("") == false)
	    	 {
	    		 
	    		 menu.findItem(R.id.action_search).expandActionView();
	    		 m_searchView.setQuery(text, false);
	    	 }
	     }
	     
	     
	     
	     int searchPlateId = m_searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
	     View searchPlate = m_searchView.findViewById(searchPlateId);
	     searchPlate.setBackgroundColor (Color.TRANSPARENT);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	//	if (id == R.id.action_settings) {
	//		return true;
	//	}
	/*	if( id == android.R.id.home)
		{
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
		}
		*/
		
		  switch (item.getItemId()) {
		  	case android.R.id.home:
              NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
              return true;
		  }
		
		return super.onOptionsItemSelected(item);
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
			 m_listFilesItems.setFastScrollAlwaysVisible(true);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Book file = m_structure.getM_items().get(position).getM_file();
		if(m_theme.equals(Constants.m_activityOpenPDF))
		{
			try
			{
			loadProgressDialog(file.getTitle());
			Intent intent = new Intent(this, ActivityTextDisplayer.class);
			intent.putExtra(Constants.nameExtraStarttextDisplayer, file.getPath());
			intent.putExtra(Constants.nameFile, file.getTitle());
			startActivity(intent);
			}
			catch(Exception e)
			{
				Log.i("message", "EROARE");
			}
		}
		if(m_theme.equals(Constants.m_activityExercise))
		{
			Intent intent = new Intent(this,  ActivityExercise.class);
			intent.putExtra(Constants.nameFile, file.getTitle());
			startActivity(intent);
		}
		if(m_theme.equals(Constants.m_activityDictionary))
			startActivity(parent.getItemAtPosition(position).toString());
		
	}
	
	/**
	 * shows the words in the dictionary from a book selected or from all books
	 * @param titleBook : the title of the book of which the words will be shown
	 * 
	 */
	public void startActivity(String titleBook)
	{			
		
    	Intent intent = new Intent(this, ActivityDictionary_simpleList.class);
    	intent.putExtra(ConstantsTabs.titleBook, titleBook);
    	startActivity(intent);
	}
	
	private void loadProgressDialog(String _title) {
		 m_progress = new ProgressDialog(this, R.style.CustomDialog);
		 m_progress.setMessage("Loading " + _title);
		 m_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 m_progress.setIndeterminate(true);
		 m_progress.show();
		
	}
	
	public void onStop()
	{
		super.onStop();
		if(m_progress != null)
			m_progress.cancel();
	}
	


	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current game state
	    savedInstanceState.putString(this.M_QUERY, m_searchView.getQuery().toString());
	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}
}


class MySearchView extends SearchView{
	
	public MySearchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	
}

