package com.liferay.mobile.screens.westerosemployees.activities;

import android.os.Bundle;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingListener;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class UserActivity extends WesterosActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SessionContext.createBasicSession("test@liferay.com","test");
		setContentView(R.layout.user);
	}
}
