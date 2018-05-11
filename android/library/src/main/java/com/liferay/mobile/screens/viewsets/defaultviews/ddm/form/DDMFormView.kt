package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.base.BaseScreenlet
import com.liferay.mobile.screens.base.interactor.Interactor
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.form.view.FormViewModel
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.model.Thing

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
    ScrollView(context, attrs, defStyleAttr) {

    private val layoutIds = mutableMapOf<Field.EditorType, Int?>()
    private val fieldsContainerView by bindNonNull<LinearLayout>(R.id.ddmfields_container)

    override var screenlet: ThingScreenlet? = null
    override var thing: Thing? by converter<FormInstance> {

        for (page in it.ddmStructure.pages) {
            for (field in page.fields) {
                addFieldView(field)
            }
        }
    }

    private fun addFieldView(field: Field<*>) {
        val layoutId = DEFAULT_LAYOUT_IDS[field.editorType]

        val view = LayoutInflater.from(context).inflate(layoutId!!, this, false)

        val viewModel = view as DDLFieldViewModel<*>
        viewModel.field = field

        fieldsContainerView.addView(view)
    }

    override fun <T> sendEvent(event: Event<T>): T? {
        return null
    }

    override fun onDestroy() {

    }

    override fun showError(message: String?) {

    }

    companion object {

        private val DEFAULT_LAYOUT_IDS = HashMap<Field.EditorType, Int>(1)

        init {
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX] = R.layout.ddlfield_checkbox_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX_MULTIPLE] = R.layout.ddmfield_checkbox_multiple_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.DATE] = R.layout.ddlfield_date_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.NUMBER] = R.layout.ddlfield_number_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.INTEGER] = R.layout.ddlfield_number_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.DECIMAL] = R.layout.ddlfield_number_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.RADIO] = R.layout.ddlfield_radio_default;
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT] = R.layout.ddlfield_text_default
            DEFAULT_LAYOUT_IDS.put(Field.EditorType.SELECT, R.layout.ddlfield_select_default);
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT_AREA] = R.layout.ddlfield_text_area_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DOCUMENT] = R.layout.ddlfield_document_default;
            //DEFAULT_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);
        }
    }
}
