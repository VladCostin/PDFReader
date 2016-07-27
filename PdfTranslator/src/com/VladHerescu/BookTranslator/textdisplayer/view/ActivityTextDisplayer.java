package com.VladHerescu.BookTranslator.textdisplayer.view;

import java.io.IOException;
import java.util.ArrayList;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.ExceptionHandler;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.dictionary.ActivityDictionary;
import com.VladHerescu.BookTranslator.dictionary.ConstantsTabs;
import com.VladHerescu.BookTranslator.logicMainPage.SharedPreferenceLogic;
import com.VladHerescu.BookTranslator.textdisplayer.logic.ShPref_TextDisplayer;
import com.VladHerescu.BookTranslator.textdisplayer.logic.TextParserLogic;
import com.VladHerescu.BookTranslator.textdisplayer.model.Appearance;
import com.VladHerescu.BookTranslator.textdisplayer.model.Languages;
import com.VladHerescu.BookTranslator.R;
import com.VladHerescu.BookTranslator.R.color;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import Database.Appeareance;
import Database.AttributesBook;
import Database.Book;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


//So for any other FragmentActivity you can reuse the same Fragment.
/**
 * activity where the pdf is shown
 * @author Vlad Herescu
 *
 */
public class ActivityTextDisplayer extends FragmentActivity implements OnSeekBarChangeListener {
	
	
	 /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public static ViewPager m_ViewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter m_PagerAdapter;
    
    
	/**
	 * object containing the book
	 */
 	static PdfReader m_reader;

	
	/**
	 * for each letter an index is been associated
	 */
	//public static 	ArrayList<String> m_letter_index;
	
	
    
    /**
     * the book shown on the display
     * the information regarding the current book are used 
     * for adding new data into the database
     */
    private static Book m_book;
    
    
    
    /**
     * contains data about the appearance of the activity:
     * <br> theme, text size, background color, fg color
     */
    Appearance m_Appearance;


    /**
     * updates the views found in the actionBar and scrollbar
     */
    ActionBarData m_dataActionBar;
    
    static String m_title;
    
    String m_path;
    boolean m_saveFile;
    
    Languages m_languages;
    
    static String STATE_THEME;
    
    
    static LinearLayout layout;
    
