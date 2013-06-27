package com.joeywessel.cryptaroo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsDialogFragment extends DialogFragment {
	
	int cryptoMethodId;
	int layoutId;
	String[] optionTitles;
	EditText editText1;
	EditText editText2;
	EditText editText3;
	TextView multShiftTV;
	TextView addShiftTV;
	TextView label1;
	TextView label2;
	RadioGroup radioGroup;
	
	static OptionsDialogFragment newInstance(int cryptoMethodId, int optionsLayoutId)
	{
		OptionsDialogFragment fragment = new OptionsDialogFragment();
		
		Bundle args = new Bundle();
		args.putInt("cryptoMethodId", cryptoMethodId);
		args.putInt("layoutId", optionsLayoutId);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialog);
		cryptoMethodId = getArguments().getInt("cryptoMethodId");
		layoutId = getArguments().getInt("layoutId");
		
		setOptionsTitleArray();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
	{
		View v = inflater.inflate(layoutId, container, false);
		
//        LayoutParams lp=getDialog().getWindow().getAttributes();    
//        lp.x=20;lp.y=130;lp.width=100;lp.height=120;lp.gravity=Gravity.TOP | Gravity.LEFT;
//        lp.dimAmount=0;            
//        lp.flags=LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        
		getUIElementsFromView(v);
		setOptionLabels();
		
        // Watch for button clicks.
        Button applyButton = (Button)v.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
				if( !checkForNullFields() )
				{
	            	saveOptions();
	            	getDialog().dismiss();
	            	Toast.makeText(getActivity(), "New options applied", Toast.LENGTH_SHORT).show();
				}
            }
        });
        
        Button cancelButton = (Button)v.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
        
        Button plusButton = null;
        Button minusButton = null;
        Button plusButton2 = null;
        Button minusButton2 = null;
        
        
        if( cryptoMethodId == CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK )
        {
        	plusButton = (Button)v.findViewById(R.id.optionset5_plusBtn);
        	minusButton = (Button)v.findViewById(R.id.optionset5_minusBtn);
        }
        else if( cryptoMethodId == CryptoMethods.AFFINE_ENCIPHER || cryptoMethodId == CryptoMethods.AFFINE_DECIPHER )
        {
        	plusButton = (Button)v.findViewById(R.id.optionset3_aPlusButton);
        	minusButton = (Button)v.findViewById(R.id.optionset3_aMinusButton);
        	plusButton2 = (Button)v.findViewById(R.id.optionset3_mPlusButton);
        	minusButton2 = (Button)v.findViewById(R.id.optionset3_mMinusButton);
        	
            plusButton2.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				onNumberPickerPlusPressed(v);
    			}
    		});
            
            minusButton2.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				onNumberPickerMinusPressed(v);
    			}
    		});
        }
        else if( cryptoMethodId == CryptoMethods.NGRAPHS || cryptoMethodId == CryptoMethods.SPLIT_OFF_ALPHABETS ||
        		cryptoMethodId == CryptoMethods.POLYMONO_CACULATOR || cryptoMethodId == CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK )
        {
        	plusButton = (Button)v.findViewById(R.id.optionset1_plusButton);
        	minusButton = (Button)v.findViewById(R.id.optionset1_minusButton);
        }
        
        if( null != plusButton )
        {
	        plusButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onNumberPickerPlusPressed(v);
				}
			});
        }
        
        if( null != minusButton )
        {
	        minusButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onNumberPickerMinusPressed(v);
				}
			});
        }

        return v;
	}

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(getIntent().getIntExtra("layoutId", 0));
//		
//		cryptoMethodId = getIntent().getIntExtra("cryptoMethodId", -1);
//		setOptionsTitleArray();
//		getUIElements();
//		setOptionLabels();
//		
//		
//	}
	
	public void onStart()
	{
		super.onStart();
		restoreOptions();
	}
	
	public void onStop()
	{
		super.onStop();
	}
	
	public void onNumberPickerPlusPressed(View v)
	{
		if( v.getId() == R.id.optionset3_aPlusButton )
		{
			if( Integer.parseInt(addShiftTV.getText().toString()) < 25 )
				addShiftTV.setText( "" + (Integer.parseInt(addShiftTV.getText().toString()) + 1) );
		}
		else if( v.getId() == R.id.optionset3_mPlusButton )
		{
			if( Integer.parseInt(multShiftTV.getText().toString()) == 11 )
				multShiftTV.setText( "15" );
			else if( Integer.parseInt(multShiftTV.getText().toString()) < 25 )
				multShiftTV.setText( "" + (Integer.parseInt(multShiftTV.getText().toString()) + 2) );
		}
		else
		{
			if( editText1.getText().toString().matches("") )
				editText1.setText("1");
			else
				editText1.setText( "" + (Integer.parseInt(editText1.getText().toString()) + 1) );
		}
	}
	
	public void onNumberPickerMinusPressed(View v)
	{
		if(v.getId() == R.id.optionset3_aMinusButton)
		{
			if( Integer.parseInt(addShiftTV.getText().toString()) > 0 )
				addShiftTV.setText( "" + (Integer.parseInt(addShiftTV.getText().toString()) - 1) );
		}
		else if( v.getId() == R.id.optionset3_mMinusButton )
		{
			if( Integer.parseInt(multShiftTV.getText().toString()) == 15 )
				multShiftTV.setText( "11" );
			else if( Integer.parseInt(multShiftTV.getText().toString()) > 1 )
				multShiftTV.setText( "" + (Integer.parseInt(multShiftTV.getText().toString()) - 2) );
		}
		else
		{
			if( editText1.getText().toString().matches("") )
				editText1.setText("1");
			else if( Integer.parseInt(editText1.getText().toString()) > 1 )
				editText1.setText( "" + (Integer.parseInt(editText1.getText().toString()) -1) );
		}
	}
	
	
	private void getUIElementsFromView(View v)
	{
		switch(cryptoMethodId)
		{
			case CryptoMethods.NGRAPHS : 
				label1 = (TextView)v.findViewById(R.id.optionset1_label);
				editText1 = (EditText)v.findViewById(R.id.optionset1_editText);
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				label1 = (TextView)v.findViewById(R.id.optionset2_label1);
				editText1 = (EditText)v.findViewById(R.id.optionset2_editText);
				label2 = (TextView)v.findViewById(R.id.optionset2_label2);
				radioGroup = (RadioGroup)v.findViewById(R.id.optionset2_radiogroup);
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				label1 = (TextView)v.findViewById(R.id.optionset3_label1);
				multShiftTV = (TextView)v.findViewById(R.id.optionset3_mTextView);
				label2 = (TextView)v.findViewById(R.id.optionset3_label2);
				addShiftTV = (TextView)v.findViewById(R.id.optionset3_aTextView);
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				label1 = (TextView)v.findViewById(R.id.optionset3_label1);
				multShiftTV = (TextView)v.findViewById(R.id.optionset3_mTextView);
				label2 = (TextView)v.findViewById(R.id.optionset3_label2);
				addShiftTV = (TextView)v.findViewById(R.id.optionset3_aTextView);
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				label1 = (TextView)v.findViewById(R.id.optionset1_label);
				editText1 = (EditText)v.findViewById(R.id.optionset1_editText);
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				label1 = (TextView)v.findViewById(R.id.optionset1_label);
				editText1 = (EditText)v.findViewById(R.id.optionset1_editText);
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				label1 = (TextView)v.findViewById(R.id.optionset4_label);
				editText1 = (EditText)v.findViewById(R.id.optionset4_editText);
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				label1 = (TextView)v.findViewById(R.id.optionset4_label);
				editText1 = (EditText)v.findViewById(R.id.optionset4_editText);
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				label1 = (TextView)v.findViewById(R.id.optionset1_label);
				editText1 = (EditText)v.findViewById(R.id.optionset1_editText);
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				label1 = (TextView)v.findViewById(R.id.optionset5_label1);
				editText1 = (EditText)v.findViewById(R.id.optionset5_editText);
				label2 = (TextView)v.findViewById(R.id.optionset5_label2);
				editText2 = (EditText)v.findViewById(R.id.optionset5_lowerfriedman);
				editText3 = (EditText)v.findViewById(R.id.optionset5_upperfriedman);
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				label1 = (TextView)v.findViewById(R.id.optionset2_label1);
				editText1 = (EditText)v.findViewById(R.id.optionset2_editText);
				label2 = (TextView)v.findViewById(R.id.optionset2_label2);
				radioGroup = (RadioGroup)v.findViewById(R.id.optionset2_radiogroup);
				break;
			default:
				break;
		}
	}
	
	private void setOptionLabels()
	{
		label1.setText(optionTitles[0]);
		if(null != label2)
		{
			label2.setText(optionTitles[1]);
			
			if(cryptoMethodId == CryptoMethods.AUTOKEY_DECIPHER)
				label2.setTextSize(12f);
		}
	}
	
	private void setOptionsTitleArray()
	{
		switch(cryptoMethodId)
		{
			case CryptoMethods.NGRAPHS : optionTitles = new String[]{"Length of NGraph"};
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : optionTitles = new String[]{"Keyword", "Shift first"};
				break;
			case CryptoMethods.AFFINE_ENCIPHER : optionTitles = new String[]{"Multiplicative Shift", "Additive Shift"};
				break;
			case CryptoMethods.AFFINE_DECIPHER : optionTitles = new String[]{"Multiplicative Shift", "Additive Shift"};
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : optionTitles = new String[]{"Wordlength"};
				break;
			case CryptoMethods.POLYMONO_CACULATOR : optionTitles = new String[]{"Keyword Size"};
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : optionTitles = new String[]{"Keyword"};
				break;
			case CryptoMethods.VIGENERE_DECIPHER : optionTitles = new String[]{"Keyword"};
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : optionTitles = new String[]{"Keyword Length"};
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : optionTitles = new String[]{"Max Keyword Length", "Friedman Range"};
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : optionTitles = new String[]{"Keyword", "Include Plaintext In Key"};
				break;
			default:
				break;
		}
	}
	
	private void restoreOptions()
	{
		// Note the line below does NOT retrieve preferences as committed by editor, editor gives default name of class opened from (MainActivity)
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
		
		switch(cryptoMethodId)
		{
			case CryptoMethods.NGRAPHS : 
				editText1.setText( Integer.toString(sharedPreferences.getInt("ngraph_size", 1)) );
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				editText1.setText( sharedPreferences.getString("affineknowplaintext_keyword", "the") );
				if( sharedPreferences.getBoolean("affineknownplaintext_shiftfirst", false) )
					radioGroup.check(R.id.optionset2_radiobtn1);
				else
					radioGroup.check(R.id.optionset2_radiobtn2);
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				multShiftTV.setText( Integer.toString(sharedPreferences.getInt("affineencipher_mult_shift", 1)) );
				addShiftTV.setText( Integer.toString(sharedPreferences.getInt("affineencipher_add_shift", 0)) );
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				multShiftTV.setText( Integer.toString(sharedPreferences.getInt("affinedecipher_mult_shift", 1)) );
				addShiftTV.setText( Integer.toString(sharedPreferences.getInt("affinedecipher_add_shift", 0)) );
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				editText1.setText( Integer.toString(sharedPreferences.getInt("splitoffalphabets_num", 1)) );
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				editText1.setText( Integer.toString(sharedPreferences.getInt("polymonocalculator_keywordsize", 1)) );
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				editText1.setText( sharedPreferences.getString("vigenereencipher_keyword", "") );
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				editText1.setText( sharedPreferences.getString("vigeneredecipher_keyword", "") );
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				editText1.setText( Integer.toString(sharedPreferences.getInt("autokeycyphertextattack_keywordlength", 1)) );
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				editText1.setText( Integer.toString(sharedPreferences.getInt("autokeyplaintextattack_maxkeywordlenth", 1)) );
				editText2.setText( Float.toString(sharedPreferences.getFloat("autokeyplaintextattack_lowerfriedman", (float)0.055)) );
				editText3.setText( Float.toString(sharedPreferences.getFloat("autokeyplaintextattack_upperfriedman", (float)2.0)) );
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				editText1.setText( sharedPreferences.getString("autokeydecipher_keyword", "") );
				if( sharedPreferences.getBoolean("autokeydecipher_plaintext", true) )
					radioGroup.check(R.id.optionset2_radiobtn1);
				else
					radioGroup.check(R.id.optionset2_radiobtn2);
				break;
			default:
				break;
		}
	}
	
	void saveOptions()
	{
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		switch(cryptoMethodId)
		{
			case CryptoMethods.NGRAPHS : 
				editor.putInt("ngraph_size", Integer.parseInt( editText1.getText().toString() ) );
				break;
			case CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK : 
				editor.putString("affineknowplaintext_keyword", editText1.getText().toString() );
				if( radioGroup.getCheckedRadioButtonId() == R.id.optionset2_radiobtn1 )
					editor.putBoolean("affineknownplaintext_shiftfirst", true);
				else
					editor.putBoolean("affineknownplaintext_shiftfirst", false);
				break;
			case CryptoMethods.AFFINE_ENCIPHER : 
				editor.putInt("affineencipher_mult_shift", Integer.parseInt( multShiftTV.getText().toString() ) );
				editor.putInt("affineencipher_add_shift", Integer.parseInt( addShiftTV.getText().toString() ) );
				break;
			case CryptoMethods.AFFINE_DECIPHER : 
				editor.putInt("affinedecipher_mult_shift", Integer.parseInt( multShiftTV.getText().toString() ) );
				editor.putInt("affinedecipher_add_shift", Integer.parseInt( addShiftTV.getText().toString() ) );
				break;
			case CryptoMethods.SPLIT_OFF_ALPHABETS : 
				editor.putInt("splitoffalphabets_num", Integer.parseInt( editText1.getText().toString() ) );
				break;
			case CryptoMethods.POLYMONO_CACULATOR : 
				editor.putInt("polymonocalculator_keywordsize", Integer.parseInt( editText1.getText().toString() ) );
				break;
			case CryptoMethods.VIGENERE_ENCIPHER : 
				editor.putString("vigenereencipher_keyword", editText1.getText().toString() );
				break;
			case CryptoMethods.VIGENERE_DECIPHER : 
				editor.putString("vigeneredecipher_keyword", editText1.getText().toString() );
				break;
			case CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK : 
				editor.putInt("autokeycyphertextattack_keywordlength", Integer.parseInt( editText1.getText().toString() ) );
				break;
			case CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK : 
				editor.putInt("autokeyplaintextattack_maxkeywordlenth", Integer.parseInt( editText1.getText().toString() ) );
				editor.putFloat("autokeyplaintextattack_lowerfriedman", Float.parseFloat( editText2.getText().toString() ) );
				editor.putFloat("autokeyplaintextattack_upperfriedman", Float.parseFloat( editText3.getText().toString() ) );
				break;
			case CryptoMethods.AUTOKEY_DECIPHER : 
				editor.putString("autokeydecipher_keyword", editText1.getText().toString() );
				if( radioGroup.getCheckedRadioButtonId() == R.id.optionset2_radiobtn1 )
					editor.putBoolean("autokeydecipher_plaintext", true);
				else
					editor.putBoolean("autokeydecipher_plaintext", false);
				break;
			default:
				break;
		}
		
		editor.commit();
	}
	
	private boolean checkForNullFields()
	{
		Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
		String err = "";
		
		if( cryptoMethodId == CryptoMethods.AUTOKEY_PLAINTEXT_ATTACK )
		{
			boolean isNull = false;
			if( editText1.getText().toString().matches("") )
			{
				editText1.startAnimation(shake);
				isNull = true;
			}
			if( editText2.getText().toString().matches("") )
			{
				editText2.startAnimation(shake);
				isNull = true;
			}
			if( editText3.getText().toString().matches("") )
			{
				editText3.startAnimation(shake);
				isNull = true;
			}
			
			if( isNull )
				Toast.makeText(getActivity(), "Please fill in the fields", Toast.LENGTH_SHORT).show();
			
			return true;
			
		}
		if( cryptoMethodId == CryptoMethods.NGRAPHS || cryptoMethodId == CryptoMethods.SPLIT_OFF_ALPHABETS || 
				cryptoMethodId == CryptoMethods.AUTOKEY_CYPHERTEXT_ATTACK )
		{
			if( editText1.getText().toString().matches("") || editText1.getText().toString().matches("0*") )
				err = "Please enter a length greater than 0";
		}
		else if( cryptoMethodId == CryptoMethods.POLYMONO_CACULATOR )
		{
			if( editText1.getText().toString().matches("") || editText1.getText().toString().matches("0*") )
				err = "Please enter a keyword size";
		}
		else if( cryptoMethodId == CryptoMethods.AFFINE_KNOWN_PLAINTEXT_ATTACK || cryptoMethodId == CryptoMethods.AUTOKEY_DECIPHER ||
				cryptoMethodId == CryptoMethods.VIGENERE_ENCIPHER || cryptoMethodId == CryptoMethods.VIGENERE_DECIPHER )
		{
			if( editText1.getText().toString().matches("") )
				err = "Please enter a keyword";
			else if( !editText1.getText().toString().matches("[A-Za-z]+") )
				err = "A keyword can only contain alpha characters";
		}
	
		if( err != "" )
		{
			Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
			editText1.startAnimation(shake);
			return true;
		}
		
		return false;
	}
}
