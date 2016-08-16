package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowLayout extends RecyclerView.OnScrollListener implements View.OnLayoutChangeListener {

	public SlideshowLayout(RecyclerView recyclerView) {
		this.recyclerView = recyclerView;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		int childCount = recyclerView.getChildCount();
		int width = recyclerView.getChildAt(0).getWidth();
		int padding = (recyclerView.getWidth() - width) / 2;

		for (int j = 0; j < childCount; j++) {
			View v = recyclerView.getChildAt(j);

			float rate = 0;

			if (v.getLeft() <= padding) {
				if (v.getLeft() >= padding - v.getWidth()) {
					rate = (padding - v.getLeft()) * 1f / v.getWidth();
				} else {
					rate = 1;
				}

				v.setScaleY(1 - rate * 0.3f);
				v.setScaleX(1 - rate * 0.3f);

				v.setTranslationY(rate * 200);

				v.setAlpha((1 - rate) * 1 + 0.5f);
			} else {

				if (v.getLeft() <= recyclerView.getWidth() - padding) {
					rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
				}
				v.setScaleY(0.7f + rate * 0.3f);
				v.setScaleX(0.7f + rate * 0.3f);

				v.setTranslationY((1 - rate) * 200);

				v.setAlpha(rate * 1 + 0.5f);
			}
		}
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
		int oldRight, int oldBottom) {

		// Set the default values to the neighbors
		if (recyclerView.getChildCount() < 3) {
			if (recyclerView.getChildAt(1) != null) {
				if (((RecyclerViewPager) recyclerView).getCurrentPosition() == 0) {
					View view = recyclerView.getChildAt(1);
					assignDefaultValues(view);
				} else {
					View view = recyclerView.getChildAt(0);
					assignDefaultValues(view);
				}
			}
		} else {
			if (recyclerView.getChildAt(0) != null) {
				View view = recyclerView.getChildAt(0);
				assignDefaultValues(view);
			}
			if (recyclerView.getChildAt(2) != null) {
				View view = recyclerView.getChildAt(2);
				assignDefaultValues(view);
			}
		}
	}

	private void assignDefaultValues(View view) {
		view.setScaleY(0.7f);
		view.setScaleX(0.7f);
		view.setTranslationY(200);
		view.setAlpha(0.5f);
	}

	private RecyclerView recyclerView;
}
