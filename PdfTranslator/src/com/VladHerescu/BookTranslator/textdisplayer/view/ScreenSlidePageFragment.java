/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.VladHerescu.BookTranslator.textdisplayer.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.Translate.ActivityTranslation;
import com.VladHerescu.BookTranslator.textdisplayer.logic.ShPref_TextDisplayer;
import com.VladHerescu.BookTranslator.textdisplayer.logic.TextParserLogic;
import com.VladHerescu.BookTranslator.textdisplayer.model.Appearance;
import com.VladHerescu.BookTranslator.textdisplayer.model.Languages;
import com.VladHerescu.BookTranslator.R;
import com.itextpdf.text.ListLabel;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.memetix.mst.language.Language;

import Database.AttributesWord;
import Database.Word;
import InternetConnection.InternetConnection;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A fragment representing a single step in a wizard. 
 *
 */
public class ScreenSlidePageFragment extends Fragment implements OnTouchListener {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int m_PageNumber;
    
    /**
     * associated with the textView pressed
     */
    View m_viewPressed;
    

    /**
     * contains the text displayed in the activity
     */
    TextView m_textView_textDisplay;
    
    
    /**
     * the appearance of the activity : size of the text, theme, etc
     */
    static Appearance m_appearance;
    
    
    /**
     * detects whether the user has done several gestures
     * <br> long pressed associated with selecting a word
     * <br> double click associated with makeing the screen bigger
     */
    GestureDetector m_gestureDetector;
    
    /**
     * parses the text to remove the unwanted \n characters
     */
    TextParserLogic m_parser;
    
    /**
     * instance in order to hide it when double doap
     */
    static SeekBar  m_sliderJumpToPage;
    
    public ArrayList<String> m_letter_index;
    
    /**
     * constructor of the fragment
     */
    public ScreenSlidePageFragment(){
    	
    	m_letter_index = new ArrayList<String>();
    	
    	m_gestureDetector = new GestureDetector(getActivity(), 
    	(new GestureDetector.SimpleOnGestureListener() {
    		        public void onLongPress(MotionEvent e) 
    		        {
    		        	
    		        		switch (e.getAction()) {
    		        			case MotionEvent.ACTION_DOWN:
    		        			//	MotionEvent.A

    		        			//	String text = "<font color=#cc0030>This is layrics o</font> <font color=#ffff00>f the song</font>";

    		        			//	m_textView_textDisplay.setText(Html.fromHtml(text));
    		        				
    		        				determineWordSelected(m_viewPressed, e);
    		        				break;

    		        			default:
    		        				
    		        				break;
    		        		}
    		        }
    		        
    		    	public boolean onDoubleTap(MotionEvent e)
    		    	{
    		    		if(m_appearance.isM_actionBarShown() == true)
    		    		{
    		    			ActivityTextDisplayer.layout.setVisibility(View.GONE);
  		    	    		//	m_sliderJumpToPage.setVisibility(View.GONE);
    		    	    		m_appearance.setM_actionBarShown(false);
    		    	    		getActivity().getActionBar().hide();

    		    		}
    		    		else
    		    		{
    		    			m_appearance.setM_actionBarShown(true);
		    	    		getActivity().getActionBar().show();
		    	    		ActivityTextDisplayer.layout.setVisibility(View.VISIBLE);
		    	    		//m_sliderJumpToPage.setVisibility(View.VISIBLE);

    		    		}
    		    		
    		    		return true;
    		    	}
    		        
    		        
    	}));
    	m_parser = new TextParserLogic();
		
	}
    

