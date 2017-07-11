package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import com.liferay.mobile.screens.westerosemployees_hybrid.views.Card;

public class ModalDetailActivity extends AppCompatActivity {

	private Card commentAddCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String className = getIntent().getStringExtra("className");
		long classPK = getIntent().getLongExtra("classPK", 0);
		String mimeType = getIntent().getStringExtra("mimeType");

		findViews(className, mimeType);

	}

	private void findViews(String className, String mimeType) {


		commentAddCard = (Card) findViewById(R.id.comment_add_card);
	}

	private void hideSoftKeyBoard() {
		Activity activity = LiferayScreensContext.getActivityFromContext(this);
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (imm.isAcceptingText()) {
			IBinder windowToken = activity.getCurrentFocus().getWindowToken();

			if (windowToken != null) {
				imm.hideSoftInputFromWindow(windowToken, 0);
			}
		}
	}
}
