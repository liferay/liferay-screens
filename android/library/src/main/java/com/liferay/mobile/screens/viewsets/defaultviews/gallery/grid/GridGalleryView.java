package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class GridGalleryView
	extends BaseListScreenletView<ImageEntry, GridGalleryAdapter.GridGalleryViewHolder, GridGalleryAdapter>
	implements GalleryViewModel {

	public GridGalleryView(Context context) {
		super(context);
	}

	public GridGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
	public void setColumns(int numCols) {
		_columnsSize = numCols;
	}

	@Override
	public void deleteImage(long imageEntryId) {
		List<ImageEntry> entries = getAdapter().getEntries();

		for(int i = 0, size = entries.size(); i < size; i++) {
			if(entries.get(i).getFileEntryId() == imageEntryId) {
				entries.remove(i);
				int newRowCount = getAdapter().getItemCount() - 1;
				getAdapter().setRowCount(newRowCount);
				_recyclerView.getAdapter().notifyItemRemoved(i);
				break;
			}
		}
	}

	@Override
	public void updateView() {
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), _columnsSize));
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
	protected void onFinishInflate() {
		super.onFinishInflate();
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), _columnsSize));
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		return new GridDividerItemDecoration(_imagesSpacing);
	}

	private static final int DEFAULT_COLS = 3;

	private int _columnsSize = DEFAULT_COLS;
	private int _imagesSpacing = 3;
}
