package com.joeywessel.cryptaroo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
	
	private MenuListViewAdapter listViewAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up default crypto options
		setDefaultOptions();
		
		SpannableString s = new SpannableString("CRYPTAROO");
		s.setSpan(new TypefaceSpan(this, "fairview_regular"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(s);
		actionbar.setIcon(R.drawable.ic_home);
		
		listView = (ListView)findViewById(R.id.mainMenuListView);
		listViewAdapter = new MenuListViewAdapter(this, R.layout.list_item, R.id.primaryTextView, getResources().getStringArray(R.array.crypto_methods));
		listView.setAdapter(listViewAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position,
					long arg3) {
				
				Intent cryptTextIntent;
				
				if( position == CryptoMethods.GCD_AND_INVERSE )
				{
					cryptTextIntent = new Intent(MainActivity.this, GCDandInverseActivity.class);
					startActivity(cryptTextIntent);
				}
				else
				{
					cryptTextIntent = new Intent(MainActivity.this, CryptTextActivity.class);
					cryptTextIntent.putExtra("cryptoMethodId", position);
					startActivity(cryptTextIntent);
				}
			}
		});
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("About")
            .setIcon(R.drawable.ic_menu_info_details)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home || item.getItemId() == 0) {
        	launchAboutActivity();
            return true;
        }
        return true;
    }
	
	public void launchAboutActivity()
	{
		Intent aboutIntent = new Intent(this, AboutActivity.class);
		startActivity(aboutIntent);
	}
	
	public void setDefaultOptions()
	{	
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		// Get Ngraph options
		editor.putInt("ngraph_size", 1);
		
		// AffineKnownPlainTextAttack options
		editor.putString("affineknowplaintext_keyword", "the");
		editor.putBoolean("affineknowplaintext_shiftfirst", false);
		
		// Affine Encipher options
		editor.putInt("affineencipher_mult_shift", 1);
		editor.putInt("affineencipher_add_shift", 0);
		
		// Affine Decipher options
		editor.putInt("affinedecipher_mult_shift", 1);
		editor.putInt("affinedecipher_add_shift", 0);
		
		// SplitOffAlphabets options
		editor.putInt("splitoffalphabets_num", 1);
		
		// PolyMonoCalculator options
		editor.putInt("polymonocalculator_keywordsize", 1);
		
		// AutoKeyCypherTextAttack options
		editor.putInt("autokeycyphertextattack_keywordlength", 1);
		
		// AutoKeyPlainTextAttack options
		editor.putInt("autokeyplaintextattack_maxkeywordlenth", 1);
		editor.putFloat("autokeyplaintextattack_lowerfriedman", (float)0.055);
		editor.putFloat("autokeyplaintextattack_upperfriedman", (float)2.0);
		
		// AutokeyDecipher options
		editor.putBoolean("autokeydecipher_plaintext", false);
		
		// GCDAndInverse options
		editor.putInt("gcdandinverse_inverseof", 1);
		editor.putInt("gcdandinverse_mod", 26);
		editor.putInt("gcdandinverse_gcd", 1);
		editor.putString("gcdandinverse_inverse", "1");
		
		
		// Commit preferences
		editor.commit();	
	}


	
}
