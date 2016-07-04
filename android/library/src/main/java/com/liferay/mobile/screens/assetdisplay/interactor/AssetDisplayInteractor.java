package com.liferay.mobile.screens.assetdisplay.interactor;

import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayInteractor extends Interactor<AssetDisplayListener> {

	void getAssetEntryExtended(long entryId) throws Exception;
}
