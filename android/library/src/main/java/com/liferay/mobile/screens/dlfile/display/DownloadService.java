package com.liferay.mobile.screens.dlfile.display;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class DownloadService extends IntentService {

    public static final String FILE_DOWNLOAD_PROGRESS = "FILE_DOWNLOAD_PROGRESS";
    public static final String REMOTE_PATH = "REMOTE_PATH";
    public static final String LOCAL_PATH = "LOCAL_PATH";
    public static final String RESULT_RECEIVER = "RESULT_RECEIVER";
    public static final String EXCEPTION = "EXCEPTION";

    public static final int ERROR_DOWNLOADING = -1;
    public static final int UPDATE_PROGRESS = 1;
    public static final int FINISHED_DOWNLOAD = 2;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String remotePath = intent.getStringExtra(REMOTE_PATH);
        String localPath = intent.getStringExtra(LOCAL_PATH);
        ResultReceiver receiver = intent.getParcelableExtra(RESULT_RECEIVER);

        downloadFile(remotePath, localPath, receiver);
    }

    private void downloadFile(String remotePath, String localPath, ResultReceiver receiver) {
        OutputStream output = null;
        InputStream input = null;

        try {
            URL url = new URL(remotePath);
            URLConnection connection = url.openConnection();
            Map<String, String> headers = LiferayServerContext.getAuthHeaders();

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            connection.connect();

            int fileLength = connection.getContentLength();

            input = new BufferedInputStream(connection.getInputStream());
            output = new FileOutputStream(localPath);

            byte[] data = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                output.write(data, 0, count);

                sendProgress(receiver, fileLength, total);
            }

            output.flush();
            receiver.send(FINISHED_DOWNLOAD, null);
        } catch (Exception e) {
            sendException(receiver, e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                sendException(receiver, e);
            }
        }
    }

    private void sendProgress(ResultReceiver receiver, int fileLength, long total) {
        int progress = (int) (total * 100 / fileLength);
        Bundle resultData = new Bundle();
        resultData.putInt(FILE_DOWNLOAD_PROGRESS, progress);
        receiver.send(UPDATE_PROGRESS, resultData);
        LiferayLogger.i("Progress downloading file: " + progress);
    }

    private void sendException(ResultReceiver receiver, Exception e) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXCEPTION, e);
        receiver.send(ERROR_DOWNLOADING, bundle);
        LiferayLogger.e("Error downloading: " + e.getMessage());
    }
}