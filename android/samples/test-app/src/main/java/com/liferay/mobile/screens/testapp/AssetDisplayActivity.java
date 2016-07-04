package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayScreenlet;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayActivity extends ThemeActivity implements AssetDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_display);

		((AssetDisplayScreenlet) findViewById(R.id.asset_display_screenlet)).setListener(this);

		SessionContext.createBasicSession("test@liferay.com", "liferay");
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
		error("Could not receive asset entry", e);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info("Asset entry received! -> " + assetEntry.getTitle());
	}
}
