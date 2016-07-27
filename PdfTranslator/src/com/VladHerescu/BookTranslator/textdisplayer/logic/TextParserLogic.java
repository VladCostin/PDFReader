package com.VladHerescu.BookTranslator.textdisplayer.logic;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

//String regex = "\n([^\\s+$]|[a-z]|[A-Z]|[',.\"]())";
//  String regex = "\n([^\\s+$]|[a-z]|[A-Z]())";
// String regex = "([\\w]|[\\s]|,)\n([\\w]|[\\s]|,)";  
//	String regex = "\n"; // end of line
//	String regex = "[^.!?]";
//	String regex = "[^\\w]";
//	String regex = "\\w\\s\n";

/**
 * the logic that parses the text in order to eliminate the 
 * @author Vlad Herescu
 *
 */
public class TextParserLogic{
	
	
	
	   
    /**
     * @param _textPage : the page read from pdf, without being arranged
     * @return : the arranged text page : the string continues until the end of the line 
     */
    public String getParsedtext(String _textPage)
    {  	    
      
    	String regex = "[\\w,:;-]\\s\n";

    	ArrayList<Integer> indexes = patternMethod(regex, _textPage);
    	String newData = deleteValues(_textPage, indexes, 2, 3, " ", 0);
    	
    	//regex = "['!.?]\n[A-Z']";
    	regex = "[\"'!.?]\\s\n[A-Z'\"]";
    	 indexes = patternMethod(regex, newData);
    //	 Log.i("message", "lista de indecsi este : " + indexes);
    	 String offset = "    ";
    	newData = deleteValues(newData, indexes, 2, 3, "\n" + offset, offset.length());
    	
  	   return newData;
  	     
  	      

    }
    
	/**
	 * replaces the unnecessary  \n with " " 
	 * @param text : the text unparsed
	 * @param indexes : the indexes of \n
	 * @param _indexStart : the index where it starts to be replace the string
	 * @param _indexEnd : the index where it ends to replace the string
	 * @param _replaceString : the string it replaces with
	 * @return : the parsed text
	 */
    /*public static String deleteValues(String text, ArrayList<Integer> indexes, 
    		int _indexStart, int _indexEnd, String _replaceString) {
		
		StringBuffer buffer = new StringBuffer(text);
		for(Integer index : indexes)
			buffer.replace(index + _indexStart, index + _indexEnd, _replaceString);
		

		return buffer.toString();
	}
	*/
    public static String deleteValues(String text, ArrayList<Integer> indexes, 
    		int _indexStart, int _indexEnd, String _replaceString, int _offset) {
		
		StringBuffer buffer = new StringBuffer(text);
		int value;
		int add = 0;
		for(Integer index : indexes)
		{ 	value = index + add;
			buffer.replace(value + _indexStart, value + _indexEnd, _replaceString);
			add += _offset;
		}

		return buffer.toString();
	}
	/**
	 * @param regex : the pattern used to identify the unnecessary \n
	 * @param text : the unparsed text
	 * @return : the list of indexes with the \n identified
	 * 
	 */
	public static ArrayList<Integer> patternMethod(String regex, String text)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
	    Pattern pattern = Pattern.compile(regex, 0);
	    Matcher matcher = pattern.matcher(text);
	    while (matcher.find()) {
	        values.add(matcher.start());
	    }
	    
	    return values;
	}
	
    
    
}
