package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.ddl.form.util.FormConstants;
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
    private ModalProgressBarWithLabel modalProgress;

    private long formInstanceId = 36205;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddm_form);

        screenlet = findViewById(R.id.ddm_form_screenlet);
        modalProgress = findViewById(R.id.liferay_modal_progress);

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
        String serverUrl = "http://10.0.2.2:8080";
        return serverUrl + String.format(FormConstants.URL_TEMPLATE, formInstanceId);
    }

    private void loadResource() {
        String url = getResourcePath();

        modalProgress.show("Loading Form");
        screenlet.setVisibility(View.GONE);
        screenlet.load(url, Detail.INSTANCE, null, onLoadCompleted, onError);
    }

    private Function1<ThingScreenlet, Unit> onLoadCompleted = new Function1<ThingScreenlet, Unit>() {
        @Override
        public Unit invoke(ThingScreenlet thingScreenlet) {
            modalProgress.hide();
            screenlet.setVisibility(View.VISIBLE);

            return Unit.INSTANCE;
        }
    };

    private Function1<Exception, Unit> onError = new Function1<Exception, Unit>() {
        @Override
        public Unit invoke(Exception e) {
            modalProgress.hide();
            screenlet.setVisibility(View.VISIBLE);

            return Unit.INSTANCE;
        }
    };
}
