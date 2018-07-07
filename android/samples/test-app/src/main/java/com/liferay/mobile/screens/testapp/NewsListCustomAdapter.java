package com.liferay.mobile.screens.testapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list.WebContentListAdapter;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class NewsListCustomAdapter extends WebContentListAdapter {

	private Picasso picasso =
		new Picasso.Builder(LiferayScreensContext.getContext()).downloader(new OkHttpDownloader(new OkHttpClient()))
			.build();

	public NewsListCustomAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@Override
	public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new NewsHolder(view, listener);
	}

	@Override
	protected void fillHolder(WebContent entry, ViewHolder holder) {

		NewsHolder newsHolder = (NewsHolder) holder;

		Object smallimageid = entry.getValues().get("smallImageId").toString();
		picasso.load("http://screens.liferay.org.es/image/journal/article?img_id=" + smallimageid).into(newsHolder._image);

		newsHolder._title.setText(entry.getTitle());
	}

	private class NewsHolder extends ViewHolder {
		protected final ImageView _image;
		protected final TextView _title;

		public NewsHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			_image = view.findViewById(R.id.news_item_image);
			_title = view.findViewById(R.id.news_title);
		}
	}
}