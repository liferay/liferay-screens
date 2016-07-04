package com.liferay.mobile.screens.viewsets.defaultviews.assetdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Picasso;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayActivity extends AppCompatActivity implements AssetDisplayListener {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_display);

		Intent intent = getIntent();
		String path = intent.getStringExtra("filepath");

		_image = (ImageView) findViewById(R.id.liferay_asset_image);
		Picasso.with(this).load(path).into(_image);
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
		LiferayLogger.e(e.getMessage());
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		LiferayLogger.i("Image asset entry load! -> " + assetEntry.getTitle());
	}

	private ImageView _image;
}
