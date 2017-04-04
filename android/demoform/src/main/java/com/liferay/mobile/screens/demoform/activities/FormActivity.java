package com.liferay.mobile.screens.demoform.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.AccountsApp;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.analytics.TrackingAction;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.Map;
import org.json.JSONObject;
import rx.Subscription;

import static com.liferay.mobile.screens.ddl.form.EventType.FORM_CANCEL;
import static com.liferay.mobile.screens.ddl.form.EventType.FORM_LEAVE;

public class FormActivity extends AppCompatActivity implements DDLFormListener {

	private Long timer = System.currentTimeMillis();
	private DDLFormScreenlet ddlFormScreenlet;
	private Subscription subscribe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		Record record = getIntent().getParcelableExtra("record");

		ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.form);
		ddlFormScreenlet.setListener(this);
		Long recordSetId = Long.valueOf((String) record.getServerValue("recordSetId"));
		ddlFormScreenlet.setRecordSetId(recordSetId);
		Long structureId = Long.valueOf((String) record.getServerValue("structureId"));
		ddlFormScreenlet.setStructureId(structureId);
		ddlFormScreenlet.load();

		AccountsApp accountsApp = (AccountsApp) getApplicationContext();
		accountsApp.setCurrentRecord(ddlFormScreenlet.getRecord());
	}

	@Override
	protected void onResume() {
		super.onResume();

		subscribe = ddlFormScreenlet.getEventsObservable().subscribe(eventProperty -> {
			trackForm(eventProperty);
			System.out.println("Field: " + eventProperty.getName() + ", time (in millis): " + eventProperty.getTime());
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

		trackForm(EventType.FORM_ENTER, 0L);
	}

	private void trackForm(EventType eventType, Long timer) {
		DDMStructure ddmStructure = ddlFormScreenlet.getRecord().getDDMStructure();
		EventProperty newEventProperty = new EventProperty(eventType, ddmStructure.getName(), timer);
		trackForm(newEventProperty);
	}

	private void trackForm(EventProperty eventProperty) {

		Location location = ((AccountsApp) getApplicationContext()).getLocation();
		DDMStructure ddmStructure = ddlFormScreenlet.getRecord().getDDMStructure();
		eventProperty.setEntityId(ddmStructure.getClassNameId());
		eventProperty.setEntityType(ddmStructure.getClassPK());

		TrackingAction.post(getApplicationContext(), eventProperty, location);
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		LiferayLogger.d(":)");

		long timer = this.timer - System.currentTimeMillis();
		trackForm(EventType.FORM_SUBMIT, timer);

		Snackbar.make(ddlFormScreenlet, "Form added!", Snackbar.LENGTH_LONG).show();
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

		long timer = this.timer - System.currentTimeMillis();
		boolean notSubmitted = ddlFormScreenlet.getRecord().getRecordId() == 0;
		if (notSubmitted) {
			trackForm(FORM_CANCEL, timer);
		}
		trackForm(FORM_LEAVE, timer);
	}
}
