package com.liferay.mobile.screens.viewsets.defaultviews.assetdisplay;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.ImageDisplayActivity;
import com.liferay.mobile.screens.assetdisplay.model.FileEntry;
import com.liferay.mobile.screens.assetdisplay.view.ImageDisplayViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayView extends LinearLayout implements ImageDisplayViewModel, View.OnClickListener {

	public ImageDisplayView(Context context) {
		super(context);
	}

	public ImageDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
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

		_btnViewImage = (Button) findViewById(R.id.liferay_btn_image);
		_imageView = (ImageView) findViewById(R.id.liferay_image_asset);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		_fileEntry = fileEntry;
		_btnViewImage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getContext(), ImageDisplayActivity.class);
		intent.putExtra("filepath", getResources().getString(R.string.liferay_server) + _fileEntry.getUrl());
		getContext().startActivity(intent);
	}

	private Button _btnViewImage;
	private BaseScreenlet _screenlet;
	private FileEntry _fileEntry;
	private ImageView _imageView;
}
