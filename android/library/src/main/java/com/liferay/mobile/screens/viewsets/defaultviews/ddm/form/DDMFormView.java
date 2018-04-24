package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.model.DDMField;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.view.DDMFieldViewModel;
import com.liferay.mobile.screens.ddm.form.view.DDMFormViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class DDMFormView extends ScrollView implements DDMFormViewModel, View.OnClickListener {

    private static final Map<DDMField.EditorType, Integer> DEFAULT_LAYOUT_IDS = new HashMap<>(1);

    static {
        DEFAULT_LAYOUT_IDS.put(DDMField.EditorType.TEXT, R.layout.ddmfield_text_default);
    }

    private final Map<DDMField.EditorType, Integer> layoutIds = new HashMap<>();
    private final Map<String, Integer> customLayoutIds = new HashMap<>();

    protected ViewGroup fieldsContainerView;

    private BaseScreenlet screenlet;

    public DDMFormView(Context context) {
        super(context);
    }

    public DDMFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void addFieldView(DDMField field) {
        //boolean containsKey = customLayoutIds.containsKey(field.getName());
        //int layoutId = containsKey ? getCustomFieldLayoutId(field.getName()) : getFieldLayoutId(field.getEditorType());

        int layoutId = this.getFieldLayoutId(field.getEditorType());

        View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        DDMFieldViewModel viewModel = (DDMFieldViewModel) view;

        viewModel.setField(field);
        viewModel.setParentView(this);

        fieldsContainerView.addView(view);
    }

    protected DDMFormScreenlet getDDMFormScreenlet() {
        return (DDMFormScreenlet) getScreenlet();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showStartOperation(String actionName) {

    }

    @Override
    public void showStartOperation(String actionName, Object argument) {

    }

    @Override
    public void showFinishOperation(String actionName) {

    }

    @Override
    public void showFinishOperation(String actionName, Object argument) {
        FormInstance formInstance = (FormInstance) argument;
        showFormFields(formInstance);
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {

    }

    @Override
    public void showFailedOperation(String actionName, Exception e, Object argument) {

    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }

    @Override
    public int getFieldLayoutId(DDMField.EditorType editorType) {
        return layoutIds.get(editorType);
    }

    @Override
    public void setFieldLayoutId(DDMField.EditorType editorType, int layoutId) {
        layoutIds.put(editorType, layoutId);
    }

    @Override
    public void resetFieldLayoutId(DDMField.EditorType editorType) {
        layoutIds.put(editorType, DEFAULT_LAYOUT_IDS.get(editorType));
    }

    @Override
    public int getCustomFieldLayoutId(String fieldName) {
        return customLayoutIds.get(fieldName);
    }

    @Override
    public void setCustomFieldLayoutId(String fieldName, int layoutId) {
        customLayoutIds.put(fieldName, layoutId);
    }

    @Override
    public void resetCustomFieldLayoutId(String fieldName) {
        customLayoutIds.remove(fieldName);
    }

    @Override
    public void showValidationResults(Map<DDMField, Boolean> fieldResults, boolean autoscroll) {

    }

    @Override
    public void showFormFields(FormInstance formInstance) {
        fieldsContainerView.removeAllViews();
        fieldsContainerView.setVisibility(INVISIBLE);

        for(DDMField field : formInstance.getFields()) {
            addFieldView(field);
        }

        DefaultAnimation.showViewWithReveal(fieldsContainerView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        fieldsContainerView = findViewById(R.id.ddmfields_container);
    }
}
