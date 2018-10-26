/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.comment.add;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.interactor.CommentAddInteractor;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;

/**
 * @author Alejandro Hernández
 */
public class CommentAddScreenlet extends BaseScreenlet<CommentAddViewModel, CommentAddInteractor>
    implements CommentAddListener {

    private CommentAddListener listener;
    private String className;
    private long classPK;

    public CommentAddScreenlet(Context context) {
        super(context);
    }

    public CommentAddScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentAddScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentAddScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentAddScreenlet, 0, 0);

        className = typedArray.getString(R.styleable.CommentAddScreenlet_className);

        classPK = castToLong(typedArray.getString(R.styleable.CommentAddScreenlet_classPK));

        int layoutId = typedArray.getResourceId(R.styleable.CommentAddScreenlet_layoutId, getDefaultLayoutId());

        typedArray.recycle();

        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected CommentAddInteractor createInteractor(String actionName) {
        return new CommentAddInteractor();
    }

    @Override
    protected void onUserAction(String userActionName, CommentAddInteractor interactor, Object... args) {
        String body = (String) args[0];

        interactor.start(new CommentEvent(0, className, classPK, body));
    }

    @Override
    public void error(Exception e, String userAction) {

        getViewModel().showFailedOperation(null, e);

        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    public void onAddCommentSuccess(CommentEntry commentEntry) {

        getViewModel().showFinishOperation(null);

        if (getListener() != null) {
            getListener().onAddCommentSuccess(commentEntry);
        }
    }

    public CommentAddListener getListener() {
        return listener;
    }

    public void setListener(CommentAddListener listener) {
        this.listener = listener;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassPK() {
        return classPK;
    }

    public void setClassPK(long classPK) {
        this.classPK = classPK;
    }
}
