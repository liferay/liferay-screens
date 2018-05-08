package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;

/**
 * @author Paulo Cruz
 */
public class DDMFormActivity extends ThemeActivity {

    private ThingScreenlet screenlet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddm_form);

        screenlet = findViewById(R.id.ddm_form_screenlet);

        initScreenletFromIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        //screenlet.load();
    }

    private void initScreenletFromIntent(Intent intent) {
        //if (intent.hasExtra("formInstanceId")) {
        //    screenlet.setFormInstanceId(intent.getLongExtra("formInstanceId", 0));
        //}
    }

}
