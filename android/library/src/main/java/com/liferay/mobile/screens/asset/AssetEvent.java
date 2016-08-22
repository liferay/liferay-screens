package com.liferay.mobile.screens.asset;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;

public class AssetEvent extends OfflineEventNew {

	private AssetEntry assetEntry;

	public AssetEvent() {
		super();
	}

	public AssetEvent(AssetEntry assetEntry) {
		this.assetEntry = assetEntry;
	}

	public AssetEntry getAssetEntry() {
		return assetEntry;
	}

	public void setAssetEntry(AssetEntry assetEntry) {
		this.assetEntry = assetEntry;
	}
}