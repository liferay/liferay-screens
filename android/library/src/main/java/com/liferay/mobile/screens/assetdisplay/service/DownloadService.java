package com.liferay.mobile.screens.assetdisplay.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
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

	public DownloadService() {
		super("DownloadService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String serverUrl = intent.getStringExtra("url");
		ResultReceiver receiver = intent.getParcelableExtra("receiver");
		try {
			URL url = new URL(serverUrl);
			URLConnection connection = url.openConnection();
			connection.connect();

			int fileLength = connection.getContentLength();

			InputStream input = new BufferedInputStream(connection.getInputStream());
			OutputStream output =
				new FileOutputStream(intent.getStringExtra("cacheDir") + "/" + intent.getStringExtra("filename"));

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;

				Bundle resultData = new Bundle();
				resultData.putInt("progress", (int) (total * 100 / fileLength));
				receiver.send(UPDATE_PROGRESS, resultData);
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Bundle resultData = new Bundle();
		resultData.putInt("progress", 100);
		receiver.send(UPDATE_PROGRESS, resultData);
	}

	public static final int UPDATE_PROGRESS = 5454;
}