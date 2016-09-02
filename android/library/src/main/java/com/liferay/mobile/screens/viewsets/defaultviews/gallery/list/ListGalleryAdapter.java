package com.liferay.mobile.screens.viewsets.defaultviews.gallery.list;

import android.graphics.Bitmap;
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
public class ListGalleryAdapter extends BaseListAdapter<ImageEntry, ListGalleryAdapter.ListGalleryViewHolder> {

	public ListGalleryAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public ListGalleryViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new ListGalleryViewHolder(view, listener);
	}

	@Override
	protected void fillHolder(ImageEntry entry, ListGalleryViewHolder holder) {
		holder.bind(entry);
	}

	public class ListGalleryViewHolder extends BaseListAdapter.ViewHolder {

		public ListGalleryViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			imageView = (ImageView) view.findViewById(R.id.gallery_item_image);
		}

		public void bind(final ImageEntry entry) {

			Bitmap image = entry.getImage();
			if (image != null) {
				imageView.setImageBitmap(image);
			} else {
				PicassoScreens.load(entry.getThumbnailUrl())
					.placeholder(R.drawable.default_placeholder_image)
					.into(imageView);
			}

			this.textView.setText(entry.getTitle());
		}

		private final ImageView imageView;
	}
}
