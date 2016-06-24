package com.liferay.mobile.screens.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractor;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorImpl;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.DetailImageActivity;
import java.util.Locale;

/**
 * @author Víctor Galán Grande
 */
public class GalleryScreenlet extends BaseListScreenlet<ImageEntry, GalleryInteractor> {

  private long _groupId;
  private long _folderId;
  private OfflinePolicy _offlinePolicy;

  public GalleryScreenlet(Context context) {
    super(context);
  }

  public GalleryScreenlet(Context context, AttributeSet attributes) {
    super(context, attributes);
  }

  public GalleryScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
    super(context, attributes, defaultStyle);
  }

  public long getGroupId() {
    return _groupId;
  }

  public void setGroupId(long _groupId) {
    this._groupId = _groupId;
  }

  public long getFolderId() {
    return _folderId;
  }

  public void setFolderId(long _folderId) {
    this._folderId = _folderId;
  }

  public OfflinePolicy getOfflinePolicy() {
    return _offlinePolicy;
  }

  public void setOfflinePolicy(OfflinePolicy _offlinePolicy) {
    this._offlinePolicy = _offlinePolicy;
  }

  @Override
  protected void loadRows(GalleryInteractor interactor, int startRow, int endRow, Locale locale)
      throws Exception {
    interactor.loadRows(startRow, endRow, _groupId, _folderId);
  }

  @Override protected GalleryInteractor createInteractor(String actionName) {
    return new GalleryInteractorImpl(getScreenletId(), _offlinePolicy);
  }

  @Override protected View createScreenletView(Context context, AttributeSet attributes) {
    TypedArray typedArray =
        context.getTheme().obtainStyledAttributes(attributes, R.styleable.GalleryScreenlet, 0, 0);

    Integer offlinePolicy = typedArray.getInteger(R.styleable.AssetListScreenlet_offlinePolicy,
        OfflinePolicy.REMOTE_ONLY.ordinal());
    _offlinePolicy = OfflinePolicy.values()[offlinePolicy];

    long groupId = LiferayServerContext.getGroupId();

    _groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.GalleryScreenlet_groupId), groupId);

    _folderId = castToLong(typedArray.getString(R.styleable.GalleryScreenlet_folderId));

    typedArray.recycle();

    return super.createScreenletView(context, attributes);
  }

  @Override public void loadingFromCache(boolean success) {

  }

  @Override public void retrievingOnline(boolean triedInCache, Exception e) {

  }

  @Override public void storingToCache(Object object) {

  }

  public void onImageClicked(ImageEntry image, View view) {
    if(_listener != null) {
      _listener.onListItemSelected(image, view);
    }

    GalleryViewModel viewModel = (GalleryViewModel) getViewModel();
    viewModel.showDetailImage(image);
  }

  public void showImageInFullScreenActivity(ImageEntry image) {
    Intent intent = new Intent(getContext(), DetailImageActivity.class);
    intent.putExtra(DetailImageActivity.GALLERY_SCREENLET_IMAGE_DETAILED, image);

    getContext().startActivity(intent);
  }
}
