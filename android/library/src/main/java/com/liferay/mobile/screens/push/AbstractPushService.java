package com.liferay.mobile.screens.push;

import android.app.IntentService;
import android.content.Intent;
import com.liferay.mobile.push.bus.BusUtil;
import com.liferay.mobile.push.util.GoogleServices;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class AbstractPushService extends IntentService {

    private final GoogleServices googleService = new GoogleServices();

    public AbstractPushService() {
        super(AbstractPushService.class.getSimpleName());
    }

    public void onHandleIntent(Intent intent) {
        try {
            JSONObject json = this.googleService.getPushNotification(this, intent);

            //BusUtil.post(json);

            processJSONNotification(json);

            AbstractPushReceiver.completeWakefulIntent(intent);
        } catch (Exception exception) {
            BusUtil.post(exception);
        }
    }

    protected abstract void processJSONNotification(JSONObject json) throws JSONException;
}


