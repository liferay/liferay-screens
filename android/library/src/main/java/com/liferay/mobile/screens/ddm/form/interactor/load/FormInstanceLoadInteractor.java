package com.liferay.mobile.screens.ddm.form.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormInstanceLoadInteractor
    extends BaseCacheReadInteractor<DDMFormListener, FormInstanceEvent> {

    @Override
    public FormInstanceEvent execute(Object... args) throws Exception {
        long formInstanceId = (long) args[0];

        FormInstanceConnector connector =
                ServiceProvider.getInstance().getFormInstanceConnector(getSession());

        FormInstance formInstance = connector.getFormInstance(formInstanceId);

        FormInstanceEvent event = new FormInstanceEvent(formInstance);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceLoaded(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), DDMFormScreenlet.LOAD_FORM_ACTION);
        }
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return args[0].toString();
    }
}
