package com.liferay.mobile.screens.cache;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.v62.ddlrecord.DDLRecordService;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.service.UploadService;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitService;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.liferay.mobile.screens.cache.DefaultCachedType.DDL_RECORD;
import static com.liferay.mobile.screens.cache.DefaultCachedType.DOCUMENT_UPLOAD;
import static com.liferay.mobile.screens.cache.DefaultCachedType.USER_PORTRAIT_UPLOAD;

/**
 * @author Javier Gamarra
 */
public class CacheSyncService extends IntentService {

	public CacheSyncService() {
		super(CacheSyncService.class.getName());
	}

	//FIXME finish cache when ddl document upload error
	//TODO check differences with date instead of json content,
	//TODO and return in order,

	//FIXME review: upload portrait, upload file, save/update record,

	//TODO review iOS code,
	//TODO check documentation dev net
	//FIXME finish send/sync cache + conflict resolutions
//	https://docs.google.com/document/d/16lXoDe3M_XtYGt8CnkmYVIxH0ROVyl5Xzt9_7JCIRsE/edit#heading=h.togulq81iduv
//	Conflicts support four possible resolutions
//	Keep local version: the remote version will be overwritten with the local one. Both the local cache and the portal will have the same version (Version 2 in the example above)
//	Keep remote version: the local version will be overwritten with the remote one. Both the local cache and the portal will have the same version (Version 3 in the example above)
//	Discard: the local version will be removed and the remote one won’t be overwritten.
//		Ignore: neither local nor remote data is changed. The next synchronization process will detect the conflict again.

//	Structure Load form structure from portal Go offline Load form again: the structure should be loaded from cache
//
//	Record Load form structure and record from portal Go offline Fill the form Load form and record again Expected: the data should be loaded from cache
//
//	Offline sync Load form structure from portal Fill the form Go offline Submit offline Sync Expected: The sync should fail (error code -5) Sync again Expected: the same items should appear
//
//	Add record Load form structure from portal
//	Fill the form
//	Go offline
//	Submit offline
//	Go online
//	Sync
//	Expected: The new record should be sent to portal
//	Try again with more than 1 offline record
//
//	Update record: no conflicts
//	Load form structure and record from portal
//	Go offline
//	Update the record
//	Submit offline
//	Go online
//	Sync
//	Expected: the updated record should be sent to portal
//
//	Update record: ignored conflict
//	Load form structure and record from portal
//	Change that record in the portal (this is the remote version)
//	Go offline
//	Update the record (this is the local version)
//	Submit offline
//	Go online
//	Sync
//	Expected: the conflict should be detected
//	Choose ignore conflict
//	Expected: the changed shouldn’t be sent to the portal.
//	Expected: the items should be kept in the cache (sync again and the items should appear or load the record offline and should be loaded).
//
//	Update record: discarded conflict
//	Load form structure and record from portal
//	Change that record in the portal (this is the remote version)
//	Go offline
//	Update the record (this is the local version)
//	Submit offline
//	Go online
//	Sync
//	Expected: the conflict should be detected
//	Choose discard conflict
//	Expected: the changed shouldn’t be sent to the portal.
//	Expected: the items should be removed from the cache (sync again and it shouldn’t appear)
//
//	Update record: use local version
//	Load form structure and record from portal
//	Change that record in the portal (this is the remote version)
//	Go offline
//	Update the record (this is the local version)
//	Submit offline
//	Go online
//	Sync
//	Expected: the conflict should be detected
//	Choose “use local” version
//	Expected: the changed should be sent to the portal.
//	Expected: the items should be removed from the cache (sync again and it shouldn’t appear)
//
//	Update record: use remote version
//	Load form structure and record from portal
//	Change that record in the portal (this is the remote version)
//	Go offline
//	Update the record (this is the local version)
//	Submit offline
//	Go online
//	Sync
//	Expected: the conflict should be detected
//	Choose “use remote” version
//	Expected: the changed shouldn’t be sent to the portal.
//	Expected: the item updated-local version should be removed from the cache (sync again and it shouldn’t appear)
//	Expected: the remote version should be added to cache (load the record offline and the remote version should appear)
//
//	Offline Document & form: sync
//	Load form structure form portal (including document and media fields)
//	Go offline
//	Fill the form adding a document
//	Submit offline
//	Go online
//	Sync
//	Expected: the document should be uploaded
//	Expected: the record should be sent with the document reference attached
//
//	Offline Document & form: restore
//	Load form structure form portal (including document and media fields)
//	Go offline
//	Fill the form adding a document
//	Submit offline
//	Load the record
//	Expected: the structure should be loaded
//	Expected: the form’s data should be loaded
//	Expected: the form’s document should be loaded
//
//	Online Document & offline form: sync
//	Load form structure form portal (including document and media fields)
//	Upload a document
//	Expected: should be sent to the portal
//	Go offline
//	Fill the form
//	Submit offline
//	Go online
//	Sync
//	Expected: the record should be sent with the document reference attached
//
//	Online Document & offline form: restore
//	Load form structure form portal (including document and media fields)
//	Upload a document
//	Expected: should be sent to the portal
//	Go offline
//	Fill the form
//	Submit offline
//	Load the record
//	Expected: the structure should be loaded
//	Expected: the form’s data should be loaded
//	Expected: the form’s document should be loaded
//


