package com.liferay.mobile.screens.demoform.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Map;
import org.json.JSONObject;
import rx.functions.Action1;

public class MainActivity2 extends AppCompatActivity implements DDLFormListener {

	private DDLFormScreenlet ddlFormScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddl_form_screenlet);
		ddlFormScreenlet.setListener(this);
		ddlFormScreenlet.load();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ddlFormScreenlet.getEventsObservable().subscribe(new Action1<Object[]>() {
			@Override
			public void call(Object[] array) {
				System.out.println("Field: " + ((Field) array[0]).getLabel() + ", time (in millis): " + array[1]);
			}
		});
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
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		LiferayLogger.d(":)");

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
		super.onBackPressed();
	}
}
