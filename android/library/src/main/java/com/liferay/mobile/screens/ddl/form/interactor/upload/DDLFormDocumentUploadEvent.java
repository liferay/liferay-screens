package com.liferay.mobile.screens.ddl.form.interactor.upload;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentUploadEvent extends CacheEvent {

    private DocumentField documentField;
    private Long repositoryId;
    private Long folderId;
    private String filePrefix;
    private Integer connectionTimeout;

    public DDLFormDocumentUploadEvent() {
        super();
    }

    public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId, String filePrefix,
        Integer connectionTimeout) {
        this(documentField, repositoryId, folderId, filePrefix, connectionTimeout, new JSONObject());
    }

    public DDLFormDocumentUploadEvent(DocumentField documentField, Long repositoryId, Long folderId, String filePrefix,
        Integer connectionTimeout, JSONObject jsonObject) {
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

    public void setDocumentField(DocumentField documentField) {
        this.documentField = documentField;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public DocumentField getDocumentField() {
        return documentField;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
