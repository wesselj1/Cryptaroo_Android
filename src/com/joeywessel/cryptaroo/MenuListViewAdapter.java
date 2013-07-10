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
		ViewHolder viewHolder;
		
		if( convertView == null ) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(rowLayoutId, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.textView1 = (TextView)convertView.findViewById(R.id.primaryTextView);
			viewHolder.textView2 = (TextView)convertView.findViewById(R.id.primaryTextView2);
			viewHolder.textView3 = (TextView)convertView.findViewById(R.id.primaryTextView3);
			viewHolder.textView4 = (TextView)convertView.findViewById(R.id.primaryTextView4);
			viewHolder.textView5 = (TextView)convertView.findViewById(R.id.primaryTextView4);
			viewHolder.textView6 = (TextView)convertView.findViewById(R.id.primaryTextView4);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
			
		String text = rowText[pos];
		if( text != null ) {
			viewHolder.textView1.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView1.setText(text);
			resizeTextView(viewHolder.textView1);
			viewHolder.textView2.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView2.setText(text);
			resizeTextView(viewHolder.textView2);
			viewHolder.textView3.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView3.setText(text);
			resizeTextView(viewHolder.textView3);
			viewHolder.textView4.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView4.setText(text);
			resizeTextView(viewHolder.textView4);
			viewHolder.textView5.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView5.setText(text);
			resizeTextView(viewHolder.textView5);
			viewHolder.textView6.setTypeface(Consts.TYPEFACE.FairViewRegular(context));
			viewHolder.textView6.setText(text);
			resizeTextView(viewHolder.textView6);
		}
		
		return(convertView);
	}
	
	private void resizeTextView(TextView tv) {
		tv.measure(0, 0);
		int width = tv.getMeasuredWidth();
		//tv.setWidth((int) ((width+0.5) * context.getResources().getDisplayMetrics().density));
//		tv.setWidth(width);
	}
	
static class ViewHolder {
	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	TextView textView5;
	TextView textView6;
}

	
}
