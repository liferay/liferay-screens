package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.OfflineCallback;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
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
		extends BaseCachedRemoteInteractor<DDLFormListener, DDLFormUpdateRecordEvent>
		implements DDLFormDocumentUploadInteractor {

	public DDLFormDocumentUploadInteractorImpl(int targetScreenletId, CachePolicy cachePolicy, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, cachePolicy, offlinePolicy);
	}

	@Override
	public void upload(final long groupId, final long userId, final long repositoryId, final long folderId,
					   final String filePrefix, final DocumentField file) throws Exception {

		storeOnError(new OfflineCallback() {
			@Override
			public void sendOnline() throws Exception {
				Intent service = new Intent(LiferayScreensContext.getContext(), UploadService.class);
				service.putExtra("file", file);
				service.putExtra("userId", userId);
				service.putExtra("groupId", groupId);
				service.putExtra("repositoryId", (repositoryId != 0) ? repositoryId : groupId);
				service.putExtra("folderId", folderId);
				service.putExtra("screenletId", getTargetScreenletId());
				service.putExtra("filePrefix", filePrefix);

				LiferayScreensContext.getContext().startService(service);
			}

			@Override
			public void storeToCache() {
				String path = file.getCurrentValue().toString();
				Cache cache = CacheSQL.getInstance();
				cache.set(new DocumentUploadCache(path, userId, groupId, repositoryId, folderId, filePrefix));
			}
		});
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

}
