package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class VideoDisplayView extends RelativeLayout implements BaseFileDisplayViewModel {

	private BaseScreenlet screenlet;
	private FileEntry fileEntry;
	private VideoView videoView;
	private TextView message;
	private ProgressBar progressBar;

	public VideoDisplayView(Context context) {
		super(context);
	}

	public VideoDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VideoDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public VideoDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void showStartOperation(String actionName) {
		progressBar.setVisibility(VISIBLE);
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException(
			"showFinishOperation(String) is not supported." + " Use showFinishOperation(FileEntry) instead.");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		progressBar.setVisibility(GONE);
		message.setText(R.string.video_error);
		LiferayLogger.e("Could not load file asset: " + e.getMessage());
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		videoView = (VideoView) findViewById(R.id.liferay_video_asset);
		message = (TextView) findViewById(R.id.liferay_video_message);
		progressBar = (ProgressBar) findViewById(R.id.liferay_progress);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
		loadVideo();
		loadPrepareListener();
		loadErrorListener();
	}

	private void loadVideo() {
		progressBar.setVisibility(VISIBLE);
		videoView.setVideoPath(getResources().getString(R.string.liferay_server) + fileEntry.getUrl());
		videoView.setMediaController(new MediaController(getContext()));
		videoView.setZOrderOnTop(true);
		videoView.requestFocus();
		videoView.start();
	}

	private void loadPrepareListener() {
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				progressBar.setVisibility(GONE);
			}
		});
	}

	private void loadErrorListener() {
		videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				progressBar.setVisibility(GONE);
				message.setText(R.string.video_error);
				return false;
			}
		});
	}
}
