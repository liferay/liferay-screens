package com.liferay.mobile.screens.viewsets.westeros.asset.list.blogs;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Víctor Galán Grande
 */
public class AssetListAdapter extends BaseListAdapter<AssetEntry, AssetListAdapter.AssetListViewHolder> {

    public AssetListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public AssetListViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new AssetListViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(AssetEntry entry, AssetListViewHolder holder) {
        holder.bind(entry);
    }

    public class AssetListViewHolder extends BaseListAdapter.ViewHolder {

        private final TextView dateTextView;
        private final TextView titleTextView;

        private final ImageDisplayScreenlet imageDisplayScreenlet;

        public AssetListViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            dateTextView = view.findViewById(R.id.liferay_list_date);
            titleTextView = view.findViewById(R.id.liferay_list_title);
            imageDisplayScreenlet = view.findViewById(R.id.asset_list_document_type_image_screenlet);
        }

        public void bind(AssetEntry entry) {
            BlogsEntry blogsEntry = new BlogsEntry(entry.getValues());

            imageDisplayScreenlet.setClassPK(blogsEntry.getCoverImage());
            imageDisplayScreenlet.setClassName("com.liferay.document.library.kernel.model.DLFileEntry");
            imageDisplayScreenlet.load();

            String dateText = String.format("%s  · %s", blogsEntry.getDate(), blogsEntry.getUserName());

            dateTextView.setText(dateText);

            titleTextView.setText(blogsEntry.getTitle());
        }
    }
}
