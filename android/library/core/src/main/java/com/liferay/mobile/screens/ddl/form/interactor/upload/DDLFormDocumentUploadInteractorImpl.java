package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.interactor.update.DDLFormUpdateRecordEvent;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.DocumentField;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadInteractorImpl
	extends BaseCachedWriteRemoteInteractor<DDLFormListener, DDLFormUpdateRecordEvent>
	implements DDLFormDocumentUploadInteractor {

	public DDLFormDocumentUploadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void upload(final long groupId, final long userId, final long repositoryId, final long folderId,
					   final String filePrefix, final DocumentField file) throws Exception {

		long repository = (repositoryId != 0) ? repositoryId : groupId;

		loadWithCache(file, userId, groupId, repository, folderId, filePrefix);
	}

	public void onEventMainThread(DDLFormDocumentUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormDocumentUploadFailed(event.getDocumentField(), event.getException());
		}
		else {
			getListener().onDDLFormDocumentUploaded(event.getDocumentField(), event.getJSONObject());
		}
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
	protected void storeToCache(Object[] args) {

		DocumentField file = (DocumentField) args[0];
		long userId = (long) args[1];
		long groupId = (long) args[2];
		long repositoryId = (long) args[3];
		long folderId = (long) args[4];
		String filePrefix = (String) args[5];

		String path = file.getCurrentValue().toString();
		CacheSQL.getInstance().set(new DocumentUploadCache(path, userId, groupId, repositoryId, folderId, filePrefix));
	}

}
