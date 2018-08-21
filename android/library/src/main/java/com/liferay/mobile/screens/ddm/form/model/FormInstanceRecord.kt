package com.liferay.mobile.screens.ddm.form.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get

/**
 * @author Paulo Cruz
 */
typealias FieldValue = MutableMap<String, String>

@SuppressLint("ParcelCreator")
class FormInstanceRecord(
        var id: String? = null,
        var fieldValues: MutableMap<String, String>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        mutableMapOf()) {

        parcel.readMap(fieldValues, String::class.java.classLoader)
    }

    companion object {
        val converter: (Thing) -> FormInstanceRecord = { it: Thing ->

            val id = it.id

            val fieldValues =
                (it["fieldValues"] as? List<FieldValue>)?.reduce { acc, fieldValue ->
                        acc.apply { putFieldValue(fieldValue) }
                    } ?: mutableMapOf()

            FormInstanceRecord(id, fieldValues)
        }

        private fun MutableMap<String, String>.putFieldValue(
                mutableMap: FieldValue) {

            val name = mutableMap["name"] ?: ""
            val value = mutableMap["value"] ?: ""

            this[name] = value
        }

        object CREATOR : Parcelable.Creator<FormInstanceRecord> {
            override fun createFromParcel(parcel: Parcel): FormInstanceRecord {
                return FormInstanceRecord(parcel)
            }

            override fun newArray(size: Int): Array<FormInstanceRecord?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

}