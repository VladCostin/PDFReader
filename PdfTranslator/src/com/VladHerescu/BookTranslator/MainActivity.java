package com.VladHerescu.BookTranslator;


import java.util.ArrayList;
import java.util.List;

import modelMainPage.Core;

import com.VladHerescu.BookTranslator.BookList.ActivityBookList;
import com.VladHerescu.BookTranslator.ItemAdaptors.ItemAdaptorDictionary;
import com.VladHerescu.BookTranslator.SectionIndexer.SectionIndexerBooksDictionary;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.dictionary.ActivityDictionary;
import com.VladHerescu.BookTranslator.dictionary.ConstantsTabs;
import com.VladHerescu.BookTranslator.logicMainPage.CurrentFileAdapter;
import com.VladHerescu.BookTranslator.logicMainPage.SharedPreferenceLogic;
import com.VladHerescu.BookTranslator.R;









import com.memetix.mst.language.Language;

import Database.Book;
import Database.DatabaseHandler;
import InternetConnection.InternetConnection;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author Vlad Herescu
 *
 */
public class MainActivity extends Activity implements OnClickListener{


	/**
	 * the button used for browsing for a pdf file
	 */
	Button m_buttonSelectPdf;
	
	
	/**
	 * the button which shows the opened pdfs
	 */
	Button m_buttonShowStartedPdf;
	
	/**
	 * the button to show dictionary
	 */
	Button m_buttonShowDictionary;
	
	/**
	 * the button to enter the activity where the user can exercise the vocabulary
	 */
	Button m_buttonShowExerciseActivity;
	
	TextView m_textViewTitle;
	
	

	
	
	
	/**
	 * code associated with the information received from ActivityTextDispayer
	 */
	static String codeExtraReceiveFromReader = "NAME_PAGE";
	
	
	/**
	 * contains the data saved such as the dictionary, info about the books, etc
	 */
	public static DatabaseHandler m_database;
	
	
	/**
	 * the ListView used to open a started book at a specified page 
	 */
	ListView m_viewOpenPdf;
	
    /**
     * showing loading circle/bar when the user selects to continue reading a book
     */
    public static ProgressDialog m_loadBook;
	

    
    /**
     * the dialog is shown in case a button is pressed
     * <br> used as a member to close it when hte activity stops
     */
    Dialog m_dialogOpened;
    
	
    /**
     * class that deals with retrieving data from shared preferences and update it
     */
    public static SharedPreferenceLogic m_sharedPref_logic;
    
    Activity m_activity;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Core();
		
		
		m_loadBook = new ProgressDialog(this, R.style.CustomDialog);	
	     //  m_linProgressBar.setVisibility(View.VISIBLE);
		
		m_buttonSelectPdf = (Button) findViewById(R.id.buttonChoosePdfFile);
		m_buttonSelectPdf.setOnClickListener(this);
		
		
		m_buttonShowStartedPdf = (Button) findViewById(R.id.buttonOpenLastPdfFile);
		m_buttonShowStartedPdf.setOnClickListener(this);
		
		m_buttonShowDictionary = (Button) findViewById(R.id.buttonOpenDictionary);
		m_buttonShowDictionary.setOnClickListener(this);
		
		
		m_buttonShowExerciseActivity = (Button) findViewById(R.id.buttonExerciseDictionary);
		m_buttonShowExerciseActivity.setOnClickListener(this);
		m_textViewTitle = (TextView) findViewById(R.id.textViewTitleMainPage);
		

		
		m_activity = this;
		
		
	//	m_buttonTranslate = (Button) findViewById(R.id.bu)
		
