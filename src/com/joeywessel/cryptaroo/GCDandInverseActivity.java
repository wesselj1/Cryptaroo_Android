package com.joeywessel.cryptaroo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class GCDandInverseActivity extends SherlockActivity {

	EditText inverseOfField;
	EditText modulusField;
	TextView inverseTV;
	TextView GCDTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gcdand_inverse);
		
		// Allow back navigation on actionbar
  		ActionBar actionBar = getSupportActionBar();
  	    actionBar.setDisplayHomeAsUpEnabled(true);
  	    actionBar.setHomeButtonEnabled(true);
		
		inverseOfField = (EditText)findViewById(R.id.gcdandinverse_inverseoffield);
		modulusField = (EditText)findViewById(R.id.gcdandinverse_modfield);
		inverseTV = (TextView)findViewById(R.id.gcdandinverse_inversefield);
		GCDTV = (TextView)findViewById(R.id.gcdandinverse_gcdfield);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}
	
	protected void onResume()
	{
		super.onResume();
		
		SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
		
		inverseOfField.setText( Integer.toString(sharedPreferences.getInt("gcdandinverse_inverseof", 1)) );
		modulusField.setText( Integer.toString(sharedPreferences.getInt("gcdandinverse_mod", 26)) );
		GCDTV.setText( Integer.toString(sharedPreferences.getInt("gcdandinverse_gcd", 1)) );
		inverseTV.setText( sharedPreferences.getString("gcdandinverse_inverse", "26") );
	}
	
	protected void onPause()
	{
		super.onPause();
		
		SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.putInt( "gcdandinverse_inverseof", Integer.parseInt(inverseOfField.getText().toString()) );
		editor.putInt( "gcdandinverse_mod", Integer.parseInt(modulusField.getText().toString()) );
		editor.putInt( "gcdandinverse_gcd", Integer.parseInt( GCDTV.getText().toString()) );
		editor.putString( "gcdandinverse_inverse", inverseTV.getText().toString());
		
		editor.commit();
	}
	
	public void onCalculateButtonPressed(View v)
	{	
		String[] results = CryptoHelperMethods.GCDandInverse( Integer.parseInt(inverseOfField.getText().toString()),
				Integer.parseInt(modulusField.getText().toString()) );
		
		GCDTV.setText( results[0] );
		inverseTV.setText( results[1] );
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_gcdand_inverse, menu);
//		return true;
//	}

}
