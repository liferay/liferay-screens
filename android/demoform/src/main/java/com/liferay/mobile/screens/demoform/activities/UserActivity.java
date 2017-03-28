package com.liferay.mobile.screens.demoform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.demoform.R;

/**
 * @author Víctor Galán Grande
 */
public class UserActivity extends WesterosActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);

		bindViews();
	}

	private void bindViews() {
		TextView userNameTextView = (TextView) findViewById(R.id.liferay_username);
		userNameTextView.setOnClickListener(this);

		findViewById(R.id.userscreenlet_home).setOnClickListener(this);

		userNameTextView.setText(SessionContext.getCurrentUser().getFullName());
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, UserProfileActivity.class));
	}
}
