package com.liferay.mobile.screens.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.FileUtil;
import java.io.File;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreRequestShadowActivity extends Activity {

	public static final int SELECT_IMAGE_FROM_GALLERY = 0;
	public static final int TAKE_PICTURE_WITH_CAMERA = 1;
	public static final int RECORD_VIDEO = 2;

	public static final String MEDIA_STORE_TYPE = "MEDIA_STORE_TYPE";
	public static final String MEDIA_STORE_RECEIVER = "MEDIA_STORE_RECEIVER";
	public static final String FILE_URI = "FILE_URI";

	private int mediaStoreType;
	private Uri fileUri;
	private AlertDialog fileDialog;
	private ResultReceiver resultReceiver;

	public static void show(Context context, int mediaStore, MediaStoreCallback callback) {
		Activity activity = LiferayScreensContext.getActivityFromContext(context);

		Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);
		intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_RECEIVER,
			new MediaStoreResult(new Handler(), callback));

		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mediaStoreType = getIntent().getIntExtra(MEDIA_STORE_TYPE, 0);
		resultReceiver = getIntent().getParcelableExtra(MEDIA_STORE_RECEIVER);

		sendIntent();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri uri = mediaStoreType == SELECT_IMAGE_FROM_GALLERY ? data.getData() : fileUri;
			sendEvent(uri);
		}

		finish();
	}

	private void sendEvent(Uri uri) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(FILE_URI, uri);

		resultReceiver.send(RESULT_OK, bundle);
	}

	private void sendIntent() {
		if (mediaStoreType == SELECT_IMAGE_FROM_GALLERY) {
			openGallery();
		} else if (mediaStoreType == TAKE_PICTURE_WITH_CAMERA) {
			openCamera();
		} else if (mediaStoreType == RECORD_VIDEO) {
			openVideCamera();
		}
	}

	private void openGallery() {
		Intent galleryIntent =
			new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, mediaStoreType);
	}

	private void openCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			File imageFile = FileUtil.createImageFile();
			fileUri = FileProvider.getUriForFile(this, getPackageName() + ".screens.fileprovider",
				imageFile);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(cameraIntent, mediaStoreType);
		}
	}

	private void openVideCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			File videoFile = FileUtil.createVideoFile();
			fileUri = FileProvider.getUriForFile(this, getPackageName() + ".screens.fileprovider",
				videoFile);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(cameraIntent, mediaStoreType);
		}
	}

	private static class MediaStoreResult extends ResultReceiver {
		private MediaStoreCallback callback;

		MediaStoreResult(Handler handler, MediaStoreCallback callback) {
			super(handler);

			this.callback = callback;
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			if (resultCode == RESULT_OK) {
				callback.onUriReceived((Uri) resultData.getParcelable(FILE_URI));
			}
		}
	}

	public interface MediaStoreCallback {

		void onUriReceived(Uri uri);
	}
}
