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

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_detailed_default);

		Intent intent = getIntent();

		if (intent != null && intent.hasExtra(GALLERY_SCREENLET_IMAGE_DETAILED)) {
			ImageEntry imageEntry = (ImageEntry) intent.getExtras().get(GALLERY_SCREENLET_IMAGE_DETAILED);

			ImageView detailedImageView = (ImageView) findViewById(R.id.detailed_image);
			PicassoScreens.load(imageEntry.getImageUrl()).into(detailedImageView);
		}
	}
}
