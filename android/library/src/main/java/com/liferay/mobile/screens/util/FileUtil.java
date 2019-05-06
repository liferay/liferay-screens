package com.liferay.mobile.screens.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class FileUtil {

    private FileUtil() {
        super();
    }

    public static File createImageFile() {
        return createFile("PHOTO", Environment.DIRECTORY_PICTURES, ".jpg");
    }

    public static File createVideoFile() {
        return createFile("VIDEO", Environment.DIRECTORY_MOVIES, ".mp4");
    }

    public static File createFile(String name, String directory, String extension) {
        try {
            File storageDir = Environment.getExternalStoragePublicDirectory(directory);
            return File.createTempFile(name, extension, storageDir);
        } catch (IOException e) {
            LiferayLogger.e("error creating temporal file", e);
        }
        return null;
    }

    public static String getPath(final Context context, final Uri uri) {

        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && isDocumentUri(context, uri)) {
            return getDataForKitKat(context, uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                throw new IllegalArgumentException("Google photos not located in the phone are not supported");
            } else {
                return getDataColumn(context, uri, null, null);
            }
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getDataForKitKat(Context context, Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");

        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = "_id=?";
        String[] selectionArgs = new String[] { split[1] };

        return getDataColumn(context, contentUri, selection, selectionArgs);
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
