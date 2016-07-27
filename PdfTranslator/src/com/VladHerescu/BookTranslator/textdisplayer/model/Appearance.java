package com.VladHerescu.BookTranslator.textdisplayer.model;


import android.graphics.Color;
import android.view.KeyEvent;

/**
 * describes the apperance of the activity : the size of the text,
 * luminosity, etc
 * @author Vlad Herescu
 *
 */
public class Appearance
{
    /**
     * specifies if the theme os dark or light
     */
    boolean m_darkTheme;
    
    /**
     * the size of the text displayed 
     */
    int m_textSize;
    
    
   /**
    *  the color of the background
    */
    int m_color_Background;
   
   /**
    *  the color of the foreground
 	*/
    int m_color_ForeGround;
    
    
    /**
     * the current page to be shown
     */
    Integer m_IndexPage;
    
    
    /**
     * the number of pages of the book
     */
    Integer m_nrPages;
    
    /**
     * if the action bar is shown
     * <br> it dissapears using hide when the user double clicks the screen
     * <br> it appears again when the user taps the screen again
     */
    boolean m_actionBarShown;
    
    /**
     * initializing the appearance 
     */
    public Appearance(boolean _darkTheme) {
		
    	m_darkTheme = _darkTheme;
    	
    	m_textSize = 24;
    	
    	m_color_Background = Color.WHITE;
    	
    	m_color_ForeGround = Color.BLACK;
    	m_actionBarShown = true;
    	
	}
    
    
    /**
     * @param keyCode : the code assocaited with the key pressed
     * @return : true if the key volume was pressed
     * 
     */
    public  boolean textSizeChanged(int keyCode)
    {
    	if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && m_textSize > 15))
    	{
    		m_textSize--;
         	return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP && m_textSize < 30))
        {
        	m_textSize++;
        	return true;
        }
    	
    	return false;
    }
    
    
    /**
     * changing the theme of the screen
     * @return : returning the new color
     */
    public int changeTheme()
    {

		if(m_darkTheme == true)
		{
			m_darkTheme = false;
			m_color_Background = Color.WHITE;
			m_color_ForeGround = Color.BLACK;
		}
		else
		{
			m_darkTheme = true;
			m_color_Background = Color.BLACK;
			m_color_ForeGround = Color.WHITE;
			
		}
		
		return m_color_Background;
    }
    
    


	/**
	 * @return the m_darkTheme
	 */
	public boolean isM_darkTheme() {
		return m_darkTheme;
	}


	/**
	 * @param m_darkTheme the m_darkTheme to set
	 */
	public void setM_darkTheme(boolean m_darkTheme) {
		this.m_darkTheme = m_darkTheme;
	}


	/**
	 * @return the m_textSize
	 */
	public int getM_textSize() {
		return m_textSize;
	}


	/**
	 * @param m_textSize the m_textSize to set
	 */
	public void setM_textSize(int m_textSize) {
		this.m_textSize = m_textSize;
	}


	/**
	 * @return the m_color_Background
	 */
	public int getM_color_Background() {
		return m_color_Background;
	}


	/**
	 * @param m_color_Background the m_color_Background to set
	 */
	public void setM_color_Background(int m_color_Background) {
		this.m_color_Background = m_color_Background;
	}


	/**
	 * @return the m_color_ForeGround
	 */
	public int getM_color_ForeGround() {
		return m_color_ForeGround;
	}


	/**
	 * @param m_color_ForeGround the m_color_ForeGround to set
	 */
	public void setM_color_ForeGround(int m_color_ForeGround) {
		this.m_color_ForeGround = m_color_ForeGround;
	}


	/**
	 * @return the m_IndexPage
	 */
	public Integer getM_IndexPage() {
		return m_IndexPage;
	}


	/**
	 * @param m_IndexPage the m_IndexPage to set
	 */
	public void setM_IndexPage(Integer m_IndexPage) {
		this.m_IndexPage = m_IndexPage;
	}


	/**
	 * @return the m_actionBarShown
	 */
	public boolean isM_actionBarShown() {
		return m_actionBarShown;
	}


	/**
	 * @param m_actionBarShown the m_actionBarShown to set
	 */
	public void setM_actionBarShown(boolean m_actionBarShown) {
		this.m_actionBarShown = m_actionBarShown;
	}


	/**
	 * @return the m_nrPages
	 */
	public Integer getM_nrPages() {
		return m_nrPages;
	}


	/**
	 * @param m_nrPages the m_nrPages to set
	 */
	public void setM_nrPages(Integer m_nrPages) {
		this.m_nrPages = m_nrPages;
	}
}
