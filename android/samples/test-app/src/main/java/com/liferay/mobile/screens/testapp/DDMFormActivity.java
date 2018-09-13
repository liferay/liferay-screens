package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.liferay.apio.consumer.ApioConsumerKt;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * @author Paulo Cruz
 */
public class DDMFormActivity extends ThemeActivity {

    public static final String FORM_INSTANCE_ID_KEY = "formInstanceId";

    private ThingScreenlet screenlet;
    private ProgressBar progressBar;

    private long formInstanceId = 36465;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddm_form);

        progressBar = findViewById(R.id.form_progress_bar);
        screenlet = findViewById(R.id.ddm_form_screenlet);

        if (savedInstanceState == null) {
            loadResource();
        }
    }

    private void initScreenletFromIntent(Intent intent) {
        if (intent.hasExtra(FORM_INSTANCE_ID_KEY)) {
            formInstanceId = intent.getLongExtra(FORM_INSTANCE_ID_KEY, 0);
        }
    }

    private String getResourcePath() {
        String serverUrl = getResources().getString(R.string.liferay_server);
        String formEndpoint = "/o/api/p/form-instance/%d?embedded=structure";

        return serverUrl + String.format(formEndpoint, formInstanceId);
    }

    private void loadResource() {
        String url = getResourcePath();

        progressBar.setVisibility(View.VISIBLE);
        screenlet.setVisibility(View.GONE);

        screenlet.load(url, Detail.INSTANCE, ApioConsumerKt.getCredentials(), onLoadCompleted);
    }

    private Function1<ThingScreenlet, Unit> onLoadCompleted =
        new Function1<ThingScreenlet, Unit>() {
            @Override
            public Unit invoke(ThingScreenlet thingScreenlet) {
                progressBar.setVisibility(View.GONE);
                screenlet.setVisibility(View.VISIBLE);

                return null;
            }
        };

}
