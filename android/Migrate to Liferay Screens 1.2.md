# Migrate to Liferay Screens 1.2

## Android SDK

* We recommend using 23 as the `compileSdkVersion`, that means using 23.0.1 or later for android support libraries (appcompat, design...) like this:

```xml

compile 'com.android.support:appcompat-v7:23.+'

```

## Offline changes

* We have added 3 new callbacks in the screenlets that support offline. You don't need to do anything on them, just implement them empty when setting a onValueChangedListener. Like this:

```java
@Override
public void loadingFromCache(boolean success) {		
}

@Override
public void retrievingOnline(boolean triedInCache, Exception e) {

}

@Override
public void storingToCache(Object object) {

}
```

Alt + enter in Android Studio when selecting the compiler error can automatically at those methods.

* We have renamed `DDLEntry` to `Record` (in fact we have merged both entities)
	* `getAttributes(_)` is now `getModelAttributes().get(_)`
	* `getValue(_)` is now `getModelValues().get(_)`

## Mobile SDK Changes

* `JSONObjectAsyncTaskCallback` has been renamed to `JSONObjectCallback` from (com.liferay.mobile.android.callback.typed).
* There are other few small naming changes in the `Mobile SDK` classes, you can see the changes done to the [Liferay Screens project](https://github.com/liferay/liferay-screens/commit/891ae692b2623f74fd19065a5dd45098e45c5fbf)
* There are breaking changes with the `DownloadUtils` class of the Mobile SDK, you have to use `HttpUtil.download` for the same task