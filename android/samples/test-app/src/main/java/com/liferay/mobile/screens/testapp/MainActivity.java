package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;

/**
 * @author Silvio Santos
 */
public class MainActivity extends Activity
		implements View.OnClickListener {

	private Integer currentTheme;

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		currentTheme = getIntent().getIntExtra("theme", DefaultTheme.DEFAULT_THEME);
		setTheme(currentTheme);
		setContentView(R.layout.activity_main);

		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.asset_list).setOnClickListener(this);
		findViewById(R.id.ddl_form).setOnClickListener(this);
		findViewById(R.id.ddl_list).setOnClickListener(this);
		findViewById(R.id.sign_up).setOnClickListener(this);
		findViewById(R.id.forgot_password).setOnClickListener(this);
		findViewById(R.id.user_portrait).setOnClickListener(this);
		findViewById(R.id.web_view).setOnClickListener(this);
		findViewById(R.id.change_theme).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ddl_form:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(DDLFormActivity.class));
				break;
			case R.id.ddl_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(DDLListActivity.class));
				break;
			case R.id.asset_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(AssetListActivity
						.class));
				break;
			case R.id.sign_up:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(SignUpActivity.class));
				break;
			case R.id.forgot_password:
				DefaultAnimation.startActivityWithAnimation(this,
						getIntent(ForgotPasswordActivity.class));
				break;
			case R.id.user_portrait:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(UserPortraitActivity
						.class));
				break;
			case R.id.web_view:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(WebViewActivity.class));
				break;
			case R.id.change_theme:
				Integer theme = currentTheme == R.style.default_theme ? R.style.material_theme
						: R.style.default_theme;
				finish();
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("theme", theme);
				startActivity(intent);
				break;
			default:
				DefaultAnimation.startActivityWithAnimation(this, getIntent(LoginActivity.class));
		}
	}

	private Intent getIntent(Class destinationClass) {
		Intent intent = new Intent(this, destinationClass);
		intent.putExtra("defaultTheme", currentTheme == R.style.default_theme);
		return intent;
	}


}
