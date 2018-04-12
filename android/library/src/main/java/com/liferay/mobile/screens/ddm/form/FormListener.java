package com.liferay.mobile.screens.ddm.form;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

/**
 * @author Paulo Cruz
 */
public interface FormListener extends BaseCacheListener {

    /**
     * Called when the form definition successfully loads.
     */
    void onFormContextEvaluated(FormContext formContext);

    /**
     * Called when the form definition successfully loads.
     */
    void onFormInstanceLoaded(FormInstance formInstance);

    /**
     * Called when the form record data successfully loads.
     */
    void onFormInstanceRecordLoaded(FormInstanceRecord formInstanceRecord);

    /**
     * Called when the form record is successfully added.
     */
    void onFormInstanceRecordAdded(FormInstanceRecord formInstanceRecord);

    /**
     * Called when the form record data successfully updates.
     */
    void onFormInstanceRecordUpdated(FormInstanceRecord formInstanceRecord);

}
