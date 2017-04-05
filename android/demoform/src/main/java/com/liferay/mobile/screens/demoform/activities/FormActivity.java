package com.liferay.mobile.screens.demoform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.analytics.TrackingAction;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.Map;
import org.json.JSONObject;
import rx.Subscription;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.liferay.mobile.screens.ddl.form.EventType.FORM_CANCEL;
import static com.liferay.mobile.screens.ddl.form.EventType.FORM_LEAVE;

public class FormActivity extends AppCompatActivity implements DDLFormListener {

	private Long timer = System.currentTimeMillis();
	private DDLFormScreenlet ddlFormScreenlet;
	private Subscription subscribe;
	private EventProperty lastField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		Record record = getIntent().getParcelableExtra("record");

		ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.form);
		ddlFormScreenlet.setListener(this);
		Long recordSetId = Long.valueOf((String) record.getServerValue("recordSetId"));
		ddlFormScreenlet.setRecordSetId(recordSetId);
		ddlFormScreenlet.load();
	}

	@Override
	protected void onResume() {
		super.onResume();

		subscribe = ddlFormScreenlet.getEventsObservable().subscribe(eventProperty -> {
			lastField = eventProperty;
			decorateEventAndSend(eventProperty);
			System.out.println(
				"Field: " + eventProperty.getElementName() + ", time (in millis): " + eventProperty.getTime());
		}, t -> Log.e("ERROR! :(", t.getMessage(), t));
	}

	@Override
	public void error(Exception e, String userAction) {
		LiferayLogger.e("!", e);

		Snackbar.make(ddlFormScreenlet, "Error :(", Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		LiferayLogger.e("!", e);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		LiferayLogger.d(":)");

		trackFormActions(EventType.FORM_ENTER, 0L);
	}

	private void trackFormActions(EventType eventType, Long timer) {
		trackFormActions(eventType, timer, null);
	}

	private void trackFormActions(EventType eventType, Long timer, String lastElementName) {
		Record record = ddlFormScreenlet.getRecord();
		EventProperty eventProperty =
			new EventProperty(eventType, record.getRecordSetName(), record.getRecordSetName());
		eventProperty.setTime(timer);
		eventProperty.setLastElementName(lastElementName);
		decorateEventAndSend(eventProperty);
	}

	private void decorateEventAndSend(EventProperty eventProperty) {

		Record record = ddlFormScreenlet.getRecord();

		eventProperty.setEntityId(record.getDDMStructure().getClassNameId());
		eventProperty.setEntityType(record.getDDMStructure().getClassName());
		eventProperty.setEntityName(record.getRecordSetName());

		TrackingAction.post(getApplicationContext(), eventProperty);
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		LiferayLogger.d(":)");

		trackFormActions(EventType.FORM_SUBMIT, getTimer());

		Intent intent = new Intent(this, UserActivity.class);
		intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("added", true);
		startActivity(intent);
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onBackPressed() {
		//RecyclerViewPager recyclerViewPager = ((TrackedDDLFormView) ddlFormScreenlet.getView()).getRecyclerViewPager();
		RecyclerViewPager recyclerViewPager = ddlFormScreenlet.getView();
		int currentPosition = recyclerViewPager.getCurrentPosition();
		if (currentPosition == 0) {
			super.onBackPressed();
		} else {
			recyclerViewPager.smoothScrollToPosition(currentPosition - 1);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (subscribe != null && !subscribe.isUnsubscribed()) {
			subscribe.unsubscribe();
		}

		boolean notSubmitted = ddlFormScreenlet.getRecord().getRecordId() == 0;
		if (notSubmitted) {
			trackFormActions(FORM_CANCEL, getTimer(), lastField == null ? null : lastField.getElementName());
		}
		trackFormActions(FORM_LEAVE, timer);
	}

	private long getTimer() {
		return System.currentTimeMillis() - this.timer;
	}
}
