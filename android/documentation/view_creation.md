# Creating a View in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

You can create your own views to give your screenlets a different look, feel, and behavior. This document explains the steps required to create your own views in Liferay Screens for Android.

Before reading this guide, you may want to read the [Architecture Guide](architecture.md) to understand the concepts underlying views. It may also be useful to read the [Screenlet Creation Guide](screenlet_creation.md).

The first step in creating a new view is deciding what kind of view to create. There are three basic view types:

- Full view
- Child view
- Extended view

For more detail on these view types, see the [View Layer section in the Architecture Guide](architecture.md#view-layer). The following steps illustrate view creation by creating a new view for the `LoginScreenlet`.

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
		android:hint="@string/email_address"
		android:inputType="textEmailAddress" />
	
		<Button
			android:id="@+id/login_button"
			android:text="@string/sign_in" />
	
	</com.your.package.LoginFullView>
	```
	
2. Create the new custom view class used in the layout XML. In this example, this is `LoginFullView`. As before, you can use the default `LoginView` class as a template. In the new class, first get a reference to the components. The components in this example are the Add button and its listener. The class must also implement the `LoginViewModel` interface, with its getter and setter methods. In this case, the `getPassword()` method returns the `ANDROID_ID`.

3. Insert the `LoginScreenlet` in any of your activities or fragments, using `@layout/login_full` as the `liferay:layoutId` attribute's value.

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

## Extended View

The example Extended view here presents the same components as the Default view, but adds new functionality to the screenlet: the password strength is computed before sending the request.

1. Create a new layout file called `login_password.xml`. However, there's no need to actually build a new UI. All you need to do is change the parent's class to a new custom class. To use the same UI, use the `<include>` tag to import the parent's layout:

	```xml 
	<?xml version="1.0" encoding="utf-8"?>
	<com.your.package.LoginCheckPasswordView
		xmlns:android="http://schemas.android.com/apk/res/android"
	    style="@style/default_screenlet">
	    
		<include layout="@layout/login_default"/>
	
	</com.your.package.LoginCheckPasswordView>
	```

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

3. Insert the `LoginScreenlet` in any of your activities or fragments and use `@layout/login_password` as the `liferay:layoutId` attribute's value.

<!-- 
## Packaging Your Views

Views are created typically as source code inside your app's project. However, if you want to distribute your views or reuse them from different projects, you need to package your views inside a project and add this project as your app's project dependency.

For that, just follow next steps:

TODO
-->
