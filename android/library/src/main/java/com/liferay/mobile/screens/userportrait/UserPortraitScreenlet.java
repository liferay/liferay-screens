/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.userportrait;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.MediaStoreCallback;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractor;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadEvent;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractor;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenlet extends BaseScreenlet<UserPortraitViewModel, Interactor>
    implements UserPortraitInteractorListener {

    public static final String UPLOAD_PORTRAIT = "UPLOAD_PORTRAIT";
    public static final String LOAD_PORTRAIT = "LOAD_PORTRAIT";
    public static final String LOAD_CURRENT_USER = "LOAD_CURRENT_USER";
    private static final String STATE_SUPER = "userportrait-super";
    private boolean autoLoad;
    private boolean male;
    private long portraitId;
    private long userId;
    private String uuid;
    private boolean editable;
    private UserPortraitListener listener;

    public UserPortraitScreenlet(Context context) {
        super(context);
    }

    public UserPortraitScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserPortraitScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UserPortraitScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Searches the user portrait with the given attributes ({@link #portraitId}
     * and {@link #uuid} or {@link #userId}) and loads it in the screenlet.
     */
    public void load() {
        performUserAction(LOAD_PORTRAIT);
    }

    /**
     * Loads the user portrait that correspond to the user logged.
     */
    public void loadLoggedUserPortrait() {
        performUserAction(LOAD_CURRENT_USER);
    }

    /**
     * Selects a new user portrait from the device camera.
     */
    public void openCamera() {
        MediaStoreRequestShadowActivity.show(getContext(), MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA,
            new MediaStoreCallback() {

                @Override
                public void onUriReceived(Uri uri) {
                    onPictureUriReceived(uri);
                }
            });
    }

    /**
     * Selects a new user portrait from the device image gallery.
     */
    public void openGallery() {
        MediaStoreRequestShadowActivity.show(getContext(), MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY,
            new MediaStoreCallback() {

                @Override
                public void onUriReceived(Uri uri) {
                    onPictureUriReceived(uri);
                }
            });
    }

    @Override
    public Bitmap onEndUserPortraitLoadRequest(Bitmap bitmap) {
        Bitmap finalImage = bitmap;

        if (listener != null) {
            finalImage = listener.onUserPortraitLoadReceived(bitmap);

            if (finalImage == null) {
                finalImage = bitmap;
            }
        }

        getViewModel().showFinishOperation(finalImage);

        return finalImage;
    }

    @Override
    public void onUserPortraitUploaded(Long uuid) {
        if (listener != null) {
            listener.onUserPortraitUploaded();
        }

        getViewModel().showFinishOperation(UPLOAD_PORTRAIT);

        ((UserPortraitLoadInteractor) getInteractor(LOAD_PORTRAIT)).start(uuid);
    }

    public void onPictureUriReceived(Uri pictureUri) {
        performUserAction(UPLOAD_PORTRAIT, pictureUri);
    }

    @Override
    public void onUserWithoutPortrait(User user) {
        getViewModel().showPlaceholder(user);
        getViewModel().showFinishOperation(LOAD_PORTRAIT);
    }

    @Override
    public void error(Exception e, String userAction) {
        if (listener != null) {
            listener.error(e, userAction);
        }

        getViewModel().showFailedOperation(userAction, e);
    }

    public UserPortraitListener getListener() {
        return listener;
    }

    public void setListener(UserPortraitListener listener) {
        this.listener = listener;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public long getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(long portraitId) {
        this.portraitId = portraitId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean getEditable() {
        return editable;
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Checks if exists {@link #portraitId} and {@link #uuid} or {@link #userId} attributes
     * and then calls {@link #load()} method. It's not necessary to be logged in.
     */
    protected void autoLoad() {
        if (((portraitId != 0) && (uuid != null)) || (userId != 0)) {
            try {
                load();
            } catch (Exception e) {
                LiferayLogger.e("Error loading portrait", e);
            }
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.UserPortraitScreenlet, 0, 0);

        autoLoad = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_autoLoad, true);
        male = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_male, true);
        portraitId = typedArray.getInt(R.styleable.UserPortraitScreenlet_portraitId, 0);
        uuid = typedArray.getString(R.styleable.UserPortraitScreenlet_uuid);
        editable = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_editable, false);
        userId = typedArray.getInt(R.styleable.UserPortraitScreenlet_userId, 0);

        int layoutId = typedArray.getResourceId(R.styleable.UserPortraitScreenlet_layoutId, getDefaultLayoutId());

        typedArray.recycle();

        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected Interactor createInteractor(String actionName) {
        if (UPLOAD_PORTRAIT.equals(actionName)) {
            return new UserPortraitUploadInteractor();
        } else {
            return new UserPortraitLoadInteractor();
        }
    }

    @Override
    protected void onUserAction(String userActionName, Interactor interactor, Object... args) {
        switch (userActionName) {
            case UPLOAD_PORTRAIT:
                UserPortraitUploadInteractor userPortraitInteractor =
                    (UserPortraitUploadInteractor) getInteractor(userActionName);
                Uri path = (Uri) args[0];
                if (userId != 0) {
                    userPortraitInteractor.start(new UserPortraitUploadEvent(path));
                }
                break;
            case LOAD_CURRENT_USER:
                UserPortraitLoadInteractor userPortraitLoadInteractorCurrentUser =
                    (UserPortraitLoadInteractor) getInteractor(userActionName);
                long currentUserId = SessionContext.isLoggedIn() ? SessionContext.getCurrentUser().getId() : 0;
                userPortraitLoadInteractorCurrentUser.start(currentUserId);
                break;
            default:
                UserPortraitLoadInteractor userPortraitLoadInteractor =
                    (UserPortraitLoadInteractor) getInteractor(userActionName);
                if (portraitId != 0 && uuid != null) {
                    userPortraitLoadInteractor.start(male, portraitId, uuid);
                } else {
                    userPortraitLoadInteractor.start(userId);
                }
                break;
        }
    }

    @Override
    protected void onScreenletAttached() {
        if (autoLoad) {
            autoLoad();
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable inState) {
        Bundle bundle = (Bundle) inState;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    private void startShadowActivityForMediaStore(int mediaStore) {
        Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

        Intent intent = new Intent(activity, MediaStoreRequestShadowActivity.class);
        intent.putExtra(MediaStoreRequestShadowActivity.MEDIA_STORE_TYPE, mediaStore);

        activity.startActivity(intent);
    }
}