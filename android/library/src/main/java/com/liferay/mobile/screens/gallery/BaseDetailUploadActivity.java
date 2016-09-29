package com.liferay.mobile.screens.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.screens.gallery.interactor.GalleryEvent;
import com.liferay.mobile.screens.util.EventBusUtil;

/**
 * @author Víctor Galán Grande
 */
public abstract class BaseDetailUploadActivity extends AppCompatActivity {

	public static final String SCREENLET_ID = "SCREENLET_ID";
	public static final String PICTURE_PATH = "PICTURE_PATH";
	public static final String ACTION_NAME = "ACTION_NAME";
	private String actionName;
	private int screenletId;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionName = getIntent().getStringExtra(ACTION_NAME);
		screenletId = getIntent().getIntExtra(SCREENLET_ID, 0);
	}

	public void finishActivityAndStartUpload(String picturePath, String title, String description, String changelog) {

		GalleryEvent event = new GalleryEvent(picturePath, title, description, changelog);
		event.setActionName(actionName);
		event.setTargetScreenletId(screenletId);
		EventBusUtil.post(event);

		finish();
	}
}
