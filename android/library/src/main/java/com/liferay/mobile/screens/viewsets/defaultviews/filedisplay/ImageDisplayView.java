package com.liferay.mobile.screens.viewsets.defaultviews.filedisplay;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.filedisplay.FileEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Picasso;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayView extends LinearLayout implements BaseFileDisplayViewModel {

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

		_imageView = (ImageView) findViewById(R.id.liferay_image_asset);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		_fileEntry = fileEntry;
		loadImage();
	}

	private void loadImage() {
		String path = getResources().getString(R.string.liferay_server) + _fileEntry.getUrl();
		Picasso.with(getContext()).load(path).into(_imageView);
	}

	private BaseScreenlet _screenlet;
	private FileEntry _fileEntry;
	private ImageView _imageView;
}
