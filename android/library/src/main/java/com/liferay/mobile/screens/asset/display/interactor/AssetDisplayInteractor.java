package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayInteractor extends Interactor<AssetDisplayListener> {

	void getAssetEntry(long entryId) throws Exception;

	void getAssetEntry(String className, long classPK) throws Exception;
}
