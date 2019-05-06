package com.liferay.mobile.pushnotifications.notification;

import androidx.annotation.NonNull;
import com.liferay.mobile.screens.push.AbstractPushReceiver;

/**
 * @author Javier Gamarra
 */
public class PushReceiver extends AbstractPushReceiver {
    @NonNull
    @Override
    protected Class getPushServiceClass() {
        return PushService.class;
    }
}
