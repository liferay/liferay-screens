package com.liferay.mobile.screens.demoform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Map;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DDLFormScreenlet ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddl_form_screenlet);
		ddlFormScreenlet.setListener(this);
	}

	@Override
	public void error(Exception e, String userAction) {
		LiferayLogger.e("!", e);
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
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		LiferayLogger.d(":)");
	}
}
