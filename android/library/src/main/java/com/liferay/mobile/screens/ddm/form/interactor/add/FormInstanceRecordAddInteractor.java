package com.liferay.mobile.screens.ddm.form.interactor.add;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordAddInteractor
    extends BaseCacheWriteInteractor<DDMFormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(FormInstanceRecordEvent event) throws Exception {
        FormInstanceRecordConnector connector =
            ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord currentFormInstanceRecord = event.getModel();
        JSONArray fieldValuesJson = new JSONArray(currentFormInstanceRecord.getFieldValues());

        FormInstanceRecord updatedFormInstanceRecord = connector.addFormInstanceRecord(
                currentFormInstanceRecord.getFormInstanceRecordId(),
                currentFormInstanceRecord.isDraft(), fieldValuesJson);

        event.setFormInstanceRecord(updatedFormInstanceRecord);

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
            getListener().error(event.getException(), DDMFormScreenlet.ADD_RECORD_ACTION);
        }
    }
}
