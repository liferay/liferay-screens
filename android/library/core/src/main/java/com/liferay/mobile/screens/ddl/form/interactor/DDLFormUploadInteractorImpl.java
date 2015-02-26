package com.liferay.mobile.screens.ddl.form.interactor;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.FileField;

/**
 * @author Javier Gamarra
 */
public class DDLFormUploadInteractorImpl extends BaseRemoteInteractor<DDLFormListener>
		implements DDLFormUploadInteractor {

	public DDLFormUploadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void upload(long groupId, long userId, long repositoryId, long folderId, FileField file) throws Exception {

		Intent service = new Intent(LiferayScreensContext.getContext(), UploadService.class);
		service.putExtra("file", file);
		service.putExtra("userId", userId);
		service.putExtra("groupId", groupId);
		service.putExtra("repositoryId", repositoryId);
		service.putExtra("folderId", folderId);
		service.putExtra("screenletId", getTargetScreenletId());
		LiferayScreensContext.getContext().startService(service);
	}

	public void onEventMainThread(DDLFormFileEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormFileUploadFailed(event.getFileField(), event.getException());
		} else {
			getListener().onDDLFormFileUploaded(event.getFileField());
		}
	}

}
