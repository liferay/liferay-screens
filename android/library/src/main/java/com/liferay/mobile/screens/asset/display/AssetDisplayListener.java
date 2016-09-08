package com.liferay.mobile.screens.asset.display;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.thread.listener.BaseCacheListener;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayListener extends BaseCacheListener {

	void onRetrieveAssetSuccess(AssetEntry assetEntry);
}
