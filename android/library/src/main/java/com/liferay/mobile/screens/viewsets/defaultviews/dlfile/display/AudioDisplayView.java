package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.MediaController;
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
public class AudioDisplayView extends FrameLayout implements BaseFileDisplayViewModel {

	public AudioDisplayView(Context context) {
		super(context);
	}

	public AudioDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AudioDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AudioDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException(
			"showFinishOperation(String) is not supported." + " Use showFinishOperation(FileEntry) instead.");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
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

		audioView = (VideoView) findViewById(R.id.liferay_audio_asset);
		title = (TextView) findViewById(R.id.liferay_audio_title);
		message = (TextView) findViewById(R.id.liferay_audio_message);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
		loadAudio();
		loadErrorListener();
	}

	private void loadAudio() {
		title.setText(fileEntry.getTitle());
		audioView.setVideoPath(getResources().getString(R.string.liferay_server) + fileEntry.getUrl());
		audioView.setMediaController(new MediaController(getContext()));
		audioView.setZOrderOnTop(true);
		audioView.requestFocus();
		audioView.start();
	}

	private void loadErrorListener() {
		audioView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				message.setText(R.string.audio_error);
				return false;
			}
		});
	}

	private BaseScreenlet screenlet;
	private VideoView audioView;
	private FileEntry fileEntry;
	private TextView title;
	private TextView message;
}
