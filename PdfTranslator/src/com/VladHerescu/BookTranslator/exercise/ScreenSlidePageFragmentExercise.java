package com.VladHerescu.BookTranslator.exercise;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.Translate.MyExpandableListAdapter;
import com.VladHerescu.BookTranslator.R;
import com.VladHerescu.BookTranslator.R.color;
import com.VladHerescu.BookTranslator.R.drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.memetix.mst.language.Language;

import Database.Word;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragmentExercise extends Fragment implements OnClickListener {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    
    /**
     * the edit text where the user has inserted the data
     */
    static String m_editTextValue;
    
    /**
     * the text view where the answer Wrong or Right will be shown
     */
     TextView m_textViewAnswerApp;
     
     
     String valueCorrect;
     
     
     
     EditText m_editText;
     
     Button m_ButtonCheck;
     
     Button m_ButtonSolution;
     
     
 	List<Integer> index_definitions;
     

    
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     * @param pageNumber : the page to be instantiated
     * @param m_controller : the controller that listenes for the events
     * @return : the new fragment
     */
    public static ScreenSlidePageFragmentExercise create(int pageNumber) {
        ScreenSlidePageFragmentExercise fragment = new ScreenSlidePageFragmentExercise();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page_exercise, container, false);
        Word word = ExerciseModel.getWordAtPosition(mPageNumber);
        m_ButtonCheck = (Button) rootView.findViewById(R.id.ButtonCheckAnswer);
        m_ButtonSolution = (Button) rootView.findViewById(R.id.ButtonSeeAnswer);
        m_ButtonCheck.setOnClickListener(this);;
        m_ButtonSolution.setOnClickListener(this);
        
        
        valueCorrect = word.getM_translation();
        
        if(word != null)
        {
        
          setUserAnswerView(rootView);
          setClues(rootView, word);
        }
        return rootView;
    }

    /**
     * @param _rootView 
     * @param _word 
     * 
     */
    public void setClues(ViewGroup _rootView, Word _word)
    {
    	ExpandableListView  expListView = (ExpandableListView) _rootView.findViewById(R.id.expListViewClues);
    	
    	DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
    	
    	expListView.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
    	TreeMap<String, List<String>> list = new TreeMap<String, List<String>>();
    	List<String> headers = new ArrayList<String>();
    	
    	list.put("Clues",  getDefinitionsWithPartSpeech(_word.getM_definition(), _word.getM_partSpeech()));
    	headers.add("Clues");
    	ExpandableListAdapter listAdapter = new MyExpandableListAdapter(getActivity(), headers, list, index_definitions);
    	expListView.setAdapter(listAdapter);
		
	}
    
    
    public List<String> getDefinitionsWithPartSpeech(List<String> _definition, List<String> _partSpeech)
	{
		List<String> definitions = new ArrayList<String>();
		index_definitions = new ArrayList<Integer>();
		List<String> partSpeechClone = new ArrayList<String>(_partSpeech);
		Collections.sort(partSpeechClone);
		Set<String> foo = new HashSet<String>(partSpeechClone);
		int j=0;
		for(String fo : foo)
		{
			definitions.add(fo);
			j++;
			for(int i = 0; i < _partSpeech.size(); i++)
			{
				if(_partSpeech.get(i).equals(fo))
				{
					definitions.add(_definition.get(i));
					index_definitions.add(j++);
					
				}
			}
		}
		
		return definitions;
		

	}
    
    public int GetDipsFromPixel(float pixels)
    {
     // Get the screen's density scale
     final float scale = getResources().getDisplayMetrics().density;
     // Convert the dps to pixels, based on density scale
     return (int) (pixels * scale + 0.5f);
}


	private void setUserAnswerView(ViewGroup _rootView){
    	 m_editText = (EditText)  _rootView.findViewById(R.id.CellAnswer);
         m_editText.setText("");
         m_editText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityExercise.m_layoutWrongRight.setVisibility(View.GONE);
				//ActivityExercise.m_layoutWrongRight.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
			}
		});
			
		m_editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			
				ActivityExercise.m_edtitext = m_editText;
				
			}
		});

         
         m_editText.addTextChangedListener(new TextWatcher() {
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             	
             }

             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 // TODO Auto-generated method stub
             }

             @Override
             public void afterTextChanged(Editable s) {

             	m_editTextValue = s.toString();
             }
         });
		
	}


	/**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    

	@Override
	public void onClick(View v) {
		
		Log.i("message", valueCorrect + " " + m_editTextValue);
		ActivityExercise.m_layoutWrongRight.setVisibility(View.VISIBLE);
		Log.i("message", "numarul paginii este : " + mPageNumber);
		
		View view = getActivity().getCurrentFocus();
		if(view != null)
		{
		  InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}   
		    
		 Button button = (Button) v;
		 String text = button.getText().toString();
		
		 if(text.equals(getResources().getString(R.string.exercise_cell_check)) == true) 
		 {
			 
		/*	 Resources res = this.getResources();
	    	    // Change locale settings in the app.
	    	    DisplayMetrics dm = res.getDisplayMetrics();
	    	    android.content.res.Configuration conf = res.getConfiguration();
	    	    conf.locale = new Locale(Language.ROMANIAN.toString().toLowerCase());
	    	    res.updateConfiguration(conf, dm);
	    	    */
		//	 getActivity().startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS), 0); 
			 
		    
			 if(valueCorrect.equals(m_editTextValue))
			 {
				 m_ButtonCheck.setText("Next");
				 m_editText.setEnabled(false);
				 ActivityExercise.createMessageCorrect(getResources().getDrawable(R.drawable.answer_exercise_correct), getResources().getColor(R.color.darkGreen)); 
				 ExerciseModel.addIfCorrect(mPageNumber);
			 }
			 else
				 ActivityExercise.createMessageWrong(getResources().getDrawable(R.drawable.answer_exercise_wrong), getResources().getColor(R.color.darkRed));
			
		 }
		
		 ActivityExercise.handleMenuItemNext(text.toLowerCase()); 
		 
		 if(text.equals(getResources().getString(R.string.exercise_cell_See_solution)) == true)
		 {

			 if(mPageNumber == ActivityExercise.NUM_PAGES - 1)
				 m_ButtonCheck.setText("Finish");
			 else
			 m_ButtonCheck.setText("Next");
			 m_editText.setEnabled(false);
			 ActivityExercise.showAnswer(valueCorrect, getResources().getDrawable(R.drawable.answer_exercise_wrong), getResources().getColor(R.color.darkRed));
		 } 
		 
 
		
	}
	
	public void onPause()
	{
		Log.i("message", "este in pauza");
		super.onPause();
	}



}


