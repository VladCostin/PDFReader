package com.VladHerescu.BookTranslator.exercise;

import com.VladHerescu.BookTranslator.textdisplayer.view.ActivityTextDisplayer;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class LoadBook extends AsyncTask<Void, Void, Void>{

	/**
	 * a copy of the text displayer to access its functions
	 */
	ActivityTextDisplayer m_textDisplayer;
	
	/**
	 * @param _textDisplayer: the text displayer instance, used to acces its methods
	 */
	public LoadBook(ActivityTextDisplayer _textDisplayer) {
		
		m_textDisplayer = _textDisplayer;
	
	}
	 
	
	@Override
	protected Void doInBackground(Void... params) {
		
		/*
		 m_textDisplayer.getDataFromActivities();
	     ActivityTextDisplayer.setBooleanSearchNewWord(true);
	     m_textDisplayer.setHeaderTitle();
	     m_textDisplayer.initSeekBar();
	     m_textDisplayer.initTextFromPdf();
	     m_textDisplayer.initViewPager();
	     m_textDisplayer.addBookIfIdDoesNotExist();
	     m_textDisplayer.m_progressTranslate = new ProgressDialog(m_textDisplayer);
		*/
      //  m_textDisplayer.initTextFromPdf();

		
		return null;
	}
	
	/**
	 * 
	 */
	protected void onPostExecute(Void noParam) {
		// m_textDisplayer.m_loadBook.cancel();
	}
	

}
