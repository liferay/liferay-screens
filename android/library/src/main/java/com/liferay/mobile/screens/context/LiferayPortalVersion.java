package com.liferay.mobile.screens.context;

/**
 * @author Javier Gamarra
 */
public enum LiferayPortalVersion {

    VERSION_62(62), VERSION_70(70), VERSION_71(71), VERSION_72(72), VERSION_73(73);

    private final int version;

    LiferayPortalVersion(int version) {
        this.version = version;
    }

    public static LiferayPortalVersion fromInt(int version) {
        if (version == 62) {
            return VERSION_62;
        } else if (version == 71) {
            return VERSION_71;
        } else if (version == 72) {
            return VERSION_72;
        } else if (version == 73) {
            return VERSION_73;
        } else {
            return VERSION_70;
        }
    }

    public int getVersion() {
        return version;
    }
}
