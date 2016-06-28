package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.squareup.picasso.Picasso;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowGalleryAdapter
	extends BaseListAdapter<ImageEntry, SlideshowGalleryAdapter.SlideshowGalleryViewHolder> {

	public SlideshowGalleryAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public SlideshowGalleryViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new SlideshowGalleryViewHolder(view, listener);
	}

	@Override
	protected void fillHolder(ImageEntry entry, SlideshowGalleryViewHolder holder) {
		holder.bind(entry);
	}

	public class SlideshowGalleryViewHolder extends BaseListAdapter.ViewHolder {

		public SlideshowGalleryViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			_imageView = (ImageView) view.findViewById(R.id.gallery_item_image);
			_context = view.getContext().getApplicationContext();
		}

		public void bind(ImageEntry entry) {
			Picasso.with(_context).load(entry.getImageUrl()).into(_imageView);
		}

		private final ImageView _imageView;
		private final Context _context;
	}
}
