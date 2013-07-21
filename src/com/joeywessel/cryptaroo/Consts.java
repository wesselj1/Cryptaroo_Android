package com.joeywessel.cryptaroo;

import android.content.Context;
import android.graphics.Typeface;

public class Consts {

	public static final class TYPEFACE {
		
		public static Typeface FAIRVIEW_REGULAR = null;
		public static Typeface FAIRVIEW_SMALLCAPS = null;
		
		public static Typeface FairViewRegular(Context context) {
			if( FAIRVIEW_REGULAR == null ) {
				FAIRVIEW_REGULAR = Typeface.createFromAsset(context.getAssets(), "fonts/fairview_regular.otf");
			}
			return FAIRVIEW_REGULAR;
		}
		
		public static Typeface FairViewSmallCaps(Context context) {
			if( FAIRVIEW_SMALLCAPS == null ) {
				FAIRVIEW_SMALLCAPS = Typeface.createFromAsset(context.getAssets(), "fonts/fairview_smallcaps.otf");
			}
			return FAIRVIEW_SMALLCAPS;
		}
	}
}
