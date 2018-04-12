package com.liferay.mobile.screens.ddm.form.interactor.update;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordUpdateInteractor
    extends BaseCacheWriteInteractor<DDMFormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(FormInstanceRecordEvent event)
        throws Exception {

        FormInstanceRecordConnector connector =
                ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord formInstanceRecord = connector.updateFormInstanceRecord(
                event.getFormInstanceRecordId(), event.isDraft(), event.getFieldValues());

        event.setModel(formInstanceRecord);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceRecordUpdated(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), DDMFormScreenlet.UPDATE_RECORD_ACTION);
        }
    }
}
