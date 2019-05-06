package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.slideshow;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowLayout extends RecyclerView.OnScrollListener implements View.OnLayoutChangeListener {

    private static final int Y_OFFSET = 200;
    private static final float SCALE_OFFSET = 0.7f;
    private static final float ALPHA_OFFSET = 0.5f;
    private static final float SCALE_DELTA = 1 - SCALE_OFFSET;
    private final RecyclerView recyclerView;

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

                v.setScaleY(1 - rate * SCALE_DELTA);
                v.setScaleX(1 - rate * SCALE_DELTA);

                v.setTranslationY(rate * Y_OFFSET);

                v.setAlpha((1 - rate) * 1 + ALPHA_OFFSET);
            } else {

                if (v.getLeft() <= recyclerView.getWidth() - padding) {
                    rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                }
                v.setScaleY(SCALE_OFFSET + rate * SCALE_DELTA);
                v.setScaleX(SCALE_OFFSET + rate * SCALE_DELTA);

                v.setTranslationY((1 - rate) * Y_OFFSET);

                v.setAlpha(rate * 1 + ALPHA_OFFSET);
            }
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
        int oldBottom) {

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
        view.setScaleY(SCALE_OFFSET);
        view.setScaleX(SCALE_OFFSET);
        view.setTranslationY(Y_OFFSET);
        view.setAlpha(ALPHA_OFFSET);
    }
}
