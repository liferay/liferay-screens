package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
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

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {

	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
