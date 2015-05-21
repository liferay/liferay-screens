# test-app

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Introduction

The **test-app** application is a showcase of all the main screenlets distributed inside *Liferay Screens* core. The entry point is *MainActivity* and  presents a list of buttons that direct to an specific activity with an example of how to use each screenlet. All screenlets are presented in two view sets, *default* and *material*. 

Remember to change the *server_context.xml* to point to your Liferay Portal instance. Note that some screenlets may need to be logged in to work.

## Dependencies

The test application for Liferay Screens uses the following dependencies:

- **Material view set**: *test-app* presents all the screenlet currently available in both view sets, you can change them anytime using the *Change Theme* button.
- **AddBookmarkScreenlet**: an screenlet created following the [screenlet creation guide](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md) and distributed through jCenter.
- **Liferay Screens**: as you can already imagine, *test-app* uses Liferay Screens.

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The *test-app* currently showcases the following screenlets:

- [AssetListScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/AssetListScreenlet.md)
- [DLLFormScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLFormScreenlet.md)
- [DDLListScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLListScreenlet.md)
- [LoginScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/LoginScreenlet.md)
- [ForgotPasswordScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/ForgotPasswordScreenlet.md)
- [SignUpScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/SignUpScreenlet.md)
- [UserPortraitScrenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/UserPortraitScreenlet.md)
- [WebContentDisplayScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/WebContentDisplayScreenlet.md)
- [AddBookmarkScreenlet](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md)





