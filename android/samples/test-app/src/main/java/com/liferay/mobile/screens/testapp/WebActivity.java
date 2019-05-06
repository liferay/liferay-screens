package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.CordovaLifeCycleObserver;

/**
 * @author Sarai Díaz García
 */
public class WebActivity extends ThemeActivity implements WebListener {

    private CordovaLifeCycleObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        observer = new CordovaLifeCycleObserver();

        WebScreenlet screenlet = findViewById(R.id.web_screenlet);

        if (getIntent().getStringExtra("url") != null) {
            WebScreenletConfiguration webScreenletConfiguration =
                new WebScreenletConfiguration.Builder(getIntent().getStringExtra("url")).addLocalCss("gallery.css")
                    .addLocalJs("gallery.js")
                    .enableCordova(observer)
                    .load();

            screenlet.setWebScreenletConfiguration(webScreenletConfiguration);
            screenlet.setScrollEnabled(true);
            screenlet.setListener(this);
            screenlet.load();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        observer.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        observer.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();

        observer.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        observer.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        observer.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        observer.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        observer.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        observer.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        observer.onConfigurationChanged(newConfig);
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.portlet_display_error), e);
    }

    @Override
    public void onPageLoaded(String url) {
        info(getString(R.string.portlet_display_success));
    }

    @Override
    public void onScriptMessageHandler(String namespace, String body) {
        if ("gallery".equals(namespace)) {
            String[] allImgSrc = body.split(",");
            int imgSrcPosition = Integer.parseInt(allImgSrc[allImgSrc.length - 1]);

            Intent intent = new Intent(getApplicationContext(), DetailMediaGalleryActivity.class);
            intent.putExtra("allImgSrc", allImgSrc);
            intent.putExtra("imgSrcPosition", imgSrcPosition);
            startActivity(intent);
        }
    }
}
