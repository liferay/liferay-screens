package com.liferay.mobile.screens.ddm.form.view

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddm.form.model.SuccessPage

/**
 * @author Victor Oliveira
 */
class SuccessPageActivity : AppCompatActivity() {

    private val headlineTextView: TextView by lazy {
        findViewById<TextView>(R.id.headline_text_view)
    }

    private val textTextView: TextView by lazy {
        findViewById<TextView>(R.id.text_text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ddm_form_success_page_default)

        val successPage = intent.extras["successPage"] as? SuccessPage

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(baseContext, R.color.success_darker_green_default)
        }

        supportActionBar?.hide()

        successPage?.let {
            headlineTextView.text = it.headline
            textTextView.text = it.text
        }
    }
}