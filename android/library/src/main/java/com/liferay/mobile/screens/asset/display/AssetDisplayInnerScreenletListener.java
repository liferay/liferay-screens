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

package com.liferay.mobile.screens.asset.display;

import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;

/**
 * @author Sarai Díaz García
 */

public interface AssetDisplayInnerScreenletListener {

    /**
     * Configure or customize the child screenlet which
     * has been initialized correctly.
     *
     * @param innerScreenlet child screenlet
     */
    void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
        AssetEntry assetEntry);

    /**
     * Render a custom asset.
     *
     * @return custom view for the custom asset.
     */
    View onRenderCustomAsset(AssetEntry assetEntry);
}
