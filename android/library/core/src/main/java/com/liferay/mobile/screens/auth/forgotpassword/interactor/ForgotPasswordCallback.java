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

package com.liferay.mobile.screens.auth.forgotpassword.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordCallback
	extends InteractorAsyncTaskCallback<Boolean> {

	public ForgotPasswordCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public Boolean transform(Object obj) throws Exception {
		return (Boolean) obj;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Boolean result) {
		return new ForgotPasswordEvent(targetScreenletId, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new ForgotPasswordEvent(targetScreenletId, e);
	}

}