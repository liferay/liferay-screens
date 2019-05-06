package org.apache.cordova;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONException;

/**
 * @author Víctor Galán Grande
 */

public class ScreensCordovaActivity extends CordovaActivity {

    private Activity activity;

    public ScreensCordovaActivity(Activity activity) {
        super();
        this.activity = activity;
    }

    public CordovaWebView initCordova() {
        loadConfig();
        return makeWebView();
    }

    public void loadConfig() {
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(activity.getApplicationContext());
        this.preferences = parser.getPreferences();
        this.launchUrl = parser.getLaunchUrl();
        this.pluginEntries = parser.getPluginEntries();

        this.pluginEntries.add(new PluginEntry("RemoteInjectionPlugin",
            "com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.RemoteInjectionPlugin", true));
        this.pluginEntries.add(new PluginEntry("ScreensCordovaPlugin",
            "com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.ScreensCordovaPlugin", true));
    }

    protected CordovaWebViewEngine makeWebViewEngine() {
        return CordovaWebViewImpl.createEngine(activity, this.preferences);
    }

    public CordovaWebView makeWebView() {
        this.appView = super.makeWebView();
        this.cordovaInterface = makeCordovaInterface();
        this.appView.init(this.cordovaInterface, this.pluginEntries, this.preferences);

        return this.appView;
    }

    @Override
    protected CordovaInterfaceImpl makeCordovaInterface() {
        return new CordovaInterfaceImpl(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void handlePause() {
        LOG.d(TAG, "Paused the activity.");
        if (this.appView != null) {
            boolean keepRunning = this.keepRunning || this.cordovaInterface.activityResultCallback != null;
            this.appView.handlePause(keepRunning);
        }
    }

    public void onNewIntent(Intent intent) {
        if (this.appView != null) {
            this.appView.onNewIntent(intent);
        }
    }

    public void handleResume() {
        if (this.appView != null) {
            activity.getWindow().getDecorView().requestFocus();
            this.appView.handleResume(this.keepRunning);
        }
    }

    public void handleStop() {
        LOG.d(TAG, "Stopped the activity.");
        if (this.appView != null) {
            this.appView.handleStop();
        }
    }

    public void handleStart() {
        LOG.d(TAG, "Started the activity.");
        if (this.appView != null) {
            this.appView.handleStart();
        }
    }

    public void handleDestroy() {
        if (this.appView != null) {
            this.appView.handleDestroy();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        this.cordovaInterface.onSaveInstanceState(outState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.appView != null) {
            PluginManager pm = this.appView.getPluginManager();
            if (pm != null) {
                pm.onConfigurationChanged(newConfig);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.appView != null) {
            this.appView.getPluginManager().postMessage("onCreateOptionsMenu", menu);
        }

        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.appView != null) {
            this.appView.getPluginManager().postMessage("onPrepareOptionsMenu", menu);
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.appView != null) {
            this.appView.getPluginManager().postMessage("onOptionsItemSelected", item);
        }

        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            this.cordovaInterface.onRequestPermissionResult(requestCode, permissions, grantResults);
        } catch (JSONException ex) {
            LOG.d(TAG, "JSONException: Parameters fed into the method are not valid");
        }
    }
}
