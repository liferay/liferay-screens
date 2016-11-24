package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import java.util.List;

public class FilteredAssetActivity extends ThemeActivity implements BaseListListener<AssetEntry> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filtered_asset);

		AssetListScreenlet assetListScreenlet = (AssetListScreenlet) findViewById(R.id.filtered_asset_list);
		assetListScreenlet.setListener(this);
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {
		error("Page request failed", e);
	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {
		info("Row " + startRow + " received!");
	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {
		Intent intent = getIntentWithTheme(AssetDisplayActivity.class);
		intent.putExtra("entryId", element.getEntryId());
		DefaultAnimation.startActivityWithAnimation(this, intent);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
