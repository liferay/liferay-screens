package com.liferay.mobile.pushnotifications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONObject;

public class NotificationDetailActivity extends AppCompatActivity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_detail);

		if (!SessionContext.isLoggedIn()) {
			startActivity(new Intent(this, LoginActivity.class));
		}

		new LiferayCrouton.Builder().withInfoColor(R.color.material_primary_crouton).build();

		ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddl_form_screenlet);
		ddlFormScreenlet.setRecordId(getIntent().getIntExtra("recordId", 0));
		ddlFormScreenlet.setListener(this);

	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {

	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		LiferayCrouton.info(this, "Notification updated");

		Intent intent = new Intent(this, NotificationsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			ddlFormScreenlet.startUploadByPosition(requestCode);
		}
	}
	private DDLFormScreenlet ddlFormScreenlet;
}
