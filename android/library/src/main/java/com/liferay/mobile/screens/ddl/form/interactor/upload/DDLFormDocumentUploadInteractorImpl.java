package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Intent;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.DocumentField;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<DDLFormListener, DDLFormDocumentUploadEvent> {

	@Override
	protected void online(DDLFormDocumentUploadEvent event) throws Exception {

		Intent service = new Intent(LiferayScreensContext.getContext(), UploadService.class);
		service.putExtra("file", event.getDocumentField());
		service.putExtra("userId", userId);
		service.putExtra("folderId", event.getFolderId());
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("filePrefix", event.getFilePrefix());

		LiferayScreensContext.getContext().startService(service);
	}

	@Override
	public DDLFormDocumentUploadEvent execute(DDLFormDocumentUploadEvent event) throws Exception {
		throw new AssertionError("Should not be called");
	}

	@Override
	protected DDLFormDocumentUploadEvent createEvent(Object[] args) throws Exception {

		DocumentField documentField = (DocumentField) args[0];
		long repositoryId = args[1] == null ? groupId : (long) args[1];
		long folderId = (long) args[2];
		String filePrefix = (String) args[3];

		return new DDLFormDocumentUploadEvent(documentField, repositoryId, folderId, filePrefix);
	}

	@Override
	public void onSuccess(DDLFormDocumentUploadEvent event) throws Exception {
		getListener().onDDLFormDocumentUploaded(event.getDocumentField(), event.getJSONObject());
	}

	@Override
	public void onFailure(DDLFormDocumentUploadEvent event) {
		getListener().onDDLFormDocumentUploadFailed(event.getDocumentField(), event.getException());
	}
}