	@Override
	protected void onHandleIntent(Intent intent) {
		ConnectivityManager cm =
			(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
			activeNetwork.isConnectedOrConnecting();

		if (isConnected && SessionContext.hasSession()) {
			try {
				Cache cache = CacheSQL.getInstance();
				sendPortrait(cache);
				sendDocuments(cache);
				sendRecords(cache);
			}
			catch (Exception e) {
				LiferayLogger.e("Error syncing resources", e);
			}
		}
		CacheReceiver.completeWakefulIntent(intent);
	}

	private void sendPortrait(Cache cache) {
		long userId = SessionContext.getDefaultUserId();

		List<TableCache> userPortraits = cache.get(USER_PORTRAIT_UPLOAD,
			" AND " + TableCache.DIRTY + " = 1 " +
				" AND " + TableCache.USER_ID + " = ? ",
			userId);

		for (TableCache userPortrait : userPortraits) {
			try {
				UserPortraitService userPortraitService = new UserPortraitService();
				JSONObject jsonObject = userPortraitService.uploadUserPortrait(Long.valueOf(userPortrait.getId()), userPortrait.getContent());

				userPortrait.setDirty(false);
				userPortrait.setSyncDate(new Date());
				cache.set(userPortrait);
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending portrait images", e);
			}
		}
	}

	private void sendDocuments(Cache cache) {
		long userId = SessionContext.getDefaultUserId();
		long groupId = LiferayServerContext.getGroupId();

		List<DocumentUploadCache> documentsToUpload = cache.get(DOCUMENT_UPLOAD,
			DocumentUploadCache.DIRTY + " = 1 " +
				"AND " + DocumentUploadCache.USER_ID + " = ? " +
				"AND " + DocumentUploadCache.GROUP_ID + " = ? ",
			userId,
			groupId);

		for (DocumentUploadCache document : documentsToUpload) {
			try {
				Map<String, Object> objectObjectHashMap = new HashMap<>();
				DocumentField documentField = new DocumentField(objectObjectHashMap, new Locale("es"));
				documentField.createLocalFile(document.getPath());

				UploadService uploadService = new UploadService();
				JSONObject jsonObject = uploadService.uploadFile(documentField, document.getUserId(), document.getGroupId(),
					document.getRepositoryId(), document.getFolderId(), document.getFilePrefix());

				document.setDirty(false);
				document.setSyncDate(new Date());
				cache.set(document);
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending documentsToUpload", e);
			}
		}
	}

	private void sendRecords(Cache cache) {

		Long groupId = LiferayServerContext.getGroupId();
		List<DDLRecordCache> records = getLatestRecordsToSync(cache);

		DDLRecordService recordService = new DDLRecordService(SessionContext.createSessionFromCurrentSession());

		for (DDLRecordCache cachedRecord : records) {
			try {
				Record record = cachedRecord.getRecord();
				record.setCreatorUserId(SessionContext.getLoggedUser().getId());
				final JSONObject serviceContextAttributes = new JSONObject();
				serviceContextAttributes.put("userId", record.getCreatorUserId());
				serviceContextAttributes.put("scopeGroupId", groupId);
				JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);
				JSONObject jsonContent = cachedRecord.getJSONContent();

				JSONObject jsonObject = saveOrUpdate(recordService, record, groupId, serviceContextWrapper, jsonContent);

				cachedRecord.setDirty(false);
				cachedRecord.setSyncDate(new Date());
				cache.set(cachedRecord);
			}
			catch (Exception e) {
				LiferayLogger.e("Error syncing a record", e);
			}
		}
	}

	private List<DDLRecordCache> getLatestRecordsToSync(Cache cache) {
		long groupId = LiferayServerContext.getGroupId();
		return cache.get(DDL_RECORD, DDLRecordCache.DIRTY + " = 1 AND " + TableCache.GROUP_ID + " = ? ", groupId);
	}

	private JSONObject saveOrUpdate(DDLRecordService recordService, Record record, long groupId, JSONObjectWrapper serviceContextWrapper, JSONObject jsonContent) throws Exception {
		if (record.getRecordId() == 0) {
			return recordService.addRecord(groupId, record.getRecordSetId(), 0, jsonContent, serviceContextWrapper);
		}
		else {
			return recordService.updateRecord(record.getRecordId(), 0, jsonContent, true, serviceContextWrapper);
		}
	}
}
