package com.VladHerescu.BookTranslator.browse.view;

/**
 * used to make the communication with the other fragment
 * @author Vlad Herescu
 *
 */
public interface OnHeadlineSelectedListener{
	
	/**
	 * after selecting the file to be previewed
	 * @param title 
	 * 
	 */
	public void onArticleSelected(String title);

}
