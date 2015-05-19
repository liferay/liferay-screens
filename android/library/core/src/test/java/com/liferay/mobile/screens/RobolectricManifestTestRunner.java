package com.liferay.mobile.screens;

import org.junit.runners.model.InitializationError;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

public class RobolectricManifestTestRunner extends org.robolectric.RobolectricGradleTestRunner {

	public RobolectricManifestTestRunner(Class<?> aClass) throws InitializationError {
		super(aClass);
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		String appRoot = "./core/src/main/";
		String manifestPath = appRoot + "AndroidManifest.xml";
		String resDir = appRoot + "res";
		String assetsDir = appRoot + "assets";
		AndroidManifest manifest = createAppManifest(Fs.fileFromPath(manifestPath),
			Fs.fileFromPath(resDir),
			Fs.fileFromPath(assetsDir));

		manifest.setPackageName("com.my.package.name");
		// Robolectric is already going to look in the  'app' dir ...
		// so no need to add to package name
		return manifest;
	}
}