/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.westeros.ddl.list;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.swipe.SwipeLayout;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.westeros.R;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter extends BaseListAdapter<Record, DDLListAdapter.SwipeActionsViewHolder> {

    public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

        super(layoutId, progressLayoutId, listener);
    }

    @Override
    public SwipeActionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;

        if (viewType == LAYOUT_TYPE_DEFAULT) {
            view = inflater.inflate(getLayoutId(), parent, false);
            SwipeLayout swipe = view.findViewById(R.id.liferay_swipe_layout);
            swipe.setShowMode(SwipeLayout.ShowMode.LayDown);
            swipe.setDragEdge(SwipeLayout.DragEdge.Right);
        } else {
            view = inflater.inflate(getProgressLayoutId(), parent, false);
        }

        return new SwipeActionsViewHolder(view, getListener());
    }

    @NonNull
    @Override
    public SwipeActionsViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new SwipeActionsViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(Record entry, SwipeActionsViewHolder holder) {

        StringBuilder builder = new StringBuilder();

        if (entry != null && getLabelFields() != null && !getLabelFields().isEmpty()) {

            String titleField = (String) entry.getServerValue(getLabelFields().get(0));

            for (int i = 1; i < getLabelFields().size(); ++i) {
                String field = getLabelFields().get(i);
                String value = (String) entry.getServerValue(field);
                if (value != null && !value.isEmpty()) {
                    builder.append(value);
                    builder.append(" ");
                }
            }
            if (builder.length() == 0) {
                String date =
                    new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(entry.getServerAttribute("createDate"));
                builder.append("Created ");
                builder.append(date);
            }

            holder.textView.setText(titleField);
            holder.subtitleTextView.setText(builder.toString());
        }
    }

    public static class SwipeActionsViewHolder extends BaseListAdapter.ViewHolder implements View.OnClickListener {

        private final BaseListAdapterListener listener;
        private final TextView subtitleTextView;
        private final SwipeLayout swipeLayout;
        public final ImageView imageView;

        public SwipeActionsViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            this.imageView = view.findViewById(R.id.liferay_state_list_icon);
            this.subtitleTextView = view.findViewById(R.id.liferay_list_subtitle);

            this.listener = listener;

            view.setOnClickListener(this);
            view.findViewById(R.id.liferay_list_edit).setOnClickListener(this);
            view.findViewById(R.id.liferay_list_view).setOnClickListener(this);
            swipeLayout = view.findViewById(R.id.liferay_swipe_layout);
        }

        @Override
        public void onClick(View v) {
            boolean opened = SwipeLayout.Status.Open.equals(swipeLayout.getOpenStatus());
            if (opened && (v.getId() == R.id.liferay_list_edit || v.getId() == R.id.liferay_list_view)) {

                listener.onItemClick(getAdapterPosition(), v);
            } else if (!opened) {
                swipeLayout.open(true);
            } else {
                swipeLayout.close(true);
            }
        }
    }
}