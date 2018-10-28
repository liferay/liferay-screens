package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

public class ModalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogs_detail_subview);

        boolean isLatestChanges = getIntent().getBooleanExtra("latest", false);

        if (isLatestChanges) {
            TextView textView = findViewById(R.id.blogs_detail_title);
            textView.setText(R.string.latest_changes);
        }

        loadDetail(getIntent().getStringExtra("id"));
    }

    private void loadDetail(final String id) {
        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/detail?id=" + id).addRawCss(R.raw.detail_css,
                "detail_css.css").addRawJs(R.raw.detail_js, "detail_js.js").load();

        WebScreenlet webScreenlet = findViewById(R.id.portlet_blog_item);
        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.load();
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
