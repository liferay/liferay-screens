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

package com.liferay.mobile.screens.base.interactor.event.interactor.event;

/**
 * @author Silvio Santos
 */
public abstract class BaseEvent {

	public BaseEvent() {
		this(null);
	}

	public BaseEvent(Exception exception) {
		_exception = exception;
	}

	public Exception getException() {
		return _exception;
	}

	public boolean isFailed() {
		return _exception != null;
	}

	private Exception _exception;

}