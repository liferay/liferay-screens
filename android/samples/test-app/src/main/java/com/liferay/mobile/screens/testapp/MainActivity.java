package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
				startActivity(new Intent(this, DDLFormActivity.class));
				break;
			case R.id.ddl_list:
				startActivity(new Intent(this, DDLListActivity.class));
				break;
			case R.id.asset_list:
				startActivity(new Intent(this, AssetListActivity.class));
				break;
			case R.id.sign_up:
				startActivity(new Intent(this, SignUpActivity.class));
				break;
			case R.id.forgot_password:
				startActivity(new Intent(this, ForgotPasswordActivity.class));
				break;
			case R.id.user_portrait:
				startActivity(new Intent(this, UserPortraitActivity.class));
				break;
			case R.id.web_view:
				startActivity(new Intent(this, WebViewActivity.class));
				break;
			default:
				startActivity(new Intent(this, LoginActivity.class));
		}
	}
}
