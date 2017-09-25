package com.liferay.mobile.screens.dlfile.display.video;

import com.liferay.mobile.screens.asset.display.AssetDisplayListener;

/**
 * @author Víctor Galán Grande
 */

public interface VideoDisplayScreenletListener extends AssetDisplayListener {

	void onVideoPrepared();

	void onVideoError(Exception e);

	void onVideoCompleted();

}
