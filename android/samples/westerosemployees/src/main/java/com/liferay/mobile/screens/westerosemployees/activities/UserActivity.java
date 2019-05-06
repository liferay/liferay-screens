package com.liferay.mobile.screens.westerosemployees.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.westerosemployees.R;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public class UserActivity extends WesterosActivity implements View.OnClickListener, BaseListListener<AssetEntry> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        bindViews();

        AssetListScreenlet assetListScreenlet = findViewById(R.id.news_display_screenlet);
        assetListScreenlet.loadPage(0);
    }

    private void bindViews() {
        TextView userNameTextView = findViewById(R.id.liferay_username);
        userNameTextView.setOnClickListener(this);

        UserPortraitScreenlet userPortraitScreenlet = findViewById(R.id.userscreenlet_home);
        userPortraitScreenlet.setOnClickListener(this);
        userPortraitScreenlet.loadLoggedUserPortrait();

        AssetListScreenlet assetListScreenlet = findViewById(R.id.news_display_screenlet);
        assetListScreenlet.setListener(this);

        userNameTextView.setText(SessionContext.getCurrentUser().getFullName());
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
    public void onListItemSelected(AssetEntry assetEntry, View view) {

        Intent intent = new Intent(this, ModalDetailActivity.class);
        intent.putExtra("className", assetEntry.getClassName());
        intent.putExtra("classPK", assetEntry.getClassPK());
        intent.putExtra("mimeType", assetEntry.getMimeType());
        startActivity(intent);
    }

    @Override
    public void error(Exception e, String userAction) {

    }
}
