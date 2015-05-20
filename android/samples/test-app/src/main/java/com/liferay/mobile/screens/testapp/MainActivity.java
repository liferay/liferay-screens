package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.testapp.fullview.LoginFullActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

/**
 * @author Silvio Santos
 */
public class MainActivity extends ThemeActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_main);

		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.asset_list).setOnClickListener(this);
		findViewById(R.id.ddl_form).setOnClickListener(this);
		findViewById(R.id.ddl_list).setOnClickListener(this);
		findViewById(R.id.sign_up).setOnClickListener(this);
		findViewById(R.id.forgot_password).setOnClickListener(this);
		findViewById(R.id.user_portrait).setOnClickListener(this);
		findViewById(R.id.web_view).setOnClickListener(this);
		findViewById(R.id.add_bookmark).setOnClickListener(this);
		findViewById(R.id.login_full_screenlet).setOnClickListener(this);
		findViewById(R.id.change_theme).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ddl_form:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(DDLFormActivity.class));
				break;
			case R.id.ddl_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(DDLListActivity.class));
				break;
			case R.id.asset_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(SelectAssetActivity.class));
				break;
			case R.id.sign_up:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(SignUpActivity.class));
				break;
			case R.id.forgot_password:
				DefaultAnimation.startActivityWithAnimation(this,
						getIntentWithTheme(ForgotPasswordActivity.class));
				break;
			case R.id.user_portrait:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(UserPortraitActivity.class));
				break;
			case R.id.web_view:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(WebViewActivity.class));
				break;
			case R.id.add_bookmark:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(AddBookmarkActivity.class));
				break;
			case R.id.login_full_screenlet:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(LoginFullActivity.class));
				break;
			case R.id.change_theme:
				finish();
				currentTheme = isDefaultTheme() ? R.style.material_theme : R.style.default_theme;
				startActivity(getIntentWithTheme(MainActivity.class));
				break;
			default:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(LoginActivity.class));
		}
	}

}
