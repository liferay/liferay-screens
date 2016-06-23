package com.liferay.mobile.screens.assetdisplay.interactor;

import android.support.annotation.NonNull;
import android.util.Log;
import com.liferay.mobile.android.callback.Callback;
import com.liferay.mobile.android.callback.typed.JSONArrayCallback;
import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.assetentry.AssetEntryService;
import com.liferay.mobile.android.v7.blogsentry.BlogsEntryService;
import com.liferay.mobile.android.v7.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.android.v7.bookmarksfolder.BookmarksFolderService;
import com.liferay.mobile.android.v7.classname.ClassNameService;
import com.liferay.mobile.android.v7.dlfileentry.DLFileEntryService;
import com.liferay.mobile.android.v7.dlfolder.DLFolderService;
import com.liferay.mobile.android.v7.journalarticle.JournalArticleService;
import com.liferay.mobile.android.v7.journalfolder.JournalFolderService;
import com.liferay.mobile.android.v7.mbmessage.MBMessageService;
import com.liferay.mobile.android.v7.wikipage.WikiPageService;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.EventBusUtil;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractorImpl extends BaseRemoteInteractor<AssetDisplayListener> implements AssetDisplayInteractor {

  public AssetDisplayInteractorImpl(int targetScreenletId) {
    super(targetScreenletId);
  }

  @Override public void getAssetEntryExtended(long entryId) throws Exception {
    Session session = getCurrentSession();

    ScreensassetentryService screensassetentryService = new ScreensassetentryService(session);
    screensassetentryService.getAssetEntryExtended(entryId, Locale.getDefault().getLanguage());
  }

  public void onEvent(JSONObjectEvent event) {
    if (!isValidEvent(event)) {
      return;
    }

    if (event.isFailed()) {
      getListener().onRetrieveAssetFailure(event.getException());
    }
    else {
      getListener().onRetrieveAssetSuccess();
    }
  }

  @NonNull private Session getCurrentSession() {
    Session session = SessionContext.createSessionFromCurrentSession();
    session.setCallback(getCallback());
    return session;
  }

  @NonNull
  private JSONArrayCallback getCallback() {
    return new JSONArrayCallback() {
      @Override
      public void onFailure(Exception exception) {
        EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), exception));
      }

      @Override
      public void onSuccess(JSONArray result) {
        EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), result));
      }
    };
  }
}
