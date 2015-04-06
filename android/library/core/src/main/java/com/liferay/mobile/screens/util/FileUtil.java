package com.liferay.mobile.screens.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class FileUtil {

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
		}
		catch (IOException e) {
			LiferayLogger.e("error creating temporal file", e);
		}
		return null;
	}
}
