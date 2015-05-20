package com.liferay.mobile.screens.bankofwesteros.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.bankofwesteros.push.IssueEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class PushActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//TODO check for play services
		//TODO check if registration id active

		new AsyncPush().execute(null, null, null);
	}

	public void onEvent(IssueEvent event) {
		LiferayCrouton.info(this, "New Issue received, reloading...");
		LiferayLogger.i("Push received: " + event.getMessage());
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBusUtil.register(this);
	}

	@Override
	protected void onPause() {
		EventBusUtil.unregister(this);
		super.onPause();
	}

	private void registerWithLiferayPortal(String register) {
		Push.with(SessionContext.createSessionFromCurrentSession()).onSuccess(new Push.OnSuccess() {

			@Override
			public void onSuccess(Object result) {
				LiferayLogger.i("Device registered with Liferay Portal: " + result);
			}

		}).onFailure(new Push.OnFailure() {

			@Override
			public void onFailure(Exception e) {
				LiferayLogger.e("Some error occurred: ", e);
			}

		}).register(register);
	}

	private class AsyncPush extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... voids) {
			try {
				final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(PushActivity.this);
				String registrationId = gcm.register(SENDER_ID);

				LiferayLogger.i("RegistrationId: " + registrationId);

				registerWithLiferayPortal(registrationId);

				return registrationId;
			}
			catch (IOException ex) {
				LiferayLogger.e("Error", ex);
			}
			return null;
		}
	}

	private static final String SENDER_ID = "324849185389";
}
