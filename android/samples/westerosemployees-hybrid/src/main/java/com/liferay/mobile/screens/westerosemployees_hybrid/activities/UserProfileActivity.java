package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.portlet.PortletConfiguration;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

public class UserProfileActivity extends WesterosActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_display);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        loadUserProfile();
    }

    private void loadUserProfile() {
        PortletDisplayScreenlet portletDisplayScreenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_user_profile);
        PortletConfiguration configuration = new PortletConfiguration.Builder("/web/guest/userprofile").disableTheme().addRawCss(R.raw.user_profile_portlet_css).load();

        portletDisplayScreenlet.setPortletConfiguration(configuration);
        portletDisplayScreenlet.load();
    }


    @Override
    public void onClick(View v) {
        SessionContext.logout();
        SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.AUTO);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}