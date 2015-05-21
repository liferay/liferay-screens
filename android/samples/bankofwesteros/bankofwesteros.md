# Bank of Westeros

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Introduction

The **Bank of Westeros** application is an example application built with *Liferay Screens* to manage technical and computer issues in a bank. Allows creating new users against Liferay Portal, editing issues and changing user details. A video demonstrating all the features is available in [youtube](https://www.youtube.com/watch?v=AroTd6zI794).

You can also download it using [Google Play](https://play.google.com/store/apps/details?id=com.liferay.mobile.screens.bankofwesteros).



## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The *Bank of Westeros* currently showcases the following screenlets:

- [LoginScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/LoginScreenlet.md): to allow users to log in to Liferay Portal. Uses a custom *LoginScreenlet* (an [extended view](https://github.com/liferay/liferay-screens/blob/master/android/documentation/view_creation.md#extended-view) that adds a button to show the characters of the password in plain text, to help the user enter the right password.
- [SignUpScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/SignUpScreenlet.md): allows creating new users in *Westeros Bank*. Uses a custom *SignUpScreenlet* (an example of creating a [full view](https://github.com/liferay/liferay-screens/blob/master/android/documentation/view_creation.md#full-view)) to add a new interactor.
- [ForgotPasswordScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/ForgotPasswordScreenlet.md): sends an email if the user has forgotten his password.
- [DDLListScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLListScreenlet.md): lists all the open issues, uses a custom list adapter to show the date of creation of the issue, custom drawables and custom actions.
- [DLLFormScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLFormScreenlet.md): to create new issues and edit them. Uses a custom view for the *select field* to alter the behaviour when showing the field label.
- [UserPortraitScrenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/UserPortraitScreenlet.md): to show the user portrait, uses an [extended view](https://github.com/liferay/liferay-screens/blob/master/android/documentation/view_creation.md#extended-view) to change the border color and width.





