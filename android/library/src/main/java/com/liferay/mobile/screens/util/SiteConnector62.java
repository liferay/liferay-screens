package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.group.GroupService;
import com.liferay.mobile.screens.sites.SiteConnector;

/**
 * @author Javier Gamarra
 */
public class SiteConnector62 implements SiteConnector {
	public SiteConnector62(Session session) {
		_groupServiceImpl = new GroupService(session);
	}

	@Override
	public void getSites() throws Exception {
		_groupServiceImpl.getUserSites();
	}

	private final GroupService _groupServiceImpl;
}
