package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowGalleryAdapter
	extends BaseListAdapter<ImageEntry, SlideshowGalleryAdapter.SlideshowGalleryViewHolder> {

	public static final int RESIZED_WIDTH = 900;
	public static final int RESIZED_HEIGHT = RESIZED_WIDTH;

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

		private final ImageView imageView;

		public SlideshowGalleryViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			imageView = (ImageView) view.findViewById(R.id.gallery_item_image);
		}

		public void bind(ImageEntry entry) {
			PicassoScreens.load(entry.getImageUrl())
				.placeholder(R.drawable.default_placeholder_image)
				.resize(RESIZED_WIDTH, RESIZED_HEIGHT)
				.centerInside()
				.into(imageView);
		}
	}
}
