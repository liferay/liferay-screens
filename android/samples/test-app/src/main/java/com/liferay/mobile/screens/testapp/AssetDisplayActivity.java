package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.user.display.UserAsset;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayActivity extends ThemeActivity implements AssetDisplayListener,
	AssetDisplayInnerScreenletListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_display);

		screenlet = ((AssetDisplayScreenlet) findViewById(R.id.asset_display_screenlet));

		screenlet.setEntryId(getIntent().getLongExtra("entryId", 0));
		screenlet.setListener(this);
		screenlet.setInnerListener(this);
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
		error("Could not receive asset entry", e);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info("Asset entry received! -> " + assetEntry.getTitle());
	}

	@Override
	public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
		AssetEntry assetEntry) {
	}

	@Override
	public View onRenderCustomAsset(AssetEntry assetEntry) {
	}

	private AssetDisplayScreenlet screenlet;
}
