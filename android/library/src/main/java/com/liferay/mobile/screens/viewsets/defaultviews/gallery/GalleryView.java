package com.liferay.mobile.screens.viewsets.defaultviews.gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractor;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class GalleryView
    extends BaseListScreenletView<ImageEntry, GalleryAdapter.GalleryViewHolder, GalleryAdapter>
    implements GalleryViewModel, View.OnKeyListener {

  private ImageView _detailedImage;

  public GalleryView(Context context) {
    super(context);
    initialize();
  }

  public GalleryView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize();
  }

  public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialize();
  }

  private void initialize() {
    setFocusableInTouchMode(true);
    requestFocus();
    setOnKeyListener(this);
  }

  @Override protected GalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
    return new GalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
  }

  @Override protected int getItemLayoutId() {
    return R.layout.gallery_item;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    _detailedImage = (ImageView) findViewById(R.id.gallery_detailed_image);
    _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    _recyclerView.removeItemDecoration(getDividerDecoration());
  }

  @Override public void onItemClick(int position, View view) {
    List<ImageEntry> entries = getAdapter().getEntries();

    ImageEntry image = entries.get(position);
    GalleryScreenlet screenlet = (GalleryScreenlet) getScreenlet();

    if (!entries.isEmpty() && entries.size() > position) {
      screenlet.onImageClicked(image, view);
    }
  }

  @Override public void showDetailImage(ImageEntry image) {
    _recyclerView.setVisibility(GONE);
    _detailedImage.setVisibility(VISIBLE);
    Picasso.with(getContext()).load(image.getImageUrl()).into(_detailedImage);
  }

  @Override public boolean onKey(View v, int keyCode, KeyEvent event) {

    if (_recyclerView.getVisibility() == GONE && keyCode == KeyEvent.KEYCODE_BACK) {

      _detailedImage.setVisibility(GONE);
      _recyclerView.setVisibility(VISIBLE);
      return true;
    }
    return false;
  }
}
