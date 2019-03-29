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
package com.liferay.mobile.screens.ddm.form.service

import com.liferay.mobile.screens.context.LiferayServerContext
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.Callable

/**
 * @author Victor Oliveira
 */
abstract class BaseService<T> {
	inline fun execute(url: String, crossinline transformFunction: (Response) -> T): Observable<T> {
		return rx.Observable.fromCallable(object : Callable<Response> {
			override fun call(): Response {
				val request = Request.Builder()
					.url(url)
					.build()

				return LiferayServerContext.getOkHttpClient(true).newCall(request).execute()
			}
		}).map {
			transformFunction(it)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
	}
}