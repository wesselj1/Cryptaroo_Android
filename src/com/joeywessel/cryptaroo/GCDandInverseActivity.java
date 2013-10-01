package com.joeywessel.cryptaroo;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
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
		
		SpannableString s = new SpannableString("GCD AND INVERSE");
		s.setSpan(new TypefaceSpan(this, "fairview_regular"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		// Allow back navigation on actionbar
  		ActionBar actionBar = getSupportActionBar();
  	    actionBar.setDisplayHomeAsUpEnabled(true);
  	    actionBar.setHomeButtonEnabled(true);
  	    actionBar.setIcon(R.drawable.ic_home);
  	    actionBar.setTitle(s);
		
		inverseOfField = (EditText)findViewById(R.id.gcdandinverse_inverseoffield);
		modulusField = (EditText)findViewById(R.id.gcdandinverse_modfield);
		inverseTV = (TextView)findViewById(R.id.gcdandinverse_inversefield);
		GCDTV = (TextView)findViewById(R.id.gcdandinverse_gcdfield);
		
		setFonts();
		
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
	
	public void setFonts() {
		Typeface fairviewRegular = Consts.TYPEFACE.FairViewRegular(this);
		Typeface fairviewSmallCaps = Consts.TYPEFACE.FairViewSmallCaps(this);
		
		TextView tv1 = (TextView)findViewById(R.id.lblInverseOf);
		tv1.setTypeface(fairviewSmallCaps);
		
		TextView tv2 = (TextView)findViewById(R.id.lblMod);
		tv2.setTypeface(fairviewSmallCaps);
		
		TextView tv3 = (TextView)findViewById(R.id.lblGCD);
		tv3.setTypeface(fairviewSmallCaps);
		
		TextView tv4 = (TextView)findViewById(R.id.lblInverse);
		tv4.setTypeface(fairviewSmallCaps);
		
		inverseOfField.setTypeface(fairviewRegular);
		modulusField.setTypeface(fairviewRegular);
		inverseTV.setTypeface(fairviewRegular);
		GCDTV.setTypeface(fairviewRegular);
		
		Button btn = (Button)findViewById(R.id.gcdandinverse_btn);
		btn.setTypeface(fairviewRegular);
	}

}
