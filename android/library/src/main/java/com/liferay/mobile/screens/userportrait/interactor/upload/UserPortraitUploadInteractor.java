package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
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
public class UserPortraitUploadInteractor
    extends BaseCacheWriteInteractor<UserPortraitInteractorListener, UserPortraitUploadEvent> {

    @Override
    public void online(UserPortraitUploadEvent event) throws Exception {
        decorateEvent(event, false);
        execute(event);
    }

    @Override
    public UserPortraitUploadEvent execute(UserPortraitUploadEvent event) {

        Context context = LiferayScreensContext.getContext();
        Intent intent = new Intent(context, UserPortraitService.class);
        intent.putExtra("pictureUri", event.getUriPath());
        intent.putExtra("actionName", event.getActionName());
        intent.putExtra("userId", event.getUserId());
        intent.putExtra("groupId", event.getGroupId());
        intent.putExtra("locale", event.getLocale());
        intent.putExtra("targetScreenletId", getTargetScreenletId());
        intent.putExtra("actionName", getActionName());

        context.startService(intent);
        return null;
    }

    @Override
    public void onSuccess(UserPortraitUploadEvent event) {

        User oldLoggedUser = SessionContext.getCurrentUser();

        User user = new User(event.getJSONObject());

        if (oldLoggedUser != null && user.getId() == oldLoggedUser.getId()) {
            SessionContext.setCurrentUser(user);
        }

        Uri userPortraitUri = new UserPortraitUriBuilder().getUserPortraitUri(LiferayServerContext.getServer(), true,
            user.getPortraitId(), user.getUuid());
        invalidateUrl(userPortraitUri);

        getListener().onUserPortraitUploaded(oldLoggedUser == null ? null : oldLoggedUser.getId());
    }

    @Override
    public void onFailure(UserPortraitUploadEvent event) {
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
}
