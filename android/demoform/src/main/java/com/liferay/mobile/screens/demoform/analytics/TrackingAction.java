package com.liferay.mobile.screens.demoform.analytics;

import android.content.Context;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import com.google.gson.Gson;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.demoform.AccountsApp;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.content.Context.WIFI_SERVICE;

public class TrackingAction {

	public static final String SERVLET = "/";
	public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static void post(Context context, EventProperty eventProperty, Location location) {

		final long groupId = LiferayServerContext.getGroupId();

		final String date = new SimpleDateFormat(PATTERN, Locale.US).format(new Date());

		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("time", eventProperty.getTime());

		TrackingEvent trackingEvent =
			new TrackingEvent(eventProperty.getEventType(), groupId, date, additionalInfo, eventProperty);

		MessageContext messageContext = createMessageContext(context, location);
		final TrackingMessage trackingMessage = new TrackingMessage(messageContext, trackingEvent);

		final String url = LiferayServerContext.getServer() + SERVLET;

		Executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Response response = sendContent(url, trackingMessage);

					LiferayLogger.e(response.message());
					if (response.code() == 200) {

					}
				} catch (Exception e) {
					LiferayLogger.e("Error sending tracking action", e);
				}
			}
		});
	}

	private static MessageContext createMessageContext(Context context, Location location) {

		Context applicationContext = context.getApplicationContext();

		final long companyId = LiferayServerContext.getCompanyId();
		final Locale currentLocale = LiferayLocale.getDefaultLocale();
		final String localeLanguage = LiferayLocale.getSupportedLocale(currentLocale.getDisplayLanguage());
		final Long userId = SessionContext.getUserId();
		String userName = SessionContext.isLoggedIn() ? SessionContext.getCurrentUser().getFullName() : "-";
		final boolean loggedIn = SessionContext.isLoggedIn();
		String deviceId =
			Settings.Secure.getString(applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		String deviceType = Build.MODEL;

		WifiManager wm = (WifiManager) applicationContext.getSystemService(WIFI_SERVICE);
		String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

		MessageLocation messageLocation =
			location == null ? null : new MessageLocation(location.getLatitude(), location.getLongitude());

		AccountsApp accountsApp = (AccountsApp) applicationContext;
		UUID sessionId = accountsApp.getUUID();

		return new MessageContext(companyId, localeLanguage, userId, userName, loggedIn, deviceId, deviceType,
			messageLocation, ipAddress, sessionId);
	}

	private static Response sendContent(String url, TrackingMessage trackingEvent) throws IOException {

		String content = new Gson().toJson(trackingEvent);
		Log.e("TRACKING_EVENT", content);

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody requestBody = RequestBody.create(mediaType, content);
		Request request = new Request.Builder().url(url).post(requestBody).build();

		OkHttpClient okHttpClient = new OkHttpClient();
		Call call = okHttpClient.newCall(request);

		return call.execute();
	}
}
