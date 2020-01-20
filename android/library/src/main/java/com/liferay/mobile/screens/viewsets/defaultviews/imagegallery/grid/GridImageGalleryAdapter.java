package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.grid;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public class GridImageGalleryAdapter
    extends BaseListAdapter<ImageEntry, GridImageGalleryAdapter.GridGalleryViewHolder> {

    public GridImageGalleryAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
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

        private final ImageView imageView;

        public GridGalleryViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            imageView = view.findViewById(R.id.gallery_item_image);
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
        }
    }
}
