# Liferay Screens

[![License](http://img.shields.io/badge/license-LGPL_2.1-red.svg?style=flat-square)](http://opensource.org/licenses/LGPL-2.1)
[![iOS Platform](http://img.shields.io/badge/platform-iOS_7+-blue.svg?style=flat-square)](https://github.com/liferay/liferay-screens/tree/master/ios)

[![Build Status](http://img.shields.io/travis/jmnavarro/liferay-screens.svg?style=flat-square)](https://travis-ci.org/jmnavarro/liferay-screens/)
[![Release](http://img.shields.io/badge/release-Beta_1-orange.svg?style=flat-square)](https://github.com/liferay/liferay-screens/releases/)
[![Tag](http://img.shields.io/github/tag/liferay/liferay-screens.svg?style=flat-square)](https://github.com/liferay/liferay-screens/tags/)

## Introduction

Screens is Liferay's effort to speed up and simplify the development of native apps for mobile devices. This may grow to include apps for any of the new categories of smart devices that are currently emerging. Liferay Screens provides a common infrastructure and a family of components called *screenlets* that are connected to the Liferay platform. This allows your mobile applications to leverage the wide variety of server-side features that Liferay already provides.

Before Screens, you could leverage those features by developing mobile apps using the [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk). However, Mobile SDK is a low-level layer on top of the Liferay JSON API. This means that you need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them. If you already know some of those Liferay concepts, then you may want to take a look at the [Mobile SDK documentation](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en) to check whether it's convenient for your needs.

![App based on Liferay Screens](ios/Documentation/Images/screens-phone.png)

The goal of Screens is to speed up mobile app development by hiding the additional complexity of calling Liferay's remote services. Screens gives you a set of screenlets that make all the server calls under the hood, so you don't have to deal with remote services, JSON responses, or any other seemingly strange parameters. With Screens you're able to customize the UI, hide or extend screenlet's features, or even override the default behavior to implement specific features in your project.

## iOS

The implementation of Screens for iOS devices uses standard development tools for iOS, such as Xcode, iOS SDK, iOS Simulator, and others. What's more, Screens can be seamlessly integrated into any of your existing projects. It's developed using the new Swift language and modern development techniques, such as functional Swift code and the [Model View Presenter architecture](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter). This makes Screens a great way to construct your app in a modular way.

### iOS Quick Start

If you want to add Liferay Screens to your project right away, just jump to the section [Preparing Your Project for Liferay Screens](ios#preparing-your-project-for-liferay-screens).

Once your project is ready, the following screenlets are available for use in your app:

- [`LoginScreenlet`](ios/Documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay Portal.
- [`SignUpScreenlet`](ios/Documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay Portal.
- [`ForgotPasswordScreenlet`](ios/Documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
- [`DDLFormScreenlet`](ios/Documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled out by users and submitted back to the server.
- [`DDLListScreenlet`](ios/Documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing [Dynamic Data List](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/using-web-forms-and-dynamic-data-lists-liferay-portal-6-2-user-guide-10-en) in a Liferay instance.
- [`AssetListScreenlet`](ios/Documentation/AssetListScreenlet.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). Assets include web content, blog entries, documents and more.
- [`WebContentDisplayScreenlet`](Documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features available in [Web Content Management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/web-content-management-liferay-portal-6-2-user-guide-02-en).

Liferay Screens for iOS also contains a set of *themes* that you can use to style screenlets:
	  
- **Default**: The standard theme that is used when you insert any screenlet in your app's UI. The Default theme can be used as the parent theme for any of your custom themes. Please refer to the [Architecture Guide](ios/Documentation/architecture.md#theme-layer) for more details on this.
- **Flat7**: A sample theme intended to demonstrate how to develop your own full theme from scratch. Please refer to the [Theme Guide](ios/Documentation/themes.md) for instructions on creating your own theme.

To get started with Liferay Screens for iOS, please see [this document](https://github.com/liferay/liferay-screens/tree/master/ios).

## Android

The implementation of Screens for Android is still in its early stages. It is not available to the community at this time. We hope to make it available in the next few months.
