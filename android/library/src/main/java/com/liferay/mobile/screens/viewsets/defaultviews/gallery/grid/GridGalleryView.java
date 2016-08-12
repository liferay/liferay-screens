package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.UploadProgressView;
import java.util.List;
import rx.functions.Action1;

/**
 * @author Víctor Galán Grande
 */
public class GridGalleryView
	extends BaseListScreenletView<ImageEntry, GridGalleryAdapter.GridGalleryViewHolder, GridGalleryAdapter>
	implements GalleryViewModel, View.OnClickListener {

	public GridGalleryView(Context context) {
		super(context);
	}

	public GridGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void onItemClick(int position, View view) {
		List<ImageEntry> entries = getAdapter().getEntries();

		ImageEntry image = entries.get(position);
		GalleryScreenlet screenlet = (GalleryScreenlet) getScreenlet();

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
				_recyclerView.getAdapter().notifyItemRemoved(i);
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
	public void imageUploadStart(String picturePath) {
		if (uploadProgressView == null) {
			createProgressView();
		}

		uploadProgressView.setVisibility(VISIBLE);
		uploadProgressView.addUpload(picturePath);
	}

	private void createProgressView() {
		uploadProgressView =
			(UploadProgressView) LayoutInflater.from(getContext()).inflate(R.layout.gallery_progress_view_default, null);

		int height = getPixelsFromDp(60);
		int margin = getPixelsFromDp(5);

		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
		layoutParams.setMargins(margin, margin, margin, 0);

		addView(uploadProgressView, layoutParams);
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

	@Override
	public void imageUploadError(Exception e) {
		uploadProgressView.uploadCompleteOrError();
	}

	public void reloadView() {
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
	}

	@Override
	public void onClick(View v) {
		choseOriginDialog =
			new MediaStoreSelectorDialog().createOriginDialog(getContext(), openCamera(), openGallery(), null);
		choseOriginDialog.show();
	}

	@Override
	protected GridGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new GridGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_grid;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_empty;
	}

	@Override
	public void showStartOperation(String actionName) {
		if(actionName == null){
			super.showStartOperation(actionName);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
		FloatingActionButton _uploadFAB = (FloatingActionButton) findViewById(R.id.liferay_upload_fab);
		_uploadFAB.setOnClickListener(this);
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		int _imagesSpacing = 3;
		return new GridDividerItemDecoration(_imagesSpacing);
	}

	private Action1 openCamera() {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {
					((GalleryScreenlet) getScreenlet()).openCamera();
				}
				choseOriginDialog.dismiss();
			}
		};
	}

	private Action1 openGallery() {
		return new Action1<Boolean>() {
			@Override
			public void call(Boolean result) {
				if (result) {
					((GalleryScreenlet) getScreenlet()).openGallery();
				}
				choseOriginDialog.dismiss();
			}
		};
	}

	private int getPixelsFromDp(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}


	private static final int DEFAULT_COLS = 4;

	private UploadProgressView uploadProgressView;
	private AlertDialog choseOriginDialog;

	public int columnsSize = DEFAULT_COLS;
}
