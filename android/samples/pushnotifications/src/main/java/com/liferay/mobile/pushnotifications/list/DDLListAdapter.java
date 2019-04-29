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

package com.liferay.mobile.pushnotifications.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.pushnotifications.download.DownloadPicture;
import com.liferay.mobile.pushnotifications.service.v62.DLFileEntryService;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLListAdapter extends BaseListAdapter<Record, DDLListAdapter.ImageViewHolder> {

    public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public ImageViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new ImageViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(Record entry, ImageViewHolder holder) {

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

            buildURL(entry, holder.imageView);

            holder.textView.setText(titleField);
            holder.subtitleTextView.setText(builder.toString());
        }
    }

    private void buildURL(Record entry, final ImageView imageView) {
        try {
            String photo = (String) entry.getServerValue("Photo");
            if (photo != null) {

                final Session session = SessionContext.createSessionFromCurrentSession();

                JSONObject jsonObject = new JSONObject(photo);
                final String uuid = jsonObject.getString("uuid");
                final Long groupId = jsonObject.getLong("groupId");

                downloadPicture(session, uuid, groupId, imageView);
            }
        } catch (Exception e) {
            LiferayLogger.e("Error loading image", e);
        }
    }

    private void downloadPicture(final Session session, final String uuid, final Long groupId,
        final ImageView imageView) throws Exception {

        final Context context = LiferayScreensContext.getContext();
        final String server = LiferayServerContext.getServer();

        session.setCallback(new JSONObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    new DownloadPicture().
                        createRequest(context, result, server, 200).into(imageView);
                } catch (Exception e) {
                    LiferayLogger.e("Error downloading picture", e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                LiferayLogger.e("Error downloading picture", e);
            }
        });

        DLFileEntryService entryService = new DLFileEntryService(session);
        entryService.getFileEntryByUuidAndGroupId(uuid, groupId);
    }

    public static class ImageViewHolder extends BaseListAdapter.ViewHolder implements View.OnClickListener {

        private final BaseListAdapterListener listener;
        private final TextView subtitleTextView;
        private final ImageView imageView;

        public ImageViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            this.textView = (TextView) view.findViewById(R.id.notification_title);
            this.subtitleTextView = (TextView) view.findViewById(R.id.notification_subtitle);
            this.imageView = (ImageView) view.findViewById(R.id.notification_image);

            this.listener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition(), v);
        }
    }
}