package com.liferay.mobile.screens.testapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
public class GalleryActivity extends ThemeActivity implements GalleryListener, View.OnClickListener {

	private GalleryScreenlet galleryScreenletGrid;
	private GalleryScreenlet galleryScreenletSlideShow;

	private Button changeGalleryView;
	private Button buttonMinus;
	private Button buttonPlus;
	private ProgressDialog progressDialog;

	private int columnsSize = 5;

	private boolean isGridMode = true;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		galleryScreenletGrid = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_grid);
		galleryScreenletGrid.setListener(this);

		galleryScreenletSlideShow = (GalleryScreenlet) findViewById(R.id.gallery_screenlet_slideshow);
		galleryScreenletSlideShow.setListener(this);

		buttonMinus = (Button) findViewById(R.id.image_gallery_button_plus);
		buttonMinus.setOnClickListener(this);

		buttonPlus = (Button) findViewById(R.id.image_gallery_button_minus);
		buttonPlus.setOnClickListener(this);

		changeGalleryView = (Button) findViewById(R.id.change_gallery_view);
		changeGalleryView.setOnClickListener(this);

		createProgressDialog();

		SessionContext.createBasicSession("test@liferay.com", "test");
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
	public void onImageUploadStarted() {
		progressDialog.setProgress(0);
		progressDialog.show();
	}

	@Override
	public void onImageUploadProgress(int totalBytes, int totalBytesSent) {
		float percentage = (totalBytesSent / (float) totalBytes) * 100;
		progressDialog.setProgress((int) percentage);
	}

	@Override
	public void onImageUploadEnd(ImageEntry entry) {
		progressDialog.dismiss();
		info("Image uploaded: " + entry.toString());
	}

	@Override
	public void onImageUploadFailure(Exception e) {
		error("Error uploading image", e);
		progressDialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.change_gallery_view) {
			if (isGridMode) {
				setSlideshowMode();
			} else {
				setGridMode();
			}
		}

		if (v.getId() == R.id.image_gallery_button_minus) {
			if (columnsSize > 1) {
				galleryScreenletGrid.updateView();
				galleryScreenletGrid.setColumnsSize(columnsSize--);
			}
		}

		if (v.getId() == R.id.image_gallery_button_plus) {
			galleryScreenletGrid.updateView();
			galleryScreenletGrid.setColumnsSize(columnsSize++);
		}
	}

	private void setGridMode() {
		isGridMode = true;
		galleryScreenletSlideShow.setVisibility(View.VISIBLE);
		galleryScreenletGrid.setVisibility(View.GONE);
		buttonMinus.setVisibility(View.GONE);
		buttonPlus.setVisibility(View.GONE);

		changeGalleryView.setText(R.string.grid);
	}

	private void setSlideshowMode() {
		isGridMode = false;
		galleryScreenletSlideShow.setVisibility(View.GONE);
		galleryScreenletGrid.setVisibility(View.VISIBLE);
		buttonMinus.setVisibility(View.VISIBLE);
		buttonPlus.setVisibility(View.VISIBLE);

		changeGalleryView.setText(getString(R.string.slideshow));
	}

	private void createProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Uploading image");
		progressDialog.setMessage("Upload in progress");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setCanceledOnTouchOutside(false);
	}
}
