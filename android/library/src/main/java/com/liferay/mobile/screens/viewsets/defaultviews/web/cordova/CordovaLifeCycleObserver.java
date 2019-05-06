package com.liferay.mobile.screens.viewsets.defaultviews.web.cordova;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * @author Víctor Galán Grande
 */

public class CordovaLifeCycleObserver {

    private CordovaLifeCycleListener listener;

    public CordovaLifeCycleObserver() {

    }

    public void setListener(CordovaLifeCycleListener listener) {
        this.listener = listener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (listener != null) {
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPause() {
        if (listener != null) {
            listener.onPause();
        }
    }

    public void onStop() {
        if (listener != null) {
            listener.onStop();
        }
    }

    public void onStart() {
        if (listener != null) {
            listener.onStart();
        }
    }

    public void onResume() {
        if (listener != null) {
            listener.onResume();
        }
    }

    public void onDestroy() {
        if (listener != null) {
            listener.onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (listener != null) {
            listener.onSaveInstanceState(outState);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (listener != null) {
            listener.onConfigurationChanged(newConfig);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (listener != null) {
            listener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
