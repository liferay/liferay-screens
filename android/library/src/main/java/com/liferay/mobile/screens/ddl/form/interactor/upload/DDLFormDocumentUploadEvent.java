package com.liferay.mobile.screens.ddl.form.interactor.upload;

import com.liferay.mobile.screens.base.interactor.event.CachedEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadEvent extends CachedEvent {

	private DocumentField documentField;
	private Long repositoryId;
	private Long folderId;
	private String filePrefix;

	public DDLFormDocumentUploadEvent() {
		super();
	}

	public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId,
		String filePrefix) {
		this(documentField, repositoryId, folderId, filePrefix, new JSONObject());
	}

	public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId, String filePrefix,
		JSONObject jsonObject) {
		super(jsonObject);
		this.documentField = documentField;
		this.repositoryId = repositoryId;
		this.folderId = folderId;
		this.filePrefix = filePrefix;
	}

	public DDLFormDocumentUploadEvent(Exception e) {
		super(e);
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public Long getFolderId() {
		return folderId;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public DocumentField getDocumentField() {
		return documentField;
	}
}