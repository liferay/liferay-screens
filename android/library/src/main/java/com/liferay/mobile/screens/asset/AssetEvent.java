package com.liferay.mobile.screens.asset;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import org.json.JSONObject;

public class AssetEvent extends ListEvent<AssetEntry> {

	private AssetEntry assetEntry;

	public AssetEvent() {
		super();
	}

	public AssetEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getCacheKey() {
		return assetEntry.getEntryId();
	}

	@Override
	public AssetEntry getModel() {
		return assetEntry;
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