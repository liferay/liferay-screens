package com.liferay.mobile.screens.viewsets.westeros.asset.list.documents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
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

        public AssetListViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            titleTextView = view.findViewById(R.id.liferay_list_title);
            descriptionTextView = view.findViewById(R.id.liferay_list_description);
            documentExtensionImage = view.findViewById(R.id.asset_list_document_type_image);
        }

        public void bind(AssetEntry entry) {
            FileEntry fileEntry = new FileEntry(entry.getValues());

            setImageForExtension(fileEntry.getExtension());

            titleTextView.setText(fileEntry.getTitle());

            String descriptionText = fileEntry.getValues().get("description").toString();
            if (!descriptionText.isEmpty()) {
                descriptionTextView.setText(descriptionText);
            }
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
