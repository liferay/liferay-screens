package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

/**
 * @author Paulo Cruz
 */
public class DDMFormActivity extends ThemeActivity implements DDMFormListener {

    private DDMFormScreenlet screenlet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddm_form);

        screenlet = findViewById(R.id.ddm_form_screenlet);
        screenlet.setListener(this);

        initScreenletFromIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        screenlet.load();
    }

    @Override
    public void onFormContextEvaluated(FormContext formContext) {

    }

    @Override
    public void onFormInstanceLoaded(FormInstance formInstance) {

    }

    @Override
    public void onFormInstanceRecordLoaded(FormInstanceRecord formInstanceRecord) {

    }

    @Override
    public void onFormInstanceRecordAdded(FormInstanceRecord formInstanceRecord) {

    }

    @Override
    public void onFormInstanceRecordUpdated(FormInstanceRecord formInstanceRecord) {

    }

    @Override
    public void error(Exception e, String userAction) {

    }

    private void initScreenletFromIntent(Intent intent) {
        if (intent.hasExtra("formInstanceId")) {
            screenlet.setFormInstanceId(intent.getLongExtra("formInstanceId", 0));
        }
    }

}
