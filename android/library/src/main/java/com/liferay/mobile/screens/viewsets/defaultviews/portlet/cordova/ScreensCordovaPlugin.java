package com.liferay.mobile.screens.viewsets.defaultviews.portlet.cordova;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.liferay.mobile.screens.util.EventBusUtil;
import org.apache.cordova.CordovaPlugin;

/**
 * @author Víctor Galán Grande
 */

public class ScreensCordovaPlugin extends CordovaPlugin {

	@Override
	public Object onMessage(String id, Object data) {
		if (id.equals("onPageFinished")) {
			EventBusUtil.post(new CordovaEvent(CordovaEvent.CordovaEventType.PAGE_FINISHED, ""));
		}
		else if(id.equals("onPageStarted")) {
			EventBusUtil.post(new CordovaEvent(CordovaEvent.CordovaEventType.PAGE_STARTED, ""));
		}

		return null;
	}
}