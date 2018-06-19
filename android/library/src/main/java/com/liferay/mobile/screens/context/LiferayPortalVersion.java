package com.liferay.mobile.screens.context;

/**
 * @author Javier Gamarra
 */
public enum LiferayPortalVersion {

	VERSION_62(62), VERSION_70(70), VERSION_71(71);

	private final int version;

	LiferayPortalVersion(int version) {
		this.version = version;
	}

	public static LiferayPortalVersion fromInt(int version) {
		if (version == 70) {
			return VERSION_70;
		} else {
			return VERSION_62;
		}
	}

	public int getVersion() {
		return version;
	}
}
