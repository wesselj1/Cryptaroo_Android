package com.joeywessel.cryptaroo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class HelpActivity extends SherlockActivity {
	
	TextView helpTextView;
	int cryptoMethodId;
	String[] helpStrings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		// Allow back navigation on actionbar
  		ActionBar actionBar = getSupportActionBar();
  	    actionBar.setDisplayHomeAsUpEnabled(true);
  	    actionBar.setHomeButtonEnabled(true);
		
		helpStrings = getResources().getStringArray(R.array.help_info);
		helpTextView = (TextView)findViewById(R.id.help_textView);
		helpTextView.setText(helpStrings[cryptoMethodId]);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent homeIntent = new Intent(this, MainActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			break;
		}
		return true;
	}


}
