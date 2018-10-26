package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import java.util.List;

public class UserActivity extends WesterosActivity
    implements View.OnClickListener, BaseListListener<AssetEntry>, WebListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        bindViews();
    }

    private void bindViews() {
        TextView userNameTextView = findViewById(R.id.liferay_username);
        userNameTextView.setOnClickListener(this);

        UserPortraitScreenlet userPortraitScreenlet = findViewById(R.id.userscreenlet_home);
        userPortraitScreenlet.setOnClickListener(this);
        userPortraitScreenlet.loadLoggedUserPortrait();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLastChanges();
            }
        }, 1000);

        userNameTextView.setText(SessionContext.getCurrentUser().getFullName());
    }

    private void loadLastChanges() {
        WebScreenlet webScreenlet = findViewById(R.id.portlet_last_changes);

        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/lastchanges").addRawCss(
                R.raw.last_changes_portlet_css, "last_changes_portlet_css.css")
                .addRawJs(R.raw.last_changes_portlet_js, "last_changes_portlet_js.js")
                .load();

        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.setListener(this);
        webScreenlet.load();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, UserProfileActivity.class));
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {

    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(AssetEntry element, View view) {

    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onPageLoaded(String url) {

    }

    @Override
    public void onScriptMessageHandler(String namespace, final String body) {
        if ("last-changes-item".equals(namespace)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(UserActivity.this, ModalDetailActivity.class);
                    intent.putExtra("id", body);
                    intent.putExtra("latest", true);
                    startActivity(intent);
                }
            });
        }
    }
}
