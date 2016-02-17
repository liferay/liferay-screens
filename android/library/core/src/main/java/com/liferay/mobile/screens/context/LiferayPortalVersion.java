package com.liferay.mobile.screens.context;

/**
 * @author Javier Gamarra
 */
public enum LiferayPortalVersion {

	VERSION_62, VERSION_70;

	public static LiferayPortalVersion fromInt(int version) {
		if (version == 70) {
			return VERSION_70;
		}
		else {
			return VERSION_62;
		}
	}
}
