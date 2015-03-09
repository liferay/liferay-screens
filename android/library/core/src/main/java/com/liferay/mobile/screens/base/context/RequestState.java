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

package com.liferay.mobile.screens.base.context;

import android.util.SparseArray;

import java.util.HashSet;

/**
 * @author Silvio Santos
 */
public class RequestState {

	//TODO we should add javadoc for the most important classes like this one.

	public synchronized boolean contains(int targetScreenletId, Object state) {
		HashSet<Object> set = _states.get(targetScreenletId);

		if (set == null) {
			return false;
		}

		return set.contains(state);
	}

	public synchronized static RequestState getInstance() {
		if (_instance == null) {
			_instance = new RequestState();
		}

		return _instance;
	}

	public synchronized void put(int targetScreenletId, Object state) {
		HashSet<Object> set = _states.get(targetScreenletId);

		if (set == null) {
			set = new HashSet<>();

			_states.put(targetScreenletId, set);
		}

		set.add(state);
	}

	public synchronized void remove(int targetScreenletId, Object state) {
		HashSet<Object> set = _states.get(targetScreenletId);

		if (set == null) {
			return;
		}

		set.remove(state);

		if (set.isEmpty()) {
			_states.remove(targetScreenletId);
		}
	}

	private static RequestState _instance;

	private SparseArray<HashSet<Object>> _states = new SparseArray<>();

}