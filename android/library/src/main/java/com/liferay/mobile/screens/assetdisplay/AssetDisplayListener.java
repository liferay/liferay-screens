package com.liferay.mobile.screens.assetdisplay;

import com.liferay.mobile.screens.assetlist.AssetEntry;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayListener {

	void onRetrieveAssetFailure(Exception exception);

	void onRetrieveAssetSuccess(AssetEntry assetEntry);
}
