package com.VladHerescu.BookTranslator.ItemAdaptors;


import com.VladHerescu.BookTranslator.Constants;
import com.VladHerescu.BookTranslator.ItemLists.ItemFileBrowse;
import com.VladHerescu.BookTranslator.browse.model.ListStructure;
import com.VladHerescu.BookTranslator.textdisplayer.view.ActivityTextDisplayer;
import com.VladHerescu.BookTranslator.R;

import Database.Book;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * @author Vlad Herescu
 *
 */
public class ItemAdapter extends ArrayAdapter<String> implements SectionIndexer, OnClickListener
{
	/**
	 * structure containing the data about each item
	 */
	ListStructure m_structure;
	
	/**
	 * the layout of the item
	 */
	int m_layoutResourceId;
	
	/**
	 * needed to create the adaptor and to get the layout inflator
	 */
	Activity m_activity;
	
	/**
	 * @param _activity : the activity that inflates the adaptor
	 * @param _layout
	 * @param _structure
	 */
	public ItemAdapter(Activity _activity, int _layout, ListStructure _structure) 
	{
		super(_activity, _layout, _structure.getM_itemValues());
		m_structure = _structure;
		m_layoutResourceId = _layout;
		m_activity = _activity;
	}
	
	 @Override
	 public View getView(int i, View convertView, ViewGroup viewGroup) {
		 
		
		 LayoutInflater inflater = m_activity.getLayoutInflater();
		 
		 if( m_structure.isHeader(i) == false) 
		 {
			 ItemFileBrowse file = new ItemFileBrowse();
			 convertView = inflater.inflate(m_layoutResourceId, viewGroup, false);
			 file.m_fileName = (TextView) convertView.findViewById(R.id.textViewSimpleList);
			 file.m_preview = (ImageButton) convertView.findViewById

(R.id.buttonPreview);
			 file.m_open = (ImageButton) convertView.findViewById

(R.id.buttonOpenFileTwo);
		 
		 
			 file.m_fileName.setText(m_structure.getM_items().get(i).getM_value());
	    
	    	 file.m_preview.setVisibility(View.VISIBLE);
	    	 file.m_open.setVisibility(View.VISIBLE);
	    	 file.m_preview.setTag(Integer.valueOf(-i));
	    	 file.m_open.setTag(Integer.valueOf(i));

	     
	     
	    //	 file.m_preview.setOnClickListener(this);
	    	 file.m_open.setOnClickListener(this);

		 }
		 else
		 {   
			 convertView = inflater.inflate(R.layout.item_listview_browser, viewGroup, 

false);
			 TextView text = (TextView) convertView.findViewById

(R.id.textViewFilename);
		
			 
			 text.setText(m_structure.getM_items().get(i).getM_value());
			 int section = m_structure.getM_items().get(i).getM_Section();
			 
			 if(m_structure.getM_Sections() != null)
				 if(m_structure.getM_Sections().get(section).isM_shown() == false) 
					 text.setVisibility(View.GONE);

		 }
	    return convertView; 

	 }
	
	

	@Override
	public Object[] getSections() {
		
		return m_structure.getSectionsValues().toArray();
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
	
		  return   m_structure.getM_Sections().get(sectionIndex).getM_position();
	}

	@Override
	public int getSectionForPosition(int position) {
		 return  m_structure.getM_items().get(position).getM_Section();
	}
	
	@Override
	public int getCount() {
	    if (super.getCount() != 0){
	        //sometimes your data set gets invalidated. In this case getCount()
	        //should return 0 and not our adjusted count for the headers.
	        //Any easy way to know if data is invalidated is to check if
	        //super.getCount() is 0.
	        return m_structure.getSize();
	    }
	     
	    return 0;
	}
	
	/**
	 * starts the ActivityTextDisplayer
	 * @param fileName : the name of the file
	 * @param pathFileToBeShown : the path of the file shown
	 */
	public void startIntent(String fileName, String pathFileToBeShown)
	{
		
		
		Intent intent = new Intent(m_activity, ActivityTextDisplayer.class);
		intent.putExtra(Constants.nameExtraStarttextDisplayer, pathFileToBeShown);
		intent.putExtra(Constants.nameFile, fileName);
		m_activity.startActivity(intent);
	}

	@Override
	public void onClick(View v) 
	{
		
		ImageButton button = (ImageButton) v;
		Integer id = (Integer) button.getTag();
		Book file = m_structure.getM_items().get(id).getM_file();
		startIntent(file.getTitle(), file.getPath());
		
	}
	
}


