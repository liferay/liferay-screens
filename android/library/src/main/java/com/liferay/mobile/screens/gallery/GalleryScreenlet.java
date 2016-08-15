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
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.interactor.BaseGalleryInteractor;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractor;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadInteractor;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.upload.CancelUploadEvent;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractor;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractorImpl;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailImageActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailUploadDefaultActivity;
import java.util.Locale;

import static java.lang.Class.forName;

/**
 * @author Víctor Galán Grande
 */
public class GalleryScreenlet extends BaseListScreenlet<ImageEntry, BaseGalleryInteractor>
	implements GalleryInteractorListener {

	public static final String LOAD_GALLERY = "LOAD_GALLERY";
	public static final String DELETE_IMAGE = "DELETE_IMAGE";
	public static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";

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

		Integer offlinePolicy =
			typedArray.getInteger(R.styleable.GalleryScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		this.offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		PicassoScreens.setOfflinePolicy(this.offlinePolicy);

		long groupId = LiferayServerContext.getGroupId();

		this.groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.GalleryScreenlet_groupId), groupId);

		folderId = castToLong(typedArray.getString(R.styleable.GalleryScreenlet_folderId));

		mimeTypes = parseMimeTypes(typedArray.getString(R.styleable.GalleryScreenlet_mimeTypes));

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected BaseGalleryInteractor createInteractor(String actionName) {
		switch (actionName) {
			case LOAD_GALLERY:
				return new GalleryLoadInteractorImpl(getScreenletId(), offlinePolicy);
			case DELETE_IMAGE:
				return new GalleryDeleteInteractorImpl(getScreenletId());
			case UPLOAD_IMAGE:
				return new GalleryUploadInteractorImpl(getScreenletId());
			default:
				return null;
		}
	}

	@Override
	protected void onUserAction(String userActionName, BaseGalleryInteractor interactor, Object... args) {
		switch (userActionName) {
			case LOAD_GALLERY:
				loadPage(0);
				break;
			case DELETE_IMAGE:
				long fileEntryId = (long) args[0];
				GalleryDeleteInteractor galleryDeleteInteractor = (GalleryDeleteInteractor) interactor;
				galleryDeleteInteractor.deleteImageEntry(fileEntryId);
				break;
			case UPLOAD_IMAGE:
				String picturePath = (String) args[0];
				String title = (String) args[1];
				String description = (String) args[2];
				GalleryUploadInteractor galleryUploadInteractor = (GalleryUploadInteractor) interactor;
				galleryUploadInteractor.uploadImageEntry(groupId, folderId, title, description, "", picturePath);
				break;
		}
	}

	@Override
	protected void loadRows(BaseGalleryInteractor interactor, int startRow, int endRow, Locale locale)
		throws Exception {
		GalleryLoadInteractor galleryLoadInteractor = (GalleryLoadInteractor) interactor;
		galleryLoadInteractor.loadRows(groupId, folderId, mimeTypes, startRow, endRow, locale);
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


	public void openCamera() {
		startShadowActivityForMediaStore(MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA);
	}

	public void openGallery() {
		startShadowActivityForMediaStore(MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY);
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	public void onImageClicked(ImageEntry image, View view) {
		if (getListener() != null) {
			getListener().onListItemSelected(image, view);
		}
	}

	@Override
	public void onImageEntryDeleteFailure(Exception e) {
		if (getListener() != null) {
			getListener().onImageEntryDeleteFailure(e);
		}
	}

	@Override
	public void onImageEntryDeleted(long imageEntryId) {
		if (getListener() != null) {
			getListener().onImageEntryDeleted(imageEntryId);
		}

		getViewModel().deleteImage(imageEntryId);
	}

	@Override
	public void onPicturePathReceived(String picturePath) {
		Class activityUploadDetail = null;
		if (getListener() != null) {
			activityUploadDetail = getListener().provideImageUploadDetailActivity();
		}

		startUploadDetailActivity(activityUploadDetail, picturePath);
		//performUserAction(UPLOAD_IMAGE, picturePath);
	}

	@Override
	public void onPictureUploaded(ImageEntry entry) {
		if (getListener() != null) {
			getListener().onImageUploadEnd(entry);
		}
		getViewModel().imageUploadComplete();
		getViewModel().addImage(entry);
	}

	@Override
	public void onPictureUploadProgress(int totalBytes, int totalBytesSent) {
		if (getListener() != null) {
			getListener().onImageUploadProgress(totalBytes, totalBytesSent);
		}

		getViewModel().imageUploadProgress(totalBytes, totalBytesSent);
	}

	@Override
	public void onPictureUploadFailure(Exception e) {
		getListener().onImageUploadFailure(e);
	}

	@Override
	public void onPictureUploadInformationReceived(String picturePath, String title, String description) {
		if (getListener() != null) {
			getListener().onImageUploadStarted();
		}

		getViewModel().imageUploadStart(picturePath);

		performUserAction(UPLOAD_IMAGE, picturePath, title, description);
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

	@Override
	public BaseGalleryInteractor getInteractor() {
		return super.getInteractor(LOAD_GALLERY);
	}

	public GalleryListener getListener() {
		return ((GalleryListener) super.getListener());
	}

	public GalleryViewModel getViewModel() {
		return ((GalleryViewModel) super.getViewModel());
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy _offlinePolicy) {
		this.offlinePolicy = _offlinePolicy;
	}


	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && groupId > 0) {
			load();
		}
	}

	private String[] parseMimeTypes(String mimeTypesRaw) {
		if (mimeTypesRaw == null) {
			return null;
		}

		return mimeTypesRaw.split(",");
	}

	private void startUploadDetailActivity(Class activityUploadDetail, String picturePath) {

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = null;
		if (activityUploadDetail == null || activityUploadDetail.isAssignableFrom(BaseDetailUploadActivity.class)) {
			intent = new Intent(activity, DetailUploadDefaultActivity.class);
		}
		else {
			intent = new Intent(activity, activityUploadDetail);
		}

		intent.putExtra(BaseDetailUploadActivity.SCREENLET_ID_KEY, getScreenletId());
		intent.putExtra(BaseDetailUploadActivity.PICTURE_PATH_KEY, picturePath);

		activity.startActivity(intent);
	}

	private void startShadowActivityForMediaStore(int mediaStore) {

		GalleryUploadInteractor galleryUploadInteractor = (GalleryUploadInteractor) getInteractor(UPLOAD_IMAGE);
		LiferayLogger.e("We initialize the interactor to be able to send him messages, objId:"
			+ galleryUploadInteractor.toString());

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);
		intent.putExtra(MediaStoreRequestShadowActivity.SCREENLET_ID, getScreenletId());

		activity.startActivity(intent);
	}

	private long groupId;
	private long folderId;
	private String[] mimeTypes;
	private OfflinePolicy offlinePolicy;
}
