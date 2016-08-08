package com.liferay.mobile.screens.filedisplay;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Sarai Díaz García
 */
public class DownloadService extends IntentService {

	public static final String FILE_DOWNLOAD_PROGRESS = "FILE_DOWNLOAD_PROGRESS";
	public static final String URL = "URL";
	public static final String CACHE_DIR = "CACHE_DIR";
	public static final String FILENAME = "FILENAME";
	public static final String RESULT_RECEIVER = "RESULT_RECEIVER";
	public static final int UPDATE_PROGRESS = 1;
	public static final int FINISHED_DOWNLOAD = 2;

	public DownloadService() {
		super("DownloadService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		String serverUrl = intent.getStringExtra(URL);
		String cacheDir = intent.getStringExtra(CACHE_DIR);
		String filename = intent.getStringExtra(FILENAME);
		ResultReceiver receiver = intent.getParcelableExtra(RESULT_RECEIVER);

		try {
			URL url = new URL(serverUrl);
			URLConnection connection = url.openConnection();
			connection.connect();

			int fileLength = connection.getContentLength();

			InputStream input = new BufferedInputStream(connection.getInputStream());

			OutputStream output = new FileOutputStream(cacheDir + "/" + filename);

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;

				output.write(data, 0, count);

				Bundle resultData = new Bundle();
				int progress = (int) (total * 100 / fileLength);
				resultData.putInt(FILE_DOWNLOAD_PROGRESS, progress);
				receiver.send(UPDATE_PROGRESS, resultData);
				LiferayLogger.i("Progress downloading file: " + progress);
			}

			output.flush();
			output.close();
			input.close();

			receiver.send(FINISHED_DOWNLOAD, null);
		} catch (IOException e) {
			LiferayLogger.e("Error downloading file", e);
			receiver.send(-1, null);
		}
	}
}