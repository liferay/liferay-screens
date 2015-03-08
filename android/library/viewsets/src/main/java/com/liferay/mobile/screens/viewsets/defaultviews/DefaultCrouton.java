package com.liferay.mobile.screens.viewsets.defaultviews;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.liferay.mobile.screens.viewsets.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class DefaultCrouton {

	public static final Style INFO;
	public static final Style ALERT;

	static {
		INFO = new Style.Builder().setBackgroundColor(R.color.default_light_blue).build();
		ALERT = new Style.Builder().setBackgroundColor(R.color.default_red).build();
	}

	public static void error(Context context, String message, Exception e) {
		String error = message;
		if (e instanceof IllegalArgumentException) {
			//TODO create validation exception
			error = e.getMessage();
		}
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, error, DefaultCrouton.ALERT);
	}

	public static void info(Context context, String message) {
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, message, DefaultCrouton.INFO);
	}

	protected static Activity getContextFromActivity(Context context) {
		if (context instanceof Activity) {
			return (Activity) context;
		}
		else {
			Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
			return (Activity) baseContext;
		}
	}

}
