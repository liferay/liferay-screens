package com.liferay.mobile.screens.asset.display;

import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;

/**
 * @author Sarai Díaz García
 */

public interface AssetDisplayInnerScreenletListener {

	void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
		AssetEntry assetEntry);

	View onRenderCustomAsset(AssetEntry assetEntry);
}
