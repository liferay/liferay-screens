package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.grid;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.AttributeSet;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.BaseImageGalleryView;

/**
 * @author Víctor Galán Grande
 */
public class GridImageGalleryView
    extends BaseImageGalleryView<GridImageGalleryAdapter.GridGalleryViewHolder, GridImageGalleryAdapter> {

    public static final int COLUMNS_SIZE = 3;

    public GridImageGalleryView(Context context) {
        super(context);
    }

    public GridImageGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void reloadView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMNS_SIZE));
    }

    @Override
    protected GridImageGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new GridImageGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMNS_SIZE));
    }

    @Override
    protected DividerItemDecoration getDividerDecoration() {
        int imagesSpacing = 3;
        return new GridDividerItemDecoration(imagesSpacing);
    }
}
