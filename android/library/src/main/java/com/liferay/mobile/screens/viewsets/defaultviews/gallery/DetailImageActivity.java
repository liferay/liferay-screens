package com.liferay.mobile.screens.viewsets.defaultviews.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.squareup.picasso.Picasso;

/**
 * @author Víctor Galán Grande
 */
public class DetailImageActivity extends AppCompatActivity {

  public static final String GALLERY_SCREENLET_IMAGE_DETAILED = "gallery-screenlet-image-detailed";

  private ImageEntry _imageEntry;

  private ImageView _detailedImageView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gallery_detailed_default);

    initialize();
    bindViews();
  }

  private void initialize() {
    Intent intent = getIntent();

    if(intent != null) {
      _imageEntry = (ImageEntry) intent.getExtras().get(GALLERY_SCREENLET_IMAGE_DETAILED);
    }
  }

  private void bindViews() {
    _detailedImageView = (ImageView) findViewById(R.id.detailed_image);

    if(_imageEntry != null) {
      Picasso.with(this).load(_imageEntry.getImageUrl()).into(_detailedImageView);
    }
  }
}
