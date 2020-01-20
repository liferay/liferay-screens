package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayActivity extends ThemeActivity
    implements AssetDisplayListener, AssetDisplayInnerScreenletListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_display);

        AssetDisplayScreenlet screenlet = findViewById(R.id.asset_display_screenlet);

        long entryId = getIntent().getLongExtra("entryId", 0);
        String portletItemName = getIntent().getStringExtra("portletItemName");

        if (entryId != 0) {
            screenlet.setEntryId(entryId);
        }

        if (portletItemName != null && !portletItemName.equals("")) {
            screenlet.setPortletItemName(portletItemName);
        }

        screenlet.loadAsset();
        screenlet.setListener(this);
        screenlet.setConfigurationListener(this);
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.asset_error), e);
    }

    @Override
    public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
        info(getString(R.string.asset_received_info) + " " + assetEntry.getTitle());
    }

    @Override
    public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
        AssetEntry assetEntry) {
        if ("blogsEntry".equals(assetEntry.getObjectType())) {
            innerScreenlet.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_westeros));
        }
        info(getString(R.string.configure_screenlet_info) + assetEntry.getTitle());
    }

    @Override
    public View onRenderCustomAsset(AssetEntry assetEntry) {
        if (assetEntry instanceof User) {
            View view = getLayoutInflater().inflate(R.layout.user_display, null);
            User user = (User) assetEntry;

            TextView greeting = view.findViewById(R.id.liferay_user_greeting);
            UserPortraitScreenlet userPortraitScreenlet = view.findViewById(R.id.user_portrait_screenlet);
            TextView name = view.findViewById(R.id.liferay_user_name);
            TextView jobTitle = view.findViewById(R.id.liferay_user_jobtitle);
            TextView email = view.findViewById(R.id.liferay_user_email);
            TextView nickname = view.findViewById(R.id.liferay_user_nickname);

            greeting.setText(user.getGreeting());

            userPortraitScreenlet.setUserId(user.getId());
            userPortraitScreenlet.load();

            name.setText(user.getFirstName() + " " + user.getLastName());
            jobTitle.setText(user.getJobTitle());
            email.setText(user.getEmail());
            nickname.setText(user.getScreenName());

            return view;
        }
        return null;
    }
}
