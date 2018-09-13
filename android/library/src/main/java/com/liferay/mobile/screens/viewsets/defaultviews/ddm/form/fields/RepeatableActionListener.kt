package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields

/**
 * @author Paulo Cruz
 */
interface RepeatableActionListener {
    fun onRepeatableFieldAdded(callerFieldView: DDMFieldRepeatableItemView)
    fun onRepeatableFieldRemoved(fieldView: DDMFieldRepeatableItemView)
}