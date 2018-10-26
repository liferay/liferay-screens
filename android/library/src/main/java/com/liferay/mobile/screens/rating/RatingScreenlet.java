/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.rating.interactor.delete.RatingDeleteInteractor;
import com.liferay.mobile.screens.rating.interactor.load.RatingLoadInteractor;
import com.liferay.mobile.screens.rating.interactor.update.RatingUpdateInteractor;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */

public class RatingScreenlet extends BaseScreenlet<RatingViewModel, Interactor> implements RatingListener {

    public static final String LOAD_RATINGS_ACTION = "LOAD_RATINGS";
    public static final String UPDATE_RATING_ACTION = "UPDATE_RATING";
    public static final String DELETE_RATING_ACTION = "DELETE_RATING";
    private RatingListener listener;
    private long entryId;
    private boolean autoLoad;
    private boolean editable;
    private String className;
    private long classPK;

    public RatingScreenlet(Context context) {
        super(context);
    }

    public RatingScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RatingScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Allows or disallows the rating edition.
     */
    public void enableEdition(boolean editable) {
        getViewModel().enableEdition(editable);
    }

    @Override
    protected void onScreenletAttached() {
        super.onScreenletAttached();

        if (autoLoad) {
            autoLoad();
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.RatingScreenlet, 0, 0);

        int layoutId = typedArray.getResourceId(R.styleable.RatingScreenlet_layoutId, getDefaultLayoutId());

        autoLoad = typedArray.getBoolean(R.styleable.RatingScreenlet_autoLoad, true);
        editable = typedArray.getBoolean(R.styleable.RatingScreenlet_editable, true);

        RatingViewModel view = (RatingViewModel) LayoutInflater.from(context).inflate(layoutId, null);
        view.enableEdition(editable);

        entryId = castToLong(typedArray.getString(R.styleable.RatingScreenlet_entryId));
        className = typedArray.getString(R.styleable.RatingScreenlet_className);
        classPK = castToLong(typedArray.getString(R.styleable.RatingScreenlet_classPK));

        typedArray.recycle();

        return (View) view;
    }

    @Override
    protected Interactor createInteractor(String actionName) {
        switch (actionName) {
            case LOAD_RATINGS_ACTION:
                return new RatingLoadInteractor();
            case DELETE_RATING_ACTION:
                return new RatingDeleteInteractor();
            case UPDATE_RATING_ACTION:
                return new RatingUpdateInteractor();
            default:
                return null;
        }
    }

    @Override
    protected void onUserAction(String userActionName, Interactor interactor, Object... args) {
        int count = getViewModel().getRatingsLength();
        switch (userActionName) {
            case LOAD_RATINGS_ACTION:
                ((RatingLoadInteractor) interactor).start(entryId, classPK, className, count);
                break;
            case UPDATE_RATING_ACTION:
                double score = (double) args[0];
                ((RatingUpdateInteractor) interactor).start(new RatingEvent(classPK, className, count, score));
                break;
            case DELETE_RATING_ACTION:
                ((RatingDeleteInteractor) interactor).start(
                    new RatingEvent(classPK, className, count, new JSONObject()));
                break;
            default:
                break;
        }
    }

    /**
     * Checks if there is a session created and then calls {@link #load()} method.
     */
    protected void autoLoad() {
        if (SessionContext.isLoggedIn() && (entryId != 0 || (className != null && classPK != 0))) {
            load();
        }
    }

    /**
     * Searches the {@link AssetRating} with the given attributes ({@link #entryId} or {@link #className}
     * and {@link #classPK}) and loads it in the screenlet.
     */
    public void load() {
        performUserAction(LOAD_RATINGS_ACTION);
    }

    @Override
    public void onRatingOperationSuccess(AssetRating assetRating) {

        getViewModel().showFinishOperation(null, assetRating);

        classPK = assetRating.getClassPK();
        className = assetRating.getClassName();

        if (listener != null) {
            listener.onRatingOperationSuccess(assetRating);
        }
    }

    @Override
    public void error(Exception exception, String userAction) {
        getViewModel().showFailedOperation(userAction, exception);

        if (listener != null) {
            listener.error(exception, userAction);
        }
    }

    public RatingListener getListener() {
        return listener;
    }

    public void setListener(RatingListener listener) {
        this.listener = listener;
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
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

    public boolean isEditable() {
        return editable;
    }
}
