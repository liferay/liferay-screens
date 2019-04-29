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

package com.liferay.mobile.screens.userportrait.interactor.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitUriBuilder;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitLoadInteractor
    extends BaseCacheReadInteractor<UserPortraitInteractorListener, UserPortraitEvent> implements Target {

    @Override
    public UserPortraitEvent execute(Object... args) throws Exception {
        //TODO move to 2 interactors
        if (args.length == 1) {
            long userId = (long) args[0];

            validate(userId);

            UserConnector userConnector = ServiceProvider.getInstance().getUserConnector(getSession());

            JSONObject jsonObject = userConnector.getUserById(userId);
            return new UserPortraitEvent(jsonObject);
        } else {
            return createEventFromUUID(args);
        }
    }

    @Override
    public void onFailure(UserPortraitEvent event) {
        getListener().error(event.getException(), UserPortraitScreenlet.LOAD_PORTRAIT);
    }

    @NonNull
    private UserPortraitEvent createEventFromUUID(Object[] args) throws JSONException {
        boolean male = (boolean) args[0];
        long portraitId = (long) args[1];
        String uuid = (String) args[2];

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("male", male);
        jsonObject.put("portraitId", portraitId);
        jsonObject.put("uuid", uuid);

        return new UserPortraitEvent(jsonObject);
    }

    @Override
    public void onSuccess(UserPortraitEvent event) {

        try {
            JSONObject userAttributes = event.getJSONObject();
            long portraitId = userAttributes.getLong("portraitId");
            String uuid = userAttributes.getString("uuid");

            validate(uuid);

            if (portraitId == 0) {
                // User doesn't have portrait
                getListener().onUserWithoutPortrait(new User(userAttributes));
            } else {
                UserPortraitUriBuilder userPortraitUriBuilder = new UserPortraitUriBuilder();
                Uri uri =
                    userPortraitUriBuilder.getUserPortraitUri(LiferayServerContext.getServer(), true, portraitId, uuid);

                Context context = LiferayScreensContext.getContext();
                Downloader downloader = new OkHttpDownloader(userPortraitUriBuilder.getUserPortraitClient(context));
                Picasso picasso = new Picasso.Builder(context).downloader(downloader).build();
                RequestCreator requestCreator = picasso.load(uri);

                if (CachePolicy.REMOTE_ONLY.equals(getCachePolicy())) {
                    requestCreator =
                        requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE);
                }

                requestCreator.into(this);
            }
        } catch (JSONException ex) {
            event.setException(ex);
            onFailure(event);
        }
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        if (args.length == 1) {
            return String.valueOf(args[0]);
        } else {
            return (String) args[2];
        }
    }

    private void validate(long userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("userId cannot be empty");
        }
    }

    private void validate(String uuid) {
        if (getListener() == null) {
            throw new IllegalArgumentException("Listener cannot be empty");
        } else if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("userId cannot be empty");
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (getListener() != null) {
            getListener().onEndUserPortraitLoadRequest(bitmap);
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (getListener() != null) {
            getListener().error(new IOException("Portrait cannot be loaded"), UserPortraitScreenlet.LOAD_PORTRAIT);
        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
}