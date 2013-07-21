package com.joeywessel.cryptaroo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class CryptTextActivity extends SherlockFragmentActivity {
	
	int cryptoMethodId;
	Button optionsButton;
	Button actionButton;
	EditText inputTextField;
	TextView outputTextField;
	Context context;
	
	String[] optionTitles;
	EditText editText1;
	EditText editText2;
	EditText editText3;
	TextView multShiftTV;
	TextView addShiftTV;
	TextView label1;
	TextView label2;
	RadioGroup radioGroup;
	
	private static ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crypt_text);
		
		// Allow back navigation on actionbar
  		ActionBar actionBar = getSupportActionBar();
  	    actionBar.setDisplayHomeAsUpEnabled(true);
  	    actionBar.setHomeButtonEnabled(true);
  	    
  	    cryptoMethodId = getIntent().getIntExtra("cryptoMethodId", 0);
  	    
  	    String[] methodNames = getResources().getStringArray(R.array.crypto_methods);
		SpannableString s = new SpannableString(methodNames[cryptoMethodId]);
		s.setSpan(new TypefaceSpan(this, "fairview_regular"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//  	    TextView title = (TextView)actionBar.getCustomView().findViewById(R.id.title);
//		title.setText(methodNames[cryptoMethodId]);
		
		actionBar.setTitle(s);
		
		context = this;
		
		optionsButton = (Button)findViewById(R.id.optionsButton);
		optionsButton.setTypeface(Consts.TYPEFACE.FairViewRegular(this));
		optionsButton.setTextSize(30);
		actionButton = (Button)findViewById(R.id.actionButton);
		actionButton.setTypeface(Consts.TYPEFACE.FairViewRegular(this));
		actionButton.setTextSize(30);
		actionButton.setEnabled(false);
		actionButton.setTextColor(getResources().getColor(R.color.disabled_button_text));
		inputTextField = (EditText)findViewById(R.id.inputText);
		outputTextField = (TextView)findViewById(R.id.outputText);
		outputTextField.setMovementMethod(new ScrollingMovementMethod());
		
		inputTextField.setTypeface(Typeface.MONOSPACE);
		inputTextField.addTextChangedListener(new NullTextWatcher());
		outputTextField.setTypeface(Typeface.MONOSPACE);
		
		outputTextField.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
		        ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
		        cm.setText(outputTextField.getText());
		        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
				return true;
		    }
		});
		
		
		setActionButtonTitle();
		
		if( cryptoMethodId == CryptoMethods.FREQUENCY_COUNT || cryptoMethodId == CryptoMethods.RUN_THE_ALPHABET || 
				cryptoMethodId == CryptoMethods.BIGRAPHS || cryptoMethodId == CryptoMethods.TRIGRAPHS) {
			optionsButton.setEnabled(false);
			optionsButton.setTextColor(getResources().getColor(R.color.disabled_button_text));
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        menu.add("Help")
            .setIcon(R.drawable.ic_menu_help)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
        	finish();
            return true;
        } else {
        	showHelpDialog();
        }
        return true;
    }
	
	public void showHelpDialog()
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment prev = getSupportFragmentManager().findFragmentByTag("optionsDialog");
		if( prev != null )
		{
			if( prev.getTag() != "optionsDialog" )
				ft.remove(prev);
		}
		
		if( prev == null ^ (prev != null && prev.getTag() != "optionsDialog") )
		{
			ft.addToBackStack(null);
			HelpDialogFragment newFragment = HelpDialogFragment.newInstance(cryptoMethodId);
			
			if( isTablet() )
				newFragment.show(ft, "optionsDialog");
			else
			{
				newFragment.show(getSupportFragmentManager(), "optionsDialog");
			}
		}
	}
	
	void showOptionsDialog(int optionsLayoutId)
	{
//		if( isTablet() )
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment prev = getSupportFragmentManager().findFragmentByTag("optionsDialog");
			if( prev != null )
			{
				if( prev.getTag() != "optionsDialog" )
					ft.remove(prev);
			}
			
			if( prev == null ^ (prev != null && prev.getTag() != "optionsDialog") )
			{
				ft.addToBackStack(null);
				OptionsDialogFragment newFragment = OptionsDialogFragment.newInstance(cryptoMethodId, optionsLayoutId);
				
				if( isTablet() )
					newFragment.show(ft, "optionsDialog");
				else
				{
					newFragment.show(getSupportFragmentManager(), "optionsDialog");
				}
			}
		}
	}
	
	protected void onResume()
	{
		super.onResume();
		// On resume restore text data
		restoreTextData();
	}
	
	protected void onPause()
	{
		super.onPause();
		// On pause save text data
		saveTextData();
	}
	
	private void restoreTextData()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
		
		inputTextField.setText( sharedPreferences.getString("global_inputText", "") );
		
		switch(cryptoMethodId)
		{
			case CryptoMethods.FREQUENCY_COUNT : 
				outputTextField.setText( sharedPreferences.getString("frequencycount_outputText", "") );
				break;
			case CryptoMethods.RUN_THE_ALPHABET : 
				outputTextField.setText( sharedPreferences.getString("runthealphabet_outputText", "") );
				break;
			case CryptoMethods.BIGRAPHS : 
				outputTextField.setText( sharedPreferences.getString("bigraphs_outputText", "") );
				break;
			case CryptoMethods.TRIGRAPHS : 
				outputTextField.setText( sharedPreferences.getString("trigraphs_outputText", "") );
				break;
			case CryptoMethods.NGRAPHS : 
				outputTextField.setText( sharedPreferences.getString("ngraph_outputText", "") );
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				outputTextField.setText( sharedPreferences.getString("affineknownplaintext_outputText", "") );
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				outputTextField.setText( sharedPreferences.getString("affineencipher_outputText", "") );
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				outputTextField.setText( sharedPreferences.getString("affinedecipher_outputText", "") );
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				outputTextField.setText( sharedPreferences.getString("splitoffalphabets_outputText", "") );
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				outputTextField.setText( sharedPreferences.getString("polymonocalculator_outputText", "") );
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				outputTextField.setText( sharedPreferences.getString("vigenereencipher_outputText", "") );
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				outputTextField.setText( sharedPreferences.getString("vigeneredecipher_outputText", "") );
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				outputTextField.setText( sharedPreferences.getString("autokeycyphertextattack_outputText", "") );
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				outputTextField.setText( sharedPreferences.getString("autokeyplaintextattack_outputText", "") );
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				outputTextField.setText( sharedPreferences.getString("autokeydecipher_outputText", "") );
				break;
			default:
				break;
		}
	}
	
	private void saveTextData()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.putString("global_inputText", inputTextField.getText().toString() );
		
		switch(cryptoMethodId)
		{
			case CryptoMethods.FREQUENCY_COUNT :
				editor.putString("frequencycount_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.RUN_THE_ALPHABET :
				editor.putString("runthealphabet_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.BIGRAPHS :
				editor.putString("bigraphs_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.TRIGRAPHS :
				editor.putString("trigraphs_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.NGRAPHS : 
				editor.putString("ngraph_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				editor.putString("affineknownplaintext_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				editor.putString("affineencipher_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				editor.putString("affinedecipher_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				editor.putString("splitoffalphabets_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				editor.putString("polymonocalculator_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				editor.putString("vigenereencipher_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				editor.putString("vigeneredecipher_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				editor.putString("autokeycyphertextattack_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				editor.putString("autokeyplaintextattack_outputText", outputTextField.getText().toString() );
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				editor.putString("autokeydecipher_outputText", outputTextField.getText().toString() );
				break;
			default:
				break;
		}
		
		editor.commit();
	}

	public void onSetOptionsPressed(View v)
	{
		int optionsLayoutId = -1;
		
		switch(cryptoMethodId)
		{
			case CryptoMethods.NGRAPHS : optionsLayoutId = R.layout.activity_options_set1;
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : optionsLayoutId = R.layout.activity_options_set2;
				break;
			case CryptoMethods.AFFINE_ENCIPHER : optionsLayoutId = R.layout.activity_options_set3;
				break;
			case CryptoMethods.AFFINE_DECIPHER : optionsLayoutId = R.layout.activity_options_set3;
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : optionsLayoutId = R.layout.activity_options_set1;
				break;
			case CryptoMethods.POLYMONO_CACULATOR : optionsLayoutId = R.layout.activity_options_set1;
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : optionsLayoutId = R.layout.activity_options_set4;
				break;
			case CryptoMethods.VIGENERE_DECIPHER : optionsLayoutId = R.layout.activity_options_set4;
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : optionsLayoutId = R.layout.activity_options_set1;
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : optionsLayoutId = R.layout.activity_options_set5;
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : optionsLayoutId = R.layout.activity_options_set2;
				break;
			default:
				break;
		}
		
		showOptionsDialog(optionsLayoutId);
		
	}
	
	public void onActionButtonPressed(View v)
	{
		SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
//		Log.v("HEIGHT", Integer.toString(v.getHeight()));
//		
//		LinearLayout l = (LinearLayout)findViewById(R.id.buttonBar);
//		Log.v("HEIGHT", Integer.toString(l.getHeight()));
		
		outputTextField.setText("");
		
		String args[];
		switch(cryptoMethodId)
		{
			case CryptoMethods.FREQUENCY_COUNT : 
				args = new String[1];
				args[0] = inputTextField.getText().toString();
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.RUN_THE_ALPHABET : 
				args = new String[1];
				args[0] = inputTextField.getText().toString();
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.BIGRAPHS : 
				args = new String[1];
				args[0] = inputTextField.getText().toString();
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.TRIGRAPHS : 
				args = new String[1];
				args[0] = inputTextField.getText().toString();
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.NGRAPHS : 
				args = new String[2];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("ngraph_size", 0));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				args = new String[3];
				args[0] = inputTextField.getText().toString();
				args[1] = sharedPreferences.getString("affineknowplaintext_keyword", "the");
				args[2] = Boolean.toString(sharedPreferences.getBoolean("affineknownplaintext_shiftfirst", false));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				args = new String[3];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("affineencipher_mult_shift", 1));
				args[2] = Integer.toString(sharedPreferences.getInt("affineencipher_add_shift", 0));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				args = new String[3];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("affinedecipher_mult_shift", 1));
				args[2] = Integer.toString(sharedPreferences.getInt("affinedecipher_add_shift", 0));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				args = new String[2];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("splitoffalphabets_num", 1));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				args = new String[2];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("polymonocalculator_keywordsize", 1));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				if( sharedPreferences.getString("vigenereencipher_keyword", "").length() > 0 )
				{
					args = new String[2];
					args[0] = inputTextField.getText().toString();
					args[1] = sharedPreferences.getString("vigenereencipher_keyword", "");
					
					new ExecuteCryptMethodTask().execute(args);
				}
				else
					Toast.makeText(this, "Please provide a keyword in the options", Toast.LENGTH_SHORT).show();
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				if( sharedPreferences.getString("vigeneredecipher_keyword", "").length() > 0 )
				{
					args = new String[2];
					args[0] = inputTextField.getText().toString();
					args[1] = sharedPreferences.getString("vigeneredecipher_keyword", "");
					
					new ExecuteCryptMethodTask().execute(args);
				}
				else
					Toast.makeText(this, "Please provide a keyword in the options", Toast.LENGTH_SHORT).show();
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				args = new String[2];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("autokeycyphertextattack_keywordlength", 1));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				args = new String[5];
				args[0] = inputTextField.getText().toString();
				args[1] = Integer.toString(sharedPreferences.getInt("autokeyplaintextattack_maxkeywordlenth", 1));
				args[2] = Float.toString(sharedPreferences.getFloat("autokeyplaintextattack_lowerfriedman", (float)0.055));
				args[3] = Float.toString(sharedPreferences.getFloat("autokeyplaintextattack_upperfriedman", (float)2.0));
				
				new ExecuteCryptMethodTask().execute(args);
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				if( sharedPreferences.getString("autokeydecipher_keyword", "").length() > 0 )
				{
					args = new String[3];
					args[0] = inputTextField.getText().toString();
					args[1] = sharedPreferences.getString("autokeydecipher_keyword", "");
					args[2] = Boolean.toString(sharedPreferences.getBoolean("autokeydecipher_plaintext", true));
					
					new ExecuteCryptMethodTask().execute(args);
				}
				else
					Toast.makeText(this, "Please provide a keyword in the options", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
	}
	
	private void setActionButtonTitle()
	{
		if( cryptoMethodId == CryptoMethods.BIGRAPHS || cryptoMethodId == CryptoMethods.TRIGRAPHS || cryptoMethodId == CryptoMethods.NGRAPHS )
			actionButton.setText("GET");
		else if( cryptoMethodId == CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK || cryptoMethodId == CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK ||
				cryptoMethodId == CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK )
			actionButton.setText("EXECUTE");
		else if( cryptoMethodId == CryptoMethods.AFFINE_DECIPHER || cryptoMethodId == CryptoMethods.VIGENERE_DECIPHER || cryptoMethodId == CryptoMethods.AUTOKEY_DECIPHER )
			actionButton.setText("DECIPHER");
		else if( cryptoMethodId == CryptoMethods.AFFINE_ENCIPHER || cryptoMethodId == CryptoMethods.VIGENERE_ENCIPHER )
			actionButton.setText("ENCIPHER");
		else if( cryptoMethodId == CryptoMethods.FREQUENCY_COUNT || cryptoMethodId == CryptoMethods.RUN_THE_ALPHABET )
			actionButton.setText("RUN");
		else if( cryptoMethodId == CryptoMethods.POLYMONO_CACULATOR || cryptoMethodId == CryptoMethods.GCD_AND_INVERSE )
			actionButton.setText("CALCULATE");
		else
			actionButton.setText("SPLIT");
	}
	
	class ExecuteCryptMethodTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected void onPreExecute()
		{
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Working...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			progressDialog.dismiss();
			outputTextField.setText(result);
		}

		@Override
		protected String doInBackground(String... params) {
			String inputText = params[0];
			String resultStr = "";
			
			
			switch(cryptoMethodId)
			{
				case CryptoMethods.FREQUENCY_COUNT : 
					resultStr = CryptoHelperMethods.frequencyCount( inputText );
					break;
				case CryptoMethods.RUN_THE_ALPHABET : 
					resultStr = CryptoHelperMethods.runTheAlphabet( inputText );
					break;
				case CryptoMethods.BIGRAPHS : 
					resultStr = CryptoHelperMethods.getBigraphs( inputText );
					break;
				case CryptoMethods.TRIGRAPHS : 
					resultStr = CryptoHelperMethods.getTrigraphs( inputText );
					break;
				case CryptoMethods.NGRAPHS : 
					resultStr = CryptoHelperMethods.getNgraphs( inputText, 
							Integer.parseInt(params[1]));
					break;
				case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
					resultStr = CryptoHelperMethods.affineKnownPlaintextAttack(params[0],
							params[1], Boolean.parseBoolean(params[2]));
					break;
				case CryptoMethods.AFFINE_ENCIPHER : 
					resultStr = CryptoHelperMethods.affineEncipher(params[0],
							Integer.parseInt(params[1]), 
							Integer.parseInt(params[2]));
					break;
				case CryptoMethods.AFFINE_DECIPHER : 
					resultStr = CryptoHelperMethods.affineDecipher(params[0],
							Integer.parseInt(params[1]), 
							Integer.parseInt(params[2]));
					break;
				case CryptoMethods.SPLIT_OFF_ALPHABETS : 
					resultStr = CryptoHelperMethods.splitOffAlphabets(params[0],
							Integer.parseInt(params[1]));
					break;
				case CryptoMethods.POLYMONO_CACULATOR : 
					resultStr = CryptoHelperMethods.polyMonoCalculator(params[0],
							Integer.parseInt(params[1]));
					break;
				case CryptoMethods.VIGENERE_ENCIPHER : 
					resultStr = CryptoHelperMethods.vigenereEncipher(params[0], params[1]);
					break;
				case CryptoMethods.VIGENERE_DECIPHER : 
					resultStr = CryptoHelperMethods.vigenereDecipher(params[0], params[1]);
					break;
				case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
					resultStr = CryptoHelperMethods.autoKeyCyphertextAttack(params[0],
							Integer.parseInt(params[1]));
					break;
				case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
					resultStr = CryptoHelperMethods.autoKeyPlainTextAttack( inputText,
							Integer.parseInt(params[1]), 
							Float.parseFloat(params[2]), 
							Float.parseFloat(params[3]) );
					break;
				case CryptoMethods.AUTOKEY_DECIPHER : 
					resultStr = CryptoHelperMethods.autoKeyDecipher(params[0], params[1], Boolean.parseBoolean(params[2]));
					break;
				default:
					break;
			}
			
			return resultStr;
		}
	}
	
	public boolean isTablet() {
	    try {
	        // Compute screen size
	        DisplayMetrics dm = context.getResources().getDisplayMetrics();
	        float screenWidth  = dm.widthPixels / dm.xdpi;
	        float screenHeight = dm.heightPixels / dm.ydpi;
	        double size = Math.sqrt(Math.pow(screenWidth, 2) +
	                                Math.pow(screenHeight, 2));
	        // Tablet devices should have a screen size greater than 6 inches
	        return size >= 6;
	    } catch(Throwable t) {
	        return false;
	    }

	} 
	
	private class NullTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			if( s != null && s.length() > 0 ) {
				actionButton.setEnabled(true);
				actionButton.setTextColor(getResources().getColor(R.color.wool_white));
			} else {
				actionButton.setEnabled(false);
				actionButton.setTextColor(getResources().getColor(R.color.disabled_button_text));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {	
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
	}
}

