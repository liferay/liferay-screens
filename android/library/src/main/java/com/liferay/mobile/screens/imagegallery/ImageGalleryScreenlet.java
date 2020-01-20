/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.imagegallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.MediaStoreCallback;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryEvent;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryInteractorListener;
import com.liferay.mobile.screens.imagegallery.interactor.delete.ImageGalleryDeleteInteractor;
import com.liferay.mobile.screens.imagegallery.interactor.load.ImageGalleryLoadInteractor;
import com.liferay.mobile.screens.imagegallery.interactor.upload.ImageGalleryUploadInteractor;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.imagegallery.view.ImageGalleryViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.DefaultUploadDialog;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.DetailImageActivity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import rx.functions.Action;
import rx.functions.Action1;

/**
 * @author Víctor Galán Grande
 */
public class ImageGalleryScreenlet extends BaseListScreenlet<ImageEntry, ImageGalleryLoadInteractor>
    implements ImageGalleryInteractorListener {

    //public static final String DELETE_IMAGE = "DELETE_IMAGE";
    public static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";
    private final Map<String, ImageGalleryUploadInteractor> uploadInteractors = new HashMap<>();
    private long repositoryId;
    private long folderId;
    private String[] mimeTypes;
    private AlertDialog choseOriginDialog;

    public ImageGalleryScreenlet(Context context) {
        super(context);
    }

    public ImageGalleryScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageGalleryScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageGalleryScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {

        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.ImageGalleryScreenlet, 0, 0);

        PicassoScreens.setCachePolicy(this.cachePolicy);

        repositoryId = castToLong(typedArray.getString(R.styleable.ImageGalleryScreenlet_repositoryId));
        folderId = castToLong(typedArray.getString(R.styleable.ImageGalleryScreenlet_folderId));
        mimeTypes = parseMimeTypes(typedArray.getString(R.styleable.ImageGalleryScreenlet_mimeTypes));

        typedArray.recycle();

        return super.createScreenletView(context, attributes);
    }

    @Override
    protected ImageGalleryLoadInteractor createInteractor(String actionName) {
        return new ImageGalleryLoadInteractor();
    }

    /**
     * Loads the first page of images.
     */
    public void load() {
        loadPage(0);
    }

    public void startMediaSelectorAndUpload() {
        Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

        Action1<Boolean> camera = new Action1<Boolean>() {
            @Override
            public void call(Boolean permissionsGranted) {
                if (permissionsGranted) {
                    openCamera();
                }
                choseOriginDialog.dismiss();
            }
        };

        Action1<Boolean> gallery = new Action1<Boolean>() {
            @Override
            public void call(Boolean permissionsGranted) {
                if (permissionsGranted) {
                    openGallery();
                }
                choseOriginDialog.dismiss();
            }
        };

        choseOriginDialog = new MediaStoreSelectorDialog().createOriginDialog(activity, camera, gallery, null);
        choseOriginDialog.show();
    }

    /**
     * Retrieves the `fileEntryId` of the image and calls {@link #deleteEntry(long)}
     * to delete the selected image.
     *
     * @param entry image to be deleted.
     */
    public void deleteEntry(ImageEntry entry) {
        deleteEntry(entry.getFileEntryId());
    }

    /**
     * Deletes the image associated with the given `fileEntryId`.
     */
    public void deleteEntry(long fileEntryId) {
        ImageGalleryDeleteInteractor imageGalleryDeleteInteractor = new ImageGalleryDeleteInteractor();
        imageGalleryDeleteInteractor.start(new ImageGalleryEvent(new ImageEntry(fileEntryId)));
    }

    /**
     * Tries to delete all values stored in the cache.
     *
     * @throws IOException when can't delete the cache
     */
    public void deleteCaches() throws IOException {
        LiferayServerContext.getOkHttpClient().getCache().evictAll();
        try {
            Cache.destroy(groupId, userId, ImageGalleryEvent.class.getName());
        } catch (Exception e) {
            LiferayLogger.e("Error deleting db", e);
        }
    }

    /**
     * Opens the device camera to upload a new photo.
     */
    public void openCamera() {
        MediaStoreRequestShadowActivity.show(getContext(), MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA,
            new MediaStoreCallback() {

                @Override
                public void onUriReceived(Uri uri) {
                    onPictureUriReceived(uri);
                }
            });
    }

    /**
     * Opens the device image gallery to upload a new photo.
     */
    public void openGallery() {
        MediaStoreRequestShadowActivity.show(getContext(), MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY,
            new MediaStoreCallback() {

                @Override
                public void onUriReceived(Uri uri) {
                    onPictureUriReceived(uri);
                }
            });
    }

    /**
     * Called when an image is clicked.
     */
    public void onImageClicked(ImageEntry image, View view) {
        if (getListener() != null) {
            getListener().onListItemSelected(image, view);
        }
    }

    @Override
    public void onImageEntryDeleted(long imageEntryId) {
        getViewModel().deleteImage(imageEntryId);

        if (getListener() != null) {
            getListener().onImageEntryDeleted(imageEntryId);
        }
    }

    public void onPictureUriReceived(Uri pictureUri) {
        ImageGalleryUploadInteractor imageGalleryUploadInteractor = getUploadInteractor();
        LiferayLogger.e("We initialize the interactor to be able to send him messages, objId:"
            + imageGalleryUploadInteractor.toString());

        int uploadDetailViewLayout = 0;
        if (getListener() != null) {
            boolean showed = getListener().showUploadImageView(UPLOAD_IMAGE, pictureUri, getScreenletId());

            if (showed) {
                return;
            }

            uploadDetailViewLayout = getListener().provideImageUploadDetailView();
        }

        startUploadDetail(uploadDetailViewLayout, pictureUri);
    }

    @Override
    public void onPictureUploaded(ImageEntry entry) {

        getViewModel().imageUploadComplete();

        if (entry != null) {
            getViewModel().addImage(entry);

            if (getListener() != null) {
                getListener().onImageUploadEnd(entry);
            }
        }
    }

    @Override
    public void onPictureUploadProgress(int totalBytes, int totalBytesSent) {
        getViewModel().imageUploadProgress(totalBytes, totalBytesSent);

        if (getListener() != null) {
            getListener().onImageUploadProgress(totalBytes, totalBytesSent);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        getViewModel().showFailedOperation(userAction, e);

        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    public void onPictureUploadInformationReceived(Uri pictureUri, String title, String description, String changelog) {
        getViewModel().imageUploadStart(pictureUri);

        ImageGalleryUploadInteractor imageGalleryUploadInteractor = getUploadInteractor();
        ImageGalleryEvent event = new ImageGalleryEvent(pictureUri, title, description, changelog);
        event.setFolderId(folderId);
        event.setRepositoryId(repositoryId);
        imageGalleryUploadInteractor.start(event);

        if (getListener() != null) {
            getListener().onImageUploadStarted(pictureUri, title, description, changelog);
        }
    }

    /**
     * Starts an activity which shows the image in full screen.
     */
    public void showImageInFullScreenActivity(ImageEntry image) {
        Intent intent = new Intent(getContext(), DetailImageActivity.class);
        intent.putExtra(DetailImageActivity.GALLERY_SCREENLET_IMAGE_DETAILED, image);

        getContext().startActivity(intent);
    }

    @Override
    protected void onScreenletAttached() {
        if (autoLoad) {
            autoLoad();
        }
    }

    public ImageGalleryListener getListener() {
        return ((ImageGalleryListener) super.getListener());
    }

    public void setListener(ImageGalleryListener listener) {
        this.listener = listener;
    }

    @Override
    protected void loadRows(ImageGalleryLoadInteractor interactor) {
        interactor.start(repositoryId, folderId, mimeTypes);
    }

    public ImageGalleryViewModel getViewModel() {
        return ((ImageGalleryViewModel) super.getViewModel());
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * Checks if there is a session created and if exists {@link #groupId} attribute
     * and then calls {@link #load()} method.
     */
    protected void autoLoad() {
        if (SessionContext.isLoggedIn() && groupId > 0) {
            load();
        }
    }

    /**
     * Parse the given mime types.
     *
     * @return array with all the mime types.
     */
    protected String[] parseMimeTypes(String mimeTypesRaw) {
        if (mimeTypesRaw == null) {
            return null;
        }

        return mimeTypesRaw.split(",");
    }

    /**
     * Starts a dialog for uploading the new selected image.
     *
     * @param uploadDetailView detail layout.
     * @param pictureUri image Uri.
     */
    protected void startUploadDetail(@LayoutRes int uploadDetailView, final Uri pictureUri) {
        Context context = LiferayScreensContext.getContext();

        View view = inflateView(uploadDetailView, context);

        if (view instanceof BaseDetailUploadView) {
            BaseDetailUploadView baseDetailUploadView = (BaseDetailUploadView) view;

            baseDetailUploadView.initializeUploadView(UPLOAD_IMAGE, pictureUri, getScreenletId());
            new DefaultUploadDialog().createDialog(baseDetailUploadView, getContext()).show();
        } else {
            LiferayLogger.e("Detail upload view has to be a subclass of BaseDetailUploadView");
        }
    }

    private View inflateView(@LayoutRes int uploadDetailView, Context context) {
        ContextThemeWrapper ctx = new ContextThemeWrapper(context, R.style.default_transparent_theme);
        int view = uploadDetailView == 0 ? R.layout.default_upload_detail_activity : uploadDetailView;
        return LayoutInflater.from(ctx).inflate(view, null, false);
    }

    private ImageGalleryUploadInteractor getUploadInteractor() {
        if (uploadInteractors.containsKey(UPLOAD_IMAGE)) {
            return uploadInteractors.get(UPLOAD_IMAGE);
        }
        ImageGalleryUploadInteractor imageGalleryUploadInteractor = new ImageGalleryUploadInteractor();
        decorateInteractor(UPLOAD_IMAGE, imageGalleryUploadInteractor);
        uploadInteractors.put(UPLOAD_IMAGE, imageGalleryUploadInteractor);
        imageGalleryUploadInteractor.onScreenletAttached(this);
        return imageGalleryUploadInteractor;
    }
}
