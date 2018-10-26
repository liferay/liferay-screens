package com.liferay.mobile.screens.asset;

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

    public AssetEvent(AssetEntry assetEntry) {
        this.assetEntry = assetEntry;
    }

    @Override
    public String getListKey() {
        return String.valueOf(assetEntry.getEntryId());
    }

    @Override
    public AssetEntry getModel() {
        return assetEntry;
    }

    public AssetEntry getAssetEntry() {
        return assetEntry;
    }

    public void setAssetEntry(AssetEntry assetEntry) {
        this.assetEntry = assetEntry;
    }
}