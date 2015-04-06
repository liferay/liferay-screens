package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.bankofwesteros.views.WesterosCrouton;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class PushActivity extends Activity {

	//FIXME this is wrong

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//TODO check for play services
		//TODO check if registration id active

		registerInBackground();
	}

	public void onEvent(IssueEvent event) {
		WesterosCrouton.info(this, "New Issue received, reloading...");
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

	private void registerInBackground() {
		final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] params) {
				try {

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

		}.execute(null, null, null);
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

	private String SENDER_ID = "324849185389";

}
