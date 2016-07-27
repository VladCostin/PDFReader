package InternetConnection;

import android.util.Log;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;



public class BingTranslator {
	
	public static void setIds()
	{
		  Translate.setClientId(ConstantsInternet.clientId);
		  Translate.setClientSecret(ConstantsInternet.ClientSecret);
	}

	/**
	 * @param expression : the expression to be translated
	 * @param lan1 : the language from which is being translated
	 * @param lan2 : the language to which is being translated
	 * @return
	 */
	public static String translateFromBing(String expression, Language lan1, Language lan2)
	{

		
		  String translatedText ="eroare in traducerea cuvantului " + expression;
		  Log.i("message", lan1.toString());
		  Log.i("message", lan2.toString());
		  try {
			translatedText = Translate.execute(expression, lan1, lan2);

			
		   }
		  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
		  Log.i("message", translatedText);
		  return translatedText;	
	}
	
	public void speakText()
	{

	}
}
