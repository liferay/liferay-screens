# Liferay Screens

## Introduction

Screens is Liferay's effort to speed up and simplify the development of native apps for mobile devices. This may grow to include apps for any of the new categories of smart devices that are currently emerging. Liferay Screens provides a common infrastructure and a family of components called _screenlets_ that are connected to the Liferay platform. This allows your mobile applications to leverage the wide variety of server-side features that Liferay already provides.

Before Screens, you could leverage those features by developing mobile apps using the [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk). However, Mobile SDK is a low-level layer on top of the Liferay JSON API. This means that you need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them. If you already know some of those Liferay concepts, then you may want to take a look at the [Mobile SDK documentation](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en) to check whether it's convenient for your needs.

![App based on Liferay Screens](ios/Documentation/Images/screens-phone.png)


The goal of Screens is to speed up mobile app development by hiding the additional complexity of calling Liferay's remote services. Screens gives you a set of screenlets that make all the server calls under the hood, so you don't have to deal with remote services, JSON responses, or any other seemingly strange parameters. With Screens you're able to customize the UI, hide or extend screenlet's features, or even override the default behavior to implement specific features in your project.

## iOS

The implementation of Screens for iOS devices is the most mature at the moment. It's developed using the new Swift language and modern development techniques (few functional Swift code, [Model View Presenter architecture](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter), etc.).
We currently provide the following _screenlets_:

- [LoginScreenlet](ios/Documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay Portal.

- [SignUpScreenlet](ios/Documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay Portal.

- [ForgotPasswordScreenlet](ios/Documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
- [DDLFormScreenlet](ios/Documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled by users and submitted back to the server.
- [DDLListScreenlet](ios/Documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing _[Dynamic Data Lists](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/using-web-forms-and-dynamic-data-lists-liferay-portal-6-2-user-guide-10-en)_ in a Liferay Portal.
- [AssetListScreenlet](ios/Documentation/AssetListScreenlet.md): Shows a list of assets managed by _[Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en)_. This includes web content, blog entries, documents and more.
- [WebContentDisplayScreenlet](Documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features avaiable in _[Web Content Management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/web-content-management-liferay-portal-6-2-user-guide-02-en)_.

Liferay Screens for iOS also contains a set of *themes* that you can use to style screenlets:
	  
  - **Default**: The standard theme that is used when you insert any screenlet in your screen. It can be used as the parent theme for any of your custom themes (refer to the [architecture documentation](ios/Documentation/architecture.md#theme-layer) for more details on this).
  - **Flat7**: A sample theme intended to demostrate how to develop your own full theme from scratch. (refer to [themes documentation](ios/Documentation/themes.md) if you want to create your own theme)


You can start with iOS implementation [here](https://github.com/liferay/liferay-screens/tree/master/ios).

## Android

The implementation of Screens for Android is still in its early stages. It is not available to the community at this time. We hope to make it available in the next few months.

Android implementation is in progress and will be published soon.