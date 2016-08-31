package com.liferay.mobile.screens.cache;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Javier Gamarra
 */
public class CacheSyncService extends IntentService {

	public CacheSyncService() {
		super(CacheSyncService.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		ConnectivityManager cm =
			(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

		if (isConnected && SessionContext.isLoggedIn() && SessionContext.getCurrentUser() != null) {

			//Long groupId = LiferayServerContext.getGroupId();
			//Long userId = SessionContext.getUserId();

			//try {
			//	DB snappyDB = Cache.openDatabase(groupId, userId);
			//
			//	String simpleName = getFullId(DDLFormEvent.class);
			//	String[] keys = snappyDB.findKeys(simpleName);
			//
			//	for (String key : keys) {
			//		DDLFormEvent event = snappyDB.getObject(key, DDLFormEvent.class);
			//		if (event.isDirty()) {
			//			Record record = event.getRecord();
			//			BaseCachedWriteThreadRemoteInteractor interactor =
			//				record.getRecordId() == 0 ? new DDLFormAddRecordInteractorImpl()
			//					: new DDLFormUpdateRecordInteractorImpl();
			//			event = (DDLFormEvent) interactor.execute(event);
			//
			//			key += record.getStructureId() + SEPARATOR + record.getRecordId();
			//			event.setDirty(false);
			//			event.setSyncDate(new Date());
			//			snappyDB.put(key, event);
			//		}
			//	}
			//} catch (Exception e) {
			//	LiferayLogger.e("Error syncing " + DDLFormEvent.class.getSimpleName() + " resources", e);
			//}

			//sync(snappyDB, DDLFormEvent.class, new SyncConsumer() {
			//	@Override
			//	public void sync(OfflineEventNew event) throws Exception {
			//		BaseCachedWriteThreadRemoteInteractor interactor =
			//			((DDLFormEvent) event).getRecord().getRecordId() == 0 ? new DDLFormAddRecordInteractorImpl()
			//				: new DDLFormUpdateRecordInteractorImpl();
			//		interactor.execute(event);
			//	}
			//});
			//
			//sync(snappyDB, UserPortraitUploadEvent.class, new SyncConsumer() {
			//	@Override
			//	public void sync(OfflineEventNew event) throws Exception {
			//		UserPortraitUploadInteractorImpl interactor = new UserPortraitUploadInteractorImpl();
			//		interactor.online((UserPortraitUploadEvent) event);
			//	}
			//});
			//
			//sync(snappyDB, DDLFormDocumentUploadEvent.class, new SyncConsumer() {
			//	@Override
			//	public void sync(OfflineEventNew event) throws Exception {
			//		BaseCachedWriteThreadRemoteInteractor interactor = new DDLFormDocumentUploadInteractorImpl();
			//		interactor.execute(event);
			//	}
			//});
			//
			//sync(snappyDB, CommentEvent.class, new SyncConsumer() {
			//	@Override
			//	public void sync(OfflineEventNew event) throws Exception {
			//		CommentEvent commentEvent = (CommentEvent) event;
			//		BaseCachedWriteThreadRemoteInteractor interactor = getCommentInteractor(commentEvent);
			//		interactor.execute(commentEvent);
			//	}
			//
			//	@NonNull
			//	private BaseCachedWriteThreadRemoteInteractor getCommentInteractor(CommentEvent commentEvent) {
			//		if (DELETE_COMMENT_ACTION.equals(commentEvent.getActionName())) {
			//			return new CommentDeleteInteractorImpl();
			//		} else if (commentEvent.getCommentId() == 0) {
			//			return new CommentAddInteractorImpl();
			//		} else {
			//			return new CommentUpdateInteractorImpl();
			//		}
			//	}
			//});
			//
			//sync(snappyDB, RatingEvent.class, new SyncConsumer() {
			//	@Override
			//	public void sync(OfflineEventNew event) throws Exception {
			//		BaseCachedWriteThreadRemoteInteractor interactor =
			//			DELETE_RATING_ACTION.equals(event.getActionName()) ? new RatingDeleteInteractorImpl()
			//				: new RatingUpdateInteractorImpl();
			//		interactor.execute(event);
			//	}
			//});
			//
			//snappyDB.close();
		}
		CacheReceiver.completeWakefulIntent(intent);
	}

	//private void sync(DB snappyDB, Class aClass, SyncConsumer syncConsumer) {
	//	try {
	//		String simpleName = getFullId(aClass);
	//		String[] keys = snappyDB.findKeys(simpleName);
	//
	//		for (String key : keys) {
	//			OfflineEventNew event = (OfflineEventNew) snappyDB.getObject(key, aClass);
	//			if (event.isDirty()) {
	//				syncConsumer.sync(event);
	//				event.setDirty(false);
	//				event.setSyncDate(new Date());
	//				snappyDB.put(key, event);
	//			}
	//		}
	//	} catch (Exception e) {
	//		LiferayLogger.e("Error syncing " + aClass.getSimpleName() + " resources", e);
	//	}
	//}
}
