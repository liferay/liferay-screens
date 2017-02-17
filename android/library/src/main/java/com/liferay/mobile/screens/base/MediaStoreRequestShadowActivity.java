package com.liferay.mobile.screens.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.FileUtil;
import java.io.File;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreRequestShadowActivity extends Activity {

	public static final int SELECT_IMAGE_FROM_GALLERY = 0;
	public static final int TAKE_PICTURE_WITH_CAMERA = 1;

	public static final String MEDIA_STORE_TYPE = "MEDIA_STORE_TYPE";
	private int mediaStoreType;
	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mediaStoreType = getIntent().getIntExtra(MEDIA_STORE_TYPE, 0);

		sendIntent();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			sendEvent(data);
		}

		finish();
	}

	private void sendEvent(Intent intentData) {
		Uri uri = mediaStoreType == SELECT_IMAGE_FROM_GALLERY ? intentData.getData() : fileUri;
		EventBusUtil.post(new MediaStoreEvent(uri));
	}

	private void sendIntent() {
		if (mediaStoreType == SELECT_IMAGE_FROM_GALLERY) {
			openGallery();
		} else if (mediaStoreType == TAKE_PICTURE_WITH_CAMERA) {
			openCamera();
		}
	}

	private void openGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, mediaStoreType);
	}

	private void openCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			File imageFile = FileUtil.createImageFile();
			fileUri = FileProvider.getUriForFile(this, getPackageName() + ".screensfileprovider", imageFile);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(cameraIntent, mediaStoreType);
		}
	}
}
