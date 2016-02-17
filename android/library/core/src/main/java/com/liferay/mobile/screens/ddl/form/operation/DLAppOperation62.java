package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlapp.DLAppService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DLAppOperation62 implements DLAppOperation {
	public DLAppOperation62(Session session) {
		_dlAppService = new DLAppService(session);
	}

	@Override
	public JSONObject addFileEntry(Long repositoryId, Long folderId, String name, String mimeType, String fileName, String s, String s1, byte[] bytes, JSONObjectWrapper serviceContextWrapper) throws Exception {
		return _dlAppService.addFileEntry(repositoryId, folderId, name, mimeType, fileName, s, s1, bytes, serviceContextWrapper);
	}

	private final DLAppService _dlAppService;
}
