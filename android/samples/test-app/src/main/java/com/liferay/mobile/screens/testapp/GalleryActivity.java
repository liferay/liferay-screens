package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.GalleryListener;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

/**
 * * @author Víctor Galán Grande
 */
public class GalleryActivity extends ThemeActivity
	implements GalleryListener, View.OnClickListener {

	private GalleryScreenlet galleryScreenletGrid;
	private GalleryScreenlet galleryScreenletSlideShow;

	private Button changeGalleryView;

	private Button buttonMinus;
	private Button buttonPlus;

	int columnsSize = 5;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		galleryScreenletGrid = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_grid);
		galleryScreenletGrid.setListener(this);

		galleryScreenletSlideShow =
			(GalleryScreenlet) findViewById(R.id.gallery_screenlet_slideshow);
		galleryScreenletSlideShow.setListener(this);

		buttonMinus = (Button) findViewById(R.id.image_gallery_button_plus);
		buttonMinus.setOnClickListener(this);

		buttonPlus = (Button) findViewById(R.id.image_gallery_button_minus);
		buttonPlus.setOnClickListener(this);

		changeGalleryView = (Button) findViewById(R.id.change_gallery_view);
		changeGalleryView.setOnClickListener(this);

		SessionContext.createBasicSession("test@liferay.com", "test1234");
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<ImageEntry> entries,
		int rowCount) {
		Log.d(GalleryScreenlet.class.getName(),
			"page: " + page + "received with " + entries.size() + "entries");
	}

	@Override
	public void onListItemSelected(ImageEntry image, View view) {
		LiferayLogger.d("Image selected: " + image.getImageUrl());
		galleryScreenletGrid.showImageInFullScreenActivity(image);
	}

	@Override
	public void loadingFromCache(boolean success) {
		LiferayLogger.i("Loading from cache");
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		LiferayLogger.i("Retrieving online cache");
	}

	@Override
	public void storingToCache(Object object) {
		LiferayLogger.i("Storing to cache");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.change_gallery_view) {
			if (changeGalleryView.getText().toString().equals("Slideshow")) {
				galleryScreenletSlideShow.setVisibility(View.VISIBLE);
				galleryScreenletGrid.setVisibility(View.GONE);
				buttonMinus.setVisibility(View.GONE);
				buttonPlus.setVisibility(View.GONE);

				changeGalleryView.setText("Grid");
			} else {
				galleryScreenletSlideShow.setVisibility(View.GONE);
				galleryScreenletGrid.setVisibility(View.VISIBLE);
				buttonMinus.setVisibility(View.VISIBLE);
				buttonPlus.setVisibility(View.VISIBLE);

				changeGalleryView.setText("Slideshow");
			}
		}

		if (v.getId() == R.id.image_gallery_button_minus) {
			if (columnsSize > 1) {
				galleryScreenletGrid.setColumnsSize(--columnsSize);
			}
		}

		if (v.getId() == R.id.image_gallery_button_plus) {
			galleryScreenletGrid.setColumnsSize(++columnsSize);
		}
	}
}