		m_database = new DatabaseHandler(this);
		m_sharedPref_logic = new SharedPreferenceLogic(this);
		Log.i("message", "intra iar in onCreate");
		//m_sharedPref_logic.deleteAllBooks();
		/*
		m_database.deleteAllApp();
		m_database.deleteAllWords();
		m_database.deleteAllBooks();
		m_database.deleteAllDefinitions();
		m_database.deleteAllExamples();
		*/
		setSize();
		
	}
	
	
	public void setColors() {
		
		Log.i("message", "intra in setColors");
		
		if(m_sharedPref_logic.getSetDataBookSharedPreferences().size() == 0)
			setColorButton(m_buttonShowStartedPdf, getResources().getDrawable(R.drawable.round_button_grey_green), getResources().getColor(R.color.grey), false);
		else
			setColorButton(m_buttonShowStartedPdf, getResources().getDrawable(R.drawable.round_button_green), getResources().getColor(R.color.white), true);
		
		if(m_database.getAllBooks().size() == 0)
		{
			setColorButton(m_buttonShowDictionary, getResources().getDrawable(R.drawable.round_button_grey_red), getResources().getColor(R.color.grey), false);
			setColorButton(m_buttonShowExerciseActivity, getResources().getDrawable(R.drawable.round_button_grey_purple), getResources().getColor(R.color.grey), false);
		}
		else
		{
			setColorButton(m_buttonShowDictionary, getResources().getDrawable(R.drawable.round_button), getResources().getColor(R.color.white), true);
			setColorButton(m_buttonShowExerciseActivity, getResources().getDrawable(R.drawable.round_button_menu_yellow), getResources().getColor(R.color.white), true);
		}
		
	}
	
	public void setColorButton(Button _button, Drawable _background,int _color, boolean _enable )
	{
		_button.setBackground(_background);
		_button.setTextColor(_color);
		_button.setEnabled(_enable);
	}


	public void setSize()
	{
		if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			m_textViewTitle.setTextSize(100);
			m_buttonSelectPdf.setTextSize(35);
			m_buttonShowDictionary.setTextSize(35);
			m_buttonShowExerciseActivity.setTextSize(35);
			m_buttonShowStartedPdf.setTextSize(35);
		}
		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			m_textViewTitle.setTextSize(50);
			m_buttonSelectPdf.setTextSize(20);
			m_buttonShowDictionary.setTextSize(20);
			m_buttonShowExerciseActivity.setTextSize(20);
			m_buttonShowStartedPdf.setTextSize(20);
		}

		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			m_textViewTitle.setTextSize(50);
			m_buttonSelectPdf.setTextSize(20);
			m_buttonShowDictionary.setTextSize(20);
			m_buttonShowExerciseActivity.setTextSize(20);
			m_buttonShowStartedPdf.setTextSize(20);
		}
		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
		//	Toast.makeText(m_activity, "SMALL", Toast.LENGTH_LONG);
			m_textViewTitle.setTextSize(150);
			m_buttonSelectPdf.setTextSize(50);
			m_buttonShowDictionary.setTextSize(50);
			m_buttonShowExerciseActivity.setTextSize(50);
			m_buttonShowStartedPdf.setTextSize(50);
		}
		
		
	}
	public void onResume()
	{
		Log.i("message", "intra aici in onResume");
		setColors();
		super.onResume();
		

	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		if( ( ( Button) v) == m_buttonSelectPdf )
		{ 
			//	Intent intent = new Intent(this, FileBrowser.class);
			//	startActivity(intent);
			Intent intent = new Intent(this,  ActivityBookList.class);
			intent.putExtra(Constants.m_activityBookList, Constants.m_activityOpenPDF);
			startActivity(intent);
				
		}
			
		if( ( (Button) v ) == m_buttonShowStartedPdf)
		{	
				Log.i("message", "cacat");
				m_dialogOpened = openedPdfFiles();
				m_dialogOpened.show();
				
		}
		
		if( ( (Button) v) == m_buttonShowDictionary )
		{
			Intent intent = new Intent(this, ActivityBookList.class);
			intent.putExtra(Constants.m_activityBookList, Constants.m_activityDictionary);
			startActivity(intent);
			
			//m_dialogOpened = showDialogDictionary();
			//m_dialogOpened.show();
		}
		
		if(  ((Button) v) == m_buttonShowExerciseActivity )
		{
			Intent intent = new Intent(this,  ActivityBookList.class);
			intent.putExtra(Constants.m_activityBookList, Constants.m_activityExercise);
			startActivity(intent);
		}
		

		
	}

	
	
	/**
	 * shows the user the pdf started to be read
	 * @return : the dialog where the user selects the book he wants to continue reading 
	 */
	public Dialog openedPdfFiles()
	{
		Log.i("message", "intra in openedPdfFiles");
		final CustomDialog customDialog = new CustomDialog(this, R.color.darkGreen);
		LayoutInflater inflater = this.getLayoutInflater();
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);
		List<String> _openedFiles = m_sharedPref_logic.getSetDataBookSharedPreferences();
		
		customDialog.setTitle(getResources().getString(R.string.dialogSelectMarkBook));
		customDialog.setView(body);
		
		CurrentFileAdapter adapter = new CurrentFileAdapter(this, R.layout.item_list_open_current_files,_openedFiles, customDialog);
		m_viewOpenPdf = (ListView) body.findViewById(R.id.listviewDialogOpenStartedBooks); 		
		m_viewOpenPdf.setAdapter(adapter);
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setColors();
			}
		});
		
		customDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
               setColors();

            }
        });
	
		return customDialog;
		
	}
	
	public void onStop()
	{
		if(m_dialogOpened!= null)
			m_dialogOpened.cancel();
		super.onStop();
	}
	
	
	/**
	 * shows the dictionary for a book selected from the list
	 * it sends to ActivityDictionary data as the id of the book and the title
	 * @param book 
	 * 
	 */
	public  void openActivityShowDictionary(Book book) {
		
		Intent intent  = new Intent(this, ActivityDictionary.class);
		intent.putExtra(ConstantsTabs.idBook, book.getId());
		intent.putExtra(ConstantsTabs.titleBook, book.getTitle());
		
		startActivity(intent); 
		
	}
	



}







