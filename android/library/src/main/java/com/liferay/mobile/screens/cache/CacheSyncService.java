package com.liferay.mobile.screens.cache;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class CacheSyncService extends IntentService {

	public CacheSyncService() {
		super(CacheSyncService.class.getName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ConnectivityManager cm =
			(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

		if (isConnected && SessionContext.isLoggedIn() && SessionContext.getCurrentUser() != null) {
			try {
				//FIXME !
				//Cache cache = CacheSQL.getInstance();
				//sendPortrait(cache);
				//sendDocuments(cache);
				//sendRecords(cache);
			} catch (Exception e) {
				LiferayLogger.e("Error syncing resources", e);
			}
		}
		CacheReceiver.completeWakefulIntent(intent);
	}

	//private void sendPortrait(Cache cache) {
	//	Long userId = SessionContext.getUserId();
	//
	//	List<TableCache> userPortraits = cache.get(USER_PORTRAIT_UPLOAD,
	//		" AND " + TableCache.DIRTY + " = 1 " +
	//			" AND " + TableCache.USER_ID + " = ? ",
	//		userId);
	//
	//	for (TableCache userPortrait : userPortraits) {
	//		try {
	//			UserPortraitService userPortraitService = new UserPortraitService();
	//			JSONObject jsonObject = userPortraitService.uploadUserPortrait(Long.valueOf(userPortrait.getId()), userPortrait.getContent());
	//			LiferayLogger.i(jsonObject.toString());
	//
	//			userPortrait.setDirty(false);
	//			userPortrait.setSyncDate(new Date());
	//			cache.set(userPortrait);
	//		}
	//		catch (Exception e) {
	//			LiferayLogger.e("Error sending portrait images", e);
	//		}
	//	}
	//}
	//
	//private void sendDocuments(Cache cache) {
	//	Long userId = SessionContext.getUserId();
	//	long groupId = LiferayServerContext.getGroupId();
	//
	//	List<DocumentUploadCache> documentsToUpload = cache.get(DOCUMENT_UPLOAD,
	//		DocumentUploadCache.DIRTY + " = 1 " +
	//			"AND " + DocumentUploadCache.USER_ID + " = ? " +
	//			"AND " + DocumentUploadCache.GROUP_ID + " = ? ",
	//		userId,
	//		groupId);
	//
	//	for (DocumentUploadCache document : documentsToUpload) {
	//		try {
	//			Map<String, Object> objectObjectHashMap = new HashMap<>();
	//			DocumentField documentField = new DocumentField(objectObjectHashMap, LiferayLocale.getDefaultLocale(), Locale.US);
	//			documentField.createLocalFile(document.getPath());
	//
	//			UploadService uploadService = new UploadService();
	//			JSONObject jsonObject = uploadService.uploadFile(documentField, document.getUserId(), document.getGroupId(),
	//				document.getRepositoryId(), document.getFolderId(), document.getFilePrefix());
	//			LiferayLogger.i(jsonObject.toString());
	//
	//			document.setDirty(false);
	//			document.setSyncDate(new Date());
	//			cache.set(document);
	//		}
	//		catch (Exception e) {
	//			LiferayLogger.e("Error sending documentsToUpload", e);
	//		}
	//	}
	//}
	//
	//private void sendRecords(Cache cache) {
	//
	//	Long groupId = LiferayServerContext.getGroupId();
	//	List<DDLRecordCache> records = getLatestRecordsToSync(cache);
	//
	//	DDLRecordConnector recordService = ServiceProvider.getInstance().getDDLRecordConnector(SessionContext.createSessionFromCurrentSession());
	//
	//	for (DDLRecordCache cachedRecord : records) {
	//		try {
	//			Record record = cachedRecord.getRecord();
	//			record.setCreatorUserId(SessionContext.getCurrentUser().getId());
	//			final JSONObject serviceContextAttributes = new JSONObject();
	//			serviceContextAttributes.put("userId", record.getCreatorUserId());
	//			serviceContextAttributes.put("scopeGroupId", groupId);
	//			JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);
	//			JSONObject jsonContent = cachedRecord.getJSONContent();
	//
	//			if (jsonContent.has("modelValues")) {
	//				jsonContent = (JSONObject) jsonContent.get("modelValues");
	//			}
	//
	//			JSONObject jsonObject = saveOrUpdate(recordService, record, groupId, serviceContextWrapper, jsonContent);
	//			LiferayLogger.i(jsonObject.toString());
	//
	//			cachedRecord.setDirty(false);
	//			cachedRecord.setSyncDate(new Date());
	//			cache.set(cachedRecord);
	//		}
	//		catch (Exception e) {
	//			LiferayLogger.e("Error syncing a record", e);
	//		}
	//	}
	//}
	//
	//private List<DDLRecordCache> getLatestRecordsToSync(Cache cache) {
	//	long groupId = LiferayServerContext.getGroupId();
	//	return cache.get(DDL_RECORD, DDLRecordCache.DIRTY + " = 1 AND " + TableCache.GROUP_ID + " = ? ", groupId);
	//}
	//
	//private JSONObject saveOrUpdate(DDLRecordConnector recordService, Record record, long groupId, JSONObjectWrapper serviceContextWrapper, JSONObject jsonContent) throws Exception {
	//	if (record.getRecordId() == 0) {
	//		return recordService.addRecord(groupId, record.getRecordSetId(), 0, jsonContent, serviceContextWrapper);
	//	}
	//	else {
	//		return recordService.updateRecord(record.getRecordId(), 0, jsonContent, true, serviceContextWrapper);
	//	}
	//}
}
