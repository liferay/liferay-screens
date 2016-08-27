package com.liferay.mobile.screens.ddl.form.interactor.upload;

import android.content.Context;
import android.content.Intent;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.service.UploadService;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<DDLFormListener, DDLFormDocumentUploadEvent> {

	@Override
	protected void online(DDLFormDocumentUploadEvent event) throws Exception {

		Context context = LiferayScreensContext.getContext();

		Intent service = new Intent(context, UploadService.class);
		service.putExtra("file", event.getDocumentField());
		service.putExtra("userId", event.getUserId());
		service.putExtra("folderId", event.getFolderId());
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("filePrefix", event.getFilePrefix());

		context.startService(service);
	}

	@Override
	public DDLFormDocumentUploadEvent execute(DDLFormDocumentUploadEvent event) throws Exception {
		throw new AssertionError("Should not be called");
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
