package com.liferay.mobile.screens.sites;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.group.GroupService;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordEvent;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Javier Gamarra
 */
public class SiteInteractorImpl extends BaseRemoteInteractor<SiteListener>
	implements SiteInteractor {

	public SiteInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void loadSites(Long userId) throws Exception {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new InteractorAsyncTaskCallback<Site>(getTargetScreenletId()) {
			@Override
			public Site transform(Object obj) throws Exception {
				return new Site(obj);
			}

			@Override
			protected BasicEvent createEvent(int targetScreenletId, Exception e) {
				return new BasicEvent(getTargetScreenletId()) {
				};
			}

			@Override
			protected BasicEvent createEvent(int targetScreenletId, Site result) {
				return null;
			}

		});
		GroupService groupService = new GroupService(session);
		groupService.getUserSites();
	}

	public void onEvent(BasicEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().events(event.getSites());
		}
		else {
//			getListener().onForgotPasswordRequestSuccess(
//				event.isPasswordSent());
		}
	}

	private long _userId;
}
