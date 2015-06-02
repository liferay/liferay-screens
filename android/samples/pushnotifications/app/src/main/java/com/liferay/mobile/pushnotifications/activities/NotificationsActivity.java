package com.liferay.mobile.pushnotifications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.pushnotifications.push.PushActivity;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class NotificationsActivity extends PushActivity implements BaseListListener<DDLEntry> {

	private DDLListScreenlet ddlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);

		if (!SessionContext.hasSession()) {
			startActivity(new Intent(this, LoginActivity.class));
		}

		new LiferayCrouton.Builder().withInfoColor(R.color.material_primary_crouton).build();

		ddlList = (DDLListScreenlet) findViewById(R.id.ddl_list_screenlet);
		ddlList.setListener(this);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet baseListScreenlet, int i, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet baseListScreenlet, int i, List<DDLEntry> list, int i1) {

	}

	@Override
	public void onListItemSelected(DDLEntry element, View view) {
		loadDDLForm(element);
	}

	private void loadDDLForm(DDLEntry element) {
		final Integer recordId = (Integer) (element.getAttributes("recordId"));
		final Integer recordSetId = (Integer) (element.getAttributes("recordSetId"));

		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback(recordId, recordSetId));

			new DDLRecordSetService(session).getRecordSet(recordSetId);
		}
		catch (Exception e) {
			LiferayLogger.e("error loading structure id", e);
		}
	}

	private JSONObjectAsyncTaskCallback getCallback(final Integer recordId, final Integer recordSetId) {
		return new JSONObjectAsyncTaskCallback() {

			@Override
			public void onSuccess(JSONObject result) {
				try {
					Intent intent = new Intent(NotificationsActivity.this, NotificationDetailActivity.class);
					intent.putExtra("recordId", recordId);
					intent.putExtra("recordSetId", recordSetId);
					intent.putExtra("structureId", result.getInt("DDMStructureId"));

					Crouton.clearCroutonsForActivity(NotificationsActivity.this);

					startActivity(intent);
				}
				catch (JSONException e) {
					LiferayLogger.e("error parsing JSON", e);
				}
			}

			@Override
			public void onFailure(Exception e) {
				LiferayLogger.e("error loading structure id", e);
			}
		};
	}

	@Override
	protected void processPushNotification(JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Crouton.clearCroutonsForActivity(NotificationsActivity.this);
				LiferayCrouton.info(NotificationsActivity.this, "Reloading list...");
				ddlList.loadPage(0);
			}
		});
	}
}
