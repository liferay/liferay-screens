package com.liferay.mobile.screens.base;

import android.net.Uri;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent {

	private final Uri fileUri;

	public MediaStoreEvent(Uri fileUri) {
		this.fileUri = fileUri;
	}

	public Uri getFileUri() {
		return fileUri;
	}
}
