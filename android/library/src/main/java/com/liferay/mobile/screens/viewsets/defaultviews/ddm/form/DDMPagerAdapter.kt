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

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form

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
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.util.EventBusUtil
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import rx.Observable
import rx.Subscription
import java.util.concurrent.TimeUnit

/**
 * @author Victor Oliveira
 */
class DDMPagerAdapter(val pages: List<FormPage>, val ddmFormView: DDMFormView) : PagerAdapter() {
    var subscription: Subscription? = null
    private var mCurrentPosition = -1

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItemPosition(obj: Any): Int {
        return pages.indexOf(obj)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val page = pages[position]
        val inflater = LayoutInflater.from(container?.context)

        val pageItemView = inflater.inflate(R.layout.ddm_form_page_item, container, false)
        val linearLayout = pageItemView.findViewById<LinearLayout>(R.id.dmm_page_container)

        linearLayout.findViewById<TextView>(R.id.headline_text_view)?.let {
            it.text = page.headline
        }

        linearLayout.findViewById<TextView>(R.id.description_text_view)?.let {
            it.text = page.description
        }

        var mergedObservable = Observable.empty<Field<*>>()

        for (field in page.fields) {
            val layoutId = ddmFormView.layoutIds[field.editorType]
            val view = inflater.inflate(layoutId!!, linearLayout, false)

            if (view is DDLDocumentFieldView) {
                view.setUploadListener(ddmFormView)
            } else if (view is DDMFieldRepeatableView) {
                view.setLayoutIds(ddmFormView.layoutIds)
            }

            val viewModel = view as DDLFieldViewModel<*>
            viewModel.field = field
            view.tag = field

            linearLayout.addView(view)

            mergedObservable = mergedObservable.mergeWith(view.onChangedValueObservable)
        }

        linearLayout.tag = position
        container?.addView(linearLayout)

        subscription = mergedObservable
            .skip(3)
            .debounce(2, TimeUnit.SECONDS)
            .subscribe {
                EventBusUtil.post(Event.ValueChangedEvent(it.hasFormRules()))
            }

        ddmFormView.scrollView.scrollTo(0, 0)

        return linearLayout
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, instantiatedItem: Any) {
        super.setPrimaryItem(container, position, instantiatedItem)

        if (position != mCurrentPosition) {
            val linearLayout = instantiatedItem as? LinearLayout
            linearLayout?.let {
                val pager = container as WrapContentViewPager
                mCurrentPosition = position
                pager.measureCurrentView(linearLayout)

                ddmFormView.scrollView.scrollTo(0, 0)
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container?.removeView(obj as View)
    }
}