package com.liferay.mobile.screens.testapp.postings.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import com.liferay.apio.consumer.ApioConsumer
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull

class EditActivity : AppCompatActivity() {

    val container by bindNonNull<LinearLayout>(R.id.container)
    val submitButton by bindNonNull<Button>(R.id.submit_button)

    lateinit var properties: List<String>
    lateinit var thingId: String
    lateinit var operation: String
    lateinit var values: Map<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        properties = intent.getStringArrayListExtra("properties") as List<String>
        values = intent.extras.get("values") as Map<String, String>
        thingId = intent.getStringExtra("id")
        operation = intent.getStringExtra("operation")

        addFieldsBasedOnProperties()

        submitButton.setOnClickListener {
            performOperation()
        }
    }

    private fun addFieldsBasedOnProperties() {
        for (property in properties) {
            val editView = layoutInflater.inflate(R.layout.property_view, null) as PropertiesView
            editView.setPropertyName(property)

            values[property]?.let {
                editView.propertyValue = it
            }

            container.addView(editView)
        }
    }

    fun performOperation() {
        val fieldsValues = properties.map {
            val view = container.findViewWithTag<PropertiesView>(it)

            it to view.propertyValue
        }.toMap()

        ApioConsumer().performOperation(thingId, operation, fillFields = {
            fieldsValues
        }, onSuccess = {
            Snackbar.make(container, "Success", LENGTH_SHORT).show()
        }, onError = {
            Snackbar.make(container, "Error", LENGTH_SHORT).show()
        })
    }

}
