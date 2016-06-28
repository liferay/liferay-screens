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
	implements GalleryViewModel, View.OnKeyListener {

	public GridGalleryView(Context context) {
		super(context);
		initialize();
	}

	public GridGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	protected void initialize() {
		setFocusableInTouchMode(true);
		requestFocus();
		setOnKeyListener(this);
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
		_detailedImage = (ImageView) findViewById(R.id.gallery_detailed_image);
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), _columnsSize));
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		return new GridDividerItemDecoration(_imagesSpacing);
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
	public void showDetailImage(ImageEntry image) {
		//_recyclerView.setVisibility(GONE);
		//_detailedImage.setVisibility(VISIBLE);
		//Picasso.with(getContext()).load(image.getImageUrl()).into(_detailedImage);
	}

	@Override
	public void setColumns(int numCols) {
		_columnsSize = numCols;
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), _columnsSize));
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {

		if (_recyclerView.getVisibility() == GONE && keyCode == KeyEvent.KEYCODE_BACK) {

			_detailedImage.setVisibility(GONE);
			_recyclerView.setVisibility(VISIBLE);
			return true;
		}
		return false;
	}

	private static final int DEFAULT_COLS = 3;

	private int _columnsSize = DEFAULT_COLS;
	private int _imagesSpacing = 3;

	private ImageView _detailedImage;
}
