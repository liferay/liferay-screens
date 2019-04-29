package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.slideshow;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.BaseImageGalleryView;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowImageGalleryView extends
    BaseImageGalleryView<SlideshowImageGalleryAdapter.SlideshowGalleryViewHolder, SlideshowImageGalleryAdapter> {

    public SlideshowImageGalleryView(Context context) {
        super(context);
    }

    public SlideshowImageGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideshowImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlideshowImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected SlideshowImageGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new SlideshowImageGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        transformViews(recyclerView);
    }

    protected void transformViews(final RecyclerView recyclerView) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layout);

        SlideshowLayout slideshowLayout = new SlideshowLayout(this.recyclerView);
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
