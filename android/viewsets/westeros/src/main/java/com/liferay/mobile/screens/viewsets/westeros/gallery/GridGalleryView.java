package com.liferay.mobile.screens.viewsets.westeros.gallery;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Víctor Galán Grande
 */
public class GridGalleryView
    extends com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.grid.GridImageGalleryView
    implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;

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
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        ((BaseListScreenlet) getScreenlet()).loadPage(0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        swipeRefreshLayout = findViewById(R.id.liferay_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary_westeros));
    }
}
