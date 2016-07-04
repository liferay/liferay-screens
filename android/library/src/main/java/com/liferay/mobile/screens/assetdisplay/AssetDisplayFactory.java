package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import com.liferay.mobile.screens.assetdisplay.model.FileEntry;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayFactory {

	public BaseScreenlet getScreenlet(Context context, AssetEntry assetEntry) {
		String className = assetEntry.getClassName();
		switch (className) {
			//case "com.liferay.blogs.kernel.model.BlogsEntry":
			//case "com.liferay.portal.kernel.model.User":
			//case "com.liferay.portlet.journal.model.JournalArticle":
			case "com.liferay.document.library.kernel.model.DLFileEntry":
				String mimeType = assetEntry.getMimeType();
				if (isImage(mimeType)) {
					ImageDisplayScreenlet screenlet = new ImageDisplayScreenlet(context);
					screenlet.setFileEntry((FileEntry) assetEntry);
					return screenlet;
				} else if (isVideo(mimeType)) {
					VideoDisplayScreenlet screenlet = new VideoDisplayScreenlet(context);
					screenlet.setFileEntry((FileEntry) assetEntry);
					return screenlet;
				}
				//} else if (isAudio(mimeType)) {
				//	AudioDisplayScreenlet screenlet = new AudioDisplayScreenlet();
				//	screenlet.setFileEntry((FileEntry) assetEntry);
				//	return screenlet
				//} else if (mimeType.equals("application/pdf")) {
				//	PdfDisplayScreenlet screenlet = new PdfDisplayScreenlet(context);
				//	screenlet.setFileEntry((FileEntry) assetEntry);
				//	return screenlet;
				//}

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
