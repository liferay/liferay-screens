package com.liferay.mobile.screens.asset.display;

import com.liferay.mobile.screens.asset.list.AssetEntry;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayListener {

	void onRetrieveAssetFailure(Exception exception);

	void onRetrieveAssetSuccess(AssetEntry assetEntry);
}
