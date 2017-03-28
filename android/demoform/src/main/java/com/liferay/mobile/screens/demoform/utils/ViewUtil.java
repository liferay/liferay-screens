package com.liferay.mobile.screens.demoform.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class ViewUtil {

	private ViewUtil() {

	}

	public static int pixelFromDp(Context context, int dp) {
		Resources resources = context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	public static List<View> getViewsByTagPrefix(ViewGroup root, String tagPrefix) {
		List<View> views = new ArrayList<>();
		final int childCount = root.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = root.getChildAt(i);
			if (child instanceof ViewGroup) {
				views.addAll(getViewsByTagPrefix((ViewGroup) child, tagPrefix));
			}

			final Object tagObj = child.getTag();
			if (tagObj != null && tagObj.toString().startsWith(tagPrefix)) {
				views.add(child);
			}
		}

		return views;
	}
}
