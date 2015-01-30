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

import android.net.Uri;
import android.util.Base64;

import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitInteractorImpl
	extends BaseInteractor<Target>
	implements UserPortraitInteractor {

	@Override
	public void load(boolean male, long portraitId, String uuid) throws Exception {
		validate(portraitId, uuid);

		Uri uri = getUserPortraitURL(male, portraitId, uuid);

		Picasso.with(LiferayScreensContext.getContext()).load(uri).into(getListener());
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