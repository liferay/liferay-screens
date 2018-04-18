package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddm.form.view.DDMFormViewModel;

/**
 * @author Paulo Cruz
 */
public class DDMFormView extends ScrollView implements DDMFormViewModel, View.OnClickListener {

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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showStartOperation(String actionName) {

    }

    @Override
    public void showFinishOperation(String actionName) {

    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {

    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }
}
