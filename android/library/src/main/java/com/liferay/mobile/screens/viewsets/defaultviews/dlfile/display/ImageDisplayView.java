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
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayView extends BaseFileDisplayView implements Callback {

	private BaseScreenlet screenlet;
	private ImageView imageView;

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
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public void loadFileEntry(String url) {
		loadImage(url);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		imageView = (ImageView) findViewById(R.id.liferay_image_asset);
		progressBar = (ProgressBar) findViewById(R.id.liferay_progress);
	}

	private void loadImage(String url) {
		progressBar.setVisibility(VISIBLE);

		if(url.startsWith("/")) {
			Picasso.with(getContext()).load(new File(url)).into(imageView, this);
		} else {
			Picasso.with(getContext()).load(url).into(imageView, this);
		}
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
