package com.VladHerescu.BookTranslator.dictionary;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import Database.PartSpeech;

public class MyTab {

	PartSpeech partSpeech;
	
	//Drawable drawable;
	
	int color;
	
	
	MyTab(PartSpeech partSpeech, Drawable drawable, int color)
	{
		this.partSpeech = partSpeech;
	//	this.drawable = drawable;
		this.color = color;
	}
	MyTab(PartSpeech partSpeech, int color)
	{
		this.partSpeech = partSpeech;
		this.color = color;
	}
	
}
