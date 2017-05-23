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

		screenlet.setJs(R.raw.gallery);
		screenlet.setCss(R.raw.gallery_custom);
		screenlet.load();
	}

	@Override
	public void error(Exception e, String userAction) {
		error(getString(R.string.portlet_display_error), e);
	}

	@Override
	public void onRetrievePortletSuccess(String url) {
		info(getString(R.string.portlet_display_success));
	}
}
