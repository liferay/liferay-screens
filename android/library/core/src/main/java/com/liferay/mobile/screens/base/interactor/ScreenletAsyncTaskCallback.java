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

package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.android.task.callback.typed.GenericAsyncTaskCallback;
import com.liferay.mobile.screens.util.EventBusUtil;

/**
 * @author Jose Manuel Navarro
 */
public abstract class ScreenletAsyncTaskCallback<T>
	extends GenericAsyncTaskCallback<T> {

	public ScreenletAsyncTaskCallback(int targetScreenletId) {
		_targetScreenletId = targetScreenletId;
	}

	@Override
	public void onFailure(Exception e) {
		EventBusUtil.post(createEvent(_targetScreenletId, e));
	}

	@Override
	public void onSuccess(T result) {
		EventBusUtil.post(createEvent(_targetScreenletId, result));
	}

	protected abstract BasicEvent createEvent(int targetScreenletId, T result);

	protected abstract BasicEvent createEvent(
		int targetScreenletId, Exception e);

	private int _targetScreenletId;

}
