package com.liferay.mobile.screens.viewsets.defaultviews;

import android.content.Context;

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
		INFO = new Style.Builder().setBackgroundColor(R.color.liferay_light_blue).build();
		ALERT = new Style.Builder().setBackgroundColor(R.color.liferay_red).build();
	}

	//TODO move to other class
	public static void error(Context context, String message, Exception e) {
		String error = message;
		if (e instanceof IllegalArgumentException) {
			//TODO create validation exception
			error = e.getMessage();
		}
		Crouton.makeText((android.app.Activity) context, error, DefaultCrouton.ALERT).show();
	}
}
