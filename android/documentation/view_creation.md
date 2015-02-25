# Creating a View in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

You can create your own view to give your screenlets a different look & feel, and behavior. This document explains the steps required to create your own views in Liferay Screens for Android.

Before reading this guide, you may want to read the [Architecture Guide](architecture.md) in order to understand the underlying concepts. It may also be useful to read the [Screenlet Creation Guide](screenlet_creation.md).

The first step in creating a new view is deciding what kind of view to create:

- **Full view**
- **Child view**
- **Extended view**

For more details on these views types, refer the [Views section in the Architecture Guide](architecture.md#view-layer).

The following steps illustrate view creation by creating a new view for the `LoginScreenlet`.

## Full View

A Full view can present a completely different layout, using different components and input data. The instance here presents just a single `EditText` for the user name. The [Secure.ANDROID_ID](http://developer.android.com/reference/android/provider/Settings.Secure.html#ANDROID_ID) is used for the password. 

- Create a new layout called `login_full.xml`. You'll build your new UI there as usual. A good way to start is to duplicate `login_default.xml` and use it as a template. As you can notice, use a custom view class as root view.

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

- Create a new custom view class called `LoginFullView`. As before, you can duplicate the default `LoginView` class and use it as a template. In this class, get a reference to the components, add button and add the button listener. This class must implement to the `LoginViewModel` interface, implementing the corresponding getters and setters. In this case, the `getPassword()` method will return the ANDROID_ID.

- Insert the `LoginScreenlet` in any of your activities or fragments, and use `@layout/login_full` as the value of `liferay:layoutId` attribute.

## Child View

The example view here presents the same components as the Default view, but using a completely different layout (in this case, the view are horizontally oriented, instead of vertically)

- Create a new layout called `login_horizontal.xml`. You'll build your new UI there as usual. A good way to start is to duplicate your parent's layout XML and use it as a template. The children components (like the EditTexts, Buttons, etc) must use the same identifiers as the parent view. As you can notice below, you must use the same a custom view class as the parent view.

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

- Insert the `LoginScreenlet` in any of your activities or fragments, and use `@layout/login_horizontal` as the value of `liferay:layoutId` attribute.

## Extended View

The example view here presents the same components as the Default one, but adding a new functionality to the screenlet: computes the password strength before sending the request.

- Create a new layout called `login_password.xml`. We won't build a new UI but just change the parent's custom class by a new one. In order to use the same UI, we use the `<include>` tag to import the parent's layout into ours:

```xml 
<?xml version="1.0" encoding="utf-8"?>
<com.your.package.LoginCheckPasswordView
	xmlns:android="http://schemas.android.com/apk/res/android"
    style="@style/default_screenlet">
    
	<include layout="@layout/login_default"/>

</com.your.package.LoginCheckPasswordView>
```

- Create a new view class called `LoginCheckPasswordView` using default `LoginView` as the parent class. We override the `onClick` method and perform the password strength computation there. If the password is strong enough, we call super, otherwise, we show a message dialog to the user.

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

- Insert the `LoginScreenlet` in any of your activities or fragments, and use `@layout/login_password` as the value of `liferay:layoutId` attribute.

## Packaging your views
Views are created typically as source code inside your app's project. However, if you want to distribute your views or reuse them from different projects, you need to package your views inside a project and add this project as your app's project dependency.

For that, just follow next steps:

TODO

