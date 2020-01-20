package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list;

import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentListAdapter extends BaseListAdapter<WebContent, BaseListAdapter.ViewHolder> {

    public WebContentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new ViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(WebContent entry, ViewHolder holder) {
        holder.textView.setText(calculateValue(entry));
    }

    private String calculateValue(WebContent entry) {
        if (getLabelFields().isEmpty()) {
            return entry.getTitle();
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (String label : getLabelFields()) {
                String localized = entry.getLocalized(label);
                if (localized == null) {
                    localized = entry.getTitle();
                }
                stringBuilder.append(localized);
            }
            return stringBuilder.toString();
        }
    }
}
