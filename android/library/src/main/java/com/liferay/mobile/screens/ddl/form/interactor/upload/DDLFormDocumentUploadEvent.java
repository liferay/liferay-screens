package com.liferay.mobile.screens.ddl.form.interactor.upload;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadEvent extends OfflineEventNew {

	public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId,
		String filePrefix) {
		this(documentField, repositoryId, folderId, filePrefix, new JSONObject());
	}

	public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId, String filePrefix,
		JSONObject jsonObject) {
		super(jsonObject);
		_documentField = documentField;
		_repositoryId = repositoryId;
		_folderId = folderId;
		_filePrefix = filePrefix;
	}

	public Long getRepositoryId() {
		return _repositoryId;
	}

	public Long getFolderId() {
		return _folderId;
	}

	public String getFilePrefix() {
		return _filePrefix;
	}

	public DocumentField getDocumentField() {
		return _documentField;
	}

	private DocumentField _documentField;
	private Long _repositoryId;
	private Long _folderId;
	private String _filePrefix;

}