package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.dlfile.display.DownloadService;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import java.io.File;

/**
 * @author Víctor Galán Grande
 */

public abstract class BaseFileDisplayView  extends RelativeLayout implements BaseFileDisplayViewModel {

	protected FileEntry fileEntry;
	private File file;

	public BaseFileDisplayView(Context context) {
		super(context);
	}

	public BaseFileDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseFileDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public BaseFileDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
		file = new File(getContext().getExternalCacheDir().getPath() + "/" + fileEntry.getTitle());

		if (file.exists()) {
			loadFileEntry(file.getAbsolutePath());
		} else {
			downloadFileAndStoreLocally();
		}
	}

	@Override
	public abstract void showStartOperation(String actionName);

	@Override
	public abstract void showFinishOperation(String actionName);

	@Override
	public abstract void showFailedOperation(String actionName, Exception e);

	@Override
	public abstract BaseScreenlet getScreenlet();

	@Override
	public abstract void setScreenlet(BaseScreenlet screenlet);

	public abstract void loadFileEntry(String url);

	public void renderDownloadProgress(int progress) {

	}

	private void downloadFileAndStoreLocally() {
		String filePath = getResources().getString(R.string.liferay_server) + fileEntry.getUrl();

		Intent intent = new Intent(getContext(), DownloadService.class);
		intent.putExtra(DownloadService.REMOTE_PATH, filePath);
		intent.putExtra(DownloadService.LOCAL_PATH, file.getAbsolutePath());
		intent.putExtra(DownloadService.RESULT_RECEIVER, new DownloadReceiver(new Handler()));
		getContext().startService(intent);
	}

	private class DownloadReceiver extends ResultReceiver {

		DownloadReceiver(Handler handler) {
			super(handler);
		}

		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);

			if (resultCode == DownloadService.UPDATE_PROGRESS) {
				int progress = resultData.getInt(DownloadService.FILE_DOWNLOAD_PROGRESS);
				renderDownloadProgress(progress);
			} else if (resultCode == DownloadService.FINISHED_DOWNLOAD) {
				loadFileEntry(file.getAbsolutePath());
			} else {
				//TODO launch error
			}
		}
	}
}
