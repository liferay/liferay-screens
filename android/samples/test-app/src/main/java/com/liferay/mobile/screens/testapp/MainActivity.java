package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

/**
 * @author Silvio Santos
 */
public class MainActivity extends Activity
		implements View.OnClickListener {

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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ddl_form:
				DefaultAnimation.startActivityWithAnimation(this, DDLFormActivity.class);
				break;
			case R.id.ddl_list:
				DefaultAnimation.startActivityWithAnimation(this, DDLListActivity.class);
				break;
			case R.id.asset_list:
				DefaultAnimation.startActivityWithAnimation(this, AssetListActivity.class);
				break;
			case R.id.sign_up:
				DefaultAnimation.startActivityWithAnimation(this, SignUpActivity.class);
				break;
			case R.id.forgot_password:
				DefaultAnimation.startActivityWithAnimation(this, ForgotPasswordActivity.class);
				break;
			case R.id.user_portrait:
				DefaultAnimation.startActivityWithAnimation(this, UserPortraitActivity.class);
				break;
			case R.id.web_view:
				DefaultAnimation.startActivityWithAnimation(this, WebViewActivity.class);
				break;
			default:
				DefaultAnimation.startActivityWithAnimation(this, LoginActivity.class);
		}
	}


}
