package com.liferay.mobile.screens.context;

/**
 * @author Javier Gamarra
 */
public enum LiferayPortalVersion {

	VERSION_62(62), VERSION_70(70);

	private final int _version;

	LiferayPortalVersion(int version) {
		_version = version;
	}

	public int getVersion() {
		return _version;
	}

	public static LiferayPortalVersion fromInt(int version) {
		if (version == 70) {
			return VERSION_70;
		}
		else {
			return VERSION_62;
		}
	}
}
