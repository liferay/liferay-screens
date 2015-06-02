package com.liferay.mobile.pushnotifications.list; /**
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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.pushnotifications.download.DownloadPicture;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class DDLListAdapter
	extends BaseListAdapter<DDLEntry, DDLListAdapter.ImageViewHolder> {

	public static class ImageViewHolder
		extends BaseListAdapter.ViewHolder implements View.OnClickListener {

		public ImageViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this.textView = (TextView) view.findViewById(R.id.notification_title);
			this._subtitleTextView = (TextView) view.findViewById(R.id.notification_subtitle);
			this._imageView = (ImageView) view.findViewById(R.id.notification_image);

			_listener = listener;

			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			_listener.onItemClick(getPosition(), v);
		}

		private final BaseListAdapterListener _listener;
		private final TextView _subtitleTextView;
		private final ImageView _imageView;

	}

	public DDLListAdapter(
		int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

		super(layoutId, progressLayoutId, listener);
	}

	public void setLabelFields(List<String> labelFields) {
		_labelFields = labelFields;
	}

	@Override
	public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		int layoutId = viewType == LAYOUT_TYPE_DEFAULT ? getLayoutId() : getProgressLayoutId();
		View view = inflater.inflate(layoutId, parent, false);

		return new ImageViewHolder(view, getListener());
	}

	@Override
	protected void fillHolder(DDLEntry entry, ImageViewHolder holder) {

		StringBuilder builder = new StringBuilder();

		if (entry != null && _labelFields != null && !_labelFields.isEmpty()) {

			String titleField = entry.getValue(_labelFields.get(0));

			for (int i = 1; i < _labelFields.size(); ++i) {
				String field = _labelFields.get(i);
				String value = entry.getValue(field);
				if (value != null && !value.isEmpty()) {
					builder.append(value);
					builder.append(" ");
				}
			}

			buildURL(entry, holder._imageView);

			holder.textView.setText(titleField);
			holder._subtitleTextView.setText(builder.toString());
		}
	}

	private void buildURL(DDLEntry entry, final ImageView imageView) {
		try {
			String photo = entry.getValue("Photo");
			if (photo != null) {

				final Session session = SessionContext.createSessionFromCurrentSession();

				JSONObject jsonObject = new JSONObject(photo);
				final String uuid = jsonObject.getString("uuid");
				final Long groupId = jsonObject.getLong("groupId");

				downloadPictureInBackground(session, uuid, groupId, imageView);
			}
		}
		catch (Exception e) {
			LiferayLogger.e("Error loading image", e);
		}
	}

	private void downloadPictureInBackground(final Session session, final String uuid, final Long groupId, final ImageView imageView) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Context context = LiferayScreensContext.getContext();
					String server = LiferayServerContext.getServer();
					imageView.setImageBitmap(new DownloadPicture().downloadPicture(context, session, server, uuid, groupId, 200));
				}
				catch (Exception e) {
					LiferayLogger.e("Error downloading picture", e);
				}
			}
		}).start();
	}

	private List<String> _labelFields;

}