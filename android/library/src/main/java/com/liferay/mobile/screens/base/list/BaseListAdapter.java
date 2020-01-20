/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public abstract class BaseListAdapter<E, H extends BaseListAdapter.ViewHolder> extends RecyclerView.Adapter<H> {

    protected static final int LAYOUT_TYPE_DEFAULT = 0;
    protected static final int LAYOUT_TYPE_PROGRESS = 1;
    private final List<E> entries;
    private final int layoutId;
    private final BaseListAdapterListener listener;
    private final int progressLayoutId;
    private int rowCount;
    private List<String> labelFields;

    public BaseListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

        entries = new ArrayList<>();
        this.layoutId = layoutId;
        this.progressLayoutId = progressLayoutId;
        this.listener = listener;
    }

    public List<E> getEntries() {
        return entries;
    }

    public List<String> getLabelFields() {
        return labelFields;
    }

    public void setLabelFields(List<String> labelFields) {
        this.labelFields = labelFields;
    }

    @Override
    public int getItemCount() {
        return rowCount;
    }

    public BaseListAdapterListener getListener() {
        return listener;
    }

    @Override
    public int getItemViewType(int position) {
        E entry = entries.get(position);

        return (entry != null) ? LAYOUT_TYPE_DEFAULT : LAYOUT_TYPE_PROGRESS;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getProgressLayoutId() {
        return progressLayoutId;
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        E entry = entries.get(position);

        if (entry != null) {
            fillHolder(entry, holder);
        } else {
            listener.onPageNotFound(position);
        }
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType == LAYOUT_TYPE_DEFAULT ? layoutId : progressLayoutId, parent, false);
        return createViewHolder(view, listener);
    }

    @NonNull
    public abstract H createViewHolder(View view, BaseListAdapterListener listener);

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    protected abstract void fillHolder(E entry, H holder);

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final BaseListAdapterListener listener;
        public TextView textView;

        public ViewHolder(View view, BaseListAdapterListener listener) {
            super(view);

            this.textView = view.findViewById(R.id.liferay_list_title);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition(), v);
        }
    }
}