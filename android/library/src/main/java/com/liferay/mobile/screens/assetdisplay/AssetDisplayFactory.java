package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.FileEntry;
import com.liferay.mobile.screens.filedisplay.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.video.VideoDisplayScreenlet;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayFactory {

	public BaseScreenlet getScreenlet(Context context, AssetEntry assetEntry, HashMap<String, Integer> layouts,
		boolean autoLoad) {

		String className = assetEntry.getClassName();

		switch (className) {
			case "com.liferay.document.library.kernel.model.DLFileEntry":

				BaseFileDisplayScreenlet screenlet = getDLFileEntryScreenlet(context, assetEntry.getMimeType());

				if (screenlet != null) {
					screenlet.setFileEntry((FileEntry) assetEntry);
					screenlet.setAutoLoad(autoLoad);
					screenlet.render(layouts.get(screenlet.getClass().getName()));
					return screenlet;
				}
			default:
				return null;
		}
	}

	private BaseFileDisplayScreenlet getDLFileEntryScreenlet(Context context, String mimeType) {
		if (is(mimeType, R.array.image_mime_types, context)) {
			return new ImageDisplayScreenlet(context);
		} else if (is(mimeType, R.array.video_mime_types, context)) {
			return new VideoDisplayScreenlet(context);
		} else if (is(mimeType, R.array.audio_mime_types, context)) {
			return new AudioDisplayScreenlet(context);
		} else if (is(mimeType, R.array.pdf_mime_types, context)) {
			return new PdfDisplayScreenlet(context);
		}
		return null;
	}

	private boolean is(String mimeType, int typesIds, Context context) {
		String[] mimeTypes = context.getResources().getStringArray(typesIds);
		return Arrays.asList(mimeTypes).contains(mimeType);
	}
}
