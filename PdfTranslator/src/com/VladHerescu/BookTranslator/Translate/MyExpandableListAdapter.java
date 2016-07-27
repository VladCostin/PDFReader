package com.VladHerescu.BookTranslator.Translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.VladHerescu.BookTranslator.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity m_activity;
	private List<String> m_listDataHeader; // header titles
	// child data in format of header title, child title
	private TreeMap<String, List<String>> m_listDataChild;
	
	private int m_MaxNrBooks;
	
	List<Integer> m_index_definition;
	

	public MyExpandableListAdapter(Activity _activity, List<String> listDataHeader,
			TreeMap<String, List<String>> m_items, List<Integer> index_definitions)
	{
		m_activity = _activity;
		m_listDataHeader = listDataHeader;
		m_listDataChild = m_items;
		m_index_definition = index_definitions;
		
		Log.i("message", "lsita de indecsi este: " + m_index_definition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		if(m_listDataChild.keySet().size() == 1)
			return getDefinitionLine(groupPosition, childPosition, 0); 
		

		if(groupPosition == 0)
			return ((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
				.get(childPosition));
		
		if(groupPosition == 2)
		return  (childPosition + 1) + ". " + ((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
				.get(childPosition));
		
		if(m_index_definition == null)
			return  (childPosition + 1) + ". " + ((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
					.get(childPosition));
			
		if(groupPosition == 1 &&  m_index_definition.contains(childPosition) == false)
			return	((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
						.get(childPosition));
		
		return  (m_index_definition.indexOf(childPosition) + 1) + ". " + 
					((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
						.get(childPosition));
	
		
	}
	
	public String getDefinitionLine(int groupPosition, int childPosition, int val)
	{
		if(groupPosition == val &&  m_index_definition.contains(childPosition) == false)
			return	((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
						.get(childPosition));
		
		return  (m_index_definition.indexOf(childPosition) + 1) + ". " + 
					((String) m_listDataChild.get(m_listDataHeader.get(groupPosition))
						.get(childPosition));
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) m_activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_list_simple, null);
		}

		
		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.textViewSimpleList);
		txtListChild.setText(childText);
		
		if(m_listDataChild.keySet().size() != 1)
		{
			if(groupPosition == 1 && m_index_definition.contains(childPosition) == false)
			
				txtListChild.setTypeface(null, Typeface.ITALIC);
		}
		else
			if(groupPosition == 0 && m_index_definition.contains(childPosition) == false)
				
				txtListChild.setTypeface(null, Typeface.ITALIC);

		convertView.setOnClickListener(null);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.m_listDataChild.get(this.m_listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.m_listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.m_listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.m_activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_listview_browser, null);
		}
		
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.textViewFilename);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		lblListHeader.setPadding(getPadding(), 0, 0, 0);
		
		
		return convertView;
	}
	
	public int getPadding()
	{
		if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
		//	Toast.makeText(m_activity, "LARGE", Toast.LENGTH_LONG);
			return 36;
		}
		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
		//	Toast.makeText(m_activity, "NORMAL", Toast.LENGTH_LONG);
		    return 100;
		}

		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
		//	Toast.makeText(m_activity, "SMALL", Toast.LENGTH_LONG);
		   return 120;
		}
		else if ((m_activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
		//	Toast.makeText(m_activity, "SMALL", Toast.LENGTH_LONG);
			   return 36;
		}
		
		
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
