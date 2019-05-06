package com.liferay.mobile.screens.viewsets.defaultviews.web.cordova;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * @author Víctor Galán Grande
 */

public interface CordovaLifeCycleListener {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onPause();

    void onStop();

    void onStart();

    void onResume();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onConfigurationChanged(Configuration newConfig);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