    /**
     * specified above
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     * @param _appearance 
     * @param pageNumber 
     * @param _sliderJumpToPage 
     * @return :  instance of ScreenPafeFragment
     */
    public static ScreenSlidePageFragment create(Appearance _appearance, int pageNumber, SeekBar _sliderJumpToPage) {
    	
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        m_appearance = _appearance;
        m_sliderJumpToPage = _sliderJumpToPage;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_PageNumber = getArguments().getInt(ARG_PAGE);
        
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        String textFromPdf;

        try {		
					
			textFromPdf = PdfTextExtractor.getTextFromPage
			(ActivityTextDisplayer.getReader(),m_PageNumber + 1);
			textFromPdf = m_parser.getParsedtext(textFromPdf);

			
			for(int i = 0; i < textFromPdf.length(); i++)
				m_letter_index.add(Character.toString( textFromPdf.charAt(i)));
			
			m_textView_textDisplay =  (TextView) rootView.findViewById(android.R.id.text1);
			m_textView_textDisplay.setOnTouchListener(this);
			m_textView_textDisplay.setText(textFromPdf);
			
			setStyleText();
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        


        return rootView;
    }
    
    private void setStyleText() {
    	
    	m_textView_textDisplay.setTextColor(m_appearance.getM_color_ForeGround());
		m_textView_textDisplay.setBackgroundColor(m_appearance.getM_color_Background());
    	m_textView_textDisplay.setTextSize(m_appearance.getM_textSize());
		
	}

 
    
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		 m_viewPressed = v;
		 m_gestureDetector.onTouchEvent(event);
		

		return true;
	}

    /**
     * @param v : the TEXTVIEW touched
     * @param event : the Action_down event
     */
    public void determineWordSelected(View v,MotionEvent event) {
    	
    	Layout layout = ((TextView) v).getLayout();
   	    int x = (int)event.getX();
   	    int y = (int)event.getY();
   	    
   	  

   	    
   	    if (layout!=null){

   	        int line = layout.getLineForVertical(y);
   	        int offset = layout.getOffsetForHorizontal(line, x);	        
   	        int copy_offset= offset;
   	        
   	        
   	     String word = m_letter_index.get(offset);
 	       
	        Log.i("message", ""+offset + " ---" + word + "--- " );

	      
	        
	        while(true)
	        {
	        	copy_offset--;
	        	Log.i("message", "####"+ ((int) m_letter_index.get(copy_offset ).charAt(0)) + "###" + m_letter_index.get(copy_offset )  + "####");
	        	if(m_letter_index.get(copy_offset ).equals(" ") ||
	        	   m_letter_index.get(copy_offset ).equals("\n") || 
	        	   m_letter_index.get(copy_offset ).equals(".") ||
	        	( (int) m_letter_index.get(copy_offset ).charAt(0) == 9)
	        	   
	        	   )
	        		break;
	        	
	        	
	        	word = m_letter_index.get(copy_offset) + word;
	        }
	        
	        copy_offset = offset;
	        
	        while(true)
	        {
	        	copy_offset++;
	        	if(m_letter_index.get(copy_offset ).equals(" ") || 
	        	   m_letter_index.get(copy_offset ).equals("\n") || 
	        	   m_letter_index.get(copy_offset ).equals(".") ||
	        	   m_letter_index.get(copy_offset ).equals(",") ||
	        	   m_letter_index.get(copy_offset ).equals(";") ||
	        	   m_letter_index.get(copy_offset ).equals("") ||
	        	   m_letter_index.get(copy_offset ).equals("?") ||
	        	   m_letter_index.get(copy_offset ).equals("!") ||
	        	( (int) m_letter_index.get(copy_offset ).charAt(0) == 9)
	        	   )
	        		break;
	        	word =  word + m_letter_index.get(copy_offset);
	        }
   	        
   	        
   	        
   	        
   	        /*String word = ActivityTextDisplayer.m_letter_index.get(offset);
   	       
   	        Log.i("message", ""+offset + " ---" + word + "--- " );

   	      
   	        
   	        while(true)
   	        {
   	        	copy_offset--;
   	        	Log.i("message", "####"+ ((int) ActivityTextDisplayer.m_letter_index.get(copy_offset ).charAt(0)) + "###" + ActivityTextDisplayer.m_letter_index.get(copy_offset )  + "####");
   	        	if(ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(" ") ||
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals("\n") || 
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(".") ||
   	        	( (int) ActivityTextDisplayer.m_letter_index.get(copy_offset ).charAt(0) == 9)
   	        	   
   	        	   )
   	        		break;
   	        	
   	        	
   	        	word = ActivityTextDisplayer.m_letter_index.get(copy_offset) + word;
   	        }
   	        
   	        copy_offset = offset;
   	        
   	        while(true)
   	        {
   	        	copy_offset++;
   	        	if(ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(" ") || 
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals("\n") || 
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(".") ||
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(",") ||
   	        	   ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals(";") ||
   	        	ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals("") ||
   	        	ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals("?") ||
   	        	ActivityTextDisplayer.m_letter_index.get(copy_offset ).equals("!") ||
   	        	( (int) ActivityTextDisplayer.m_letter_index.get(copy_offset ).charAt(0) == 9)
   	        	   )
   	        		break;
   	        	word =  word + ActivityTextDisplayer.m_letter_index.get(copy_offset);
   	        }
   	       
   	       */
   	
   	        createDialogTranslation(word).show();
   	    }
		
	}
    