    /**
     * parses the text to remove the unwanted \n characters
     */
    TextParserLogic m_parser;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_activity_text_displayer);
        new ShPref_TextDisplayer(this);
        
        m_parser = new TextParserLogic();
 	    m_Appearance= new Appearance(false);
        m_languages = new Languages();
        m_saveFile = true;
     
        m_dataActionBar = new ActionBarData(this);

        
        getActionBar().setHomeButtonEnabled(true);
        try {
			getDataFromActivities();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        m_dataActionBar.initSeekBar( (SeekBar) findViewById(R.id.sliderPdfBook), NUM_PAGES, m_Appearance.getM_IndexPage()); 
     //   initTextFromPdf();
        initViewPager();
        
        
        if (savedInstanceState != null) 
        {
            // Restore value of members from saved state
        	Log.i("message", "themul este: " + savedInstanceState.getBoolean(STATE_THEME));
        	
        	if(savedInstanceState.getBoolean(STATE_THEME) != m_Appearance.isM_darkTheme())
        		changeTheme();
        	
        	//m_Appearance.setM_darkTheme(savedInstanceState.getBoolean(STATE_THEME));
        	//LinearLayout layout =   (LinearLayout) findViewById(R.id.layoutTextDisplayed);
    		//layout.setBackgroundColor(m_Appearance.);
        	//m_dataActionBar.updateIcon(m_Appearance.isM_darkTheme());

        }

        layout = (LinearLayout) findViewById(R.id.layout_slider);
     
        
        
    }



	/**
	 * gets data from the activities that have started ActivityTextDisplayer
	 * such as the title of the book and the page
	 * @throws IOException 
	 */
	public void getDataFromActivities() throws IOException {
        
		
		String filePath = getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer);
		String fileName = getIntent().getStringExtra(Constants.nameFile);
		m_Appearance.setM_IndexPage( getIntent().getIntExtra(Constants.pageSaved, 0)); 
		
		
		m_title = fileName;
		m_path = filePath;
		initReader(filePath);
		setHeaderTitle(fileName);
	
      
		
	}
	
	
	/**
	 * changes the theme of the display
	 */
	public void changeTheme()
	{ 	Log.i("message", "intra in change theme");
		LinearLayout layout =   (LinearLayout) findViewById(R.id.layoutTextDisplayed);
		layout.setBackgroundColor(m_Appearance.changeTheme());
		m_dataActionBar.updateIcon(m_Appearance.isM_darkTheme());
    	resetPageContent();
	}
	
	/**
	 * resets the viewPager after the appeareance has been changed
	 */
	public void resetPageContent()
	{
		
		  int lastPage = m_Appearance.getM_IndexPage();
        m_ViewPager.setAdapter(m_PagerAdapter);
        m_Appearance.setM_IndexPage( lastPage);
    	m_ViewPager.setCurrentItem( m_Appearance.getM_IndexPage() , false);
  
	}
	

	
	private void initReader(String path) throws IOException
	{
		
		Log.i("message", "numele fisieruluie este" + path);
		
	//	 try {
		       	m_reader = new PdfReader( path);
		       	
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
			
	//		Log.i("message", "nu a fost instantiat m_readerul cum trebuie");
	//		e.printStackTrace();
	//	}
		    NUM_PAGES = m_reader.getNumberOfPages();
		    
		    m_Appearance.setM_nrPages(NUM_PAGES); 

	}



	/**
	 * sets the title of the activity as the name of the file to be read
	 * @param _fileName : the title to be set to the activity actionBar
	 */
	public void setHeaderTitle(String _fileName) {
		
		int titleLength;
    	titleLength = _fileName.length();
    	titleLength = titleLength < 50 ? titleLength : 50;
    	setTitle(_fileName.substring(0, titleLength));
		
	}



	/**
	 * checks if in database exists a book with the same name
	 * if it does, do not include it
	 * @param _fileName : the name of the file to be added in the database
	 * @param _filePath : the path of the file to be added in the database
	 */
	public void addBookIfIdDoesNotExist(String _fileName, String _filePath) {
		
		 Log.i("message", "intra in addBookIfItDoesn;t Exist");
		  m_book = MainActivity.m_database.getBook(AttributesBook.TITLE, _fileName);
		  if(m_book != null){
			  Log.i("message", "exista deja");
			  return;
		  }
		  
		  Log.i("message", "nu exista deja");
		  m_book = new Book(_fileName, 0, NUM_PAGES, _filePath);
		  MainActivity.m_database.addBook(m_book);
		  
		  m_book.setId(MainActivity.m_database.getBookId(m_book.getTitle()));
		  
	}



    
    /**
     * initializes the view pager
     */
    public void initViewPager()
    {
    	
        m_ViewPager = (ViewPager) findViewById(R.id.pagerViewBook); 
        m_PagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), this);
        m_ViewPager.setAdapter(m_PagerAdapter);
        
        
        m_ViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	
            	
            	m_Appearance.setM_IndexPage(m_ViewPager.getCurrentItem());
               	m_dataActionBar.updateCurrentPosition(m_Appearance.getM_IndexPage());
                invalidateOptionsMenu();
                m_ViewPager.requestFocus();
            	
            }});
    	

        
		m_ViewPager.setCurrentItem(m_Appearance.getM_IndexPage()); 
    }
    
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);
        
	
       m_Appearance.setM_IndexPage(m_ViewPager.getCurrentItem());
     //   Log.i("message", "ina in onCreateOptionsMenu " + m_Appearance.getM_IndexPage());
       if(m_dataActionBar.m_init == false)
        	m_dataActionBar.setItems(menu.findItem(R.id.itemPageIndex), menu.findItem(R.id.itemChangeTheme), menu.findItem(R.id.itemNRMaxPages), menu.findItem(R.id.action_settings)); 
     
    
        m_dataActionBar.updateAllData(m_Appearance);
      
        
        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        // this is done here and not in the menu xml file because it may change, depending 
        // if it is the last page or not
        // so where are the items added programatically
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (m_ViewPager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
       item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
      
       if(m_ViewPager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
       		item.setIcon(getResources().getDrawable(R.drawable.ic_done_all_white_36dp));
       else
       		item.setIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_white_24dp));
       
       MenuItem itemPrev = menu.findItem(R.id.action_previous);
       if(m_ViewPager.getCurrentItem() == 0)
       		itemPrev.setIcon(getResources().getDrawable(R.drawable.ic_left_24dp_disable));
       else
       		itemPrev.setIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));

       m_ViewPager.requestFocus();
        return true;
    }
	






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
            	m_Appearance.setM_IndexPage(m_ViewPager.getCurrentItem() - 1);
                m_ViewPager.setCurrentItem(m_ViewPager.getCurrentItem() - 1);
            	
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.

            	 
            	
            	if(m_ViewPager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
            	{
            		CustomDialog dialog = new CustomDialog(this, color.darkGreen);
            		dialog.setTitle("Remove book from opened files list?");
            		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes!", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							SharedPreferenceLogic.deleteBook(m_title);
							m_saveFile = false;
							finish();
						}
					});
            		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No!", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							finish();
						}
					});
            		
            		
            		dialog.show();
            	}
            	else
            	{
            		m_Appearance.setM_IndexPage(m_ViewPager.getCurrentItem() + 1);
            		m_ViewPager.setCurrentItem(m_ViewPager.getCurrentItem() + 1);
            	}
				
            		
                 
            	Log.i("message", "am apasat pe action next");
                return true;

            case R.id.itemChangeTheme:
            	changeTheme();
            	break;
        }

        
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	/*
    	if(m_Appearance.textSizeChanged(keyCode) == true)
    	{
    		resetPageContent();
    		return true;
    	}
    	*/
    	
    
    	
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Log.i("message", "a intrat in onActivityResult : " + requestCode + " " + resultCode);
      
      switch(requestCode) {
        case (12) : 
        {
        		addData(resultCode, data);
        		break;
        }
        case(13) :
        {
        		addData(resultCode , data);
        		break;
        }
        
      }
    }
    
    public void addData(int resultCode, Intent data)
    {
    	 if (resultCode == Activity.RESULT_OK)
         {
       	  	
       		
       		int idWord = data.getIntExtra("idWord", -1);
       		if(idWord != -1)
       		{
       		
       			addBookIfIdDoesNotExist(m_title, m_path);
       			MainActivity.m_database.addAppeareance( new Appeareance(ActivityTextDisplayer.getBook().getId(), idWord,  m_dataActionBar.getM_sliderJumpToPage().getProgress()));
       			
       		}
         }
    }
    

    
	
	public void onPause()
	{	
		setProgressCore();
		
		super.onPause();
	}
	

	
	/**
	 * adds the book's bookmark to the shared preferences
	 */
	public void setProgressCore()
	{
		if(m_saveFile == true)
		{
			int progress = m_ViewPager.getCurrentItem();
			
			if(MainActivity.m_sharedPref_logic != null)			
				MainActivity.m_sharedPref_logic.addDataAboutStartedPDF(m_path,    progress, NUM_PAGES);
		}
	}
	


    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	/**
    	 * copy of the activity in order to send it as an instance to the fragment
    	 */
    	ActivityTextDisplayer m_activity;
    	
        /**
         * @param fm
         * @param _activity
         */
        public ScreenSlidePagerAdapter(FragmentManager fm, ActivityTextDisplayer _activity) {
        	
            super(fm);
            m_activity = _activity;
        }

        @Override
        public Fragment getItem(int position) {
       	
            ScreenSlidePageFragment fragment = ScreenSlidePageFragment.create( m_activity.getM_Appearance(), position, m_dataActionBar.getM_sliderJumpToPage());
            m_ViewPager.requestFocus();
            return fragment;
        }
        

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
        
    }
    
    
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		int newPage = seekBar.getProgress();
		
		m_ViewPager.setCurrentItem( newPage, false);
		m_Appearance.setM_IndexPage( newPage);
		
	}


	/**
	 * @return : the book
	 * 
	 */
	public static Book getBook() {
		return m_book;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current game state
	    savedInstanceState.putBoolean(STATE_THEME, m_Appearance.isM_darkTheme());

	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}



	/**
	 * @param book 
	 * 
	 */
	public static void setBook(Book book) {
		ActivityTextDisplayer.m_book = book;
	}



	/**
	 * @return the reader
	 */
	public static PdfReader getReader() {
		return m_reader;
	}



	/**
	 * @return the m_Appearance
	 */
	public Appearance getM_Appearance() {
		return m_Appearance;
	}



	/**
	 * @param m_Appearance the m_Appearance to set
	 */
	public void setM_Appearance(Appearance m_Appearance) {
		this.m_Appearance = m_Appearance;
	}



	/**
	 * @param reader the reader to set
	 */
	public static void setReader(PdfReader reader) {
		ActivityTextDisplayer.m_reader = reader;
	}

	
}



