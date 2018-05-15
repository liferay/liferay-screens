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

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.Button
import android.widget.ScrollView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.model.Thing

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseView,
    ScrollView(context, attrs, defStyleAttr) {

    private val layoutIds = mutableMapOf<Field.EditorType, Int?>()
    private val ddmFieldViewPages by bindNonNull<ViewPager>(R.id.ddmfields_container)
    private val backButton by bindNonNull<Button>(R.id.liferay_form_back)
    private val nextButton by bindNonNull<Button>(R.id.liferay_form_submit)

    override var screenlet: ThingScreenlet? = null
    override var thing: Thing? by converter<FormInstance> {

        val ddmPagerAdapter = DDMPagerAdapter(it.ddmStructure.pages)
        ddmFieldViewPages.adapter = ddmPagerAdapter
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        backButton.setOnClickListener({
            if (ddmFieldViewPages.currentItem >= 1) {
                ddmFieldViewPages.currentItem -= 1

                nextButton.text = context.getString(R.string.next)
                if (ddmFieldViewPages.currentItem == 0) {
                    backButton.isEnabled = false
                }
            }
        })

        nextButton.setOnClickListener({
            val size = ddmFieldViewPages.adapter.count - 1

            if (ddmFieldViewPages.currentItem < size) {
                ddmFieldViewPages.currentItem += 1

                backButton.isEnabled = true
                if (ddmFieldViewPages.currentItem == size) {
                    nextButton.text = context.getString(R.string.submit)
                }
            } else {
                LiferayLogger.d("Submit")
            }
        })
    }

    companion object {

        val DEFAULT_LAYOUT_IDS = HashMap<Field.EditorType, Int>(1)

        init {
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX] = R.layout.ddlfield_checkbox_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX_MULTIPLE] = R.layout.ddmfield_checkbox_multiple_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DATE] = R.layout.ddlfield_date_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.NUMBER] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.INTEGER] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DECIMAL] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.RADIO] = R.layout.ddlfield_radio_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT] = R.layout.ddlfield_text_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.SELECT] = R.layout.ddlfield_select_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT_AREA] = R.layout.ddlfield_text_area_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.PARAGRAPH] = R.layout.ddmfield_paragrah_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DOCUMENT] = R.layout.ddlfield_document_default
            //DEFAULT_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);
        }
    }
}
