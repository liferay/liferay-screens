package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.DocumentField;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadInteractorImpl
	extends BaseCachedWriteRemoteInteractor<DDLFormListener, DDLFormDocumentUploadEvent>
	implements DDLFormDocumentUploadInteractor {

	public DDLFormDocumentUploadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void upload(final long groupId, final long userId, final long repositoryId, final long folderId,
					   final String filePrefix, final DocumentField file) throws Exception {

		long repository = (repositoryId != 0) ? repositoryId : groupId;

		storeWithCache(file, userId, groupId, repository, folderId, filePrefix);
	}

	public void onEventMainThread(DDLFormDocumentUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event, event.getDocumentField(), event.getUserId(), event.getGroupId(),
			event.getRepositoryId(), event.getFolderId(), event.getFilePrefix());
	}

	@Override
	public void online(Object[] args) throws Exception {
		Intent service = new Intent(LiferayScreensContext.getContext(), UploadService.class);
		service.putExtra("file", (DocumentField) args[0]);
		service.putExtra("userId", (long) args[1]);
		service.putExtra("groupId", (long) args[2]);
		service.putExtra("repositoryId", (long) args[3]);
		service.putExtra("folderId", (long) args[4]);
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("filePrefix", (String) args[5]);

		LiferayScreensContext.getContext().startService(service);
	}

	@Override
	protected void notifySuccess(DDLFormDocumentUploadEvent event) {
		getListener().onDDLFormDocumentUploaded(event.getDocumentField(), event.getJSONObject());
	}

	@Override
	protected void notifyError(DDLFormDocumentUploadEvent event) {
		getListener().onDDLFormDocumentUploadFailed(event.getDocumentField(), event.getException());
	}

	@Override
	protected void storeToCache(boolean synced, Object[] args) {

		DocumentField file = (DocumentField) args[0];
		long userId = (long) args[1];
		long groupId = (long) args[2];
		long repositoryId = (long) args[3];
		long folderId = (long) args[4];
		String filePrefix = (String) args[5];

		String path = file.getCurrentValue().toString();
		DocumentUploadCache documentUploadCache = new DocumentUploadCache(path, userId, groupId, repositoryId, folderId, filePrefix);
		documentUploadCache.setDirty(!synced);
		CacheSQL.getInstance().set(documentUploadCache);

		onEventMainThread(new DDLFormDocumentUploadEvent(getTargetScreenletId(), file, userId,
			groupId, repositoryId, folderId, filePrefix, new JSONObject()));
	}

}
