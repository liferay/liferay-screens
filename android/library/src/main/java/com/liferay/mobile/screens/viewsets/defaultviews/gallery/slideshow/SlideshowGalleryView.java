package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.BaseGalleryView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowGalleryView extends
	BaseGalleryView<SlideshowGalleryAdapter.SlideshowGalleryViewHolder, SlideshowGalleryAdapter> {

	public SlideshowGalleryView(Context context) {
		super(context);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected SlideshowGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new SlideshowGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		transformViews(_recyclerView);
	}

	protected void transformViews(final RecyclerView recyclerView) {
		LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(layout);

		SlideshowLayout slideshowLayout = new SlideshowLayout(_recyclerView);
		recyclerView.addOnScrollListener(slideshowLayout);
		recyclerView.addOnLayoutChangeListener(slideshowLayout);
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_empty;
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_slideshow;
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		return null;
	}
}
