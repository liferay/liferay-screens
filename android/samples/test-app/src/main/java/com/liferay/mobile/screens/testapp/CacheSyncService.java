package com.liferay.mobile.screens.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liferay.mobile.screens.cache.Cache;
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

		UserPortraitUploadInteractorImpl userPortraitUploadInteractor = new UserPortraitUploadInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (TableCache userPortrait : userPortraits) {

			try {
				userPortraitUploadInteractor.upload(Long.valueOf(userPortrait.getId()), userPortrait.getContent());
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

		DDLFormDocumentUploadInteractorImpl ddlFormDocumentUploadInteractor = new DDLFormDocumentUploadInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (DocumentUploadCache document : documentsToUpload) {

			Map<String, Object> objectObjectHashMap = new HashMap<>();
			DocumentField documentField = new DocumentField(objectObjectHashMap, new Locale("es"));
			documentField.createLocalFile(document.getPath());
			try {
				ddlFormDocumentUploadInteractor.upload(document.getGroupId(), document.getUserId(), document.getRepositoryId(),
					document.getFolderId(), document.getFilePrefix(), documentField);
			}
			catch (Exception e) {
				LiferayLogger.e("Error sending documentsToUpload", e);
			}
		}
	}

	private void sendRecords(Cache cache) {

		long groupId = LiferayServerContext.getGroupId();
		List<RecordCache> records = cache.get(DDL_RECORD, DDLRecordCache.SENT + " = 0 AND " + TableCache.GROUP_ID + " = ? ", groupId);

		DDLFormAddRecordInteractorImpl ddlFormAddRecordInteractor = new DDLFormAddRecordInteractorImpl(0, OfflinePolicy.STORE_ON_ERROR);

		for (RecordCache cachedRecord : records) {

			Record record = cachedRecord.getRecord();
			record.setCreatorUserId(SessionContext.getLoggedUser().getId());
			try {
				ddlFormAddRecordInteractor.sendOnline(LiferayServerContext.getGroupId(), cachedRecord.getContent(), record);
			}
			catch (Exception e) {
				LiferayLogger.e("Error syncing a record", e);
			}
		}
	}
}
