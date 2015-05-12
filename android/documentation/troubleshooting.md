# Troubleshooting & FAQs

We recommend compiling your project against the latest Android API level (when writing this article, API level 22 was the latest) and using the **latest Android Studio version** (1.2.1.1). Liferay Screens also works with Eclipse ADT or manual building using Gradle, but Android Studio is the preferred IDE.

If you're having trouble using Liferay Screens, check out the sample apps ([Westeros Bank](https://github.com/liferay/liferay-screens/tree/master/android/samples/bankofwesteros) and [Test App](https://github.com/liferay/liferay-screens/tree/master/android/samples/test-app)). Both contain good examples on how to use the more relevant screenlets.

If you get stuck, post your question to our [forum](https://www.liferay.com/community/forums/-/message_boards/category/42706063). We're happy to assist you :)

Found a bug or want to suggest an improvement? File a ticket in our [Jira](https://issues.liferay.com/browse/LMW/). Note that you must [login](https://issues.liferay.com/login.jsp?os_destination=%2Fbrowse%2F) first to be able to see the project.

## Troubleshooting

### Could not find com.liferay.mobile:liferay-screens

Gradle isn't able to find Liferay Screens or the repository. 

First, check that the version number corresponds with an existing version uploaded to jCenter. You can use this [link](https://bintray.com/liferay/liferay-mobile/liferay-screens/view) to see all uploaded versions.

It could be possible that you're using an old gradle plugin that doesn't use jCenter as the default repository (we use version 1.2.3 and newer). You can add jCenter as a new repository with this code:

```groovy
repositories {
	jcenter()
}
```

### Failed to resolve: com.android.support:appcompat-v7

Liferay Screens uses the appcompat library from Google, as do all the new Android projects created with the latest Android Studio. The appcompat library uses a custom repository maintained by Google, so it has to be updated manually with the Android SDK manager.

In the Android SDK manager (located at *Tools* &rarr; *Android* &rarr; *SDK manager* in Android Studio) you have to install at least version 14 of the Android Support Repository (in the *Extras* menu), and version 22.1.1 of the Android Support Library.

### Duplicate files copied in APK META-INF (...)

This is a common Android error when using libraries. It occurs because gradle can't merge duplicated files such as license or notice files. To prevent this error, add the following code to your `build.gradle` file:

```groovy
android {
  ...
  packagingOptions {
      exclude 'META-INF/LICENSE'
      exclude 'META-INF/NOTICE'
  }
  ...
}
```

### connect failed: ECONNREFUSED (Connection refused) or org.apache.http.conn.HttpHostConnectException

This error occurs when Liferay Screens (and the underlying Liferay Mobile SDK) can't connect to the Liferay Portal instance. Check the IP address of the server. If you have overridden the default IP address in the `server_context.xml` file ([override server_context](https://github.com/liferay/liferay-screens/tree/master/android/README.md#override-server-context)), you should check to make sure that you've set it to the correct IP. Also, remember that in Genymotion you have to use 192.168.56.1 instead of localhost to have your app communicate with a local Liferay instance.

### java.io.IOException: open failed: EACCES (Permission denied)

Some Screenlets use temporary files to store information, such as when `UserPortraitScreenlet` uploads a new portrait, or `DDLFormScreenlet` uploads new files to the portal. You need to add the necessary permissions to your app to use that specific functionality. To grant those permissions, add the following line to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

If you're using the device's camera, you also need to add the following permission:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

## FAQs

### Do I have to use Android Studio?

No, Liferay Screens works with Eclipse ADT or compiling manually (with gradle or not). Just use the compiled `aar` in your lib folder.

### Does Screens support iOS Apps?

Of course! Check out our twin [project for iOS](https://github.com/liferay/liferay-screens/tree/master/ios). 

### How do you treat the orientation-change problem?

We're using an event bus (specifically, the `EventBus` library) to notify the activities when the interactor has finished its work.

### How can I use a Liferay feature not available in Liferay Screens?

There are several ways you can use new features. For example, using the [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk) directly gives you access to all of Liferay's remote APIs. You can also create a custom screenlet to support any features not included in Screens by default.

### How do I create a new Screenlet?

Screenlet creation is explained in detail [here](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md).

### How can I customize a screenlet?

There's a [guide](https://github.com/liferay/liferay-screens/blob/master/android/documentation/view_creation.md) for that!

### Does Screens have offline support or Push notifications?

This will be ready soon! :D