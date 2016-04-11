package com.liferay.mobile.screens.sites;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Javier Gamarra
 */
public class UserSitesScreenlet extends BaseScreenlet<UserSiteViewModel, SiteInteractor> {

	public UserSitesScreenlet(Context context) {
		super(context);
	}

	public UserSitesScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserSitesScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		int layoutId = R.layout.site;
		View view = LayoutInflater.from(context).inflate(layoutId, null);
		((ListView) view).setAdapter(new ArrayAdapter<Site>(getContext(), android.R.layout.simple_list_item_1));
		return view;
	}

	@Override
	protected SiteInteractor createInteractor(String actionName) {
		return new SiteInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, SiteInteractor interactor, Object... args) {
		try {
			interactor.loadSites(SessionContext.getUserId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OfflinePolicy _offlinePolicy;
	private long _userId;
}
