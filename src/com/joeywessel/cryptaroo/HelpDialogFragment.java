package com.joeywessel.cryptaroo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HelpDialogFragment extends DialogFragment {
	
	int mCryptoMethodId;
	
	static HelpDialogFragment newInstance(int cryptoMethodId) {
		HelpDialogFragment fragment = new HelpDialogFragment();
		
		Bundle args = new Bundle();
		args.putInt("cryptoMethodId", cryptoMethodId);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialog);
		mCryptoMethodId = getArguments().getInt("cryptoMethodId");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		View v = inflater.inflate(R.layout.activity_help, container, false);
		
		// Setup Dimiss Button
		Button btnOkay = (Button)v.findViewById(R.id.btnOkay);
	    btnOkay.setTypeface(Consts.TYPEFACE.FairViewRegular(getActivity()));
        btnOkay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
        
        // Setup header view
        TextView header = (TextView)v.findViewById(R.id.helpHeader);
        header.setTypeface(Consts.TYPEFACE.FairViewSmallCaps(getActivity()));
        
        // Set help text
        String[] helpStrings = getResources().getStringArray(R.array.help_info);
		TextView helpText = (TextView)v.findViewById(R.id.helpText);
		helpText.setTypeface(Consts.TYPEFACE.FairViewSmallCaps(getActivity()));
		helpText.setText(helpStrings[mCryptoMethodId]);
		
		return v;
	}
}
