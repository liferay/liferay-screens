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
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowGalleryView extends
	BaseListScreenletView<ImageEntry, SlideshowGalleryAdapter.SlideshowGalleryViewHolder, SlideshowGalleryAdapter>
	implements GalleryViewModel {
	public SlideshowGalleryView(Context context) {
		super(context);
	}

	public SlideshowGalleryView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public SlideshowGalleryView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void setColumns(int numCols) {

	}

	@Override
	public void deleteImage(long imageEntryId) {

	}

	@Override
	public void updateView() {
		
	}

	@Override
	protected SlideshowGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new SlideshowGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		LinearLayoutManager layout =
			new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		_recyclerView.setLayoutManager(layout);
		_recyclerView.addOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				int childCount = recyclerView.getChildCount();
				int width = recyclerView.getChildAt(0).getWidth();
				int padding = (recyclerView.getWidth() - width) / 2;

				for (int j = 0; j < childCount; j++) {
					View v = recyclerView.getChildAt(j);

					float rate = 0;
					;
					if (v.getLeft() <= padding) {
						if (v.getLeft() >= padding - v.getWidth()) {
							rate = (padding - v.getLeft()) * 1f / v.getWidth();
						} else {
							rate = 1;
						}
						v.setScaleY(1 - rate * 0.1f);
						v.setScaleX(1 - rate * 0.1f);
					} else {

						if (v.getLeft() <= recyclerView.getWidth() - padding) {
							rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f
								/ v.getWidth();
						}
						v.setScaleY(0.9f + rate * 0.1f);
						v.setScaleX(0.9f + rate * 0.1f);
					}
				}
			}
		});

		_recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom,
				int oldLeft, int oldTop, int oldRight, int oldBottom) {
				if (_recyclerView.getChildCount() < 3) {
					if (_recyclerView.getChildAt(1) != null) {
						if (((RecyclerViewPager) _recyclerView).getCurrentPosition() == 0) {
							View v1 = _recyclerView.getChildAt(1);
							v1.setScaleY(0.9f);
							v1.setScaleX(0.9f);
						} else {
							View v1 = _recyclerView.getChildAt(0);
							v1.setScaleY(0.9f);
							v1.setScaleX(0.9f);
						}
					}
				} else {
					if (_recyclerView.getChildAt(0) != null) {
						View v0 = _recyclerView.getChildAt(0);
						v0.setScaleY(0.9f);
						v0.setScaleX(0.9f);
					}
					if (_recyclerView.getChildAt(2) != null) {
						View v2 = _recyclerView.getChildAt(2);
						v2.setScaleY(0.9f);
						v2.setScaleX(0.9f);
					}
				}
			}
		});
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
