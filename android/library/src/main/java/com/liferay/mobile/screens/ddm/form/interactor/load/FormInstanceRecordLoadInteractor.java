package com.liferay.mobile.screens.ddm.form.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddm.form.FormListener;
import com.liferay.mobile.screens.ddm.form.FormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordLoadInteractor
    extends BaseCacheReadInteractor<FormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(Object... args) throws Exception {
        long formInstanceRecordId = (long) args[0];

        FormInstanceRecordConnector connector =
            ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord formInstanceRecord = connector.getFormInstanceRecord(
            formInstanceRecordId);

        FormInstanceRecordEvent event = new FormInstanceRecordEvent(formInstanceRecord);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceRecordLoaded(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), FormScreenlet.LOAD_RECORD_ACTION);
        }
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return args[0].toString();
    }
}
