package com.joeywessel.cryptaroo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar ab = this.getSupportActionBar();
		
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setIcon(R.drawable.ic_home);

		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title_vew, null);
		
		((TextView)v.findViewById(R.id.title)).setTypeface(Consts.TYPEFACE.FAIRVIEW_REGULAR);

		ab.setCustomView(v);
	}

}
