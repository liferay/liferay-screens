/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.mobile.screens.cache;

import android.app.IntentService;
import android.content.Intent;
import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.comment.add.interactor.CommentAddInteractor;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.update.DDLFormUpdateRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadInteractor;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.rating.interactor.delete.RatingDeleteInteractor;
import com.liferay.mobile.screens.rating.interactor.update.RatingUpdateInteractor;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadEvent;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractor;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Date;
import java.util.Locale;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;
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
        if (AndroidUtil.isConnected(getApplicationContext())
            && SessionContext.isLoggedIn()
            && SessionContext.getCurrentUser() != null) {

            sync(DDLFormEvent.class, new SyncProvider<DDLFormEvent>() {
                @Override
                public DDLFormEvent getCacheEvent(DDLFormEvent event) throws Exception {
                    Record record = event.getRecord();
                    BaseCacheWriteInteractor interactor = record.getRecordId() == 0 ? new DDLFormAddRecordInteractor()
                        : new DDLFormUpdateRecordInteractor();
                    event = (DDLFormEvent) interactor.execute(event);
                    event.setCacheKey(record.getStructureId() + SEPARATOR + record.getRecordId());
                    return event;
                }
            });

            sync(CommentEvent.class, new SyncProvider<CommentEvent>() {
                @Override
                public CommentEvent getCacheEvent(CommentEvent event) throws Exception {
                    BaseCacheWriteInteractor interactor = getCommentInteractor(event);
                    event = (CommentEvent) interactor.execute(event);
                    event.setCacheKey(String.valueOf(event.getCommentId()));
                    event.setDeleted(DELETE_COMMENT_ACTION.equals(event.getActionName()));
                    return event;
                }

                private BaseCacheWriteInteractor getCommentInteractor(CommentEvent commentEvent) {
                    if (DELETE_COMMENT_ACTION.equals(commentEvent.getActionName())) {
                        return new CommentDeleteInteractor();
                    } else if (commentEvent.getCommentId() == 0) {
                        return new CommentAddInteractor();
                    } else {
                        return new CommentUpdateInteractor();
                    }
                }
            });

            sync(RatingEvent.class, new SyncProvider<RatingEvent>() {
                @Override
                public RatingEvent getCacheEvent(RatingEvent event) throws Exception {
                    BaseCacheWriteInteractor interactor =
                        DELETE_RATING_ACTION.equals(event.getActionName()) ? new RatingDeleteInteractor()
                            : new RatingUpdateInteractor();
                    event = (RatingEvent) interactor.execute(event);
                    event.setCacheKey(event.getClassName() + SEPARATOR + event.getClassPK());
                    event.setDeleted(DELETE_RATING_ACTION.equals(event.getActionName()));
                    return event;
                }
            });

            sync(UserPortraitUploadEvent.class, new SyncProvider<UserPortraitUploadEvent>() {

                final UserPortraitUploadInteractor interactor = new UserPortraitUploadInteractor();

                @Override
                public UserPortraitUploadEvent getCacheEvent(UserPortraitUploadEvent event) throws Exception {
                    EventBusUtil.register(this);
                    interactor.execute(event);
                    return null;
                }

                public void onEventMainThread(UserPortraitUploadEvent event) {
                    //event.setCacheKey(String.valueOf(event.getUserId()));
                    storeEvent(event);
                }
            });

            sync(DDLFormDocumentUploadEvent.class, new SyncProvider<DDLFormDocumentUploadEvent>() {
                @Override
                public DDLFormDocumentUploadEvent getCacheEvent(DDLFormDocumentUploadEvent event) throws Exception {
                    DDLFormDocumentUploadInteractor interactor = new DDLFormDocumentUploadInteractor();
                    EventBusUtil.register(this);
                    interactor.execute(event);
                    return null;
                }

                public void onEventMainThread(DDLFormDocumentUploadEvent event) {
                    //event.setCacheKey(event.getDocumentField());
                    storeEvent(event);
                }
            });
        }
        CacheReceiver.completeWakefulIntent(intent);
    }

    private void sync(Class aClass, SyncProvider syncProvider) {
        Long groupId = LiferayServerContext.getGroupId();
        Long userId = SessionContext.getUserId();
        Locale locale = LiferayLocale.getDefaultLocale();

        try {
            String[] keys = Cache.findKeys(aClass, groupId, userId, locale, 0, Integer.MAX_VALUE);
            for (String key : keys) {

                CacheEvent event = Cache.getObject(aClass, groupId, userId, key);
                if (event.isDirty()) {
                    event = syncProvider.getCacheEvent(event);
                    if (event != null) {
                        event.setLocale(locale);
                        event.setDirty(false);
                        event.setSyncDate(new Date());
                        if (event.isDeleted()) {
                            Cache.deleteObject(event);
                        } else {
                            Cache.storeObject(event);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LiferayLogger.e("Error syncing " + aClass.getSimpleName() + " resources", e);
        }
    }

    private void storeEvent(CacheEvent event) {
        try {
            event.setDirty(false);
            event.setSyncDate(new Date());
            Cache.storeObject(event);
        } catch (Exception e) {
            LiferayLogger.e("Error syncing " + event.getClass().getSimpleName() + " resources", e);
        }
    }

    private interface SyncProvider<E extends CacheEvent> {

        E getCacheEvent(E event) throws Exception;
    }
}
