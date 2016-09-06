package com.liferay.mobile.screens.asset.display;

import android.content.Context;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.screens.blogs.BlogsEntryDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayFactory {

	public BaseScreenlet getScreenlet(Context context, AssetEntry assetEntry, Map<String, Integer> layouts,
		boolean autoLoad) {

		String className = assetEntry.getClassName();

		switch (className) {
			case "com.liferay.document.library.kernel.model.DLFileEntry":

				BaseFileDisplayScreenlet screenlet = getDLFileEntryScreenlet(context, assetEntry.getMimeType());

				if (screenlet != null) {
					Integer layoutId = layouts.get(screenlet.getClass().getName());

					screenlet.setFileEntry((FileEntry) assetEntry);
					screenlet.setAutoLoad(autoLoad);
					screenlet.render(layoutId);
					return screenlet;
				}
				return null;

			case "com.liferay.blogs.kernel.model.BlogsEntry":

				BlogsEntryDisplayScreenlet blogsScreenlet = new BlogsEntryDisplayScreenlet(context);
				Integer layoutId = layouts.get(blogsScreenlet.getClass().getName());
				blogsScreenlet.setBlogsEntry((BlogsEntry) assetEntry);
				blogsScreenlet.setAutoLoad(autoLoad);
				blogsScreenlet.render(layoutId);
				return blogsScreenlet;

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
