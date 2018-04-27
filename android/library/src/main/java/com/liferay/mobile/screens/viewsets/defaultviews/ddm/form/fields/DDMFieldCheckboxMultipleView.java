package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddm.form.model.CheckboxMultipleField;

import java.util.List;

/**
 * @author Paulo Cruz
 */
public class DDMFieldCheckboxMultipleView extends LinearLayout
        implements DDLFieldViewModel<CheckboxMultipleField>,
        CompoundButton.OnCheckedChangeListener {

    protected View parentView;
    private CheckboxMultipleField field;

    public DDMFieldCheckboxMultipleView(Context context) {
        super(context);
    }

    public DDMFieldCheckboxMultipleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldCheckboxMultipleView(Context context, @Nullable AttributeSet attrs,
                                        int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        CheckboxMultipleField.Option opt = (CheckboxMultipleField.Option) compoundButton.getTag();

        if (isChecked) {
            field.selectOption(opt);
        } else {
            field.clearOption(opt);
        }
    }

    @Override
    public CheckboxMultipleField getField() {
        return field;
    }

    @Override
    public void setField(CheckboxMultipleField field) {
        this.field = field;

        if (this.field.isShowLabel()) {
            TextView label = findViewById(R.id.liferay_ddm_label);

            label.setText(field.getLabel());
            label.setVisibility(VISIBLE);
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);

        List<CheckboxMultipleField.Option> availableOptions = field.getAvailableOptions();

        if(field.isShowAsSwitcher()) {
            for (CheckboxMultipleField.Option opt : availableOptions) {
                Switch switchView = createSwitchView(opt, layoutParams);
                addView(switchView);
            }
        } else {
            for (CheckboxMultipleField.Option opt : availableOptions) {
                CheckBox checkBoxView = createCheckBoxView(opt, layoutParams);
                addView(checkBoxView);
            }
        }

        refresh();
    }
    
    private CheckBox createCheckBoxView(CheckboxMultipleField.Option option,
        LayoutParams layoutParams) {

        CheckBox checkBoxView = new CheckBox(getContext());
        checkBoxView.setLayoutParams(layoutParams);
        checkBoxView.setText(option.label);
        checkBoxView.setTag(option);
        checkBoxView.setOnCheckedChangeListener(this);
        checkBoxView.setTypeface(getTypeface());
        checkBoxView.setSaveEnabled(true);

        return checkBoxView;
    }

    private Switch createSwitchView(CheckboxMultipleField.Option option,
        LayoutParams layoutParams) {

        Switch switchView = new Switch(getContext());
        switchView.setLayoutParams(layoutParams);
        switchView.setText(option.label);
        switchView.setTag(option);
        switchView.setOnCheckedChangeListener(this);
        switchView.setTypeface(getTypeface());
        switchView.setSaveEnabled(true);

        return switchView;
    }

    @Override
    public void refresh() {
        List<CheckboxMultipleField.Option> selectedOptions = field.getCurrentValue();

        if (selectedOptions != null) {
            for (CheckboxMultipleField.Option opt : selectedOptions) {
                CheckBox checkBox = findViewWithTag(opt);

                if (checkBox != null) {
                    checkBox.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onPostValidation(boolean valid) {
        String errorText = valid ? null : getContext().getString(R.string.required_value);

        if (field.isShowLabel()) {
            TextView label = findViewById(R.id.liferay_ddm_label);
            label.setError(errorText);
        } else {
            List<CheckboxMultipleField.Option> availableOptions = field.getAvailableOptions();
            CheckboxMultipleField.Option opt = availableOptions.get(0);
            CheckBox checkBox = findViewWithTag(opt);
            if (checkBox != null) {
                checkBox.setError(errorText);
            }
        }
    }

    @Override
    public View getParentView() {
        return parentView;
    }

    @Override
    public void setParentView(View view) {
        parentView = view;
    }

    @Override
    public void setUpdateMode(boolean enabled) {
        setEnabled(enabled);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setSaveEnabled(true);
    }

    private Typeface getTypeface() {
        //FIXME replace with constructor with styles when we have the drawables
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return Typeface.DEFAULT;
        }
        return Typeface.create("sans-serif-light", Typeface.NORMAL);
    }
}
