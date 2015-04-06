package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.bankofwesteros.views.WesterosCrouton;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AccountSettings extends Activity implements View.OnClickListener, UserPortraitListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_settings);

		findViewById(R.id.arrow_back_to_issues).setOnClickListener(this);
		findViewById(R.id.account_settings_save).setOnClickListener(this);
		findViewById(R.id.replace_portrait_image).setOnClickListener(this);

		User user = SessionContext.getLoggedUser();
		_firstName = (EditText) findViewById(R.id.first_name);
		_firstName.setText(user.getFirstName());
		_lastName = (EditText) findViewById(R.id.last_name);
		_lastName.setText(user.getLastName());
		_emailAddress = (EditText) findViewById(R.id.email_address);
		_emailAddress.setText(user.getEmail());
		_password = (EditText) findViewById(R.id.password);
		_password.setText(SessionContext.getAuthentication().getPassword());

		_userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.userportrait);
		_userPortraitScreenlet.setListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.account_settings_save:
				saveUser();
				break;
			case R.id.arrow_back_to_issues:
				finish();
				break;
			case R.id.replace_portrait_image:
				_userPortraitScreenlet.updatePortraitImage();

				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			_userPortraitScreenlet.upload(data);
		}
	}


	private void saveUser() {
		String firstName = _firstName.getText().toString();
		String lastName = _lastName.getText().toString();
		String emailAddress = _emailAddress.getText().toString();
		String newPassword = _password.getText().toString();

		if (_password.getText().toString().isEmpty()) {
			setError(_password);
			return;
		}

		if (SessionContext.getAuthentication().getPassword().equals(_password.getText().toString())) {
			setError(_password);
			WesterosCrouton.error(this, "Password should be different", null);
			return;
		}

		saveUser(firstName, lastName, emailAddress, newPassword);


	}

	private void saveUser(String firstName, String lastName, final String emailAddress, final String newPassword) {
		Session sessionFromCurrentSession = SessionContext.createSessionFromCurrentSession();
		sessionFromCurrentSession.setCallback(new JSONObjectAsyncTaskCallback() {
			@Override
			public void onSuccess(JSONObject result) {

				SessionContext.createSession(emailAddress, newPassword);

				clearError(_password);
				WesterosCrouton.info(AccountSettings.this, "User updated");
			}

			@Override
			public void onFailure(Exception exception) {
				LiferayLogger.e("error", exception);
				WesterosCrouton.error(AccountSettings.this, "Error updating user", exception);
			}
		});

		User user = SessionContext.getLoggedUser();

		UserService userService = new UserService(sessionFromCurrentSession);
		try {
			JSONArray array = new JSONArray();


			userService.updateUser(user.getInt("userId"), "test2", newPassword, newPassword, false,
				user.getString("reminderQueryQuestion"), user.getString("reminderQueryAnswer"),
				user.getString("screenName"), emailAddress, user.getInt("facebookId"),
				user.getString("openId"), user.getString("languageId"), "", user.getString("greeting"),
				user.getString("comments"), firstName, user.getString("middleName"),
				lastName, 0, 0, true, 1, 1, 1900, user.getString("emailAddress"),
				"", "", "", "", "", "", "", "", "", user.getString("jobTitle"),
				array, array, array, array, array, null);
		}
		catch (Exception e) {
			LiferayLogger.e("Error updating user", e);
			WesterosCrouton.error(this, "Error updating user", e);
		}
	}


	private void setError(EditText editText) {
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_warning, 0);
		editText.setBackground(getDrawable(R.drawable.westeros_warning_edit_text_white_drawable));
	}

	private void clearError(EditText editText) {
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		editText.setBackground(getDrawable(R.drawable.westeros_edit_text_selector));
	}

	private EditText _firstName;
	private EditText _lastName;
	private EditText _emailAddress;
	private EditText _password;

	private UserPortraitScreenlet _userPortraitScreenlet;

	@Override
	public Bitmap onUserPortraitLoadReceived(UserPortraitScreenlet source, Bitmap bitmap) {
		return null;
	}

	@Override
	public void onUserPortraitLoadFailure(UserPortraitScreenlet source, Exception e) {

	}

	@Override
	public void onUserPortraitUploaded(UserPortraitScreenlet source) {
		WesterosCrouton.info(AccountSettings.this, "Portrait updated");
	}

	@Override
	public void onUserPortraitUploadFailure(UserPortraitScreenlet source, Exception e) {
		WesterosCrouton.error(AccountSettings.this, "Error updating portrait", e);
	}

}
