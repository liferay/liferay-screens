package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.portlet.PortletConfiguration;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

public class ModalDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blogs_detail_subview);
		
        loadDetail(getIntent().getStringExtra("id"));

	}

	private void loadDetail(final String id){
		PortletConfiguration configuration = new PortletConfiguration.Builder("/web/guest/detail?id=" + id).addRawCss(R.raw.detail_css).addRawJs(R.raw.detail_js).load();

		PortletDisplayScreenlet portletDisplayScreenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_blog_item);
		portletDisplayScreenlet.setPortletConfiguration(configuration);
		portletDisplayScreenlet.load();

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
