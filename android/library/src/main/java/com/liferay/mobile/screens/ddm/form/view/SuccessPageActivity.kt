package com.liferay.mobile.screens.ddm.form.view

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddm.form.model.SuccessPage
import com.liferay.mobile.screens.delegates.bindNonNull

/**
 * @author Victor Oliveira
 */
class SuccessPageActivity : AppCompatActivity() {

	private val headlineTextView: TextView by bindNonNull(R.id.headline_text_view)
	private val textTextView: TextView by bindNonNull(R.id.text_text_view)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.ddm_form_success_page_default)

		val successPage = intent.extras[FormConstants.SUCCESS_PAGE] as? SuccessPage

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