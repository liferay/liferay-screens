package com.liferay.mobile.screens.testapp.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.viewsets.defaultviews.userportrait.UserPortraitView;

/**
 * @author Sarai Díaz García
 */

public class UserPortraitInitialsView extends UserPortraitView {

	private TextView textView;

	public UserPortraitInitialsView(Context context) {
		super(context);
	}

	public UserPortraitInitialsView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitInitialsView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		textView = (TextView) findViewById(R.id.user_portrait_initials_text_view);
	}

	@Override
	public void showPlaceholder(User user) {
		portraitImage.setImageBitmap(null);

		String fullName = user.getFirstName().substring(0, 1) + user.getLastName().substring(0, 1);
		textView.setText(fullName.toUpperCase());
	}
}
