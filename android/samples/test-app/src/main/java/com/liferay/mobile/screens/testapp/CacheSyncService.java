package com.liferay.mobile.screens.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.ddl.form.RecordCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadInteractorImpl;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractorImpl;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		if (isConnected) {

			try {
				Cache cache = CacheSQL.getInstance();
				sendDocuments(cache);
				sendRecords(cache);
				sendPortrait(cache);
			}
			catch (Exception e) {
				LiferayLogger.e("Error syncing resources", e);
			}
		}
		CacheReceiver.completeWakefulIntent(intent);
	}

	private void sendPortrait(Cache cache) {
		long userId = SessionContext.getLoggedUser() == null ? 0 : SessionContext.getLoggedUser().getId();
		List<TableCache> documents = cache.get(DefaultCachedType.USER_PORTRAIT_UPLOAD, TableCache.SENT + " = 0 AND " + TableCache.USER_ID + " = ? ", userId);

		UserPortraitUploadInteractorImpl userPortraitUploadInteractor = new UserPortraitUploadInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (TableCache documentUpload : documents) {

			try {
				userPortraitUploadInteractor.upload(Long.valueOf(documentUpload.getId()), documentUpload.getContent());
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending portrait images", e);
			}

		}
	}


	private void sendDocuments(Cache cache) {
		long userId = SessionContext.getLoggedUser() == null ? 0 : SessionContext.getLoggedUser().getId();
		long groupId = LiferayServerContext.getGroupId();
		List<DocumentUploadCache> documents = cache.get(DefaultCachedType.DOCUMENT_UPLOAD, DocumentUploadCache.SENT + " = 0 AND " + TableCache.USER_ID + " = ? AND " + TableCache.GROUP_ID + " = ? ", userId, groupId);

		DDLFormDocumentUploadInteractorImpl ddlFormDocumentUploadInteractor = new DDLFormDocumentUploadInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (DocumentUploadCache documentUpload : documents) {
			Map<String, Object> objectObjectHashMap = new HashMap<>();
			DocumentField documentField = new DocumentField(objectObjectHashMap, new Locale("es"));
			documentField.createLocalFile(documentUpload.getPath());
			try {
				ddlFormDocumentUploadInteractor.upload(documentUpload.getGroupId(), documentUpload.getUserId(), documentUpload.getRepositoryId(),
					documentUpload.getFolderId(), documentUpload.getFilePrefix(), documentField);
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending documents", e);
			}
		}
	}

	private void sendRecords(Cache cache) throws Exception {
		long groupId = LiferayServerContext.getGroupId();
		List<RecordCache> caches = cache.get(DefaultCachedType.DDL_RECORD, DDLRecordCache.SENT + " = 0 AND " + TableCache.GROUP_ID + " = ? ", groupId);

		DDLFormAddRecordInteractorImpl ddlFormAddRecordInteractor = new DDLFormAddRecordInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (RecordCache recordCache : caches) {
			Record record = recordCache.getRecord();
			record.setCreatorUserId(SessionContext.getLoggedUser().getId());
			ddlFormAddRecordInteractor.sendOnline(LiferayServerContext.getGroupId(), recordCache.getContent(), record);
		}
	}
}
