package com.liferay.mobile.screens.viewsets.defaultviews.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.gallery.BaseDetailUploadActivity;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * @author Víctor Galán Grande
 */
public class DetailUploadDefaultActivity extends BaseDetailUploadActivity implements View.OnClickListener {

	private int screenletId;
	private String picturePath;
	private EditText editTitle;
	private EditText editDescription;
	private ImageView imageView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.default_upload_detail_activity);

		parseIntent();
		bindViews();
		initialize();
	}

	private void parseIntent() {
		Intent intent = getIntent();

		picturePath = intent.getStringExtra(PICTURE_PATH_KEY);
		screenletId = intent.getIntExtra(SCREENLET_ID_KEY, 0);
	}

	private void bindViews() {
		editTitle = (EditText) findViewById(R.id.liferay_gallery_upload_title);
		editDescription = (EditText) findViewById(R.id.liferay_gallery_upload_description);
		imageView = (ImageView) findViewById(R.id.liferay_gallery_upload_image);

		findViewById(R.id.liferay_gallery_upload_upload).setOnClickListener(this);
		findViewById(R.id.liferay_gallery_upload_cancel).setOnClickListener(this);
	}

	private void initialize() {
		Picasso.with(this).load(new File(picturePath)).fit().into(imageView);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.liferay_gallery_upload_upload) {

			finishActivityAndStartUpload(picturePath, editTitle.getText().toString(),
				editDescription.getText().toString());
		} else if (v.getId() == R.id.liferay_gallery_upload_cancel) {
			finish();
		}
	}
}
