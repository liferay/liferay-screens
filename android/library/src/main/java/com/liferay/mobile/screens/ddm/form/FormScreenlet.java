package com.liferay.mobile.screens.ddm.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.ddm.form.interactor.add.FormInstanceRecordAddInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.evaluate.FormContextEvaluateInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.load.FormInstanceLoadInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.load.FormInstanceRecordLoadInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.update.FormInstanceRecordUpdateInteractor;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.view.FormViewModel;

/**
 * @author Paulo Cruz
 */
public class FormScreenlet extends BaseScreenlet<FormViewModel, Interactor<FormListener>>
        implements FormListener {

    public static final String EVALUATE_CONTEXT_ACTION = "evaluateContext";
    public static final String LOAD_FORM_ACTION = "loadForm";
    public static final String LOAD_RECORD_ACTION = "loadRecord";
    public static final String ADD_RECORD_ACTION = "addRecord";
    public static final String UPDATE_RECORD_ACTION = "updateRecord";

    private FormListener listener;

    public FormScreenlet(Context context) {
        super(context);
    }

    public FormScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FormScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FormListener getListener() {
        return listener;
    }

    public void setListener(FormListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFormContextEvaluated(FormContext formContext) {
        if (listener != null) {
            listener.onFormContextEvaluated(formContext);
        }
    }

    @Override
    public void onFormInstanceLoaded(FormInstance formInstance) {
        if (listener != null) {
            listener.onFormInstanceLoaded(formInstance);
        }
    }

    @Override
    public void onFormInstanceRecordLoaded(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordLoaded(formInstanceRecord);
        }
    }

    @Override
    public void onFormInstanceRecordAdded(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordAdded(formInstanceRecord);
        }
    }

    @Override
    public void onFormInstanceRecordUpdated(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordUpdated(formInstanceRecord);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        if (listener != null) {
            listener.error(e, userAction);
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        return null;
    }

    @Override
    protected Interactor<FormListener> createInteractor(String actionName) {
        switch (actionName) {
            case EVALUATE_CONTEXT_ACTION:
                return new FormContextEvaluateInteractor();
            case LOAD_FORM_ACTION:
                return new FormInstanceLoadInteractor();
            case LOAD_RECORD_ACTION:
                return new FormInstanceRecordLoadInteractor();
            case ADD_RECORD_ACTION:
                return new FormInstanceRecordAddInteractor();
            case UPDATE_RECORD_ACTION:
                return new FormInstanceRecordUpdateInteractor();
            default:
                return null;
        }
    }

    @Override
    protected void onUserAction(String userActionName, Interactor<FormListener> interactor, Object... args) {
        switch (userActionName) {
            case EVALUATE_CONTEXT_ACTION:
                ((FormContextEvaluateInteractor) interactor).start();
                break;
            case LOAD_FORM_ACTION:
                ((FormInstanceLoadInteractor) interactor).start();
                break;
            case LOAD_RECORD_ACTION:
                ((FormInstanceRecordLoadInteractor) interactor).start();
                break;
            case ADD_RECORD_ACTION:
                ((FormInstanceRecordAddInteractor) interactor).start();
                break;
            case UPDATE_RECORD_ACTION:
                ((FormInstanceRecordUpdateInteractor) interactor).start();
                break;
        }
    }
}
