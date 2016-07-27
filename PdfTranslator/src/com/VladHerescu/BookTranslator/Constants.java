package com.VladHerescu.BookTranslator;

/**
 * @author admin
 *
 */
public interface Constants {

	/**
	 * key associated to the path send to Activity Text Displayer from the current activity
	 */
	String nameExtraStarttextDisplayer	= "FILE_TO_SHOW";
	
	/**
	 * name of the file begun and saved
	 */
	String nameFile						= "FILE_NAME";
	
	/**
	 * the page where the user has closed the pplication
	 */
	String pageSaved					= "PAGE_SAVED";
	
	/**
	 * separates the name of the pdf and the page when saved to shared preferences
	 */
	String separatorNamePage				= "#######";
	
	
	
	String m_AllBooks					= "All Books";
	
	String m_titleDialogTranslation		=	"Translate the word chosen";
	
	String m_buttonTranslate			= "Translate";
	
	
	String m_deleteBookDictionary		= "Are you sure you want to delete this book's dictionary";
	
	String m_deleteWord					= " Are you sure you want to delete the word : ";
	
	
	String dialogYesButton			= "Yes";
	
	String dialogNoButton				= "No";
	
	String dialogOpenSettings			= "Open settings";
	
	String dialogCancel					= "Cancel";
	
	
	String m_activityDictionary				= "Dictionary";
	
	String m_activityExercise				= "Exercise";
	
	String m_activityBookList				= "Further";
	
	String m_activityOpenPDF			    = "BrowseFileSystem";
	
	
	String m_activityERROROpenPDF			= "ErrorOpenPDF";
	
	
	/**
	 * the activity that started this activity
	 */
	String translation_before_activity		= "beforeActivity";
	
	String translation_before_internet		= "InternetConnection";
	
	String translation_before_dictionary	= "Dictionary";
	
	String translation_before_textDisplayer	= "TextDisplayer";
	String translation_before_textDisplayer_App_true	= "TextDisplayerAppTrue";
	
	
	String error_parsing					= "Unfortunately, the book could not have been read. Maybe you can try again";
	
	int duration_toast						= 2000;
	
}
