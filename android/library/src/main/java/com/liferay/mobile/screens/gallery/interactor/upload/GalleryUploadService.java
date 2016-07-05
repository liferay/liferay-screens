package com.liferay.mobile.screens.gallery.interactor.upload;

import android.app.IntentService;
import android.content.Intent;
import android.webkit.MimeTypeMap;
import com.liferay.mobile.android.callback.file.FileProgressCallback;
import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.dlapp.DLAppService;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.FileUtil;
import com.liferay.mobile.screens.util.JSONUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Víctor Galán Grande
 */
public class GalleryUploadService extends IntentService {

	public GalleryUploadService() {
		super(GalleryUploadService.class.getCanonicalName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		uploadFromIntent(intent);
	}

	private void uploadFromIntent(Intent intent) {
		_screenletId = intent.getIntExtra("screenletId", 0);
		long repositoryId = intent.getLongExtra("repositoryId", 0);
		long folderId = intent.getLongExtra("folderId", 0);
		String title = intent.getStringExtra("title");
		String description = intent.getStringExtra("description");
		String changeLog = intent.getStringExtra("changeLog");
		String picturePath = intent.getStringExtra("picturePath");

		try {
			JSONObject jsonObject = uploadImageEntry(repositoryId, folderId, title, description, changeLog,
				picturePath);
			ImageEntry imageEntry = new ImageEntry(JSONUtil.toMap(jsonObject));
			createEventAndPost(0, 0, true, imageEntry);
		} catch (Exception e) {
			BasicEvent event = new GalleryUploadEvent(_screenletId, e);
			EventBusUtil.post(event);
		}
	}

	private JSONObject uploadImageEntry(long repositoryId, long folderId, String title, String description,
		String changeLog, String picturePath) throws Exception {

		String sourceName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
		if(title.isEmpty()){
			title = sourceName;
		}
		UploadData uploadData = createUploadData(picturePath, sourceName);

		Session session = SessionContext.createSessionFromCurrentSession();
		return new DLAppService(session).addFileEntry(repositoryId, folderId, sourceName,
			FileUtil.getMimeType(picturePath), title, description, changeLog, uploadData,
			getJsonObjectWrapper(SessionContext.getUserId(), LiferayServerContext.getGroupId()));
	}

	private UploadData createUploadData(String picturePath, String pictureName) throws IOException {
		File file = new File(picturePath);
		InputStream is = new FileInputStream(file);

		final int fileSize = (int) file.length();
		UploadData uploadData = new UploadData(is, pictureName, new FileProgressCallback() {
			@Override
			public void onProgress(int totalBytesSended) {
				createEventAndPost(fileSize, totalBytesSended, false, null);
			}
		});

		return uploadData;
	}

	private void createEventAndPost(int totalBytes, int totalBytesSended, boolean isCompleted, ImageEntry entry) {
		BasicEvent event = new GalleryUploadEvent(_screenletId, totalBytes, totalBytesSended, isCompleted, entry);
		EventBusUtil.post(event);
	}

	private JSONObjectWrapper getJsonObjectWrapper(Long userId, Long groupId) throws JSONException {
		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", userId);
		serviceContextAttributes.put("scopeGroupId", groupId);
		serviceContextAttributes.put("addGuestPermissions", true);
		return new JSONObjectWrapper(serviceContextAttributes);
	}

	private int _screenletId;
}
