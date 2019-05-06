package com.liferay.mobile.pushnotifications.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.pushnotifications.activities.NotificationsActivity;
import com.liferay.mobile.pushnotifications.download.DownloadPicture;
import com.liferay.mobile.pushnotifications.service.v62.DLFileEntryService;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.push.AbstractPushService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class PushService extends AbstractPushService {

    public static final int NOTIFICATION_ID = 2;

    @Override
    protected void processJSONNotification(final JSONObject json) throws JSONException {
        boolean creation = json.has("newNotification") && json.getBoolean("newNotification");
        String titleHeader = (creation ? "New" : "Updated") + " notification: ";
        String title = titleHeader + getString(json, "title");
        String description = getString(json, "description");
        String photo = getString(json, "photo");

        createGlobalNotification(title, description, tryToLoadPhoto(photo));
    }

    private void createGlobalNotification(String title, String description, Bitmap bitmap) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentTitle(title)
            .setContentText(description)
            .setAutoCancel(true)
            .setSound(uri)
            .setVibrate(new long[] { 2000, 1000, 2000, 1000 })
            .setSmallIcon(R.drawable.liferay_glyph);

        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
        }

        builder.setContentIntent(createPendingIntentForNotifications());

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private PendingIntent createPendingIntentForNotifications() {
        Intent resultIntent = new Intent(this, NotificationsActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Bitmap tryToLoadPhoto(String photo) {
        if (photo != null && !photo.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(photo);
                final String uuid = getString(jsonObject, "uuid");
                final Long groupId = jsonObject.getLong("groupId");

                String username = getString(R.string.anonymous_user);
                String password = getString(R.string.anonymous_password);
                Session session = SessionContext.createBasicSession(username, password);

                JSONObject result = getFileEntry(uuid, groupId, session);

                return new DownloadPicture().createRequest(this, result, LiferayServerContext.getServer(), 100).get();
            } catch (Exception e) {
                LiferayLogger.e("Error loading picture", e);
            }
        }
        return null;
    }

    private JSONObject getFileEntry(String uuid, Long groupId, Session session) throws Exception {
        if (LiferayServerContext.isLiferay7()) {
            DLFileEntryService entryService = new DLFileEntryService(session);
            return entryService.getFileEntryByUuidAndGroupId(uuid, groupId);
        } else {

            com.liferay.mobile.pushnotifications.service.v7.DLFileEntryService entryService =
                new com.liferay.mobile.pushnotifications.service.v7.DLFileEntryService(session);
            return entryService.getFileEntryByUuidAndGroupId(uuid, groupId);
        }
    }

    private String getString(final JSONObject json, final String element) throws JSONException {
        return json.has(element) ? json.getString(element) : "";
    }
}
