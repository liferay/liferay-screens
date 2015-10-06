package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;

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
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<AssetEntry> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {

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
}
