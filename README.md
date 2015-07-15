# Liferay Screens

[![License](http://img.shields.io/badge/license-LGPL_2.1-red.svg?style=flat-square)](http://opensource.org/licenses/LGPL-2.1) [![Release](http://img.shields.io/badge/release-Beta_3-orange.svg?style=flat-square)](https://github.com/liferay/liferay-screens/releases/) [![Tag](http://img.shields.io/github/tag/liferay/liferay-screens.svg?style=flat-square)](https://github.com/liferay/liferay-screens/tags/)

[![iOS Platform](http://img.shields.io/badge/platform-iOS_7+-blue.svg?style=flat-square)](https://github.com/liferay/liferay-screens/tree/master/ios) [![Build Status](http://img.shields.io/travis/liferay/liferay-screens.svg?style=flat-square)](https://travis-ci.org/liferay/liferay-screens/)

[![Android Platform](http://img.shields.io/badge/platform-Android_4.0-green.svg?style=flat-square)](https://github.com/liferay/liferay-screens/tree/master/android) [![Build Status](http://img.shields.io/travis/liferay/liferay-screens.svg?style=flat-square)](https://travis-ci.org/liferay/liferay-screens/)

## Table of Contents

[What is Liferay Screens?](#introduction)

Liferay Screens for iOS
- [Overview](#overview-of-liferay-screens-for-ios)
- [Use](ios/README.md)
- [Creating Themes](ios/Documentation/theme_creation.md)
- [Creating Screenlets](ios/Documentation/screenlet_creation.md)

Liferay Screens for Android
- [Overview](#overview-of-liferay-screens-for-android)
- [Use](android/README.md)
- [Creating Views](android/documentation/view_creation.md)
- [Creating Screenlets](android/documentation/screenlet_creation.md)

[Contributing to the Project](CONTRIBUTING.md)

## Introduction

Screens is Liferay's effort to speed up and simplify the development of native apps for mobile devices. This may grow to include apps for any of the new categories of smart devices that are currently emerging. Liferay Screens provides a common infrastructure and a family of components called *screenlets* that are connected to the Liferay platform. This allows your mobile applications to leverage the wide variety of server-side features that Liferay already provides.

Before Screens, you could leverage those features by developing mobile apps using the [Liferay Mobile SDK](https://www.liferay.com/community/liferay-projects/liferay-mobile-sdk/overview). However, Mobile SDK is a low-level layer on top of the Liferay JSON API. This means that you need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them. If you already know some of those Liferay concepts, then you may want to take a look at the [Mobile SDK documentation](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/mobile) to check whether it's convenient for your needs.

![App based on Liferay Screens](ios/Documentation/Images/screens-phone.png)

The goal of Screens is to speed up mobile app development by hiding the additional complexity of calling Liferay's remote services. Screens gives you a set of screenlets that make all the server calls under the hood, so you don't have to deal with remote services, JSON responses, or any other seemingly strange parameters. With Screens you're able to customize the UI, hide or extend screenlets' features, or even override the default behavior to implement specific features in your project. What's more, Screens can be seamlessly integrated into any of your existing projects.

## Overview of Liferay Screens for iOS

The implementation of Screens for iOS devices uses standard development tools for iOS, such as Xcode, iOS SDK, iOS Simulator, and others. Screens is developed using the new Swift language and modern development techniques, such as functional Swift code and the [Model View Presenter architecture](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter). This makes Screens a great way to construct your app in a modular way.

### Getting Started on iOS

If you want to add Liferay Screens to your project right away, just jump to the section [Preparing Your Project for Liferay Screens](ios#preparing-your-project-for-liferay-screens).

Once your project is ready, the following screenlets are available for use in your app:

- [`LoginScreenlet`](ios/Documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay Portal.
- [`SignUpScreenlet`](ios/Documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay Portal.
- [`ForgotPasswordScreenlet`](ios/Documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
- [`UserPortraitScreenlet`](ios/Documentation/UserPortraitScreenlet.md): Gives your app the ability to show the user's portrait picture.
- [`DDLFormScreenlet`](ios/Documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled out by users and submitted back to the server.
- [`DDLListScreenlet`](ios/Documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing [Dynamic Data List](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-web-forms-and-dynamic-data-lists) in a Liferay instance.
- [`AssetListScreenlet`](ios/Documentation/AssetListScreenlet.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). Assets include web content, blog entries, documents and more.
- [`WebContentDisplayScreenlet`](ios/Documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features available in [Web Content Management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/web-content-management).

You can also check out the [Showcase app](ios/Samples/README.md) to learn how to configure and use the above screenlets.

Liferay Screens for iOS also contains a set of *themes* that you can use to change the UI and UX of the screenlets:

- **Default**: The standard theme that is used when you insert any screenlet in your app's UI. The Default theme can be used as the parent theme for any of your custom themes. Please refer to the [Architecture Guide](ios/Documentation/architecture.md#theme-layer) for more details on this.
- **Flat7**: A sample theme intended to demonstrate how to develop your own full theme from scratch. Please refer to the [Theme Guide](ios/Documentation/themes.md) for instructions on creating your own theme.
- **Westeros**: A custom theme created to customize the behaviour and appearance of the [Westeros Bank](ios/Samples/WesterosBank/README.md) demo app.


## Overview of Liferay Screens for Android

Screens for Android uses Android's standard set of development tools, including the Android SDK (starting from Android 4.0, API Level 15) and Android Studio. Since [Screens' architecture](android/documentation/architecture.md) is designed to isolate the screenlets, using Screens is a great way to construct your app in a modular fashion.

### Getting Started on Android

To quickly add Liferay Screens to your project, see the section [Preparing Your Project for Liferay Screens](android#preparing-your-project-for-liferay-screens).

Once your project is ready, you can use the following screenlets in your app:

- [`LoginScreenlet`](android/documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay Portal.
- [`SignUpScreenlet`](android/documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay Portal.
- [`ForgotPasswordScreenlet`](android/documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
- [`UserPortraitScreenlet`](android/documentation/UserPortraitScreenlet.md): Gives your app the ability to show the user's portrait picture.
- [`DDLFormScreenlet`](android/documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled out by users and submitted back to the server.
- [`DDLListScreenlet`](android/documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing [Dynamic Data List](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-web-forms-and-dynamic-data-lists) in a Liferay instance.
- [`AssetListScreenlet`](android/documentation/AssetListScreenlet.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). Assets include web content, blog entries, documents, users and more.
- [`WebContentDisplayScreenlet`](android/documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features available in [Web Content Management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/web-content-management).

Liferay Screens for Android also contains a set of *views* that you can use to change the UI and UX of the screenlets:

- **Default views**: The standard views used when you add a screenlet in your app's activities or fragments and don't set the `liferay:layoutId` attribute. The Default views can be used as parent views for any of your custom views. Refer to the [Architecture Guide](android/documentation/architecture.md#view-layer) for more details.
- **Material views**: The sample views intended to demonstrate how to develop your own full view set from scratch. Refer to the [Views Guide](android/documentation/views.md) for instructions on creating your own view set.
- **Westeros**: A custom view set created to customize the behaviour and appearance of the [Westeros Bank](android/samples/bankofwesteros/README.md) demo app.

## Contributing

Liferay welcomes any and all contributions! Please read the [Liferay Screens Contribution Guide](CONTRIBUTING.md) for details on developing and submitting your contributions.

## License

This library is free software ("Licensed Software"); you can redistribute it and/or modify it under the terms of the [GNU Lesser General Public License](http://www.gnu.org/licenses/lgpl-2.1.html) as
published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; including but not limited to, the implied warranty of MERCHANTABILITY, NONINFRINGEMENT, or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the [GNU Lesser General Public
License](http://www.gnu.org/licenses/lgpl-2.1.html) along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
Floor, Boston, MA 02110-1301 USA
