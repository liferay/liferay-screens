# Test App

The *test-app* app is a showcase of all the main Screenlets distributed with Liferay Screens. The app's entry point is `MainActivity`, which presents buttons that direct the user to other activities containing each Screenlet. All Screenlets are presented the *Default* and *Material* View Sets. Before running the app, remember to change `server_context.xml` to point to your Liferay Portal instance. Note that the user may need to be logged in for some Screenlets to work. 

## Dependencies

The test application for Liferay Screens has the following dependencies:

- **Material View Set**: The *test-app* presents all Screenlets currently available in both View Sets. You can change the View Set at any time by using the *Change Theme* button.
- **Add Bookmark Screenlet**: A Screenlet created in the [Screenlet creation tutorial](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/creating-android-screenlets), and distributed through jCenter.
- **Liferay Screens**: Of course, the *test-app* uses Liferay Screens.

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The *test-app* currently showcases the following Screenlets:

- [Asset List Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/assetlistscreenlet-for-android)
- [DLL Form Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddlformscreenlet-for-android)
- [DDL List Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddllistscreenlet-for-android)
- [Login Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/loginscreenlet-for-android)
- [Forgot Password Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/forgotpasswordscreenlet-for-android)
- [Sign Up Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/signupscreenlet-for-android)
- [User Portrait Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/userportraitscreenlet-for-android)
- [Web Content Display Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/webcontentdisplayscreenlet-for-android)
- [Add Bookmark Screenlet](../addbookmarkscreenlet)

