package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.demoform.R;

public class LoginView extends com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginView
	implements View.OnTouchListener {

	public LoginView(Context context) {
		super(context);
	}

	public LoginView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public LoginView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			getPasswordEditText().setTransformationMethod(null);
			getPasswordEditText().setInputType(InputType.TYPE_CLASS_TEXT);

			((ImageView) v).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp));
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			getPasswordEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
			((ImageView) v).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp));
		}

		return false;
	}

	@Override
	protected void refreshLoginEditTextStyle() {
		getLoginEditText().setInputType(getBasicAuthMethod().getInputType());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		final View seePassword = findViewById(R.id.liferay_see_password);
		seePassword.setOnTouchListener(this);
	}
}