package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liferay.mobile.screens.base.MediaStoreEvent;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitUriBuilder;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<UserPortraitInteractorListener, UserPortraitUploadEvent> {

	@Override
	public void online(UserPortraitUploadEvent event) throws Exception {
		decorateEvent(event, false);
		String picturePath = event.getPicturePath();

		Intent intent = new Intent(LiferayScreensContext.getContext(), UserPortraitService.class);
		intent.putExtra("picturePath", picturePath);
		intent.putExtra("screenletId", getTargetScreenletId());
		intent.putExtra("actionName", event.getActionName());
		intent.putExtra("userId", event.getUserId());
		intent.putExtra("groupId", event.getGroupId());
		intent.putExtra("locale", event.getLocale());

		LiferayScreensContext.getContext().startService(intent);
	}

	@Override
	public UserPortraitUploadEvent execute(UserPortraitUploadEvent event) throws Exception {
		throw new AssertionError("should not be called!");
	}

	@Override
	public void onSuccess(UserPortraitUploadEvent event) throws Exception {

		User oldLoggedUser = SessionContext.getCurrentUser();

		User user = new User(event.getJSONObject());
		if (oldLoggedUser != null && user.getId() == oldLoggedUser.getId()) {
			SessionContext.setCurrentUser(user);
		}

		Uri userPortraitUri = new UserPortraitUriBuilder().getUserPortraitUri(LiferayServerContext.getServer(), true,
			user.getPortraitId(), user.getUuid());
		invalidateUrl(userPortraitUri);

		getListener().onUserPortraitUploaded(oldLoggedUser.getId());
	}

	@Override
	protected void onFailure(UserPortraitUploadEvent event) {
		getListener().error(event.getException(), UserPortraitScreenlet.UPLOAD_PORTRAIT);
	}

	private void invalidateUrl(Uri userPortraitURL) {
		try {
			Context context = LiferayScreensContext.getContext();

			UserPortraitUriBuilder userPortraitUriBuilder = new UserPortraitUriBuilder();
			OkHttpClient okHttpClient = userPortraitUriBuilder.getUserPortraitClient(context);

			com.squareup.okhttp.Cache cache = okHttpClient.getCache();
			Iterator<String> urls = cache.urls();
			while (urls.hasNext()) {
				String url = urls.next();
				if (url.equals(userPortraitURL.toString())) {
					urls.remove();
				}
			}

			Picasso.with(context).invalidate(userPortraitURL);
		} catch (IOException e) {
			LiferayLogger.e("Error invalidating cache", e);
		}
	}

	public void onEvent(MediaStoreEvent event) {
		getListener().onPicturePathReceived(event.getFilePath());
	}
}
