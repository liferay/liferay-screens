package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentListView
    extends BaseListScreenletView<WebContent, BaseListAdapter.ViewHolder, WebContentListAdapter> {

    public WebContentListView(Context context) {
        super(context);
    }

    public WebContentListView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public WebContentListView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected WebContentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new WebContentListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

    @Override
    protected void restoreState(Bundle state) {
    }

    @Override
    protected void saveState(Bundle state) {
    }
}
