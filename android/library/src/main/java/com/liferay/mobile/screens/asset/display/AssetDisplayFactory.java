package com.liferay.mobile.screens.asset.display;

import android.content.Context;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.screens.blogs.BlogsEntryDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayFactory {

    public static BaseScreenlet getScreenlet(Context context, AssetEntry assetEntry, Map<String, Integer> layouts) {

        if (assetEntry instanceof FileEntry || assetEntry instanceof ImageEntry) {
            BaseFileDisplayScreenlet screenlet = getDLFileEntryScreenlet(context, assetEntry.getMimeType());

            if (screenlet != null) {
                Integer layoutId = layouts.get(screenlet.getClass().getName());

                screenlet.render(layoutId);

                if (assetEntry instanceof FileEntry) {
                    screenlet.setFileEntry((FileEntry) assetEntry);
                    screenlet.loadFile();
                } else {
                    screenlet.setClassPK(((ImageEntry) assetEntry).getFileEntryId());
                    screenlet.load();
                }
                return screenlet;
            }
        } else if (assetEntry instanceof WebContent) {
            WebContentDisplayScreenlet webContentDisplayScreenlet = new WebContentDisplayScreenlet(context);

            Integer layoutId = layouts.get(webContentDisplayScreenlet.getClass().getName());

            webContentDisplayScreenlet.render(layoutId);
            webContentDisplayScreenlet.onWebContentReceived((WebContent) assetEntry);

            return webContentDisplayScreenlet;
        } else if (assetEntry instanceof BlogsEntry) {
            BlogsEntryDisplayScreenlet blogsScreenlet = new BlogsEntryDisplayScreenlet(context);

            Integer layoutId = layouts.get(blogsScreenlet.getClass().getName());

            blogsScreenlet.setBlogsEntry((BlogsEntry) assetEntry);
            blogsScreenlet.render(layoutId);
            blogsScreenlet.loadBlogsEntry();

            return blogsScreenlet;
        }
        return null;
    }

    private static BaseFileDisplayScreenlet getDLFileEntryScreenlet(Context context, String mimeType) {
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

    private static boolean is(String mimeType, int typesIds, Context context) {
        String[] mimeTypes = context.getResources().getStringArray(typesIds);
        return Arrays.asList(mimeTypes).contains(mimeType);
    }
}
