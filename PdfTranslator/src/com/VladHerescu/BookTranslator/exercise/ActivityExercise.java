package com.VladHerescu.BookTranslator.exercise;

import java.util.ArrayList;
import java.util.Locale;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.CustomDialog;
import com.VladHerescu.BookTranslator.MainActivity;
import com.VladHerescu.BookTranslator.BookList.ActivityBookList;
import com.VladHerescu.BookTranslator.R;







import com.VladHerescu.BookTranslator.R.color;
import com.memetix.mst.language.Language;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * class that contains the viewPager where the fragments with questions will be shown
 * @author Vlad Herescu
 *
 */
public class ActivityExercise extends   FragmentActivity  {
	
	
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
     static int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private static CustomViewPager m_Pager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private static PagerAdapter m_PagerAdapter;
    
    /**
     * class which contains the data to be shown, and manages the queries
     * executes the business logic
     */
    static ExerciseModel	m_model;
    
    /**
     * listens to user input and notifies the model
     */
    static ExerciseController m_controller; 
    
    static String m_nameFile;
    
    
   static  Activity m_Activity;
   
   
  static RelativeLayout m_layoutWrongRight;
   
   static TextView m_SolutionRightWrong;
   
   static ImageView m_imageViewRightWrong;
   
   static boolean m_AnswerFound;
   
   
   
   static EditText m_edtitext;
   
   static final String STATE_SCORE = "playerScore";
   static final String STATE_ITEMS = "word";
   static final String STATE_TRANSLATION = "trans";
   static final String STATE_DEFINITION = "definitions";
   static final String STATE_PART_SPEECH = "partSPeech";
   
