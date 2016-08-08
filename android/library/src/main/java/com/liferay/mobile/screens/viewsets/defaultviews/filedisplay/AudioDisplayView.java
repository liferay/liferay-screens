package com.liferay.mobile.screens.viewsets.defaultviews.filedisplay;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.filedisplay.FileEntry;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class AudioDisplayView extends LinearLayout implements BaseFileDisplayViewModel {

	public AudioDisplayView(Context context) {
		super(context);
	}

	public AudioDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AudioDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException("showFinishOperation(String) is not supported."
			+ " Use showFinishOperation(FileEntry) instead.");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Could not load file asset: " + e.getMessage());
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this._screenlet = screenlet;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_audioView = (VideoView) findViewById(R.id.liferay_audio_asset);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		_fileEntry = fileEntry;
		loadAudio();
	}

	private void loadAudio() {
		_audioView.setVideoPath(getResources().getString(R.string.liferay_server) + _fileEntry.getUrl());
		_audioView.setMediaController(new MediaController(getContext()));
		_audioView.setZOrderOnTop(true);
		_audioView.requestFocus();
		_audioView.start();
	}

	private BaseScreenlet _screenlet;
	private VideoView _audioView;
	private FileEntry _fileEntry;
}
