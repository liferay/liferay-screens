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

package com.liferay.mobile.screens.comment.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.load.CommentLoadInteractor;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractor;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayScreenlet extends BaseScreenlet<CommentDisplayViewModel, Interactor>
    implements CommentDisplayInteractorListener {

    public static final String DELETE_COMMENT_ACTION = "DELETE_COMMENT";
    public static final String UPDATE_COMMENT_ACTION = "UPDATE_COMMENT";
    public static final String LOAD_COMMENT_ACTION = "LOAD_COMMENT";
    public static final String STATE_AUTO_LOAD = "STATE_AUTO_LOAD";
    public static final String STATE_COMMENT_ID = "STATE_COMMENT_ID";
    public static final String STATE_COMMENT_ENTRY = "STATE_COMMENT_ENTRY";
    private static final String STATE_EDITABLE = "STATE_EDITABLE";
    private CommentDisplayListener listener;
    private CommentEntry commentEntry;
    private long commentId;
    private CachePolicy cachePolicy;
    private boolean autoLoad;
    private boolean editable;

    public CommentDisplayScreenlet(Context context) {
        super(context);
    }

    public CommentDisplayScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScreenletAttached() {
        if (autoLoad) {
            autoLoad();
        }
    }

    /**
     * Checks if there is a session created and if exists {@link #commentId} attribute
     * and then calls {@link #load()} method.
     */
    protected void autoLoad() {
        if (SessionContext.isLoggedIn() && commentId != 0) {
            load();
        }
    }

    /**
     * Searches the {@link CommentEntry} with the given attribute ({@link #commentId}
     * and loads it in the screenlet.
     */
    public void load() {
        performUserAction(BaseScreenlet.DEFAULT_ACTION);
    }

    /**
     * Allows or disallows the comment edition.
     */
    public void allowEdition(boolean editable) {
        this.editable = editable;
        if (commentEntry != null) {
            getViewModel().showFinishOperation(LOAD_COMMENT_ACTION, editable, commentEntry);
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {

        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentDisplayScreenlet, 0, 0);

        autoLoad = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_autoLoad, true);

        commentId = castToLong(typedArray.getString(R.styleable.CommentDisplayScreenlet_commentId));

        editable = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_editable, true);

        int cachePolicy =
            typedArray.getInt(R.styleable.CommentDisplayScreenlet_cachePolicy, CachePolicy.REMOTE_ONLY.ordinal());
        this.cachePolicy = CachePolicy.values()[cachePolicy];

        int layoutId = typedArray.getResourceId(R.styleable.CommentDisplayScreenlet_layoutId, getDefaultLayoutId());

        typedArray.recycle();

        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected Interactor createInteractor(String actionName) {
        switch (actionName) {
            case DELETE_COMMENT_ACTION:
                return new CommentDeleteInteractor();
            case UPDATE_COMMENT_ACTION:
                return new CommentUpdateInteractor();
            case LOAD_COMMENT_ACTION:
            default:
                return new CommentLoadInteractor();
        }
    }

    @Override
    protected void onUserAction(String actionName, Interactor interactor, Object... args) {
        switch (actionName) {
            case DELETE_COMMENT_ACTION:
                ((CommentDeleteInteractor) interactor).start(new CommentEvent(commentId, null));
                break;
            case UPDATE_COMMENT_ACTION:
                String body = (String) args[0];
                ((CommentUpdateInteractor) interactor).start(new CommentEvent(commentId, body));
                break;
            case LOAD_COMMENT_ACTION:
            default:
                ((CommentLoadInteractor) interactor).start(commentId);
                break;
        }
    }

    @Override
    public void onLoadCommentSuccess(CommentEntry commentEntry) {
        this.commentEntry = commentEntry;
        this.commentId = commentEntry.getCommentId();
        getViewModel().showFinishOperation(LOAD_COMMENT_ACTION, editable, commentEntry);

        if (getListener() != null) {
            getListener().onLoadCommentSuccess(commentEntry);
        }
    }

    @Override
    public void onDeleteCommentSuccess() {
        getViewModel().showFinishOperation(DELETE_COMMENT_ACTION, editable);

        if (getListener() != null) {
            getListener().onDeleteCommentSuccess(commentEntry);
        }
    }

    @Override
    public void onUpdateCommentSuccess(CommentEntry commentEntry) {
        getViewModel().showFinishOperation(UPDATE_COMMENT_ACTION, editable, commentEntry);

        if (getListener() != null) {
            getListener().onUpdateCommentSuccess(commentEntry);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        getViewModel().showFailedOperation(userAction, e);

        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable(STATE_SUPER, superState);
        state.putBoolean(STATE_EDITABLE, editable);
        state.putBoolean(STATE_AUTO_LOAD, autoLoad);
        state.putLong(STATE_COMMENT_ID, commentId);
        state.putParcelable(STATE_COMMENT_ENTRY, commentEntry);
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable inState) {
        Bundle state = (Bundle) inState;

        editable = state.getBoolean(STATE_EDITABLE);
        autoLoad = state.getBoolean(STATE_AUTO_LOAD);
        commentId = state.getLong(STATE_COMMENT_ID);
        commentEntry = state.getParcelable(STATE_COMMENT_ENTRY);

        Parcelable superState = state.getParcelable(STATE_SUPER);
        super.onRestoreInstanceState(superState);
    }

    public CommentDisplayListener getListener() {
        return listener;
    }

    public void setListener(CommentDisplayListener listener) {
        this.listener = listener;
    }

    public CommentEntry getCommentEntry() {
        return commentEntry;
    }

    public void setCommentEntry(CommentEntry commentEntry) {
        this.commentEntry = commentEntry;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public CachePolicy getCachePolicy() {
        return cachePolicy;
    }

    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
