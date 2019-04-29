package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.DrawableRes;
import androidx.core.content.FileProvider;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayView extends BaseFileDisplayView implements ImageDisplayViewModel, Callback {

    private ImageView imageView;

    @DrawableRes private int placeholder = 0;

    private ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;
    private ImageView.ScaleType placeholderScaleType = ImageView.ScaleType.FIT_CENTER;

    public ImageDisplayView(Context context) {
        super(context);
    }

    public ImageDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showFinishOperation(FileEntry fileEntry) {
        imageView.setImageResource(placeholder);
        imageView.setScaleType(placeholderScaleType);
        super.showFinishOperation(fileEntry);
    }

    @Override
    public void loadFileEntry(String url) {
        loadImage(url);
    }

    @Override
    public void onSuccess() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onError() {
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imageView = findViewById(R.id.liferay_image_asset);
    }

    private void loadImage(String url) {
        setScaleType(scaleType);

        Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".screens.fileprovider",
            new File(url));

        Picasso.with(getContext()).load(photoURI).into(imageView, this);
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        imageView.setScaleType(scaleType);
    }

    @Override
    public void setPlaceholder(@DrawableRes int placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void setPlaceholderScaleType(ImageView.ScaleType placeholderScaleType) {
        this.placeholderScaleType = placeholderScaleType;
    }
}
