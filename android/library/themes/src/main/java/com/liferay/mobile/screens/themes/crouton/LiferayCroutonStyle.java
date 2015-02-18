package com.liferay.mobile.screens.themes.crouton;

import com.liferay.mobile.screens.themes.R;

import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class LiferayCroutonStyle {

	public static final Style INFO;
	public static final Style ALERT;

	static {
		INFO = new Style.Builder().setBackgroundColor(R.color.liferay_light_blue).build();
		ALERT = new Style.Builder().setBackgroundColor(R.color.liferay_red).build();
	}
}
