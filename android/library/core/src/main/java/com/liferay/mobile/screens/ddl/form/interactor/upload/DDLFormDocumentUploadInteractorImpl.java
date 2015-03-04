package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.DocumentField;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadInteractorImpl extends BaseRemoteInteractor<DDLFormListener>
		implements DDLFormDocumentUploadInteractor {

	public DDLFormDocumentUploadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void upload(long groupId, long userId, long repositoryId, long folderId, String filePrefix, DocumentField file) {

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

	public void onEventMainThread(DDLFormDocumentUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormDocumentUploadFailed(event.getDocumentField(), event.getException());
		} else {
			getListener().onDDLFormDocumentUploaded(event.getDocumentField(), event.getJSONObject());
		}
	}

}
