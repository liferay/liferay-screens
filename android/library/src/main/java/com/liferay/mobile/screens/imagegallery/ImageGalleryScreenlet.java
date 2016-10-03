package com.liferay.mobile.screens.imagegallery;

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

/**
 * @author Víctor Galán Grande
 */
public class ImageGalleryScreenlet extends BaseListScreenlet<ImageEntry, ImageGalleryLoadInteractor>
	implements ImageGalleryInteractorListener {

	//public static final String DELETE_IMAGE = "DELETE_IMAGE";
	public static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";
	private long repositoryId;
	private long folderId;
	private String[] mimeTypes;

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

	public void load() {
		loadPage(0);
	}

	public void deleteEntry(ImageEntry entry) {
		deleteEntry(entry.getFileEntryId());
	}

	public void deleteEntry(long fileEntryId) {
		ImageGalleryDeleteInteractor
			imageGalleryDeleteInteractor = new ImageGalleryDeleteInteractor();
		imageGalleryDeleteInteractor.start(new ImageGalleryEvent(new ImageEntry(fileEntryId)));
	}

	public void deleteCaches() throws IOException {
		LiferayServerContext.getOkHttpClient().getCache().evictAll();
		try {
			Cache.destroy(groupId, userId, ImageGalleryEvent.class.getName());
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
			boolean showed = getListener().showUploadImageView(UPLOAD_IMAGE, picturePath, getScreenletId());

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
	public void onPictureUploadInformationReceived(String picturePath, String title, String description,
		String changelog) {
		getViewModel().imageUploadStart(picturePath);

		ImageGalleryUploadInteractor imageGalleryUploadInteractor = getUploadInteractor();
		ImageGalleryEvent event = new ImageGalleryEvent(picturePath, title, description, changelog);
		event.setFolderId(folderId);
		imageGalleryUploadInteractor.start(event);

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

	public ImageGalleryListener getListener() {
		return ((ImageGalleryListener) super.getListener());
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
		Context context = LiferayScreensContext.getContext();

		View view = inflateView(uploadDetailView, context);

		if (view instanceof BaseDetailUploadView) {
			BaseDetailUploadView baseDetailUploadView = (BaseDetailUploadView) view;

			baseDetailUploadView.initializeUploadView(UPLOAD_IMAGE, picturePath, getScreenletId());
			new DefaultUploadDialog().createDialog(baseDetailUploadView, getContext()).show();
		} else {
			LiferayLogger.e("Detail upload view has to be a subclass of BaseDetailUploadView");
		}
	}

	private View inflateView(@LayoutRes int uploadDetailView, Context context) {
		if (uploadDetailView != 0) {
			return LayoutInflater.from(context).inflate(uploadDetailView, null, false);
		} else {
			return LayoutInflater.from(context).inflate(R.layout.default_upload_detail_activity, null, false);
		}
	}

	protected void startShadowActivityForMediaStore(int mediaStore) {

		ImageGalleryUploadInteractor imageGalleryUploadInteractor = getUploadInteractor();
		LiferayLogger.e("We initialize the interactor to be able to send him messages, objId:"
			+ imageGalleryUploadInteractor.toString());

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);

		activity.startActivity(intent);
	}

	private ImageGalleryUploadInteractor getUploadInteractor() {
		if (uploadInteractors.containsKey(UPLOAD_IMAGE)) {
			return uploadInteractors.get(UPLOAD_IMAGE);
		}
		ImageGalleryUploadInteractor
			imageGalleryUploadInteractor = new ImageGalleryUploadInteractor();
		decorateInteractor(UPLOAD_IMAGE, imageGalleryUploadInteractor);
		uploadInteractors.put(UPLOAD_IMAGE, imageGalleryUploadInteractor);
		imageGalleryUploadInteractor.onScreenletAttached(this);
		return imageGalleryUploadInteractor;
	}

	private final Map<String, ImageGalleryUploadInteractor> uploadInteractors = new HashMap<>();
}
