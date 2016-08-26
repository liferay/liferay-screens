package com.liferay.mobile.screens.westerosemployees.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.westerosemployees.R;

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

	public SignInCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		findViewById(R.id.card1_to_front_view).setOnClickListener(this);
		findViewById(R.id.liferay_forgot_link).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.liferay_forgot_link) {
			goRight();
		} else if (v.getId() == R.id.card1_to_front_view) {
			goLeft();
		}
	}
}
