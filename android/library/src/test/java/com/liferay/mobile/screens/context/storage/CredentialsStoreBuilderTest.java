package com.liferay.mobile.screens.context.storage;

import android.content.Context;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * @author Víctor Galán Grande
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class CredentialsStoreBuilderTest {

    @Before
    public void setUp() {
        LiferayScreensContext.reinit(RuntimeEnvironment.application);
    }

    @Test
    public void shouldCreateAVoidStorageWithNONEType() {
        Context context = RuntimeEnvironment.application.getApplicationContext();

        CredentialsStorage storage = new CredentialsStorageBuilder().setContext(context)
            .setStorageType(CredentialsStorageBuilder.StorageType.NONE)
            .build();

        Assert.assertTrue(storage instanceof CredentialsStorageVoid);
    }

    @Test
    public void shouldCreateASharedPreferencesWithAutoTypeAndBasicAuth() {
        Authentication authentication = new BasicAuthentication("test", "test");
        Context context = RuntimeEnvironment.application.getApplicationContext();

        CredentialsStorage storage = new CredentialsStorageBuilder().setContext(context)
            .setAuthentication(authentication)
            .setStorageType(CredentialsStorageBuilder.StorageType.AUTO)
            .build();

        Assert.assertTrue(storage instanceof BasicCredentialsStorageSharedPreferences);
    }

    @Test
    public void shouldCreateASharedPreferencesWithSharedPreferencesTypeAndBasicAuth() {
        Authentication authentication = new BasicAuthentication("test", "test");
        Context context = RuntimeEnvironment.application.getApplicationContext();

        CredentialsStorage storage = new CredentialsStorageBuilder().setContext(context)
            .setAuthentication(authentication)
            .setStorageType(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES)
            .build();

        Assert.assertTrue(storage instanceof BasicCredentialsStorageSharedPreferences);
    }
}
