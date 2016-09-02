package com.liferay.mobile.screens.gallery;

import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.screens.gallery.interactor.upload.StartUploadEvent;
import com.liferay.mobile.screens.util.EventBusUtil;

/**
 * @author Víctor Galán Grande
 */
public class BaseDetailUploadActivity extends AppCompatActivity {

	public static final String SCREENLET_ID_KEY = "SCREENLET_ID_KEY";
	public static final String PICTURE_PATH_KEY = "PICTURE_PATH_KEY";

	public void finishActivityAndstartUpload(int screenletId, String picturePath, String title, String description) {

		StartUploadEvent event = new StartUploadEvent(screenletId, picturePath, title, description);
		EventBusUtil.post(event);

		finish();
	}
}
