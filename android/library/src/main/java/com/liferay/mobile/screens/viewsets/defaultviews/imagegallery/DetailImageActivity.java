package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.squareup.picasso.Picasso;

/**
 * @author Víctor Galán Grande
 */
public class DetailImageActivity extends AppCompatActivity {

	public static final String GALLERY_SCREENLET_IMAGE_DETAILED = "GALLERY_SCREENLET_IMAGE_DETAILED";
	public static final String GALLERY_SCREENLET_IMAGE_DETAILED_URL = "GALLERY_SCREENLET_IMAGE_DETAILED_URL";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_detailed_default);

		Intent intent = getIntent();
		ImageView detailedImageView = (ImageView) findViewById(R.id.detailed_image);

		if (intent != null) {
			if (intent.hasExtra(GALLERY_SCREENLET_IMAGE_DETAILED)) {

				ImageEntry imageEntry = (ImageEntry) intent.getExtras().get(GALLERY_SCREENLET_IMAGE_DETAILED);
				PicassoScreens.load(imageEntry.getImageUrl()).into(detailedImageView);

			} else if (intent.hasExtra(GALLERY_SCREENLET_IMAGE_DETAILED_URL)) {

				String url = intent.getStringExtra(GALLERY_SCREENLET_IMAGE_DETAILED_URL);
				Picasso.with(getApplicationContext()).load(url).into(detailedImageView);
			}
		}
	}
}
