package com.liferay.mobile.screens.ddm.form.interactor.add;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.FormListener;
import com.liferay.mobile.screens.ddm.form.FormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordAddInteractor
    extends BaseCacheWriteInteractor<FormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(FormInstanceRecordEvent event) throws Exception {
        FormInstanceRecordConnector connector =
            ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord formInstanceRecord = connector.addFormInstanceRecord(
                event.getFormInstanceId(), event.isDraft(), event.getFieldValues());

        event.setModel(formInstanceRecord);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceRecordAdded(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), FormScreenlet.ADD_RECORD_ACTION);
        }
    }
}
