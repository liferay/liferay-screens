package com.liferay.mobile.screens.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.v62.ddlrecord.DDLRecordService;
import com.liferay.mobile.screens.cache.Cache;
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
			" AND " + TableCache.SENT + " = 0 " + " AND " + TableCache.USER_ID + " = ? ",
			userId);

		for (TableCache userPortrait : userPortraits) {
			try {
				UserPortraitService userPortraitService = new UserPortraitService();
				JSONObject jsonObject = userPortraitService.uploadUserPortrait(Long.valueOf(userPortrait.getId()), userPortrait.getContent());

				userPortrait.setSent(true);
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
			DocumentUploadCache.SENT + " = 0 " +
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

				document.setSent(true);
				cache.set(document);
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending documentsToUpload", e);
			}
		}
	}

	private void sendRecords(Cache cache) {

		long groupId = LiferayServerContext.getGroupId();
		List<DDLRecordCache> records = cache.get(DDL_RECORD, DDLRecordCache.SENT + " = 0 AND " + TableCache.GROUP_ID + " = ? ", groupId);

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

				cachedRecord.setSent(true);
				cache.set(cachedRecord);
			}
			catch (Exception e) {
				LiferayLogger.e("Error syncing a record", e);
			}
		}
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
