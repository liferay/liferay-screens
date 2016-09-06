package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.gallery.GalleryListener;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailUploadDefaultActivity;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

/**
 * * @author Víctor Galán Grande
 */
public class GalleryActivity extends ThemeActivity implements GalleryListener, OnClickListener {

	private GalleryScreenlet galleryScreenletGrid;
	private GalleryScreenlet galleryScreenletSlideShow;
	private GalleryScreenlet galleryScreenletList;

	private Button changeGalleryViewGrid;
	private Button changeGalleryViewSlideShow;
	private Button changeGalleryViewList;

	private boolean isGridMode = true;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		galleryScreenletGrid = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_grid);
		galleryScreenletGrid.setListener(this);

		galleryScreenletSlideShow = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_slideshow);
		galleryScreenletSlideShow.setListener(this);

		galleryScreenletList = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_list);
		galleryScreenletList.setListener(this);

		changeGalleryViewGrid = (Button) findViewById(R.id.change_gallery_view_grid);
		changeGalleryViewGrid.setOnClickListener(this);

		changeGalleryViewSlideShow = (Button) findViewById(R.id.change_gallery_view_slideshow);
		changeGalleryViewSlideShow.setOnClickListener(this);

		changeGalleryViewList = (Button) findViewById(R.id.change_gallery_view_list);
		changeGalleryViewList.setOnClickListener(this);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int startRow, int endRow, Exception e) {
		error("Error loading page", e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int startRow, int endRow, List<ImageEntry> entries,
		int rowCount) {
		Log.i(GalleryScreenlet.class.getName(), "row: " + startRow + "received with " + entries.size() + "entries");
	}

	@Override
	public void onListItemSelected(ImageEntry image, View view) {
		LiferayLogger.i("Image selected: " + image.getImageUrl());
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
	public void onImageEntryDeleteFailure(Exception e) {
		error("Error deleting image", e);
	}

	@Override
	public void onImageEntryDeleted(long imageEntryId) {
		info("Image deleted: " + imageEntryId);
	}

	@Override
	public void onImageUploadStarted(String picturePath, String title, String description) {
		LiferayLogger.i("Image upload started: " + picturePath + " title: " + title + " description: " + description);
	}

	@Override
	public void onImageUploadProgress(int totalBytes, int totalBytesSent) {
		LiferayLogger.i("Image upload progresss " + totalBytes + " from " + totalBytesSent);
	}

	@Override
	public void onImageUploadEnd(ImageEntry entry) {
		info("Image upload end " + entry.getImageUrl());
	}

	@Override
	public void onImageUploadFailure(Exception e) {
		error("Image upload failure", e);
	}

	@Override
	public Class provideImageUploadDetailActivity() {
		return DetailUploadDefaultActivity.class;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.change_gallery_view_grid:
				setGridMode();
				break;
			case R.id.change_gallery_view_slideshow:
				setSlideshowMode();
				break;
			case R.id.change_gallery_view_list:
				setListMode();
				break;
		}
	}

	private void setGridMode() {
		isGridMode = true;
		galleryScreenletSlideShow.setVisibility(GONE);
		galleryScreenletGrid.setVisibility(VISIBLE);
		galleryScreenletList.setVisibility(GONE);
	}

	private void setSlideshowMode() {
		isGridMode = false;
		galleryScreenletSlideShow.setVisibility(VISIBLE);
		galleryScreenletGrid.setVisibility(GONE);
		galleryScreenletList.setVisibility(GONE);
	}

	private void setListMode() {
		isGridMode = false;
		galleryScreenletList.setVisibility(VISIBLE);
		galleryScreenletSlideShow.setVisibility(GONE);
		galleryScreenletGrid.setVisibility(GONE);
	}
}
