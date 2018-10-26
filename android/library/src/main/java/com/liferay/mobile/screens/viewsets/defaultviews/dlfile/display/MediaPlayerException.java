package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.media.MediaPlayer;

/**
 * @author Víctor Galán Grande
 */

public class MediaPlayerException extends Exception {

    /**
     * The type of error and an ex
     *
     * @see {@link MediaPlayer.OnErrorListener}
     */
    private final int what;
    private final int extra;

    public MediaPlayerException(int what, int extra) {
        super("MediaPlayer error what: " + what + " extra: " + extra);
        this.what = what;
        this.extra = extra;
    }

    public int getWhat() {
        return what;
    }

    public int getExtra() {
        return extra;
    }
}
