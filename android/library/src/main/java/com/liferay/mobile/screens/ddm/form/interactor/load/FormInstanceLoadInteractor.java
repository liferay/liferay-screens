/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
