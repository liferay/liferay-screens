package com.liferay.mobile.screens.gallery.interactor.upload;

import android.app.IntentService;
import android.content.Intent;
import com.liferay.mobile.android.callback.file.FileProgressCallback;
import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.dlapp.DLAppService;
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

		int screenletId = intent.getIntExtra("screenletId", 0);
		long repositoryId = intent.getLongExtra("repositoryId", 0);
		long folderId = intent.getLongExtra("folderId", 0);
		String title = intent.getStringExtra("title");
		String description = intent.getStringExtra("description");
		String changeLog = intent.getStringExtra("changeLog");
		String picturePath = intent.getStringExtra("picturePath");

		try {
			JSONObject jsonObject =
				uploadImageEntry(screenletId, repositoryId, folderId, title, description, changeLog, picturePath);
			ImageEntry imageEntry = new ImageEntry(JSONUtil.toMap(jsonObject));

			EventBusUtil.post(new GalleryUploadEvent(screenletId, imageEntry));
		} catch (Exception e) {
			EventBusUtil.post(new GalleryUploadEvent(screenletId, e));
		}
	}

	private JSONObject uploadImageEntry(int screenletId, long repositoryId, long folderId, String title,
		String description, String changeLog, String picturePath) throws Exception {

		String sourceName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
		if (title.isEmpty()) {
			title = sourceName;
		}

		UploadData uploadData = createUploadData(screenletId, picturePath, sourceName);

		Session session = SessionContext.createSessionFromCurrentSession();
		String mimeType = FileUtil.getMimeType(picturePath);
		long groupId = LiferayServerContext.getGroupId();
		JSONObjectWrapper serviceContext = getJsonObjectWrapper(SessionContext.getUserId(), groupId);

		return new DLAppService(session).addFileEntry(repositoryId, folderId, sourceName, mimeType, title, description,
			changeLog, uploadData, serviceContext);
	}

	private UploadData createUploadData(final int screenletId, String path, String name) throws IOException {

		File file = new File(path);
		InputStream is = new FileInputStream(file);
		final int fileSize = (int) file.length();

		return new UploadData(is, name, new FileProgressCallback() {
			@Override
			public void onProgress(int totalBytesSent) {
				EventBusUtil.post(new GalleryUploadEvent(screenletId, fileSize, totalBytesSent));
			}
		});
	}

	private JSONObjectWrapper getJsonObjectWrapper(Long userId, Long groupId) throws JSONException {
		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", userId);
		serviceContextAttributes.put("scopeGroupId", groupId);
		serviceContextAttributes.put("addGuestPermissions", true);
		return new JSONObjectWrapper(serviceContextAttributes);
	}
}
