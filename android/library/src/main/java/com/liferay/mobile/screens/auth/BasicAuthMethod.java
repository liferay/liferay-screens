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

package com.liferay.mobile.screens.auth;

import android.text.InputType;

/**
 * @author Silvio Santos
 */
public enum BasicAuthMethod {

    EMAIL(0), SCREEN_NAME(1), USER_ID(2);

    private final int value;

    BasicAuthMethod(int value) {
        this.value = value;
    }

    public static BasicAuthMethod getValue(int value) {
        for (BasicAuthMethod method : BasicAuthMethod.values()) {
            if (method.value == value) {
                return method;
            }
        }

        return EMAIL;
    }

    public int getInputType() {
        switch (this) {
            case EMAIL:
            case SCREEN_NAME:
                return InputType.TYPE_CLASS_TEXT;
            case USER_ID:
                return InputType.TYPE_CLASS_NUMBER;
            default:
                return InputType.TYPE_NULL;
        }
    }

}