	/**
	 * @param _wordSelected : the word selected to be translated
	 * @return : the dialog asking the user of he wants to translate the word selected
	 * 
	 */
	public  Dialog createDialogTranslation(String _wordSelected)
	{
		CustomDialog dialog = new CustomDialog(getActivity(), R.color.darkGreen);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View body = inflater.inflate(R.layout.dialog_translation_word_selected, null);
		final EditText wordSelected = (EditText) body.findViewById(R.id.editTextWordSelected);
		final String word = _wordSelected;
		
		
		wordSelected.setText(_wordSelected);
		dialog.setView(body);
		dialog.setTitle(Constants.m_titleDialogTranslation);
		
		
		final Spinner spinner = (Spinner) body.findViewById(R.id.spinner_language);
		// Create an ArrayAdapter using the string array and a default spinner layout
		
		List<String> data = Languages.getListLanguages();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setSelection(Languages.getIndexSpinner(ShPref_TextDisplayer.getLanguage()));
		
		
		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
					// added a Cancel button
					// if the user pushes it, nothing happens
					// it just exists the dialog
			}
		});
		
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, Constants.m_buttonTranslate, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ShPref_TextDisplayer.setLanguage((String) spinner.getSelectedItem());
							handleTranslateAction( wordSelected.getText().toString(), (String) spinner.getSelectedItem());
					}
		});
		
		return dialog;
		
	}

    
    /**
     * handles the translation action, if it is possible
     * another one might be already  handled
     * @param _wordTranslated : the word selected to be translated
     */
    public void handleTranslateAction(String _wordTranslated, String _language)
    {   
    	
    	Word word = MainActivity.m_database.getWord(AttributesWord.VALUE, _wordTranslated);
    	if(word == null)
    	{
    		String params[] = {_wordTranslated,Language.ENGLISH.toString(), _language}; 
    		new InternetConnection(getActivity(), "Translating " + _wordTranslated, R.style.CustomDialog).execute(params);
    	}
    	else
    	{
    		 Log.i("message", "id-ul cuvantului este : " + word.getM_id());
    		 boolean appears = MainActivity.m_database.WordAppearsinBook(Integer.toString(word.getM_id()), ActivityTextDisplayer.m_title);
    		 Intent intent  = new Intent(getActivity(), ActivityTranslation.class);
    		 intent.putExtra("word", word);
    	     Log.i("message", "apare ? " + appears);
    		 if(appears == true)
    		 {
    			
    			 intent.putExtra(Constants.translation_before_activity, Constants.translation_before_textDisplayer_App_true);
        		 getActivity().startActivity(intent);
    		 }
    		 else
    		 {
    			 intent.putExtra(Constants.translation_before_activity, Constants.translation_before_textDisplayer);
        		 getActivity().startActivityForResult(intent, 13);
    		 }
    		
    	}
    }



}
