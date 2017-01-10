package com.liferay.mobile.screens.base;

import android.net.Uri;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent {

	private final Uri filePath;

	public MediaStoreEvent(Uri filePath) {
		this.filePath = filePath;
	}

	public Uri getFilePath() {
		return filePath;
	}
}
