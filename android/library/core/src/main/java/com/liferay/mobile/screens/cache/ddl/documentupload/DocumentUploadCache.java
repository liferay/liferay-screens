package com.liferay.mobile.screens.cache.ddl.documentupload;

import com.liferay.mobile.screens.cache.CachedContent;
import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;

/**
 * @author Javier Gamarra
 */
@StorIOSQLiteType(table = DocumentUploadCache.TABLE_NAME)
public class DocumentUploadCache implements CachedContent {

	public static final String TABLE_NAME = "document";
	public static final String PATH = "path";
	public static final String USER_ID = "userId";
	public static final String GROUP_ID = "groupId";
	public static final String REPOSITORY_ID = "repositoryId";
	public static final String FOLDER_ID = "folderId";
	public static final String FILE_PREFIX = "filePrefix";
	public static final String DIRTY = "dirty";
	public static final String SYNC_DATE = "sync_date";

	public DocumentUploadCache() {
		super();
	}

	public DocumentUploadCache(String path, long userId, long groupId, long repositoryId, long folderId, String filePrefix) {
		_path = path;
		_userId = userId;
		_groupId = groupId;
		_repositoryId = repositoryId;
		_folderId = folderId;
		_filePrefix = filePrefix;
		_dirty = 1;
	}

	public void setDirty(boolean dirty) {
		_dirty = (dirty ? 1 : 0);
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public String getFilePrefix() {
		return _filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		_filePrefix = filePrefix;
	}

	@Override
	public CachedType getCachedType() {
		return DefaultCachedType.DOCUMENT_UPLOAD;
	}

	@Override
	public String getId() {
		return _path;
	}

	@Override
	public TableCache getTableCache() {
		return new TableCache(_path, DefaultCachedType.DOCUMENT_UPLOAD, _path);
	}

	public Date getSyncDate() {
		return new Date(_syncDate);
	}

	public void setSyncDate(Date syncDate) {
		_syncDate = syncDate.getTime();
	}

	@StorIOSQLiteColumn(name = PATH, key = true)
	String _path;
	@StorIOSQLiteColumn(name = USER_ID)
	long _userId;
	@StorIOSQLiteColumn(name = GROUP_ID)
	long _groupId;
	@StorIOSQLiteColumn(name = REPOSITORY_ID)
	long _repositoryId;
	@StorIOSQLiteColumn(name = FOLDER_ID)
	long _folderId;
	@StorIOSQLiteColumn(name = FILE_PREFIX)
	String _filePrefix;
	@StorIOSQLiteColumn(name = DIRTY)
	int _dirty;
	@StorIOSQLiteColumn(name = SYNC_DATE)
	Long _syncDate;
}
