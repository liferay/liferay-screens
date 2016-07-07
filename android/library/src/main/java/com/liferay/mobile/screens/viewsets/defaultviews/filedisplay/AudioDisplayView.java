package com.liferay.mobile.screens.viewsets.defaultviews.filedisplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

		_btnPlayAudio = (Button) findViewById(R.id.liferay_btn_audio);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		_fileEntry = fileEntry;
		_btnPlayAudio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(
					Uri.parse(getResources().getString(R.string.liferay_server) + _fileEntry.getUrl()), "audio/*");
				getContext().startActivity(intent);
			}
		});
	}

	private BaseScreenlet _screenlet;
	private Button _btnPlayAudio;
	private FileEntry _fileEntry;
}
