package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.group.GroupService;
import com.liferay.mobile.screens.sites.SiteConnector;

/**
 * @author Javier Gamarra
 */
public class SiteConnector70 implements SiteConnector {
	public SiteConnector70(Session session) {
		_groupService = new GroupService(session);
	}

	@Override
	public void getSites() throws Exception {
//		_groupService.get();
	}

	private final GroupService _groupService;
}
