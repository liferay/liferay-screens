package com.liferay.mobile.pushnotifications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.Map;
import org.json.JSONObject;

public class NotificationDetailActivity extends AppCompatActivity implements DDLFormListener {

    private DDLFormScreenlet ddlFormScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        if (!SessionContext.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        ddlFormScreenlet = findViewById(R.id.ddl_form_screenlet);
        ddlFormScreenlet.setRecordId(getIntent().getLongExtra("recordId", 0));
        ddlFormScreenlet.setListener(this);
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onDDLFormLoaded(Record record) {

    }

    @Override
    public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {

    }

    @Override
    public void onDDLFormRecordAdded(Record record) {

    }

    @Override
    public void onDDLFormRecordUpdated(Record record) {

        Snackbar.make(findViewById(android.R.id.content), "Notification updated", Snackbar.LENGTH_SHORT).show();

        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

    }

    @Override
    public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

    }
}
