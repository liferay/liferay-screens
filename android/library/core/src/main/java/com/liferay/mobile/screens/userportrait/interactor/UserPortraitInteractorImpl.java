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

package com.liferay.mobile.screens.userportrait.interactor;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitInteractorImpl
	extends BaseRemoteInteractor<UserPortraitInteractorListener>
	implements UserPortraitInteractor, Target {

	public UserPortraitInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void load(boolean male, long portraitId, String uuid) throws Exception {
		validate(portraitId, uuid);

		Uri uri = getUserPortraitURL(male, portraitId, uuid);

		if (getListener() != null) {
			getListener().onStartUserPortraitRequest();
		}

		Picasso.with(LiferayScreensContext.getContext()).load(uri).into(this);
	}

	@Override
	public void load(long userId) throws Exception {
		validate(userId);

		if (SessionContext.getLoggedUser().getId() == userId) {
			boolean male = true;
			long portraitId = SessionContext.getLoggedUser().getPortraitId();
			String uuid = SessionContext.getLoggedUser().getUuid();

			load(male, portraitId, uuid);
		}
		else {
			if (getListener() != null) {
				getListener().onStartUserPortraitRequest();
			}

			getUserService().getUserById(userId);
		}
	}

	public void onEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onUserPortraitFailure(event.getException());
		}
		else {
			JSONObject userAttributes = event.getJSONObject();

			try {
				boolean male = true;
				long portraitId = userAttributes.getLong("portraitId");
				String uuid = userAttributes.getString("uuid");

				load(male, portraitId, uuid);
			}
			catch (Exception e) {
				getListener().onUserPortraitFailure(e);
			}
		}
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
		if (getListener() != null) {
			getListener().onEndUserPortraitRequest(bitmap);
		}
	}

	@Override
	public void onBitmapFailed(Drawable errorDrawable) {
		if (getListener() != null) {
			getListener().onUserPortraitFailure(new IOException("Portrait cannot be loaded"));
		}
	}

	@Override
	public void onPrepareLoad(Drawable placeHolderDrawable) {
	}


	private void validate(long portraitId, String uuid) {
		if (getListener() == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		if (portraitId == 0) {
			throw new IllegalArgumentException("portraitId cannot be null");
		}
		if (uuid == null || uuid.isEmpty()) {
			throw new IllegalArgumentException("userId cannot be null or empty");
		}
	}

	private void validate(long userId) {
		if (userId == 0) {
			throw new IllegalArgumentException("userId cannot be null");
		}
	}

	protected UserService getUserService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		return new UserService(session);
	}


	private Uri getUserPortraitURL(boolean male, long portraitId, String uuid) {
		String maleString = male ? "male" : "female";
		String url = LiferayServerContext.getServer() + "/image/user_" + maleString + "/_portrait?img_id=" + portraitId + "&img_id_token=" + getSHA1String(uuid);
		return Uri.parse(url);
	}

	private String getSHA1String(String uuid)  {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");

			digest.update(uuid.getBytes());

			byte[] bytes = digest.digest();
			String token = Base64.encodeToString(bytes, Base64.NO_WRAP);

			return URLEncoder.encode(token, "UTF8");

		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}

		return null;
	}

}