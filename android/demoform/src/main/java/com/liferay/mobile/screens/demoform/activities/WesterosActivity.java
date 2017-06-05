package com.liferay.mobile.screens.demoform.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import com.liferay.mobile.screens.demoform.R;

/**
 * @author Víctor Galán Grande
 */
public abstract class WesterosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTransparentMenuBar();
	}

	private void setTransparentMenuBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setStatusBar();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setStatusBar() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.background_gray_westeros));
	}
}
