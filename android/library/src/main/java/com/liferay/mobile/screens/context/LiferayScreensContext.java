/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.context;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * @author Jose Manuel Navarro
 */
public class LiferayScreensContext {

    private static Context context;

    private LiferayScreensContext() {
    }

    public static void init(Context context) {
        LiferayScreensContext.context = context.getApplicationContext();

        LiferayServerContext.loadFromResources(context.getResources(), context.getPackageName());
    }

    public static void reinit(Context context) {
        LiferayScreensContext.context = context.getApplicationContext();

        LiferayServerContext.reloadFromResources(context.getResources(), context.getPackageName());
    }

    /**
     * Only to be used in unit tests
     */
    public static void deinit() {
        context = null;
    }

    public static Context getContext() {
        return context;
    }

    public static Activity getActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getActivityFromContext(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
