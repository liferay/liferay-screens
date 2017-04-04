package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.imagegallery.ImageGalleryScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.imagegallery.view.ImageGalleryViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;
import rx.functions.Action1;

import static com.liferay.mobile.screens.base.list.BaseListScreenlet.LOAD_INITIAL_PAGE_ACTION;

/**
 * @author Víctor Galán Grande
 */
public abstract class BaseImageGalleryView<H extends BaseListAdapter.ViewHolder, A extends BaseListAdapter<ImageEntry, H>>
	extends BaseListScreenletView<ImageEntry, H, A> implements ImageGalleryViewModel {

	private UploadProgressView uploadProgressView;
	private AlertDialog choseOriginDialog;

	public BaseImageGalleryView(Context context) {
		super(context);
	}

	public BaseImageGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BaseImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void openMediaSelector() {
		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
		choseOriginDialog =
			new MediaStoreSelectorDialog().createOriginDialog(activity, openCamera(), openGallery(), null);
		choseOriginDialog.show();
	}

	@Override
	public void showStartOperation(String actionName) {
		if (actionName.equals(LOAD_INITIAL_PAGE_ACTION)) {
			super.showStartOperation(actionName);
		}
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Error with image", e);
		if (uploadProgressView != null) {
			uploadProgressView.uploadCompleteOrError();
		}
	}

	@Override
	public void onItemClick(int position, View view) {
		List<ImageEntry> entries = getAdapter().getEntries();

		ImageEntry image = entries.get(position);
		ImageGalleryScreenlet screenlet = (ImageGalleryScreenlet) getScreenlet();

		if (!entries.isEmpty() && entries.size() > position) {
			screenlet.onImageClicked(image, view);
		}
	}

	@Override
	public void deleteImage(long imageEntryId) {
		List<ImageEntry> entries = getAdapter().getEntries();

		for (int i = 0, size = entries.size(); i < size; i++) {
			if (entries.get(i).getFileEntryId() == imageEntryId) {
				entries.remove(i);
				int newRowCount = getAdapter().getItemCount() - 1;
				getAdapter().setRowCount(newRowCount);
				recyclerView.getAdapter().notifyItemRemoved(i);
				break;
			}
		}
	}

	@Override
	public void addImage(ImageEntry imageEntry) {
		int newRowCount = getAdapter().getItemCount() + 1;
		getAdapter().setRowCount(newRowCount);

		getAdapter().getEntries().add(imageEntry);

		getAdapter().notifyItemInserted(newRowCount - 1);
	}

	@Override
	public void imageUploadStart(Uri pictureUri) {
		if (uploadProgressView == null) {
			createProgressView();
		}

		uploadProgressView.setVisibility(VISIBLE);
		uploadProgressView.addUpload(pictureUri);
	}

	private void createProgressView() {
		uploadProgressView = (UploadProgressView) LayoutInflater.from(getContext())
			.inflate(R.layout.gallery_progress_view_default, this, false);

		addView(uploadProgressView);
	}

	@Override
	public void imageUploadProgress(int totalBytes, int totalBytesSent) {
		float percentage = (totalBytesSent / (float) totalBytes) * 100;
		uploadProgressView.setProgress((int) percentage);
	}

	@Override
	public void imageUploadComplete() {
		uploadProgressView.uploadCompleteOrError();
	}

	private Action1<Boolean> openCamera() {
		return result -> {
			if (result) {
				((ImageGalleryScreenlet) getScreenlet()).openCamera();
			}
			choseOriginDialog.dismiss();
		};
	}

	private Action1<Boolean> openGallery() {
		return result -> {
			if (result) {
				((ImageGalleryScreenlet) getScreenlet()).openGallery();
			}
			choseOriginDialog.dismiss();
		};
	}
}
