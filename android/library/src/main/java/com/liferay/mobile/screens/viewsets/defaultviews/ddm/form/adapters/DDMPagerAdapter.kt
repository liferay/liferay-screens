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

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.adapters

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormPage
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormViewContract
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import rx.Observable

/**
 * @author Victor Oliveira
 */
class DDMPagerAdapter(val pages: List<FormPage>,
	private val ddmFormView: DDMFormViewContract.DDMFormView) : PagerAdapter() {
	private var mCurrentPosition = -1

	override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
		container.removeView(obj as View)
	}

	override fun getCount(): Int {
		return pages.size
	}

	override fun getItemPosition(obj: Any): Int {
		return pages.indexOf(obj)
	}

	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		val page = pages[position]
		val inflater = LayoutInflater.from(container.context)

		val pageItemView = inflater.inflate(R.layout.ddm_form_page_item, container, false)
		val linearLayout = pageItemView.findViewById<LinearLayout>(R.id.dmm_page_container)

		linearLayout.findViewById<TextView>(R.id.headline_text_view)?.let {
			it.text = page.headline

			if (page.headline.isEmpty()) {
				it.visibility = View.GONE
			}
		}

		linearLayout.findViewById<TextView>(R.id.description_text_view)?.let {
			it.text = page.description

			if (page.description.isEmpty()) {
				it.visibility = View.GONE
			}
		}

		var mergedObservable = Observable.empty<Field<*>>()

		for (field in page.fields) {

			val view = ddmFormView.inflateFieldView(inflater, linearLayout, field)

			linearLayout.addView(view)

			mergedObservable = mergedObservable.mergeWith((view as DDLFieldViewModel<*>).onChangedValueObservable)
		}

		linearLayout.tag = position
		container.addView(linearLayout)

		ddmFormView.subscribeToValueChanged(mergedObservable)
		ddmFormView.scrollToTop()

		return linearLayout
	}

	override fun isViewFromObject(view: View, obj: Any): Boolean {
		return view == obj
	}

	override fun setPrimaryItem(container: ViewGroup, position: Int, instantiatedItem: Any) {
		super.setPrimaryItem(container, position, instantiatedItem)

		if (position != mCurrentPosition) {
			val linearLayout = instantiatedItem as? LinearLayout
			linearLayout?.let {
				val pager = container as WrapContentViewPager
				mCurrentPosition = position
				pager.measureCurrentView(linearLayout)

				ddmFormView.scrollToTop()
			}
		}
	}
}