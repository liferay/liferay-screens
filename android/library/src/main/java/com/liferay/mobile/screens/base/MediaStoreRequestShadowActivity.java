package com.liferay.mobile.screens.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
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
	public static final String SCREENLET_ID = "SCREENLET_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_screenletId = getIntent().getIntExtra(SCREENLET_ID, 0);
		_mediaStoreType = getIntent().getIntExtra(MEDIA_STORE_TYPE, 0);

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

		String path = "";

		if (_mediaStoreType == SELECT_IMAGE_FROM_GALLERY) {
			path = FileUtil.getPath(this, intentData.getData());
		} else if (_mediaStoreType == TAKE_PICTURE_WITH_CAMERA) {
			path = _filePath;
		}

		MediaStoreEvent event = new MediaStoreEvent(_screenletId, path);

		EventBusUtil.post(event);
	}

	private void sendIntent() {
		if (_mediaStoreType == SELECT_IMAGE_FROM_GALLERY) {
			openGallery();
		} else if (_mediaStoreType == TAKE_PICTURE_WITH_CAMERA) {
			openCamera();
		}
	}

	private void openGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, _mediaStoreType);
	}

	private void openCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imageFile = FileUtil.createImageFile();
		_filePath = imageFile.getPath();
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

		startActivityForResult(cameraIntent, _mediaStoreType);
	}

	private int _mediaStoreType;
	private int _screenletId;

	private String _filePath;
}
