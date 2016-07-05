package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

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
public class GridGalleryAdapter extends BaseListAdapter<ImageEntry, GridGalleryAdapter.GridGalleryViewHolder> {

	public GridGalleryAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public GridGalleryViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new GridGalleryViewHolder(view, listener);
	}

	@Override
	protected void fillHolder(ImageEntry entry, GridGalleryViewHolder holder) {
		holder.bind(entry);
	}

	public class GridGalleryViewHolder extends BaseListAdapter.ViewHolder {

		public GridGalleryViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			_imageView = (ImageView) view.findViewById(R.id.gallery_item_image);
			_context = view.getContext().getApplicationContext();
		}

		public void bind(ImageEntry entry) {
			if(entry.thumbnailNotAlreadyGenerated()) {
				Picasso.with(_context).load(entry.getImageUrl()).fit().centerCrop().into(_imageView);
			} else {
				Picasso.with(_context).load(entry.getThumbnailUrl()).into(_imageView);
			}
		}

		private final ImageView _imageView;
		private final Context _context;
	}
}
