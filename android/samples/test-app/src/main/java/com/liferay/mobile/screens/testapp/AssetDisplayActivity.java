package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayActivity extends ThemeActivity implements AssetDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_display);

		AssetDisplayScreenlet screenlet = ((AssetDisplayScreenlet) findViewById(R.id.asset_display_screenlet));

		screenlet.setEntryId(getIntent().getLongExtra("entryId", 0));
		screenlet.setListener(this);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	public void error(Exception e, String userAction) {
		error("Could not receive asset entry", e);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info("Asset entry received! -> " + assetEntry.getTitle());
	}
}
