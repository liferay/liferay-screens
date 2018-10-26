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

package com.liferay.mobile.screens.rating.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.rating.AssetRating;

/**
 * @author Alejandro Hernández
 */
public interface RatingViewModel extends BaseViewModel {

    /**
     * Called when the rating operation is succesfully finished.
     *
     * @param argument asset rating
     */
    void showFinishOperation(String actionName, AssetRating argument);

    /**
     * Call this method to allow rating edition.
     */
    void enableEdition(boolean editable);

    /**
     * Number of possible rating values available for the user in the view.
     */
    int getRatingsLength();
}
