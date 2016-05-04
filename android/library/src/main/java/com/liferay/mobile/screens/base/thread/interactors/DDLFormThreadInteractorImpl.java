package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.IdCache;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.interactor.formload.DDLFormLoadCallback;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormThreadInteractorImpl extends BaseCachedThreadRemoteInteractor<DDLFormListener, DDLFormThreadEvent> {

	public DDLFormThreadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public DDLFormThreadEvent execute(Object[] args) throws Exception {

		Record record = (Record) args[0];

		JSONObject jsonObject = getDDMStructureService(record).getStructure(record.getStructureId());
		record.parseDDMStructure(jsonObject);

		if (record.getCreatorUserId() == 0) {
			long userId = jsonObject.getLong("userId");
			record.setCreatorUserId(userId);
		}

		validate(record);

		return new DDLFormThreadEvent(jsonObject, record);
	}

	@Override
	public void onFailure(BasicThreadEvent event) {
		getListener().onDDLFormLoadFailed(event.getException());
	}

	@Override
	public void onSuccess(DDLFormThreadEvent event) {
		getListener().onDDLFormLoaded(event.getRecord());
	}

	@Override
	protected IdCache getCachedContent(Object[] args) {
		final Record record = (Record) args[0];
		return new IdCacheImpl(String.valueOf(record.getStructureId()), null, 0, null);
	}

	@Override
	protected Class<DDLFormThreadEvent> getEventClass() {
		return DDLFormThreadEvent.class;
	}

	protected DDMStructureConnector getDDMStructureService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new DDLFormLoadCallback(getTargetScreenletId(), record));
		return ServiceProvider.getInstance().getDDMStructureConnector(session);
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be empty");
		}

		if (record.getStructureId() <= 0) {
			throw new IllegalArgumentException("Record's structureId cannot be 0 or negative");
		}

		if (record.getLocale() == null) {
			throw new IllegalArgumentException("Record's Locale cannot be empty");
		}
	}

}