   static final String STATE_POSITION = "position";
   
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_slider);
        getActionBar().setHomeButtonEnabled(true);
        m_Activity = this;
        getDataIntent();
        Log.i("message", "intra iar in onCreate");
      
        
        initMessage();
        m_model = new ExerciseModel(m_nameFile);
        m_model.setTestWords(5);
        
        if(savedInstanceState != null)
        {
        	m_model.m_indexWordsCorrect = savedInstanceState.getIntegerArrayList(STATE_SCORE);
        	String value = savedInstanceState.getString(STATE_ITEMS);
        	String translation = savedInstanceState.getString(STATE_TRANSLATION);
        	
        	
        	ArrayList<String> definitions = savedInstanceState.getStringArrayList(STATE_DEFINITION);
        	ArrayList<String> partSpeech = savedInstanceState.getStringArrayList(STATE_PART_SPEECH);
        	
        	int position = savedInstanceState.getInt(STATE_POSITION);
        	m_model.getWordAtPosition(position).setM_definition(definitions);
        	m_model.getWordAtPosition(position).setM_partSpeech(partSpeech);
        	m_model.getWordAtPosition(position).setM_value(value);
        	m_model.getWordAtPosition(position).setM_translation(translation); 
        
        	
        	
        	
        	//for(int i = 0; i < values.size(); i++)
        	//{
        	//	m_model.m_testWords.add(new W)
        	//}
        }
        
        m_controller = new ExerciseController(m_model);
        m_PagerAdapter = new ScreenSlidePagerAdapter(m_Activity.getFragmentManager());
        initMPager();
        initLanguage();
    }

    public void initMessage() {
        m_layoutWrongRight = (RelativeLayout) findViewById(R.id.layoutMessageSolution);
        m_SolutionRightWrong = (TextView) findViewById(R.id.textViewRightWrong);
        m_imageViewRightWrong = (ImageView) findViewById(R.id.imageViewRightWrong);
		
	}

	private void initLanguage() {
    	 Resources res = this.getResources();
    	    // Change locale settings in the app.
    	    DisplayMetrics dm = res.getDisplayMetrics();
    	    android.content.res.Configuration conf = res.getConfiguration();
    	    conf.locale = new Locale(Language.ARABIC.toString().toLowerCase());
    	    res.updateConfiguration(conf, dm);
    	    
		
	}

	public void getDataIntent() {
		
    	Intent intent = this.getIntent();
    	m_nameFile =	intent.getStringExtra(Constants.nameFile);
		
	}

	/**
     * initiating the pager
     */
    public static void initMPager() {
    	  NUM_PAGES =  m_model.getNrSlides();
    	  m_Pager = (CustomViewPager) m_Activity.findViewById(R.id.pagerExercise);
        
          m_Pager.setAdapter(m_PagerAdapter);
          m_Pager.setCurrentItem(0);
          m_Pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
              @Override
              public void onPageSelected(int position) {
                  // When changing pages, reset the action bar actions since they are dependent
                  // on which page is currently active. An alternative approach is to have each
                  // fragment expose actions itself (rather than the activity exposing actions),
                  // but for simplicity, the activity provides the actions in this sample.
                  m_Activity.invalidateOptionsMenu();
              }
          });

		
          m_Pager.setPagingEnabled(false); 
	}
    
    public void onStop()
    {
    	m_Pager.setCurrentItem(0);
    	super.onStop();

    	
    	Log.i("message", "intra iar aici in Ondestroy");
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.android_exercise_actionbar, menu);
        m_AnswerFound = false;
        Log.i("message","pagina la care sunt este: " + m_Pager.getCurrentItem());
        Log.i("message", m_model.getWordAtPosition(m_Pager.getCurrentItem()).getM_value());
        
        setTitle(m_model.getWordAtPosition(m_Pager.getCurrentItem()).getM_value());
        
        
        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (m_Pager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        if(m_Pager.getCurrentItem() == m_PagerAdapter.getCount() - 1)
        	item.setIcon(getResources().getDrawable(R.drawable.ic_done_all_white_36dp));
        else
        	item.setIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_white_24dp));
        
    
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_next:
            	m_edtitext.setText("");
                m_layoutWrongRight.setVisibility(View.GONE);                	
               	handleMenuItemNext(item.getTitle().toString().toLowerCase());
                return true;
                
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
                
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * @param _itemValue : the value of the button with the id next
     * 
     */
    public static void handleMenuItemNext(String _itemValue)
    {


    	if(_itemValue.equals("next"))
    	{
    		m_Pager.setCurrentItem(m_Pager.getCurrentItem() + 1);
		    ActivityExercise.m_layoutWrongRight.setVisibility(View.GONE);
    	}
    	if(_itemValue.equals("finish"))
    	{
		    ActivityExercise.m_layoutWrongRight.setVisibility(View.GONE);	
    	  	
    	   	CustomDialog dialog = new CustomDialog(m_Activity, color.darkPurple);
    	   	
    	   	
    	   	TextView textViewScore;
    	    LayoutInflater inflater = 	m_Activity.getLayoutInflater();
    	    final View tableLayout = inflater.inflate(R.layout.dialog_score_shown, null); // first you have to inflate
    	    

    	   	
    	   	dialog.setView(tableLayout);
    	   	
    	   	textViewScore = (TextView) tableLayout.findViewById( R.id.textViewScoreValueExerciseActivity);
    	   	if(textViewScore == null)
    	   		Log.i("message", "este null");
    	   	else
    	   		textViewScore.setText(ExerciseModel.m_indexWordsCorrect.size()+"/" + NUM_PAGES  );
    	   	
    	   	
    	   	dialog.setButton(DialogInterface.BUTTON_POSITIVE,ConstantsExercise.message_question_positive, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				

					m_model = new ExerciseModel(m_nameFile);
			        m_model.setTestWords(5);
			        m_controller = new ExerciseController(m_model);
					m_PagerAdapter.notifyDataSetChanged();
					m_Pager.setCurrentItem(0);

				}
			});
    	   	
       	   	dialog.setButton(DialogInterface.BUTTON_NEGATIVE,ConstantsExercise.message_question_negative, new OnClickListener() {
				
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    				
    					NavUtils.navigateUpTo(m_Activity, new Intent(m_Activity, MainActivity.class)); 
    				}
       	   	});
       	   	
       	   	dialog.setButton(DialogInterface.BUTTON_NEUTRAL, ConstantsExercise.message_question_neutral, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					m_Activity.finish();
					//NavUtils.navigateUpTo(m_Activity, new Intent(m_Activity, ActivityBookList.class)); 
				}
       	   	});
    		
    	   	dialog.show();
    	   	
    	   	
    	   	
    	}
    	
    }
    
    

	public static void createMessageCorrect(Drawable _drawable, int _color) 
	{

		m_layoutWrongRight.setVisibility(View.VISIBLE);
		m_layoutWrongRight.setAnimation(AnimationUtils.loadAnimation(m_Activity, android.R.anim.fade_in));
		m_layoutWrongRight.setBackground(_drawable);
		m_SolutionRightWrong.setText("Correct!");
		m_SolutionRightWrong.setTextColor(_color);
		m_imageViewRightWrong.setImageResource(R.drawable.ic_correct);
		m_AnswerFound = true;

		
		
	}
	
	public static void createMessageWrong(Drawable _drawable, int _color)
	{
		m_layoutWrongRight.setVisibility(View.VISIBLE);
		m_layoutWrongRight.setAnimation(AnimationUtils.loadAnimation(m_Activity, android.R.anim.fade_in));
		m_layoutWrongRight.setBackground(_drawable);
		m_SolutionRightWrong.setText("Wrong!");
		m_SolutionRightWrong.setTextColor(_color);
		m_imageViewRightWrong.setImageResource(R.drawable.ic_wrong);
	}
	
	
	public static void showAnswer(String _answer, Drawable _drawable, int _color)
	{
		m_layoutWrongRight.setVisibility(View.VISIBLE);
		m_layoutWrongRight.setAnimation(AnimationUtils.loadAnimation(m_Activity, android.R.anim.fade_in));
		m_layoutWrongRight.setBackground(_drawable);
		m_SolutionRightWrong.setText("Answer is " +"\"" + _answer + "\"");
		m_SolutionRightWrong.setTextColor(_color);
		m_imageViewRightWrong.setImageResource(R.drawable.ic_wrong);
		m_AnswerFound = true;
	}
    


	/**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
        	
            super(fm);
        }

        
        // aici apeleaza noul fragment
        @Override
        public Fragment getItem(int position) {
        	Log.i("message", "getItem" + position);
        	Log.i("message", "reintra aici");
            return ScreenSlidePageFragmentExercise.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
        
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }  
    }

    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putIntegerArrayList(STATE_SCORE, ExerciseModel.m_indexWordsCorrect);
        savedInstanceState.putString(STATE_ITEMS, ExerciseModel.getWordAtPosition(m_Pager.getCurrentItem()).getM_value());
        savedInstanceState.putString(STATE_TRANSLATION, ExerciseModel.getWordAtPosition(m_Pager.getCurrentItem()).getM_translation());
        savedInstanceState.putStringArrayList(STATE_DEFINITION,  new ArrayList<>(m_model.getWordAtPosition(m_Pager.getCurrentItem()).getM_definition()));
        savedInstanceState.putStringArrayList(STATE_PART_SPEECH, new ArrayList<>(m_model.getWordAtPosition(m_Pager.getCurrentItem()).getM_partSpeech()));
        savedInstanceState.putInt(STATE_POSITION, m_Pager.getCurrentItem());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

}
