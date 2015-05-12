# Troubleshooting & FAQs

We recommend compiling your project against the latest Android version (currently 22 when writing this article) and using the **latest Android Studio version** (1.2.1.1). Liferay Screens also works with Eclipse ADT or manual building using Gradle but Android Studio is the preferred IDE.

If you are having trouble using Liferay Screens, check out the sample apps ([Westeros Bank](https://github.com/liferay/liferay-screens/tree/master/android/samples/bankofwesteros) and [Test App](https://github.com/liferay/liferay-screens/tree/master/android/samples/test-app)), both contain good examples on how to use the more relevant screenlets.

If you really got stuck, post the question to our [forum](https://www.liferay.com/community/forums/-/message_boards/category/42706063), we will happy to assist you :)

Found a bug or want to suggest an improvement? File a ticket in our [Jira](https://issues.liferay.com/browse/LMW/) (you have to [login](https://issues.liferay.com/login.jsp?os_destination=%2Fbrowse%2F) first to be able to see the project).

## Troubleshooting

### Could not find com.liferay.mobile:liferay-screens

Gradle is not able to find liferay screens or the repository. 

First, check that the version number corresponds with an existing version uploaded to jcenter. You can use this [link](https://bintray.com/liferay/liferay-mobile/liferay-screens/view) to see all uploaded versions.

It could be possible that you are using an old gradle plugin that does not use jcenter as the default repository (we use version 1.2.3 and newer). You can add jcenter as a new repository with this code:

```groovy
repositories {
	jcenter()
}
```

### Failed to resolve: com.android.support:appcompat-v7

Liferay Screens uses the appcompat library from Google, as all the new Android projects created with a new version of Android Studio.

Appcompat uses a custom repository, maintained by Google, so it has to be updated manually with the Android SDK manager.

In the Android SDK manager (located in *tools*, *android*, *SDK manager* in Android Studio) you have to install at least version 14 of the Android Support Repository (in *Extras* menu) and 22.1.1 of the Android Support Library.

### Duplicate files copied in APK META-INF (...)

A common Android Error when using libraries, Gradle can't merge duplicated files such as license files or notices.
 
To prevent this error, you should add this code to your build.gradle file:

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

Liferay Screens (and the underlying Mobile SDK) can't connect to the Liferay Portal instance. 

Check the IP address of the server or if you have overridden the default IP in the server_context.xml file ([override server_context](https://github.com/liferay/liferay-screens/tree/master/android/README.md#override-server-context))

Remember that in Genymotion you have to use 192.168.56.1 instead of localhost.

### java.io.IOException: open failed: EACCES (Permission denied)

Some Screenlets use temporal files to store information, like the user portrait screenlet to upload a new portrait or the DDL Form to upload new files to the portal.

You have to add the necessary permissions to your app if you use that specific functionality. To do so, you have to add this line to your AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

If you are using the camera, you will also have to add another permission:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

## FAQs

### Do I have to use Android Studio?

No, Liferay Screens works with Eclipse ADT or compiling manually (with Gradle or not). Just use the compiled aar in your lib folder.

### Support for iOS Apps?

Of course, check out our twin [project](https://github.com/liferay/liferay-screens/tree/master/ios)

### How do you treat the orientation-change problem?

We are using an event bus (specifically, the EventBus library) to notify the activities when the interactor has finished his work.

### How can I use a Liferay feature not available in Liferay Screens?

There are several ways you can use new features, for example, using [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk) directly that gives you access to all remote APIs or creating a custom screenlet.

### How can I create a new Screenlet?

It is explained in detail [here](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md)

### How can I customize an screenlet?

There is a [guide](https://github.com/liferay/liferay-screens/blob/master/android/documentation/view_creation.md) for that!

### Offline Support or Push Notifications?

Will be ready soon! :D