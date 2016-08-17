package com.liferay.mobile.screens.asset.display;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.thread.listener.OfflineListenerNew;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayListener extends OfflineListenerNew {

	void onRetrieveAssetSuccess(AssetEntry assetEntry);
}
