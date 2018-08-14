package com.liferay.mobile.screens.ddm.form.model

import android.os.Parcel
import android.os.Parcelable
import com.liferay.mobile.screens.ddl.model.Field
import java.io.Serializable

/**
 * @author Paulo Cruz
 */
class RepeatableField @JvmOverloads constructor(
    val baseField: Field<*>,
    private var siblings: MutableList<Field<*>> = mutableListOf()) : Field<Serializable>() {

    init {
        editorType = EditorType.REPEATABLE
        isShowLabel = baseField.isShowLabel
        label = baseField.label
        name = baseField.name

        baseField.isShowLabel = false
    }

    val repeatedFields: List<Field<*>>
        get() = listOf(baseField) + siblings

    constructor(parcel: Parcel)
            : this(parcel.readParcelable(Field::class.java.classLoader) as Field<*>) {

        siblings = parcel.readParcelableArray(
            Field::class.java.classLoader).toMutableList() as MutableList<Field<*>>
    }

    fun repeatField() : Field<*> {
        val dataType = baseField.dataType
        val attributes = baseField.attributes
        val currentLocale = baseField.currentLocale
        val defaultLocale = baseField.defaultLocale

        val repeatedField = dataType.createField(attributes, currentLocale, defaultLocale, true)
        repeatedField.isShowLabel = false

        siblings.add(repeatedField)

        return repeatedField
    }

    fun removeField(field: Field<*>) {
        siblings.remove(field)
    }

    override fun convertToData(value: Serializable): String {
        return repeatedFields.joinToString(",") {
            it.toData()
        }
    }

    override fun convertToFormattedString(value: Serializable): String {
        return repeatedFields.joinToString(",") {
            it.toFormattedString()
        }
    }

    override fun convertFromString(stringValue: String?): Serializable {
        return stringValue?.split(",").toString()
    }

    override fun setReadOnly(readOnly: Boolean) {
        super.setReadOnly(readOnly)

        baseField.attributes[formFieldKeys.isReadyOnlyKey] = readOnly
    }

    override fun isValid(): Boolean {
        return doValidate()
    }

    override fun doValidate(): Boolean {
        return repeatedFields.map { it.isValid }.reduce { acc, cur -> acc && cur }
    }

    override fun setRequired(required: Boolean) {
        super.setRequired(required)

        baseField.attributes[formFieldKeys.isRequiredKey] = required
    }

    override fun setCurrentStringValue(value: String?) {
        value?.split(",")?.let { values ->
            val newValuesCount = values.count()
            val oldValuesCount = repeatedFields.count()

            val hasFieldsAdded = newValuesCount > oldValuesCount
            val hasFieldsRemoved = newValuesCount < oldValuesCount

            if (hasFieldsAdded) {
                repeatFields(newValuesCount - oldValuesCount)
            } else if(hasFieldsRemoved) {
                removeLastFields(oldValuesCount - newValuesCount)
            }

            setRepeatedFieldValues(values)
        }
    }

    private fun repeatFields(times: Int) {
        kotlin.repeat(times) {
            repeatField()
        }
    }

    private fun removeLastFields(count: Int) {
        val fieldsToRemove = siblings.takeLast(count)
        siblings.removeAll(fieldsToRemove)
    }

    private fun setRepeatedFieldValues(values: List<String>) {
        repeatedFields.forEachIndexed { index, field ->
            field.setCurrentStringValue(values[index])
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeParcelable(baseField, flags)
        parcel.writeParcelableArray(siblings.toTypedArray(), flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepeatableField> {
        override fun createFromParcel(parcel: Parcel): RepeatableField {
            return RepeatableField(parcel)
        }

        override fun newArray(size: Int): Array<RepeatableField?> {
            return arrayOfNulls(size)
        }
    }

}