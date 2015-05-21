# Creating a View in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

You can create your own views to give your screenlets a different look, feel, and behavior. This document explains the steps required to create your own views in Liferay Screens for Android.

Before reading this guide, you may want to read the [Architecture Guide](architecture.md) to understand the concepts underlying views. It may also be useful to read the [Screenlet Creation Guide](screenlet_creation.md).

The first step in creating a new view is deciding what kind of view to create. There are three basic view types:

- Child view (reuses Java code and changes the `xml` layout)
- Extended view (inherits the parent view's Java code)
- Full View (overrides *all* Java code, including the Screenlet code)

For more detail on these view types, see the [View Layer section in the Architecture Guide](architecture.md#view-layer). The following steps illustrate view creation by creating a new view for the `LoginScreenlet`.

## Child View

The example Child view here presents the same components as the Default view, but uses a completely different layout: the view is oriented horizontally instead of vertically. The following steps show how to create a Child view.

1. Create a new layout file called `login_horizontal.xml`. This is where the new UI is built. A good way to start is to duplicate the parent layout's XML and use it as a template. The child components such as the EditText fields, Buttons, and so on must use the same identifiers as the parent view. You must also use the same custom view class as the parent view. The example `login_horizontal.xml` is shown here: 

	```xml 
	<?xml version="1.0" encoding="utf-8"?>
	<com.liferay.mobile.screens.themes.default.auth.login.LoginView
		xmlns:android="http://schemas.android.com/apk/res/android"
		style="@style/default_screenlet"
		android:orientation="horizontal">
	
	    <EditText
			android:id="@+id/login"
			style="@style/default_edit_text"
			android:drawableLeft="@drawable/default_mail_icon"
			android:hint="@string/email_address"
			android:inputType="text" />
	
	    <EditText
			android:id="@+id/password"
			style="@style/default_edit_text"
			android:drawableLeft="@drawable/default_lock_icon"
			android:hint="@string/password"
			android:inputType="textPassword" />
	
	    <Button
			android:id="@+id/login_button"
			style="@style/default_submit_button"
			android:text="@string/sign_in" />
	
	</com.liferay.mobile.screens.themes.auth.login.LoginScreenletView>
	```

2. Insert the `LoginScreenlet` in any of your activities or fragments, and use `@layout/login_horizontal` as the `liferay:layoutId` attribute's value.

A good example of this approach is the [`SignUpScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/library/viewsets/src/main/res/layout/sign_up_material.xml) in the material viewset. It uses the default base view class and a custom layout with a different theme, colors, and spacing. 

## Extended View

The example Extended view here presents the same components as the Default view, but adds new functionality to the screenlet: the password strength is computed before sending the request.

1. Create a new layout file called `login_password.xml`, based on the default layout for that screenlet.

2. Create the new view class `LoginCheckPasswordView` using the default `LoginView` parent class. Override the `onClick` method and perform the password strength computation in it. If the password is strong enough, call `super`. Otherwise, show a message dialog to the user that tells them their password isn't strong enough.

	```java
	public class LoginCheckPasswordView extends LoginView {
	
		// parent's constructors go here...
	
		@Override
		public void onClick(View view) {
			// compute password strength
	
			if (passwordIsStrong) {
				super.onClick(view);
			}
			else {
				// Present user message
			}
		}
	
	}
	```
	
3. Change the class reference in your custom layout to point to the new class:

	```java

		<com.your.package.LoginCheckPasswordView
			xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:orientation="vertical">
			
			...
	```


4. Insert the `LoginScreenlet` in any of your activities or fragments and use `@layout/login_password` as the `liferay:layoutId` attribute's value.

Several examples of this approach are found in the [Bank of Westeros](https://github.com/liferay/liferay-screens/tree/master/android/samples/bankofwesteros) app. For example, the app's `LoginView` uses a custom view class to add a new button that shows the password in clear. The `UserPortraitView` also changes the border color and width of the user's portrait picture. The `DDLFieldSelectView` is used to change the behavior and positioning of the label.

## Full View

By using different components and input data, a Full view can present a completely different layout. The example here presents a single `EditText` component for the user name. For the password, [Secure.ANDROID_ID](http://developer.android.com/reference/android/provider/Settings.Secure.html#ANDROID_ID) is used. 

1. Create a new layout called `login_full.xml`. This is where the new UI is built. A good way to start is to duplicate `login_default.xml` and use it as a template. Use a custom view class as the root view:

	```xml
	<?xml version="1.0" encoding="utf-8"?>
	<com.your.package.LoginFullView
		xmlns:android="http://schemas.android.com/apk/res/android"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:orientation="vertical">
	
	<EditText
		android:id="@+id/login"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_marginBottom="20dp"
		android:hint="Email Address"
		android:inputType="textEmailAddress"/>

	<Button
		android:id="@+id/login_button"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:text="Sign In"/>
	
	</com.your.package.LoginFullView>
	```
	
2. Create the new custom view class used in the layout XML. In this example, this is `LoginFullView`. As before, you can use the default `LoginView` class as a template. In the new class, first get a reference to the components. The components in this example are the Add button and its listener. The class must also implement the `LoginViewModel` interface, with its getter and setter methods. In this case, the `getPassword()` method returns the `ANDROID_ID`.

3. Create a new class that inherits the base screenlet class. In this example, it could be `LoginFullScreenlet` inheriting from `SignUpScreenlet`, and adding custom behavior to the listeners or a call to a custom interactor.

4. Insert the `LoginFullScreenlet` in any of your activities or fragments, using `@layout/login_full` as the `liferay:layoutId` attribute's value.

A complete example of implementing a full view is the `LoginFullScreenlet` used in the [Demo App](https://github.com/liferay/liferay-screens/tree/master/android/samples/test-app). Another good example of this approach is the `SignUpScreenlet` found in the [Bank of Westeros](https://github.com/liferay/liferay-screens/tree/master/android/samples/bankofwesteros) app. It uses a custom screenlet class to add a new listener to the base `SignUpScreenlet`, and a new user action (this could be used to call a custom interactor).

## Packaging Your Views

Views are typically created as source code inside your app's project. If you want to distribute your views or reuse them in different projects, however, you should package them in a project that is then added as an app's project dependency. To do this, use the [material](https://github.com/liferay/liferay-screens/tree/master/android/viewsets/material) subproject as a template using a similar [`build.gradle`](https://github.com/liferay/liferay-screens/blob/master/android/viewsets/material/build.gradle) file.

To use a packaged view, you need to import the new module into your project by specifying its location in the [settings.gradle](https://github.com/liferay/liferay-screens/blob/master/android/samples/bankofwesteros/settings.gradle). The Bank of Westeros and test-app projects each use a custom theme (`westeros` and `material`, respectively). These projects are good examples of how to use an independent theme in your project. 

If you want to redistribute your theme and let others use it, you can upload it to jCenter or Maven Central. In the example [`build.gradle`](https://github.com/liferay/liferay-screens/tree/master/android/samples/bankofwesteros/build.gradle) file, after entering your bintray api key you can execute `gradlew bintrayupload` to upload your project to jCenter. When finished, you and others are able to use your theme as any other Android dependency: simply add the repository, artifact, groupId, and version to your gradle file. 
