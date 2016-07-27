package InternetConnection;

import java.util.List;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.Translate.ActivityTranslation;
import com.VladHerescu.BookTranslator.R.color;
import com.memetix.mst.language.Language;
import com.wordnik.client.api.WordApi;
import com.wordnik.client.model.ApiTokenStatus;
import com.wordnik.client.model.Definition;
import com.wordnik.client.model.Example;
import com.wordnik.client.model.ExampleSearchResults;
import com.wordnik.client.common.ApiException;

import Database.Word;
import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

/**
 * performs the http request to obtain the data about the word selected by the user
 * <br> with data such as translation, definition, example, etc
 * @author Vlad Herescu
 *
 */
public class InternetConnection extends AsyncTask<String, Void, Word> implements DialogInterface.OnClickListener{
	
	
    /**
     * shown during the time the data is received
     */
    ProgressDialog m_progress;
    
    /**
     * the word to be saved in the database
     */
    Word m_wordRezult;
    
    /**
     * the activity that calls the AsyncTask
     */
    Activity m_activity;
	
    
    String m_message;
	
	/**specified above
	 * @param _activity
	 */
	public InternetConnection(Activity _activity, String _message, int _style)
	{
		  BingTranslator.setIds();
		  m_progress = new ProgressDialog(_activity, _style);
		  m_activity = _activity;
		 m_message = _message;
		 
	}
	
	@Override
	protected void  onPreExecute()
	{
		if( isNetworkAvailable() == true)
		{
			
			m_progress.setMessage(m_message);
			m_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			m_progress.setIndeterminate(true);
			m_progress.show();
		}
		else
		{
			setDialogNoInternetConnection();
			cancel(false);
		}
	}

	/**
	 * warns the user there is no internet connection
	 */
	public void setDialogNoInternetConnection() 
	{
		CustomDialog dialog = new CustomDialog(m_activity, color.darkGreen);
		dialog.setTitle(ConstantsInternet.errorInternet);

		dialog.setButton(Dialog.BUTTON_POSITIVE, Constants.dialogOpenSettings, new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				m_activity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				
			}
			
		});
		dialog.setButton(Dialog.BUTTON_NEUTRAL, Constants.dialogCancel, new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
			
		});
		dialog.show();
		
	}

	@Override
	protected Word doInBackground(String... params) 
	{	
		
		 WordApi api = new WordApi();
		 Log.i("message", params[0]);
	     api.addHeader("api_key", "938e5765b6eb9c0d2a20007483900cf7b48a83d09b9d32ee8");
	     Word word = new Word();
	     ExampleSearchResults exampleResult;
	     List<Definition> definitions;
	     
	     word.setM_value(params[0]);
	     word.setM_translation("tr1");
	     word.setM_translation(getBingTranslation(params)); 
	     

	     Log.i("message", "datele primite : " + params[0] + " " + params[1] + " " + params[2]);
	     
	     try {
	 
	    	 definitions = api.getDefinitions(params[0], null, null, null, null, null, null);
	    	 exampleResult = api.getExamples(params[0], null, null, null, null);
 
	    	 if(definitions.size() == 0)
	    	 {

	    		 Definition def = new Definition();
	    		 def.setText(ConstantsInternet.errorDefinition);
	    		 definitions.add(def);
	    	 }
	    	 if(exampleResult.getExamples().size() == 0)
	    	 {
	    		 Example ex = new Example();
	    		 ex.setText(ConstantsInternet.errorExample);
	    		 exampleResult.getExamples().add(ex);

	    	 }
	    	
	    	 word.setM_definitionFromWordnik(definitions);
	    	 word.setM_exampleFromWordnik(exampleResult.getExamples());
	    	 word.setM_value(params[0]);
	    	 
	    	 
	    	 
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	     return word;
	     
	     
	}
	
	
	/**
	 * using the BingTranslator library to perform the request
	 * @param params : contains the parameters needed to perform the request
	 * @return : the rezult from BingTranslator
	 */
	public String getBingTranslation(String... params)
	{
		Log.i("message", params[0]);
		Log.i("message", params[1]);
		Log.i("message", params[2]);
		Language lan1 = Language.fromString(params[1]);
		Language lan2 = Language.fromString(params[2]);

		return BingTranslator.translateFromBing(params[0], lan1, lan2);
	}
	
	 protected void onPostExecute(Word word) {
		 

		 Intent intent  = new Intent(m_activity, ActivityTranslation.class);
		 intent.putExtra("word", word);
		 intent.putExtra(Constants.translation_before_activity, Constants.translation_before_internet);
		 m_activity.startActivityForResult(intent, 12);
		 m_progress.cancel();

		 
     }
	 
	 /**
	  * saves the word in database
	  * also, adds an entrance in Appeareance table associating the word to the current book
	 * @param word : word to be saved in database
	 */
	public void saveDataIntoDictionary(Word word) {
		
		 Integer iWord;
		 
		 iWord = MainActivity.m_database.getWordId(word.getM_value());
		 if(iWord == null)
		 {
			 MainActivity.m_database.addWord(word);
			 iWord = MainActivity.m_database.getWordId(word.getM_value());
		 }
		
		// MainActivity.m_database.addAppeareance(
		//		 new Appeareance(ActivityTextDisplayer.getBook().getId(), iWord,  ActivityTextDisplayer.getSliderJumpToPage().getProgress()));
		
	}
    
    /**
     * check if there is connection available
     * if not, mention that to the user
     */
	private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) m_activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    
    
    /**
     * show the mssage in case there is no connection
     */
    public void showMessageNoConnection()
    {
    	 AlertDialog.Builder builder;
    	 builder = new AlertDialog.Builder(m_activity);
         builder.setMessage("Please check your internet connection");
         builder.create().show();
    }
    

	@Override
	public void onClick(DialogInterface dialog, int which) {
		 saveDataIntoDictionary(m_wordRezult);
		
	}
	

}


