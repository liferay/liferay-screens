# Bank of Westeros

The *Bank of Westeros* application is an example application built with Liferay Screens to manage technical issues in a bank. It allows new user registration with Liferay Portal, editing of existing user details, and editing of other portal content.

## Compatibility

- Xcode 6.3 or above
- iOS 8

## Features

The project is organized into two components: 

- **Westeros Theme**: customizes the look and feel of the Screenlets used in the app. For some Screenlets, it also extends the functionality.
- **Westeros App**: the app that uses the Screenlets in its view controllers.

The Bank of Westeros app currently showcases the following Screenlets:

- [Login Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/loginscreenlet-for-ios): Lets users to log in to Liferay Portal. The app uses a custom Login Screenlet with an [Extended Theme](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/creating-ios-themes) that adds a button to show the password characters in plaintext.
- [Sign Up Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/signupscreenlet-for-ios): Lets new users sign up with the Westeros Bank portal. Two Themes are added to this Screenlet: one to be used as a registration form, and the other to let the user edit their profile.
- [Forgot Password Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/forgotpasswordscreenlet-for-ios): Sends an email to the user if they forget their password.
- [DDL List Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddllistscreenlet-for-ios): Lists all open issues in the portal. This uses a [third party component](https://github.com/MortimerGoro/MGSwipeTableCell) to let the user swipe to show option buttons.
- [DLL Form Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/ddlformscreenlet-for-ios): Lets the user create new issues and edit existing issues. It uses two different Themes: one to create a new issue, and the other to edit an existing one.
- [User Portrait Screenlet](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/userportraitscreenlet-for-ios): Shows the user's portrait from the portal. The Screenlet is *editable*, allowing the user to upload new photos. 

