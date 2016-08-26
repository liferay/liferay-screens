package com.liferay.mobile.screens.cache;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.comment.add.interactor.CommentAddInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractorImpl;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.update.DDLFormUpdateRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.rating.interactor.delete.RatingDeleteInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.update.RatingUpdateInteractorImpl;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadEvent;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractorImpl;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import java.util.Locale;

import static com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet.DELETE_COMMENT_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;

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
			try {

				Context context = LiferayScreensContext.getContext();
				DB snappyDB = DBFactory.open(context);

				sync(snappyDB, DDLFormEvent.class, new SyncConsumer() {
					@Override
					public void sync(OfflineEventNew event) throws Exception {
						BaseCachedWriteThreadRemoteInteractor interactor =
							((DDLFormEvent) event).getRecord().getRecordId() == 0 ? new DDLFormAddRecordInteractorImpl()
								: new DDLFormUpdateRecordInteractorImpl();
						interactor.execute(event);
					}
				});

				sync(snappyDB, UserPortraitUploadEvent.class, new SyncConsumer() {
					@Override
					public void sync(OfflineEventNew event) throws Exception {
						UserPortraitUploadInteractorImpl interactor = new UserPortraitUploadInteractorImpl();
						interactor.online((UserPortraitUploadEvent) event);
					}
				});

				sync(snappyDB, DDLFormDocumentUploadEvent.class, new SyncConsumer() {
					@Override
					public void sync(OfflineEventNew event) throws Exception {
						BaseCachedWriteThreadRemoteInteractor interactor = new DDLFormDocumentUploadInteractorImpl();
						interactor.execute(event);
					}
				});

				sync(snappyDB, CommentEvent.class, new SyncConsumer() {
					@Override
					public void sync(OfflineEventNew event) throws Exception {
						CommentEvent commentEvent = (CommentEvent) event;
						BaseCachedWriteThreadRemoteInteractor interactor = getCommentInteractor(commentEvent);
						interactor.execute(commentEvent);
					}

					@NonNull
					private BaseCachedWriteThreadRemoteInteractor getCommentInteractor(CommentEvent commentEvent) {
						if (DELETE_COMMENT_ACTION.equals(commentEvent.getActionName())) {
							return new CommentDeleteInteractorImpl();
						} else if (commentEvent.getCommentId() == 0) {
							return new CommentAddInteractorImpl();
						} else {
							return new CommentUpdateInteractorImpl();
						}
					}
				});

				sync(snappyDB, RatingEvent.class, new SyncConsumer() {
					@Override
					public void sync(OfflineEventNew event) throws Exception {
						BaseCachedWriteThreadRemoteInteractor interactor =
							DELETE_RATING_ACTION.equals(event.getActionName()) ? new RatingDeleteInteractorImpl()
								: new RatingUpdateInteractorImpl();
						interactor.execute(event);
					}
				});

				snappyDB.close();
			} catch (Exception e) {
				LiferayLogger.e("Error syncing resources", e);
			}
		}
		CacheReceiver.completeWakefulIntent(intent);
	}

	private void sync(DB snappyDB, Class clasz, SyncConsumer syncConsumer) throws Exception {
		String simpleName = getFullId(clasz);
		String[] keys = snappyDB.findKeys(simpleName);

		for (String key : keys) {
			OfflineEventNew event = (OfflineEventNew) snappyDB.getObject(key, clasz);
			if (event.isDirty()) {
				syncConsumer.sync(event);
				event.setDirty(false);
				snappyDB.put(key, event);
			}
		}
	}

	@NonNull
	private String getFullId(Class clasz) {
		long groupId = LiferayServerContext.getGroupId();
		Long userId = SessionContext.getUserId();
		Locale locale = LiferayLocale.getDefaultLocale();
		return clasz.getSimpleName() + "_" + groupId + "_" + userId + "_" + locale;
	}

	private interface SyncConsumer {

		void sync(OfflineEventNew event) throws Exception;
	}
}
