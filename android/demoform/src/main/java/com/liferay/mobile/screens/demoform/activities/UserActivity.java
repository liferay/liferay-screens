package com.liferay.mobile.screens.demoform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;

/**
 * @author Víctor Galán Grande
 */
public class UserActivity extends WesterosActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		bindViews();

		boolean added = getIntent().getBooleanExtra("added", false);
		if (added) {
			Snackbar.make(findViewById(android.R.id.content), "Form added!", Snackbar.LENGTH_LONG).show();
		}
	}

	private void bindViews() {
		TextView userNameTextView = (TextView) findViewById(R.id.liferay_username);
		userNameTextView.setOnClickListener(this);

		findViewById(R.id.userscreenlet_home).setOnClickListener(this);

		User currentUser = SessionContext.getCurrentUser();
		userNameTextView.setText(currentUser == null ? "" : currentUser.getFullName());
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
