package com.VladHerescu.BookTranslator.textdisplayer.view;

import com.VladHerescu.BookTranslator.textdisplayer.model.Appearance;
import com.VladHerescu.BookTranslator.R;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * contains the items shown in the actionBar
 * @author Vlad Herescu
 *
 */
public class ActionBarData{
	
    /**
     * the item associated with the item where the number of the page is shown
     */
    static MenuItem m_item_IdPage;
    
    
    /**
     * the button for changing the theme
     */
    static MenuItem m_item_ChangeTheme;
    
    
    /**
     * shows the number of pages of the book
     */
    static MenuItem m_item_NR_MAX_Pages;
    
    
    /**
     * associated with the settings button
     */
    static MenuItem m_settings;
    
    
	/**
	 * the slider used to jump to other page
	 */
	private static SeekBar  m_sliderJumpToPage;
	
	/**
	 * instance to the activity that contains the ActionBarData
	 */
	ActivityTextDisplayer m_textDisplayer;
	
	
	
	 /**
	 * place where the user can specify the page he wants to jump to
	 */
	public static EditText txtSearch;
	
	/**
	 * if the menu items have been initialized
	 */
	boolean m_init;
	
	/**
	 * @param _textDisplayer
	 */
	public ActionBarData(ActivityTextDisplayer _textDisplayer) {
		m_textDisplayer = _textDisplayer;
	}
	
	
	
	/**
	 * updating the all the icons with the data received from Appearance
	 * @param _app : contains the data the items will be switch with
	 */
	public void updateAllData(Appearance _app)
	{
		updateCurrentPosition(_app.getM_IndexPage());
		updateIcon(_app.isM_darkTheme());
		m_init = false;
	}
	
	/**
	 * setting the items
	 * @param _indexPage : the item associated with the number of the page
	 * @param _changeTheme : the item associated with changing the theme
	 * @param _nrPages 
	 * @param _settings 
	 */
	public void setItems(MenuItem _indexPage, MenuItem _changeTheme, MenuItem _nrPages, MenuItem _settings)
	{

		m_item_IdPage = _indexPage;
		m_item_ChangeTheme = _changeTheme;
		m_item_NR_MAX_Pages = _nrPages;
		m_settings = _settings;
		
		m_settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				
				
				return false;
			}
		});
		
		
		m_init = true;

		View v = (View) m_item_IdPage.getActionView();
       txtSearch = (EditText) v.findViewById(R.id.Edittxt_pageIndex); 
       txtSearch.setOnEditorActionListener(new OnEditorActionListener() 
       {
		
    	   @Override
    	   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

    			
    			InputMethodManager imm = (InputMethodManager)m_textDisplayer.getSystemService(Context.INPUT_METHOD_SERVICE);
    			View view = m_textDisplayer.getCurrentFocus();
    		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    		   
    	
    		    
    		    int page = Integer.parseInt( v.getText().toString());
    		    page--;
    		    ActivityTextDisplayer.m_ViewPager.setCurrentItem( page, false);
    			m_sliderJumpToPage.setProgress(page);
    		   txtSearch.clearFocus();
    		  return true;
    	   }
       });
       
       m_item_NR_MAX_Pages.setTitle("/"+ m_textDisplayer.m_Appearance.getM_nrPages());
		
	}
    
    
	/**
	 * updates the index of the current page from the action bar and the index 
	 * <br> of the scrollbar
	 * @param _indexPage : updating the data with the new indexPage
	 */
	public void updateCurrentPosition(int _indexPage) 
	{
		if(  m_item_IdPage != null && txtSearch!= null ) // it might be null if getItem from ScreenSlidePagerAdapter
		{ // is called before onCreateOptionsMenu
			Log.i("message", "in updateCurrentPosition, valaorea este: " + _indexPage);
		//	if(_indexPage == 0)
		//	{
	//			txtSearch.setText("");
			//	txtSearch.setText(String.valueOf(1 ));
	//		}
	//		else
				txtSearch.setText(String.valueOf(_indexPage + 1));
		//	m_item_IdPage.setTitle(Integer.toString(_indexPage));
			m_sliderJumpToPage.setProgress(_indexPage);

			
		}
	}
	public void updateCurrentPosition2(int _indexPage) 
	{
		if(  m_item_IdPage != null && txtSearch!= null ) // it might be null if getItem from ScreenSlidePagerAdapter
		{ // is called before onCreateOptionsMenu
			Log.i("message", "in updateCurrentPosition, valaorea este: " + _indexPage);
		//	if(_indexPage == 0)
		//	{
	//			txtSearch.setText("");
			//	txtSearch.setText(String.valueOf(1 ));
	//		}
	//		else
		//		txtSearch.setText(String.valueOf(_indexPage + 1));
			
		}
	}
	
	
	
	/**
	 * updating the theme with the theme received 
	 * @param _darkTheme : if the new theme is dark
	 */
	public void updateIcon(boolean _darkTheme)
	{
		//if(m_Appearance.isM_darkTheme() == true)
		if(m_item_ChangeTheme == null)
			Log.i("message", "m_item_changeTheme este null");
		
		if(_darkTheme == true)
			m_item_ChangeTheme.setIcon(R.drawable.ic_theme_light);
		else
			m_item_ChangeTheme.setIcon(R.drawable.ic_theme_dark);
	}
	

	/**
     * instantiate the Seekbar to jump to a page 
	 * @param _seekBar : the bar the user can use to jump to anther file
	 * @param _MAX_PAGES : maxim number of pages
	 * @param _progress : the progress the user has done so far
     */
    public void initSeekBar(SeekBar _seekBar, int _MAX_PAGES, int _progress)
    {
        
      m_sliderJumpToPage = _seekBar;
      m_sliderJumpToPage.setMax(_MAX_PAGES - 1);
      m_sliderJumpToPage.setProgress(_progress);
	  m_sliderJumpToPage.setOnSeekBarChangeListener(m_textDisplayer);
		
    }



	/**
	 * @return the m_sliderJumpToPage
	 */
	public SeekBar getM_sliderJumpToPage() {
		return m_sliderJumpToPage;
	}



	/**
	 * @param m_sliderJumpToPage the m_sliderJumpToPage to set
	 */
	public static void setM_sliderJumpToPage(SeekBar m_sliderJumpToPage) {
		ActionBarData.m_sliderJumpToPage = m_sliderJumpToPage;
	}

}
