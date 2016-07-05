package com.liferay.mobile.screens.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.interactor.BaseGalleryInteractor;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractor;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadInteractor;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadLoadInteractorImpl;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractor;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractorImpl;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailImageActivity;
import java.util.Locale;

/**
 * @author Víctor Galán Grande
 */
public class GalleryScreenlet extends BaseListScreenlet<ImageEntry, BaseGalleryInteractor> implements
	GalleryInteractorListener {

	public static final String LOAD_GALLERY = "LOAD_GALLERY";
	public static final String DELETE_IMAGE = "DELETE_IMAGE";
	public static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";

	public GalleryScreenlet(Context context) {
		super(context);
	}

	public GalleryScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public GalleryScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long _groupId) {
		this._groupId = _groupId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long _folderId) {
		this._folderId = _folderId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy _offlinePolicy) {
		this._offlinePolicy = _offlinePolicy;
	}

	public int getColumnsSize() {
		return _columnsSize;
	}

	public void setColumnsSize(int columnsSize) {
		_columnsSize = columnsSize;
		getViewModel().setColumns(columnsSize);
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
		if(getListener() != null) {
			getListener().onImageEntryDeleteFailure(this, e);
		}
	}

	@Override
	public void onImageEntryDeleted(long imageEntryId) {
		getListener().onImageEntryDeleted(this, imageEntryId);
		getViewModel().deleteImage(imageEntryId);
	}

	@Override
	public void onPicturePathReceived(String picturePath) {
		performUserAction(UPLOAD_IMAGE, picturePath);
		getListener().onImageUploadStarted();
	}

	@Override
	public void onPictureUploaded(ImageEntry entry) {
		getViewModel().addImage(entry);
		getListener().onImageUploadEnd(entry);
	}

	@Override
	public void onPictureUploadProgress(int totalBytes, int totalBytesSended) {
		getListener().onImageUploadProgress(totalBytes, totalBytesSended);
	}

	@Override
	public void onPictureUploadFailure(Exception e) {
		getListener().onImageUploadFailure(e);
	}

	public void updateView() {
		getViewModel().updateView();
	}

	public void showImageInFullScreenActivity(ImageEntry image) {
		Intent intent = new Intent(getContext(), DetailImageActivity.class);
		intent.putExtra(DetailImageActivity.GALLERY_SCREENLET_IMAGE_DETAILED, image);

		getContext().startActivity(intent);
	}

	@Override
	protected void loadRows(BaseGalleryInteractor interactor, int startRow, int endRow, Locale locale)
		throws Exception {
		GalleryLoadInteractor galleryLoadInteractor = (GalleryLoadInteractor) interactor;
		galleryLoadInteractor.loadRows(_groupId, _folderId, _mimeTypes, startRow, endRow, locale);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme()
			.obtainStyledAttributes(attributes, R.styleable.GalleryScreenlet, 0, 0);

		Integer offlinePolicy = typedArray.getInteger(R.styleable.GalleryScreenlet_offlinePolicy,
			OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		_groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.GalleryScreenlet_groupId), groupId);

		_folderId = castToLong(typedArray.getString(R.styleable.GalleryScreenlet_folderId));

		_columnsSize = typedArray.getInt(R.styleable.GalleryScreenlet_columnsSize, 0);

		_mimeTypes = parseMimeTypes(typedArray.getString(R.styleable.GalleryScreenlet_mimeTypes));

		typedArray.recycle();

		View view = super.createScreenletView(context, attributes);

		if (_columnsSize >= 0) {
			GalleryViewModel viewModel = (GalleryViewModel) view;
			viewModel.setColumns(_columnsSize);
		}

		return view;
	}

	@Override
	protected BaseGalleryInteractor createInteractor(String actionName) {
		if (actionName.equals(LOAD_GALLERY)) {
			return new GalleryLoadLoadInteractorImpl(getScreenletId(), _offlinePolicy);
		}

		if(actionName.equals(DELETE_IMAGE)) {
			return new GalleryDeleteInteractorImpl(getScreenletId());
		}

		if(actionName.equals(UPLOAD_IMAGE)) {
			return new GalleryUploadInteractorImpl(getScreenletId());
		}

		return null;
	}

	@Override
	protected void onUserAction(String userActionName, BaseGalleryInteractor interactor, Object... args) {
		if(userActionName.equals(LOAD_GALLERY)) {
			loadPage(0);
		}
		else if(userActionName.equals(DELETE_IMAGE)) {
			long fileEntryId = (long) args[0];
			GalleryDeleteInteractor galleryDeleteInteractor = (GalleryDeleteInteractor) interactor;

			try {
				galleryDeleteInteractor.deleteImageEntry(fileEntryId);
			} catch (Exception e) {
				onImageEntryDeleteFailure(e);
			}
		} else if (userActionName.equals(UPLOAD_IMAGE)) {
			String picturePath = (String) args[0];
			GalleryUploadInteractor galleryUploadInteractor = (GalleryUploadInteractor) interactor;
			galleryUploadInteractor.uploadImageEntry(_groupId, _folderId, "", "", "", picturePath);

		}
	}

	@Override
	protected void onScreenletAttached() {
		if(_autoLoad) {
			autoLoad();
		}
	}

	@Override
	public BaseGalleryInteractor getInteractor() {
		return super.getInteractor(LOAD_GALLERY);
	}


	public GalleryListener getListener() {
		return  ((GalleryListener) super.getListener());
	}


	public GalleryViewModel getViewModel() {
		return  ((GalleryViewModel) super.getViewModel());
	}

	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && _groupId > 0) {
			load();
		}
	}

	private String[] parseMimeTypes(String mimeTypesRaw) {
		if (mimeTypesRaw == null) {
			return null;
		}

		return mimeTypesRaw.split(",");
	}

	private void startShadowActivityForMediaStore(int mediaStore) {
		getInteractor(UPLOAD_IMAGE);
		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);
		intent.putExtra(MediaStoreRequestShadowActivity.SCREENLET_ID, getScreenletId());

		activity.startActivity(intent);
	}

	private long _groupId;
	private long _folderId;
	private OfflinePolicy _offlinePolicy;
	private int _columnsSize;
	private String[] _mimeTypes;
}
