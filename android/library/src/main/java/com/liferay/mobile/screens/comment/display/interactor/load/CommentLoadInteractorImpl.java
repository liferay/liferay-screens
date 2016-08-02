package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCache;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadEvent;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadInteractorImpl extends BaseCachedRemoteInteractor<CommentDisplayInteractorListener, CommentLoadEvent>
	implements CommentLoadInteractor {

	public CommentLoadInteractorImpl(int screenletId, OfflinePolicy offlinePolicy) {
		super(screenletId, offlinePolicy);
	}

	
}
