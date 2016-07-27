package com.VladHerescu.BookTranslator.Translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.BookList.ActivityBookList;
import com.VladHerescu.BookTranslator.R;

import Database.Appeareance;
import Database.AttributesAppearences;
import Database.AttributesWord;
import Database.Word;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

/**
 * the activity where the word's translation is shown
 * <br> the examples and definitions
 * @author Vlad Herescu
 *
 */
public class ActivityTranslation extends Activity {

	/**
	 * the word translated
	 */
	Word m_word;
	
	/**
	 * the activity that started ActivityTranslation
	 * <in order to know what actionbar to use>
	 */
	String m_activityStarter;
	
	/**
	 * the headers shown in the expandable list
	 * <li> translation
	 * <li> definition
	 * <li> example
	 */
	List<String> m_headers;
	/**
	 * the definitions and examples shown and how are they mapped to the headers
	 */
	TreeMap<String, List<String>> m_items;
	

	
	
	List<Integer> index_definitions;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_translation);
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expListView);
		 m_word = getIntent().getParcelableExtra("word");
		 Log.i("message", m_word.getM_id() + " ");
		 m_activityStarter = getIntent().getStringExtra(Constants.translation_before_activity);
		 createListHeader();
		 createMap(m_word);
		 ExpandableListAdapter listAdapter = new MyExpandableListAdapter(this, m_headers, m_items, index_definitions);
		 expListView.setAdapter(listAdapter);
		 expandList(expListView);
		 setTitle(m_word.getM_value());
	        getActionBar().setHomeButtonEnabled(true);
		 
		 
	}


	private void expandList(ExpandableListView _listView) {
		for(int i = 0; i < m_headers.size(); i++)
			_listView.expandGroup(i);
		
	}

	private void createMap(Word student) {
		m_items = new TreeMap<String, List<String>>();
		m_items.put(m_headers.get(0),  createSection(student.getM_translation()));
		m_items.put(m_headers.get(1), getDefinitionsWithPartSpeech(student.getM_definition(), student.getM_partSpeech()));
		m_items.put(m_headers.get(2), student.getM_example());
		
		
	}
	
	
	public List<String> getDefinitionsWithPartSpeech(List<String> _definition, List<String> _partSpeech)
	{
		List<String> definitions = new ArrayList<String>();
		index_definitions = new ArrayList<Integer>();
		List<String> partSpeechClone = new ArrayList<String>(_partSpeech);
		Collections.sort(partSpeechClone);
		Set<String> foo = new HashSet<String>(partSpeechClone);
		int j=0, k=0;
		for(String fo : foo)
		{
			definitions.add(fo);
			j++;
			for(int i = 0; i < _partSpeech.size(); i++)
			{
				if(_partSpeech.get(i).equals(fo))
				{
					definitions.add(_definition.get(i));
					index_definitions.add(j++);
					
				}
			}
		}
		
		return definitions;
		

	}
	
	/**
	 * @param _value 
	 * @return : a section belonging to one header in the exp listview
	 * 
	 */
	public ArrayList<String> createSection(String _value)
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add(_value);
		return list;
	}
	
	
	/**
	 * setting the headers in the expandable list
	 */
	public void createListHeader() {
		m_headers = new ArrayList<String>();
		m_headers.add("Translation");
		m_headers.add("Definition");
		m_headers.add("Examples");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_translation, menu);
		
		if(m_activityStarter.equals(Constants.translation_before_internet) ||
				m_activityStarter.equals(Constants.translation_before_textDisplayer))	
		{
			
			MenuItem itemClear = menu.add(Menu.NONE, R.id.itemClearWord, Menu.NONE,"Save");
			itemClear.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
			itemClear.setIcon(getResources().getDrawable(R.drawable.ic_clear_white_24dp));
			
			MenuItem itemSave = menu.add(Menu.NONE, R.id.itemAddWord, Menu.NONE,"Clear");
			itemSave.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
			itemSave.setIcon(getResources().getDrawable(R.drawable.ic_save_white_24dp));
			
			

		}
		if(m_activityStarter.equals(Constants.translation_before_dictionary))
		{
			MenuItem itemClear = menu.add(Menu.NONE, R.id.action_delete, Menu.NONE,"Delete");
			itemClear.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
			itemClear.setIcon(getResources().getDrawable(R.drawable.ic_delete_white_24dp));
		}
		
		
       /*
       if(m_ViewPager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
       		item.setIcon(getResources().getDrawable(R.drawable.ic_done_all_white_36dp));
       else
       		item.setIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_white_24dp));
		}
		*/
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
		if( id == R.id.itemClearWord)
		{
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, resultIntent);
			finish();
		}

		
		if (id == R.id.itemAddWord)
		{
			 int idWord; 
			 if(m_activityStarter.equals(Constants.translation_before_internet))
				 	 idWord = MainActivity.m_database.add_Word_Def_Ex(m_word);
			 else
				 idWord = m_word.getM_id();
			 Log.i("message", "idWordul aici este : " + idWord);
			 Toast.makeText(this, m_word.getM_value() + " succesfully added", Constants.duration_toast).show();
			 Intent resultIntent = new Intent();
			 setResult(Activity.RESULT_OK, resultIntent);
			 resultIntent.putExtra("idWord", idWord);
			 finish();
		}
		if( id == R.id.action_delete)
		{
			
			createDialog();
			
			Log.i("message", "intra in action delete " + m_word.getM_id());
		
		}
		
		if( id == android.R.id.home)
		    NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
		return super.onOptionsItemSelected(item);
	}


	/**
	 * shows the dialog that asks the user if he is sure 
	 * <br> he wants to delete a word
	 */
	public void createDialog() {

		CustomDialog dialog = new CustomDialog(this, R.color.darkRed);
		dialog.setTitle(Constants.m_deleteWord + m_word.getM_value());
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE,Constants.dialogNoButton, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
		});
		
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, Constants.dialogYesButton, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which){
				
				MainActivity.m_database.deleteAppearance(AttributesAppearences.WORD, Integer.toString(m_word.getM_id()));
				MainActivity.m_database.deleteWord(AttributesWord.ID, Integer.toString(m_word.getM_id()));
				
				
				int bookNr = getIntent().getIntExtra("book", -1);
				Log.i("message","numarul cartii este: " + bookNr); 
				if(bookNr != -1)
				{
					List<Appeareance> app = MainActivity.m_database.getAppearances(AttributesAppearences.BOOK, Integer.toString(bookNr));
					if(app == null)
					{
						MainActivity.m_database.deleteBook(bookNr);
						NavUtils.navigateUpTo(ActivityTranslation.this,  new Intent(ActivityTranslation.this,ActivityBookList.class)); 
					}
					
				}
				
				ActivityTranslation.this.finish();
					
			}
		});
		dialog.show();
		
	}
}
