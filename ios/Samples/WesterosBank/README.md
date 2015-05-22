# Bank of Westeros

## Introduction

The *Bank of Westeros* application is an example application built with Liferay Screens to manage technical issues in a bank. It allows new user registration with Liferay Portal, editing of user details, and editing of other portal contents.

![The Westeros Bank app](../../Documentation/Images/westeros-sample.png)

## Compatibility

- Xcode 6.3 or above
- iOS 8

## Features

The project is arranged in two components:
	- *Westeros Theme*: it customizes the look and feel of the screents used in the app. For some screenlets, it also extends the funcionality.
	- *Westeros App*: the app itself that uses the screenlets in its view controllers.

The Bank of Westeros app currently showcases the following screenlets:

- [`LoginScreenlet`](../../Documentation/LoginScreenlet.md): Allows users to log in to Liferay Portal. The app uses a custom `LoginScreenlet` (an [extended theme](../../Documentation/theme_creation.md#extended-theme) that adds a button to show the password characters in plain text.
- [`SignUpScreenlet`](../../Documentation/SignUpScreenlet.md): Allows new users to sign up with the Westeros Bank portal. Two themes are added to this screenlet: one to be used as a registration form, and the other to allow user profile edition.
- [`ForgotPasswordScreenlet`](../../Documentation/ForgotPasswordScreenlet.md): Sends an email to the user if they've forgotten their password.
- [`DDLListScreenlet`](../../Documentation/DDLListScreenlet.md): Lists all open issues in the portal. This uses a [third party component](https://github.com/MortimerGoro/MGSwipeTableCell) to allow user to swipe to show option buttons.
- [`DLLFormScreenlet`](../../Documentation/DDLFormScreenlet.md): Allows creation of new issues, and editing of existing issues. It uses two different themes: one to create a new issue and the other to edit an existing one.
- [`UserPortraitScrenlet`](../../documentation/UserPortraitScreenlet.md): Shows the user's portrait from the portal. The screenlet is _editable_, allowing user to upload new photos.
