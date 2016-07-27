package com.VladHerescu.BookTranslator.logicMainPage;

import java.util.List;

import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.textdisplayer.view.ActivityTextDisplayer;
import com.VladHerescu.BookTranslator.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * adapter of the list of book already started
 * @author Vlad Herescu
 *
 */
public class CurrentFileAdapter extends ArrayAdapter<String> implements OnClickListener{
	
	/**
	 * items shown in list
	 */
	List<String> m_items; 
	/**
	 * the activity used for getting the inflator 
	 */
	Activity m_activity;
	/**
	 * the layout of the item
	 */
	int m_layoutResourceId;
	
	
	/**
	 * code to identify the ActivityTextDisplayer from which the page and the title will be received
	 */
	int m_codeReaderPass= 1234;
	
	
	ProgressDialog m_progress;
	
	Dialog m_dialog;

	/**
	 * @param context
	 * @param _resource
	 * @param _openedFiles
	 * @param _activity
	 */
	public CurrentFileAdapter( Activity _activity, int _resource, List<String> _openedFiles, Dialog _dialog) {
		super(_activity, _resource, _openedFiles);
		
		m_layoutResourceId = _resource;	
		m_activity = _activity;
		m_items = _openedFiles;
		m_dialog = _dialog;

		
		
	} 
	


	public View getView(int _position,View convertView,ViewGroup parent)
	{
		//>??????? cand returneaza null?
		if(convertView == null)
		{
			LayoutInflater inflater = m_activity.getLayoutInflater();
			convertView = inflater.inflate(m_layoutResourceId, parent, false);
		}
		TextView text = (TextView) convertView.findViewById(R.id.textViewItemListOpenFiles);
		ImageButton eraseButton = (ImageButton) convertView.findViewById(R.id.buttonEraseOpenFile);
		
		
		text.setText( getTitle(m_items.get(_position)));
		text.setTag(_position); 
		text.setOnClickListener(this); 
		
		eraseButton.setTag(_position);  
		eraseButton.setOnClickListener(this);  
		
		setProgressView( _position, convertView);
		return convertView;
	}

	@Override
	public void onClick(View v) {
	
		
		Log.i("message", "am apasat pe un buton");
		
		if(v instanceof ImageButton)
		{
			Log.i("message", "este instanta Button");
			eraseFile(v); 
			
		}
		else
		if(v instanceof TextView)
			
		{	Log.i("message", "este instanta TextView");
			openFile(v);
		
		}
	}
	
	/**
	 * we get the string associated with the item selected
	 * from this, we get the page the user has remained, the title, and the path
	 * @param _BookData : the data about the book selected, consisting of
	 * 	<br> title
	 * <br> path
	 * <br> page
	 * @return : the title of the book
	 */
	public String getTitle(String _BookData)
	{
		String title[] = _BookData.split(Constants.separatorNamePage);
		String bookPath = title[0];
		String bookData[] = bookPath.split("/");
		
	//	return _BookData;
		return bookData[bookData.length - 1];
	}
	
	private void openFile(View _v) 
	{
		int position = (Integer) ((TextView) _v).getTag();
		String BookData = m_items.get(position);
		
		int page = Integer.parseInt(BookData.split(Constants.separatorNamePage)[1]);
		String m_pathFileToBeShown =  Environment.getExternalStorageDirectory().toString() +"/"+
				BookData.split(Constants.separatorNamePage)[0];
		
		startIntent(((TextView) _v).getText().toString(), page, m_pathFileToBeShown); 
		
	}

	/**
	 * erases an icon after pressing the delete button
	 * @param _v : the button pressed for deleting the item
	 */
	public void eraseFile(View _v)
	{
		int position = (Integer) ((ImageButton) _v).getTag();
		SharedPreferenceLogic.deleteBook(m_items.get(position));
		m_items.remove(position);
		notifyDataSetChanged();
		if(m_items.size() == 0)
			if(m_dialog != null)
			m_dialog.dismiss();
		
	}
	
	public void setProgressView(int _itemSelected, View _v)
	{
		Log.i("message","valorile sunt:  "+ m_items.get(_itemSelected));
		String BookData = m_items.get(_itemSelected);
		
		
		int page = Integer.parseInt(BookData.split(Constants.separatorNamePage)[1]);
		int nrFiles = Integer.parseInt(BookData.split(Constants.separatorNamePage)[2]); 
		
		
		float progressWeight =  getWeightProgress(nrFiles, page);
		float left = getWeightProgressLeft(progressWeight);
		
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT, progressWeight);
		
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT, left);
		
		
		Log.i("message", "progressul este : " + progressWeight + " " + page );
		
		_v.findViewById(R.id.ViewProgressDone).setLayoutParams(param1);
		_v.findViewById(R.id.ViewProgressLeft).setLayoutParams(param2);
	}
	
	
	public float getWeightProgress(int _nrTotalFiles, int _Progress)
	{
		return ((float) _Progress)/ ((float) _nrTotalFiles);
	}
	
	public float getWeightProgressLeft(float _progressWeight)
	{
		return 1 - _progressWeight;
	}
	
	/**
	 * starts a new activity
	 * @param fileName 
	 * @param page 
	 * @param _pathFileToBeShown 
	 */
	public void startIntent(String fileName, int page, String _pathFileToBeShown)
	{
		
		Log.i("message", "Numele fisierului instartIntent este : " + fileName);
		
		m_progress =  new ProgressDialog(m_activity, R.style.CustomDialog);
		m_progress.setMessage("Loading book " + fileName);
		m_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		m_progress.setIndeterminate(true);
		m_progress.show();
		
		final int m_page = page;
		final String m_fileName = fileName;
		final String m_pathFileToBeShown = _pathFileToBeShown;
		
		
		Thread th = new Thread(new Runnable()
		{
		public void run()
		{

			Log.i("message", "intra si in thread");
			Intent intent = new Intent(m_activity, ActivityTextDisplayer.class);
			intent.putExtra(Constants.nameExtraStarttextDisplayer, m_pathFileToBeShown);
			intent.putExtra(Constants.pageSaved, m_page);
			intent.putExtra(Constants.nameFile, m_fileName);
			m_activity.startActivityForResult(intent, m_codeReaderPass);
			m_progress.cancel();
		}
		});
		th.start();
	}

	

}
