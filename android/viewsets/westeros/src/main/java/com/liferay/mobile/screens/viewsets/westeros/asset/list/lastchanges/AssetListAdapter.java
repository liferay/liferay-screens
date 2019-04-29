package com.liferay.mobile.screens.viewsets.westeros.asset.list.lastchanges;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
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

        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final ImageView documentExtensionImage;
        private final ImageDisplayScreenlet imageDisplayScreenlet;

        public AssetListViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            titleTextView = view.findViewById(R.id.liferay_list_title);
            descriptionTextView = view.findViewById(R.id.liferay_list_description);
            documentExtensionImage = view.findViewById(R.id.asset_list_document_type_image);
            imageDisplayScreenlet = view.findViewById(R.id.asset_list_document_type_image_screenlet);
        }

        public void bind(AssetEntry entry) {

            long creationDate = (long) entry.getValues().get("createDate");
            long modifiedDate = (long) entry.getValues().get("modifiedDate");

            boolean isFile = isFile(entry);

            String title = String.format("%s %s", Math.abs(creationDate - modifiedDate) < 5000 ? "New" : "Updated",
                isFile ? "file" : "blog");

            titleTextView.setText(title);
            descriptionTextView.setText(entry.getTitle());

            if (isFile) {
                fillFileImage(entry);
            } else {
                fillBlogImage(entry);
            }
        }

        private boolean isFile(AssetEntry entry) {
            return entry.getClassName().equals("com.liferay.document.library.kernel.model.DLFileEntry");
        }

        private void fillFileImage(AssetEntry entry) {
            FileEntry fileEntry = new FileEntry(entry.getValues());
            setImageForExtension(fileEntry.getExtension());

            documentExtensionImage.setVisibility(View.VISIBLE);
            imageDisplayScreenlet.setVisibility(View.GONE);
        }

        private void fillBlogImage(AssetEntry entry) {
            BlogsEntry blogsEntry = new BlogsEntry(entry.getValues());

            if (blogsEntry.getCoverImage() > 0) {
                imageDisplayScreenlet.setClassPK(blogsEntry.getCoverImage());
                imageDisplayScreenlet.setClassName("com.liferay.document.library.kernel.model.DLFileEntry");
                imageDisplayScreenlet.load();
            }

            imageDisplayScreenlet.setVisibility(View.VISIBLE);
            documentExtensionImage.setVisibility(View.GONE);
        }

        private void setImageForExtension(String extension) {
            int drawableId;

            switch (extension) {
                case "mp3":
                    drawableId = R.drawable.westeros_document_list_music_type;
                    break;
                case "pdf":
                    drawableId = R.drawable.westeros_document_list_pdf_type;
                    break;
                case "mp4":
                    drawableId = R.drawable.westeros_document_list_video_type;
                    break;
                case "png":
                case "jpg":
                case "jpeg":
                    drawableId = R.drawable.westeros_document_list_image_type;
                    break;
                default:
                    drawableId = R.drawable.westeros_document_list_none_type;
            }

            Context context = LiferayScreensContext.getContext();
            Drawable drawable = ContextCompat.getDrawable(context, drawableId);

            documentExtensionImage.setImageDrawable(drawable);
        }
    }
}
