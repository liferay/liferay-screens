package com.liferay.mobile.screens.bankofwesteros.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LiferayLogger.e("Push Received: " + intent.toString());

        String message = intent.getExtras().getString("message");
        EventBusUtil.post(new IssueEvent(message));

        setResultCode(Activity.RESULT_OK);
    }
}