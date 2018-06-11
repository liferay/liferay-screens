package com.liferay.mobile.screens.ddm.form.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddm.form.model.SuccessPage

/**
 * @author Victor Oliveira
 */
class SuccessPageActivity: AppCompatActivity() {

    private val headlineTextView : TextView by lazy {
        findViewById<TextView>(R.id.headline_text_view)
    }

    private val textTextView : TextView by lazy {
        findViewById<TextView>(R.id.text_text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ddm_form_success_page_default)

        val successPage = intent.extras["successPage"] as? SuccessPage

        successPage?.let {
            headlineTextView.text = it.headline
            textTextView.text = it.text
        }
    }
}