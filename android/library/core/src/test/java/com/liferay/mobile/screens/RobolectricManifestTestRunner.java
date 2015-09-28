package com.liferay.mobile.screens;

import org.junit.runners.model.InitializationError;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.res.FsFile;

public class RobolectricManifestTestRunner extends org.robolectric.RobolectricGradleTestRunner {

	public RobolectricManifestTestRunner(Class<?> aClass) throws InitializationError {
		super(aClass);
	}

	protected AndroidManifest getAppManifest(Config config) {
		AndroidManifest appManifest = super.getAppManifest(config);
		FsFile androidManifestFile = appManifest.getAndroidManifestFile();

		if (androidManifestFile.exists()) {
			return appManifest;
		}
		else {
			String moduleRoot = getModuleRootPath(config);
			androidManifestFile = FileFsFile.from(moduleRoot, appManifest.getAndroidManifestFile().getPath().replace("full", "androidTest"));
			FsFile resDirectory = FileFsFile.from(moduleRoot, appManifest.getResDirectory().getPath().replace("debug", "androidTest/debug"));
			FsFile assetsDirectory = FileFsFile.from(moduleRoot, appManifest.getAssetsDirectory().getPath());
			return new AndroidManifest(androidManifestFile, resDirectory, assetsDirectory);
		}
	}

	private String getModuleRootPath(Config config) {
		String moduleRoot = config.constants().getResource("").toString().replace("file:", "");
		return moduleRoot.substring(0, moduleRoot.indexOf("/build"));
	}
}