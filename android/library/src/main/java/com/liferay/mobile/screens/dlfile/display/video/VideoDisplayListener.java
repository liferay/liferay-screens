package com.liferay.mobile.screens.dlfile.display.video;

import com.liferay.mobile.screens.asset.display.AssetDisplayListener;

/**
 * @author Víctor Galán Grande
 */

public interface VideoDisplayListener extends AssetDisplayListener {

    /**
     * Called when the video is ready to be displayed.
     */
    void onVideoPrepared();

    /**
     * Called when there is an error displaying the video.
     *
     * @param e exception error
     */
    void onVideoError(Exception e);

    /**
     * Called when the video is completed.
     */
    void onVideoCompleted();
}
