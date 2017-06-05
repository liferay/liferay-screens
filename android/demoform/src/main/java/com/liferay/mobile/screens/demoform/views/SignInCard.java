package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.demoform.R;

/**
 * @author Víctor Galán Grande
 */
public class SignInCard extends Card implements View.OnClickListener {

	public SignInCard(Context context) {
		super(context);
	}

	public SignInCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SignInCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		findViewById(R.id.liferay_forgot_link).setOnClickListener(this);
		((TextView) findViewById(R.id.liferay_login)).setText(getResources().getString(R.string.liferay_login));
		((TextView) findViewById(R.id.liferay_password)).setText(getResources().getString(R.string.liferay_password));
	}

	@Override
	public void onClick(View v) {
		goRight();
	}
}
