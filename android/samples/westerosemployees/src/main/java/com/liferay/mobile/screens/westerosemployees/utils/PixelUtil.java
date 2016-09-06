package com.liferay.mobile.screens.westerosemployees.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Víctor Galán Grande
 */
public class PixelUtil {

	public static int pixelFromDp(Context context, int dp){
		Resources resources = context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}
}
