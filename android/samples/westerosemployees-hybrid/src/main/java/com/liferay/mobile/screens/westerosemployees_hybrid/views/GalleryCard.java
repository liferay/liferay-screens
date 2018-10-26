package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.jakewharton.rxbinding.view.RxView;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.imagegallery.BaseDetailUploadView;
import com.liferay.mobile.screens.imagegallery.ImageGalleryListener;
import com.liferay.mobile.screens.imagegallery.ImageGalleryScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.List;
import rx.functions.Action1;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author Víctor Galán Grande
 */
public class GalleryCard extends Card implements ImageGalleryListener, WebListener {

    private ImageGalleryScreenlet imageGalleryScreenlet;
    private BaseDetailUploadView uploadDetailView;
    private Card uploadImageCard;
    WebScreenlet webScreenlet;
    private boolean loaded;

    public GalleryCard(Context context) {
        super(context);
    }

    public GalleryCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ViewPropertyAnimator setState(CardState state) {
        if (!loaded && state.equals(CardState.NORMAL)) {
            loaded = true;

            uploadDetailView = findViewById(R.id.upload_detail_view);
            uploadImageCard = findViewById(R.id.upload_image_card);

            loadGallery();
        }

        return super.setState(state);
    }

    private void loadGallery() {
        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/gallery").addRawCss(R.raw.gallery_portlet_css,
                "gallery_portlet_css.css").addRawJs(R.raw.gallery_portlet_js, "gallery_portlet_js.js").load();

        webScreenlet = findViewById(R.id.portlet_gallery);

        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.load();
        webScreenlet.setListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageGalleryScreenlet = findViewById(R.id.gallery_screenlet);
        imageGalleryScreenlet.setListener(this);

        Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
        RxPermissions rxPermissions = new RxPermissions(activity);

        RxView.clicks(findViewById(R.id.gallery_button))
            .compose(rxPermissions.ensure(WRITE_EXTERNAL_STORAGE))
            .subscribe(openGallery());

        RxView.clicks(findViewById(R.id.camera_button))
            .compose(rxPermissions.ensure(CAMERA, WRITE_EXTERNAL_STORAGE))
            .subscribe(openCamera());

        RxView.clicks(findViewById(R.id.upoad_button)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                uploadDetailView.finishActivityAndStartUpload(uploadDetailView.getTitle(),
                    uploadDetailView.getDescription(), "");
            }
        });
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onPageLoaded(String url) {

    }

    @Override
    public void onScriptMessageHandler(String namespace, final String body) {
        if ("gallery-item".equals(namespace)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    WebScreenletConfiguration configuration =
                        new WebScreenletConfiguration.Builder("/web/westeros-hybrid/detail?id=" + body).addRawCss(
                            R.raw.detail_css, "detail_css.css").addRawJs(R.raw.detail_js, "detail_js.js").load();

                    WebScreenlet webScreenlet = findViewById(R.id.portlet_gallery_item);

                    webScreenlet.setWebScreenletConfiguration(configuration);
                    webScreenlet.load();

                    cardListener.moveCardRight(GalleryCard.this);
                }
            });
        }
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {

    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<ImageEntry> entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(ImageEntry element, View view) {

    }

    @Override
    public void onImageEntryDeleted(long imageEntryId) {

    }

    @Override
    public void onImageUploadStarted(Uri pictureUri, String title, String description, String changelog) {
        uploadImageCard.setState(CardState.MINIMIZED);
    }

    @Override
    public void onImageUploadProgress(int totalBytes, int totalBytesSent) {

    }

    @Override
    public void onImageUploadEnd(ImageEntry entry) {
        webScreenlet.load();
    }

    @Override
    public boolean showUploadImageView(String actionName, Uri pictureUri, int screenletId) {
        uploadDetailView.initializeUploadView(actionName, pictureUri, screenletId);
        return false;
    }

    @Override
    public int provideImageUploadDetailView() {
        return 0;
    }

    private Action1<Boolean> openGallery() {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean permissionAccepted) {
                if (permissionAccepted) {
                    imageGalleryScreenlet.openGallery();
                }
            }
        };
    }

    private Action1<Boolean> openCamera() {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean permissionAccepted) {
                if (permissionAccepted) {
                    imageGalleryScreenlet.openCamera();
                }
            }
        };
    }
}
