# Liferay Screens

## Introduction

Screens is Liferay's effort to speed up and simplify the development of native apps for mobile devices. This may grow to include apps for any of the new categories of smart devices that are currently emerging. Liferay Screens provides a common infrastructure and a family of components called _screenlets_ that are connected to the Liferay platform. This allows your mobile applications to leverage the wide variety of server-side features that Liferay already provides.

Before Screens, you could leverage those features by developing mobile apps using the [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk). However, Mobile SDK is a low-level layer on top of the Liferay JSON API. This means that you need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them. If you already know some of those Liferay concepts, then you may want to take a look at the [Mobile SDK documentation](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en) to check whether it's convenient for your needs.

The goal of Screens is to speed up mobile app development by hiding the additional complexity of calling Liferay's remote services. Screens gives you a set of screenlets that make all the server calls under the hood, so you don't have to deal with remote services, JSON responses, or any other seemingly strange parameters. With Screens you're able to customize the UI, hide or extend a screenlet's features, or even override the default behavior to implement specific features in your project.

## iOS

The implementation of Screens for iOS devices is the most mature at the moment. It's developed using the new Swift language and modern development techniques. It will be eventually distributed with CocoaPods, but until Swift is supported in this project [[1](https://github.com/CocoaPods/CocoaPods/pull/2222), [2](https://github.com/CocoaPods/CocoaPods/issues/2272)] you have to include Screen's source code in your project.

## Android

The implementation of Screens for Android is still in its early stages. It is not available to the community at this time. We hope to make it available in the next few months. 
