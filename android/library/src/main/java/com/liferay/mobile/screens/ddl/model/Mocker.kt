package com.liferay.mobile.screens.ddl.model

import com.liferay.mobile.screens.ddm.form.model.CheckboxMultipleField
import java.util.*


/**
 * @author Victor Oliveira
 */

val mockedJSONStructure = "{\n" +
    "  \"description\": \"description\",\n" +
    "  \"name\": \"name\",\n" +
    "  \"pages\": [{\n" +
    "    \"headline\": \"headline\",\n" +
    "    \"text\": \"text\",\n" +
    "    \"fields\": [{\n" +
    "      \"isAutocomplete\": false,\n" +
    "      \"isInline\": false,\n" +
    "      \"isLocalizable\": false,\n" +
    "      \"isMultiple\": false,\n" +
    "      \"isReadOnly\": false,\n" +
    "      \"isRepeatable\": false,\n" +
    "      \"isRequired\": false,\n" +
    "      \"isShowAsSwitcher\": false,\n" +
    "      \"isShowLabel\": true,\n" +
    "      \"isTransient\": false,\n" +
    "      \"label\": \"label\",\n" +
    "      \"predefinedValue\": \"\",\n" +
    "      \"tip\": \"tip\",\n" +
    "      \"dataSourceType\": \"text\",\n" +
    "      \"dataType\": \"string\",\n" +
    "      \"name\": \"fields name\",\n" +
    "      \"placeholder\": \"placeholder\",\n" +
    "      \"text\": \"field text\"\n" +
    "      }]\n" +
    "  }]\n" +
    "}"

fun getMockMapping(isAutocomplete: Boolean, isInline: Boolean, isLocalizable: Boolean,
    isMultiple: Boolean, isReadOnly: Boolean, isRepeatable: Boolean, isRequired: Boolean,
    isShowAsSwitcher: Boolean, isShowLabel: Boolean, isTransient: Boolean, label: String,
    predefinedValue: String, tip: String, dataSourceType: String, dataType: String,
    type: String, name: String, placeholder: String, text: String): Map<String, Any> {

    return mapOf(
        "isAutocomplete" to isAutocomplete,
        "isInline" to isInline,
        "isLocalizable" to isLocalizable,
        "isMultiple" to isMultiple,
        FormFieldKeys.READ_ONLY to isReadOnly,
        FormFieldKeys.REPEATABLE to isRepeatable,
        FormFieldKeys.REQUIRED to isRequired,
        "isShowAsSwitcher" to isShowAsSwitcher,
        FormFieldKeys.SHOW_LABEL to isShowLabel,
        "isTransient" to isTransient,
        FormFieldKeys.LABEL to label,
        FormFieldKeys.PREDEFINED_VALUE to predefinedValue,
        FormFieldKeys.TIP to tip,
        "dataSourceType" to dataSourceType,
        "dataType" to dataType,
        "type" to type,
        FormFieldKeys.NAME to name,
        FormFieldKeys.PLACEHOLDER to placeholder,
        FormFieldKeys.TEXT to text
    )
}

fun getMockMapping(dataType: String, type: String, options: List<Map<String, String>> = listOf(),
    showAsSwitcher: Boolean = false): Map<String, Any> {
    return mapOf(
        "dataType" to dataType,
        "readOnly" to false,
        "type" to type,
        "required" to false,
        "showLabel" to true,
        "repeatable" to false,
        "label" to "Label $type",
        "name" to "Name $type",
        "tip" to "Tip $type",
        "placeHolder" to "",
        "showAsSwitcher" to showAsSwitcher,
        "options" to options)
}

fun getAllMockedFields(): List<Field<*>> {
    val optionData1 = mapOf("label" to "Option 1", "value" to "option1")
    val optionData2 = mapOf("label" to "Option 2", "value" to "option2")
    val availableOptionsData = listOf(optionData1, optionData2)

    val checkBoxMultipleField = CheckboxMultipleField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.CHECKBOX_MULTIPLE.value,
            availableOptionsData),
        Locale.ENGLISH, Locale.ENGLISH)

    val checkBoxShowAsSwitcherField = CheckboxMultipleField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.CHECKBOX_MULTIPLE.value,
            availableOptionsData, true),
        Locale.ENGLISH, Locale.ENGLISH)

    val dateField = DateField(
        getMockMapping(Field.DataType.DATE.value, Field.EditorType.DATE.value),
        Locale.ENGLISH, Locale.ENGLISH)

    val integerField = NumberField(
        getMockMapping(Field.DataType.NUMBER.value, Field.EditorType.INTEGER.value),
        Locale.ENGLISH, Locale.ENGLISH)

    val numberField = NumberField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.NUMBER.value),
        Locale.ENGLISH, Locale.ENGLISH)

    val decimalField = NumberField(
        getMockMapping(Field.DataType.NUMBER.value, Field.EditorType.DECIMAL.value),
        Locale.ENGLISH, Locale.ENGLISH)

    val radioField = StringWithOptionsField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.RADIO.value, availableOptionsData),
        Locale.ENGLISH, Locale.ENGLISH)

    val selectField = StringWithOptionsField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.SELECT.value,
            availableOptionsData),
        Locale.ENGLISH, Locale.ENGLISH)

    val documentField = DocumentField(
        getMockMapping(Field.DataType.STRING.value, Field.EditorType.DOCUMENT.value),
        Locale.ENGLISH, Locale.ENGLISH)

    return listOf(checkBoxMultipleField, checkBoxShowAsSwitcherField, dateField, integerField, numberField,
        decimalField, radioField, selectField, documentField)
}
