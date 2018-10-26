package com.liferay.mobile.screens.testapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.liferay.mobile.screens.base.interactor.listener.CacheListener;
import com.liferay.mobile.screens.imagegallery.ImageGalleryListener;
import com.liferay.mobile.screens.imagegallery.ImageGalleryScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

/**
 * * @author Víctor Galán Grande
 */
public class GalleryActivity extends ThemeActivity implements ImageGalleryListener, OnClickListener, CacheListener {

    private ImageGalleryScreenlet imageGalleryScreenletGrid;
    private ImageGalleryScreenlet imageGalleryScreenletSlideShow;
    private ImageGalleryScreenlet imageGalleryScreenletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imageGalleryScreenletGrid = findViewById(R.id.gallery_screenlet_grid);
        imageGalleryScreenletGrid.setListener(this);

        imageGalleryScreenletSlideShow = findViewById(R.id.gallery_screenlet_slideshow);
        imageGalleryScreenletSlideShow.setListener(this);

        imageGalleryScreenletList = findViewById(R.id.gallery_screenlet_list);
        imageGalleryScreenletList.setListener(this);
        imageGalleryScreenletList.setCacheListener(this);

        Button changeGalleryViewGrid = findViewById(R.id.change_gallery_view_grid);
        changeGalleryViewGrid.setOnClickListener(this);

        Button changeGalleryViewSlideShow = findViewById(R.id.change_gallery_view_slideshow);
        changeGalleryViewSlideShow.setOnClickListener(this);

        Button changeGalleryViewList = findViewById(R.id.change_gallery_view_list);
        changeGalleryViewList.setOnClickListener(this);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        error(getString(R.string.page_error), e);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<ImageEntry> entries, int rowCount) {
        info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
    }

    @Override
    public void onListItemSelected(ImageEntry imageEntry, View view) {

        LiferayLogger.i("Image selected: " + imageEntry.getImageUrl());
        imageGalleryScreenletGrid.showImageInFullScreenActivity(imageEntry);
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
    public void onImageEntryDeleted(long imageEntryId) {
        info(getString(R.string.image_deleted_info) + " " + imageEntryId);
    }

    @Override
    public void onImageUploadStarted(Uri pictureUri, String title, String description, String changelog) {
        LiferayLogger.i("Image upload started: "
            + pictureUri
            + " title: "
            + title
            + " description: "
            + description
            + " changelog: "
            + changelog);
    }

    @Override
    public void onImageUploadProgress(int totalBytes, int totalBytesSent) {
        LiferayLogger.i("Image upload progress " + totalBytes + " from " + totalBytesSent);
    }

    @Override
    public void onImageUploadEnd(ImageEntry entry) {
        info(getString(R.string.image_upload_end_info) + " " + entry.getImageUrl());
    }

    @Override
    public boolean showUploadImageView(String actionName, Uri pictureUri, int screenletId) {
        return false;
    }

    @Override
    public int provideImageUploadDetailView() {
        return 0;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:
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
        imageGalleryScreenletSlideShow.setVisibility(GONE);
        imageGalleryScreenletGrid.setVisibility(VISIBLE);
        imageGalleryScreenletList.setVisibility(GONE);
    }

    private void setSlideshowMode() {
        imageGalleryScreenletSlideShow.setVisibility(VISIBLE);
        imageGalleryScreenletGrid.setVisibility(GONE);
        imageGalleryScreenletList.setVisibility(GONE);
    }

    private void setListMode() {
        imageGalleryScreenletList.setVisibility(VISIBLE);
        imageGalleryScreenletSlideShow.setVisibility(GONE);
        imageGalleryScreenletGrid.setVisibility(GONE);
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.error_when) + " " + userAction, e);
    }
}
