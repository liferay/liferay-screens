# test-app

## Introduction

The *test-app* app is a showcase of all the main screenlets distributed with Liferay Screens. The app's entry point is `MainActivity`, which presents buttons that direct the user to other activities containing each screenlet. All screenlets are presented the *default* and *material* view sets. Before running the app, remember to change `server_context.xml` to point to your Liferay Portal instance. Note that the user may need to be logged in for some screenlets to work. 

## Dependencies

The test application for Liferay Screens uses the following dependencies:

- **Material view set**: The *test-app* presents all screenlets currently available in both view sets. You can change the view set at any time by using the *Change Theme* button.
- **AddBookmarkScreenlet**: A screenlet created following the [screenlet creation guide](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md) and distributed through jCenter.
- **Liferay Screens**: Of course, the *test-app* uses Liferay Screens.

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The *test-app* currently showcases the following screenlets:

- [`AssetListScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/AssetListScreenlet.md)
- [`DLLFormScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLFormScreenlet.md)
- [`DDLListScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/DDLListScreenlet.md)
- [`LoginScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/LoginScreenlet.md)
- [`ForgotPasswordScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/ForgotPasswordScreenlet.md)
- [`SignUpScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/SignUpScreenlet.md)
- [`UserPortraitScrenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/UserPortraitScreenlet.md)
- [`WebContentDisplayScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/WebContentDisplayScreenlet.md)
- [`AddBookmarkScreenlet`](https://github.com/liferay/liferay-screens/blob/master/android/documentation/screenlet_creation.md)

