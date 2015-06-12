# Bank of Westeros

The *Bank of Westeros* application is an example application built with Liferay Screens to manage technical issues in a bank. It allows new user registration with Liferay Portal, editing of existing user details, and editing of other portal content. You can see the app in action [here on YouTube](https://www.youtube.com/watch?v=AroTd6zI794). You can also download it using [Google Play](https://play.google.com/store/apps/details?id=com.liferay.mobile.screens.bankofwesteros).

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The Bank of Westeros app currently showcases the following Screenlets: 

- [Login Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/loginscreenlet-for-android): Allows users to log in to Liferay Portal. The app uses a custom Login Screenlet (an [Extended View](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/creating-android-views) that adds a button to show the password characters in plain text.
- [Sign Up Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/signupscreenlet-for-android): Allows new users to sign up with the Westeros Bank portal. The app uses a custom Sign Up Screenlet (an example of creating a [Full View](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/creating-android-views)) to add a new interactor.
- [Forgot Password Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/forgotpasswordscreenlet-for-android): Sends an email to the user if they've forgotten their password.
- [DDL List Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddllistscreenlet-for-android): Lists all open issues in the portal. This uses a custom list adapter to show the issues' creation dates, custom drawables, and custom actions.
- [DLL Form Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddlformscreenlet-for-android): Allows creation of new issues, and editing of existing issues. This uses a custom View for the *select field* to alter the behavior when showing the field label.
- [User Portrait Screnlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/userportraitscreenlet-for-android): Shows the user's portrait from the portal, using an [Extended View](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/creating-android-views) to change the border color and width.
