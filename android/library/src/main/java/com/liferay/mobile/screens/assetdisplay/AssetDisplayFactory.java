package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.FileEntry;
import com.liferay.mobile.screens.filedisplay.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.video.VideoDisplayScreenlet;
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

				String mimeType = assetEntry.getMimeType();
				BaseFileDisplayScreenlet screenlet = null;

				if (isImage(mimeType)) {
					screenlet = new ImageDisplayScreenlet(context);
				} else if (isVideo(mimeType)) {
					screenlet = new VideoDisplayScreenlet(context);
				} else if (isAudio(mimeType)) {
					screenlet = new AudioDisplayScreenlet(context);
				} else if (mimeType.equals("application/pdf")) {
					screenlet = new PdfDisplayScreenlet(context);
				}

				screenlet.setFileEntry((FileEntry) assetEntry);
				screenlet.setAutoLoad(autoLoad);
				screenlet.render(layouts.get(screenlet.getClass().getName()));
				return screenlet;
			default:
				return null;
		}
	}

	private boolean isImage(String mimeType) {
		return "image/png".equals(mimeType) || "image/jpeg".equals(mimeType) || "image/jpg".equals(mimeType)
			|| "image/gif".equals(mimeType) || "image/x-ms-bmp".equals(mimeType);
	}

	private boolean isVideo(String mimeType) {
		return "video/mp4".equals(mimeType) || "video/x-flv".equals(mimeType) || "video/3gp".equals(mimeType)
			|| "video/quicktime".equals(mimeType) || "video/x-msvideo".equals(mimeType) || "video/x-ms-wmv".equals(
			mimeType);
	}

	private boolean isAudio(String mimeType) {
		return "audio/mpeg".equals(mimeType) || "audio/mpeg3".equals(mimeType) || "audio/wav".equals(mimeType);
	}
}
