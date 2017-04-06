package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class PortletDisplayActivity extends ThemeActivity implements PortletDisplayListener {

	private PortletDisplayScreenlet screenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.portlet_display);

		screenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_display_screenlet);
		screenlet.setListener(this);

		if (getIntent().getStringExtra("url") != null) {
			screenlet.setUrl(getIntent().getStringExtra("url"));
		}

		screenlet.load();
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void onRetrievePortletSuccess(String url) {
		
	}
}
