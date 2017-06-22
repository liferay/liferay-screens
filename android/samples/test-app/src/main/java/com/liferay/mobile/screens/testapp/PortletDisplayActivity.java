package com.liferay.mobile.screens.testapp;

import android.content.Intent;
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

		cssFiles.add(R.raw.portletcss);
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

	@Override
	public void onScriptMessageHandler(String namespace, String body) {
		if ("gallery".equals(namespace)) {
			String[] allImgSrc = body.split(",");
			int imgSrcPosition = Integer.parseInt(allImgSrc[allImgSrc.length - 1]);

			Intent intent = new Intent(getApplicationContext(), DetailMediaGalleryActivity.class);
			intent.putExtra("allImgSrc", allImgSrc);
			intent.putExtra("imgSrcPosition", imgSrcPosition);
			startActivity(intent);
		}
	}
}
