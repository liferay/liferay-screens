package com.liferay.mobile.screens.westerosemployees.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.westerosemployees.R;

public class UserProfileAcivity extends WesterosActivity implements View.OnClickListener {

	private TextView userNameText;
	private TextView jobTitleText;
	private TextView emailText;
	private TextView screenNameText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);

		bindViews();
		setUserValues();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void bindViews() {
		userNameText = (TextView) findViewById(R.id.user_name_text);
		jobTitleText = (TextView) findViewById(R.id.user_job_title_text);
		emailText = (TextView) findViewById(R.id.user_email_text);
		screenNameText = (TextView) findViewById(R.id.user_screen_name_text);

		findViewById(R.id.sign_out_button).setOnClickListener(this);
	}

	private void setUserValues() {
		User currentUser = SessionContext.getCurrentUser();

		userNameText.setText(currentUser.getFullName());
		jobTitleText.setText(currentUser.getJobTitle());
		emailText.setText(currentUser.getEmail());
		screenNameText.setText(currentUser.getScreenName());
	}
}
