package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.CordovaLifeCycleObserver;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

public class UserProfileActivity extends WesterosActivity implements View.OnClickListener {

    private CordovaLifeCycleObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_display);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        loadUserProfile();
    }

    private void loadUserProfile() {

        observer = new CordovaLifeCycleObserver();

        WebScreenlet webScreenlet = findViewById(R.id.portlet_user_profile);

        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/userprofile").enableCordova(observer)
                .addRawCss(R.raw.user_profile_portlet_css, "user_profile_portlet_css.css")
                .addRawJs(R.raw.user_profile_portlet_js, "user_profile_portlet_js.js")
                .load();

        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.load();
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

    @Override
    protected void onStart() {
        super.onStart();
        observer.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        observer.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        observer.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        observer.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        observer.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        observer.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        observer.onConfigurationChanged(newConfig);
    }
}
