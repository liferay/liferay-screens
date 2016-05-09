package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Javier Gamarra
 */
public class WebViewListActivity extends ThemeActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_content_display_list);
	}
}
