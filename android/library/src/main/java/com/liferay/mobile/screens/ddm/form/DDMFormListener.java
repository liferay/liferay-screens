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

package com.liferay.mobile.screens.ddm.form;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

/**
 * @author Paulo Cruz
 */
public interface DDMFormListener extends BaseCacheListener {

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
