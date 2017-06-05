package com.liferay.mobile.screens.westerosemployees.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.jakewharton.rxbinding.view.RxView;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.imagegallery.BaseDetailUploadView;
import com.liferay.mobile.screens.imagegallery.ImageGalleryListener;
import com.liferay.mobile.screens.imagegallery.ImageGalleryScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.List;
import rx.functions.Action1;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author Víctor Galán Grande
 */
public class GalleryCard extends CommentsRatingsCard implements ImageGalleryListener {

	private ImageGalleryScreenlet imageGalleryScreenlet;
	private BaseDetailUploadView uploadDetailView;
	private Card uploadImageCard;
	private AssetDisplayScreenlet imageAssetDisplayScreenlet;

	private boolean loaded;

	public GalleryCard(Context context) {
		super(context);
	}

	public GalleryCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GalleryCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public ViewPropertyAnimator setState(CardState state) {
		if (!loaded && state.equals(CardState.NORMAL)) {
			loaded = true;
			imageGalleryScreenlet.loadPage(0);
		}

		return super.setState(state);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		imageGalleryScreenlet = (ImageGalleryScreenlet) findViewById(R.id.gallery_screenlet);
		imageGalleryScreenlet.setListener(this);

		uploadDetailView = (BaseDetailUploadView) findViewById(R.id.upload_detail_view);
		uploadImageCard = (Card) findViewById(R.id.upload_image_card);

		imageAssetDisplayScreenlet = (AssetDisplayScreenlet) findViewById(R.id.asset_display_screenlet_image);

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
		RxPermissions rxPermissions = new RxPermissions(activity);

		RxView.clicks(findViewById(R.id.gallery_button))
			.compose(rxPermissions.ensure(WRITE_EXTERNAL_STORAGE))
			.subscribe(openGallery());

		RxView.clicks(findViewById(R.id.camera_button))
			.compose(rxPermissions.ensure(CAMERA, WRITE_EXTERNAL_STORAGE))
			.subscribe(openCamera());

		RxView.clicks(findViewById(R.id.upoad_button))
			.subscribe(aVoid -> uploadDetailView.finishActivityAndStartUpload(uploadDetailView.getTitle(),
				uploadDetailView.getDescription(), ""));
	}

	@Override
	public void onImageEntryDeleted(long imageEntryId) {

	}

	@Override
	public void onImageUploadStarted(Uri pictureUri, String title, String description, String changelog) {
		uploadImageCard.goLeft();
		uploadImageCard.setState(CardState.MINIMIZED);
	}

	@Override
	public void onImageUploadProgress(int totalBytes, int totalBytesSent) {

	}

	@Override
	public void onImageUploadEnd(ImageEntry entry) {

	}

	@Override
	public boolean showUploadImageView(String actionName, Uri pictureUri, int screenletId) {
		uploadDetailView.initializeUploadView(actionName, pictureUri, screenletId);
		uploadImageCard.goRight();

		return true;
	}

	@Override
	public int provideImageUploadDetailView() {
		return 0;
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<ImageEntry> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(ImageEntry element, View view) {
		imageAssetDisplayScreenlet.load(element);

		initializeRatingsAndComments("com.liferay.document.library.kernel.model.DLFileEntry", element.getFileEntryId());

		cardListener.moveCardRight(this);
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	private Action1<Boolean> openGallery() {
		return permissionAccepted -> {
			if (permissionAccepted) {
				imageGalleryScreenlet.openGallery();
			}
		};
	}

	private Action1<Boolean> openCamera() {
		return permissionAccepted -> {
			if (permissionAccepted) {
				imageGalleryScreenlet.openCamera();
			}
		};
	}
}
