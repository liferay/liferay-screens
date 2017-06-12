package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz García
 */
public class PortletDisplayActivity extends ThemeActivity implements PortletDisplayListener {

	private PortletDisplayScreenlet screenlet;
	private List<Integer> jsFiles = new ArrayList<>();
	private List<Integer> cssFiles = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.portlet_display);

		screenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_display_screenlet);
		screenlet.setListener(this);

		if (getIntent().getStringExtra("url") != null) {
			screenlet.setUrl(getIntent().getStringExtra("url"));
		}

		jsFiles.add(R.raw.gallery);
		screenlet.setJsFiles(jsFiles);

		cssFiles.add(R.raw.bigger_pagination);
		cssFiles.add(R.raw.gallery_custom);
		screenlet.setCssFiles(cssFiles);

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

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info(getString(R.string.asset_received_info) + " " + assetEntry.getTitle());
	}
}
