package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayView extends RelativeLayout implements ImageDisplayViewModel, Callback {

	private BaseScreenlet screenlet;
	private FileEntry fileEntry;
	private ImageView imageView;
	private ProgressBar progressBar;

	public ImageDisplayView(Context context) {
		super(context);
	}

	public ImageDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public ImageDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

		imageView = (ImageView) findViewById(R.id.liferay_image_asset);
		progressBar = (ProgressBar) findViewById(R.id.liferay_progress);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
		this.loadImage();
	}

	@Override
	public void setScaleType(ImageView.ScaleType scaleType) {
		imageView.setScaleType(scaleType);
	}

	private void loadImage() {
		progressBar.setVisibility(VISIBLE);
		String path = getResources().getString(R.string.liferay_server) + fileEntry.getUrl();
		Picasso.with(getContext()).load(path).into(imageView, this);
	}

	@Override
	public void onSuccess() {
		progressBar.setVisibility(GONE);
	}

	@Override
	public void onError() {
		progressBar.setVisibility(GONE);
	}
}
