package com.joeywessel.cryptaroo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuListViewAdapter extends ArrayAdapter<String>
{
	private Activity context;
	private String[] rowText;
	private int rowLayoutId;
	private int textId;
	
	public MenuListViewAdapter(Activity context, int rowLayoutId, int textId, String[] rowText)
	{
		super(context, rowLayoutId, rowText);
		
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.textId = textId;
		this.rowText = rowText;
	}
	
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(rowLayoutId, null, false);
		
		if( rowText != null )
		{
			TextView label = (TextView)row.findViewById(textId);
			label.setText( rowText[pos] );
		}
		
		return(row);
	}
	
}
