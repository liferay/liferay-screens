package com.liferay.mobile.screens.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryEvent;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractorImpl;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailImageActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailUploadDefaultActivity;
import java.io.IOException;

/**
 * @author Víctor Galán Grande
 */
public class GalleryScreenlet extends BaseListScreenlet<ImageEntry, GalleryLoadInteractorImpl>
	implements GalleryInteractorListener {

	public static final String LOAD_GALLERY = "LOAD_GALLERY";
	public static final String DELETE_IMAGE = "DELETE_IMAGE";
	public static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";
	private long folderId;
	private String[] mimeTypes;

	public GalleryScreenlet(Context context) {
		super(context);
	}

	public GalleryScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GalleryScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.GalleryScreenlet, 0, 0);

		PicassoScreens.setOfflinePolicy(this.offlinePolicy);

		folderId = castToLong(typedArray.getString(R.styleable.GalleryScreenlet_folderId));
		mimeTypes = parseMimeTypes(typedArray.getString(R.styleable.GalleryScreenlet_mimeTypes));

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected GalleryLoadInteractorImpl createInteractor(String actionName) {
		return new GalleryLoadInteractorImpl();
	}

	@Override
	protected void onUserAction(String userActionName, GalleryLoadInteractorImpl interactor, Object... args) {
		switch (userActionName) {
			case LOAD_GALLERY:
				loadPage(0);
				break;
			case DELETE_IMAGE:
				long fileEntryId = (long) args[0];
				GalleryDeleteInteractorImpl galleryDeleteInteractor = new GalleryDeleteInteractorImpl();
				galleryDeleteInteractor.start(new GalleryEvent(new ImageEntry(fileEntryId)));
				break;
			case UPLOAD_IMAGE:
				String picturePath = (String) args[0];
				String title = (String) args[1];
				String description = (String) args[2];
				GalleryUploadInteractorImpl galleryUploadInteractor = new GalleryUploadInteractorImpl();
				galleryUploadInteractor.start(folderId, title, description, picturePath);
				break;
		}
	}

	public void load() {
		performUserAction(LOAD_GALLERY);
	}

	public void deleteEntry(ImageEntry entry) {
		deleteEntry(entry.getFileEntryId());
	}

	public void deleteEntry(long fileEntryId) {
		performUserAction(DELETE_IMAGE, fileEntryId);
	}

	public void deleteCaches() throws IOException {
		LiferayServerContext.getOkHttpClient().getCache().evictAll();
		try {
			Cache.destroy(groupId, userId, GalleryEvent.class.getName());
		} catch (Exception e) {
			LiferayLogger.e("Error deleting db", e);
		}
	}

	public void openCamera() {
		startShadowActivityForMediaStore(MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA);
	}

	public void openGallery() {
		startShadowActivityForMediaStore(MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY);
	}

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

	@Override
	public void onPicturePathReceived(String picturePath) {
		Class activityUploadDetail = null;
		if (getListener() != null) {
			activityUploadDetail = getListener().provideImageUploadDetailActivity();
		}

		startUploadDetailActivity(activityUploadDetail, picturePath);
	}

	@Override
	public void onPictureUploaded(ImageEntry entry) {
		getViewModel().imageUploadComplete();
		getViewModel().addImage(entry);

		if (getListener() != null) {
			getListener().onImageUploadEnd(entry);
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
		if (getListener() != null) {
			getListener().error(e, userAction);
		}
	}

	@Override
	public void onPictureUploadInformationReceived(String picturePath, String title, String description) {
		getViewModel().imageUploadStart(picturePath);

		performUserAction(UPLOAD_IMAGE, picturePath, title, description);

		if (getListener() != null) {
			getListener().onImageUploadStarted(picturePath, title, description);
		}
	}

	public void showImageInFullScreenActivity(ImageEntry image) {
		Intent intent = new Intent(getContext(), DetailImageActivity.class);
		intent.putExtra(DetailImageActivity.GALLERY_SCREENLET_IMAGE_DETAILED, image);

		getContext().startActivity(intent);
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad) {
			autoLoad();
		}
	}

	public GalleryListener getListener() {
		return ((GalleryListener) super.getListener());
	}

	@Override
	protected void loadRows(GalleryLoadInteractorImpl interactor) {
		interactor.start(folderId, mimeTypes);
	}

	public GalleryViewModel getViewModel() {
		return ((GalleryViewModel) super.getViewModel());
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && groupId > 0) {
			load();
		}
	}

	protected String[] parseMimeTypes(String mimeTypesRaw) {
		if (mimeTypesRaw == null) {
			return null;
		}

		return mimeTypesRaw.split(",");
	}

	protected void startUploadDetailActivity(Class activityUploadDetail, String picturePath) {

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent;
		if (activityUploadDetail == null || activityUploadDetail.isAssignableFrom(BaseDetailUploadActivity.class)) {
			intent = new Intent(activity, DetailUploadDefaultActivity.class);
		} else {
			intent = new Intent(activity, activityUploadDetail);
		}

		intent.putExtra(BaseDetailUploadActivity.SCREENLET_ID_KEY, getScreenletId());
		intent.putExtra(BaseDetailUploadActivity.PICTURE_PATH_KEY, picturePath);

		activity.startActivity(intent);
	}

	protected void startShadowActivityForMediaStore(int mediaStore) {

		GalleryUploadInteractorImpl galleryUploadInteractor = new GalleryUploadInteractorImpl();
		LiferayLogger.e("We initialize the interactor to be able to send him messages, objId:"
			+ galleryUploadInteractor.toString());

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);
		intent.putExtra(MediaStoreRequestShadowActivity.SCREENLET_ID, getScreenletId());

		activity.startActivity(intent);
	}
}
