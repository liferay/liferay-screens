package com.liferay.mobile.screens.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryEvent;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.gallery.interactor.delete.GalleryDeleteInteractor;
import com.liferay.mobile.screens.gallery.interactor.load.GalleryLoadInteractor;
import com.liferay.mobile.screens.gallery.interactor.upload.GalleryUploadInteractor;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DefaultUploadDialog;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailImageActivity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Víctor Galán Grande
 */
public class GalleryScreenlet extends BaseListScreenlet<ImageEntry, GalleryLoadInteractor>
	implements GalleryInteractorListener {

	//public static final String DELETE_IMAGE = "DELETE_IMAGE";
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

	public GalleryScreenlet(Context context, AttributeSet attrs, int defStyleAttr,
		int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme()
			.obtainStyledAttributes(attributes, R.styleable.GalleryScreenlet, 0, 0);

		PicassoScreens.setCachePolicy(this.cachePolicy);

		folderId = castToLong(typedArray.getString(R.styleable.GalleryScreenlet_folderId));
		mimeTypes = parseMimeTypes(typedArray.getString(R.styleable.GalleryScreenlet_mimeTypes));

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected GalleryLoadInteractor createInteractor(String actionName) {
		return new GalleryLoadInteractor();
	}

	public void load() {
		loadPage(0);
	}

	public void deleteEntry(ImageEntry entry) {
		deleteEntry(entry.getFileEntryId());
	}

	public void deleteEntry(long fileEntryId) {
		GalleryDeleteInteractor galleryDeleteInteractor = new GalleryDeleteInteractor();
		galleryDeleteInteractor.start(new GalleryEvent(new ImageEntry(fileEntryId)));
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
		int uploadDetailViewLayout = 0;
		if (getListener() != null) {
			boolean showed =
				getListener().showUploadImageView(UPLOAD_IMAGE, picturePath, getScreenletId());

			if (showed) {
				return;
			}

			uploadDetailViewLayout = getListener().provideImageUploadDetailView();
		}

		startUploadDetail(uploadDetailViewLayout, picturePath);
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
		if (getListener() != null) {
			getListener().error(e, userAction);
		}
	}

	@Override
	public void onPictureUploadInformationReceived(String picturePath, String title,
		String description, String changelog) {
		getViewModel().imageUploadStart(picturePath);

		GalleryUploadInteractor galleryUploadInteractor = getUploadInteractor();
		GalleryEvent event = new GalleryEvent(picturePath, title, description, changelog);
		event.setFolderId(folderId);
		galleryUploadInteractor.start(event);

		if (getListener() != null) {
			getListener().onImageUploadStarted(picturePath, title, description, changelog);
		}
	}

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

	public GalleryListener getListener() {
		return ((GalleryListener) super.getListener());
	}

	@Override
	protected void loadRows(GalleryLoadInteractor interactor) {
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

	protected void startUploadDetail(@LayoutRes int uploadDetailView, final String picturePath) {
		View view = null;
		Context context = LiferayScreensContext.getContext();

		if (uploadDetailView != 0) {
			view = LayoutInflater.from(context).inflate(uploadDetailView, null, false);
		} else {
			view = LayoutInflater.from(context)
				.inflate(R.layout.default_upload_detail_activity, null, false);
		}

		if (view instanceof BaseDetailUploadView) {
			BaseDetailUploadView baseDetailUploadView = (BaseDetailUploadView) view;

			baseDetailUploadView.initializeUploadView(UPLOAD_IMAGE, picturePath, getScreenletId());
			new DefaultUploadDialog().createDialog(baseDetailUploadView, getContext()).show();
		} else {
			LiferayLogger.e("Detail upload view has to be a subclass of BaseDetailUploadView");
		}
	}

	protected void startShadowActivityForMediaStore(int mediaStore) {

		GalleryUploadInteractor galleryUploadInteractor = getUploadInteractor();
		LiferayLogger.e("We initialize the interactor to be able to send him messages, objId:"
			+ galleryUploadInteractor.toString());

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);

		activity.startActivity(intent);
	}

	private GalleryUploadInteractor getUploadInteractor() {
		if (uploadInteractors.containsKey(UPLOAD_IMAGE)) {
			return uploadInteractors.get(UPLOAD_IMAGE);
		}
		GalleryUploadInteractor galleryUploadInteractor = new GalleryUploadInteractor();
		decorateInteractor(UPLOAD_IMAGE, galleryUploadInteractor);
		uploadInteractors.put(UPLOAD_IMAGE, galleryUploadInteractor);
		galleryUploadInteractor.onScreenletAttached(this);
		return galleryUploadInteractor;
	}

	private final Map<String, GalleryUploadInteractor> uploadInteractors = new HashMap<>();
}
