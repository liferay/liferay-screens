# Liferay Screens

## Introduction
Screens is Liferay's effort to speed up and simplify the development of native apps for mobile devices, tablets and potentially any of the new family of smart devices that are getting more and more popular every day. Liferay Screens provides a common infrastructure and a family of widgets that are connected to the Liferay platform as a backend. This allows leveraging the wide variety of server side features that Liferay already provides.

Before Screens, it was already possible to leverage these features by developing mobile apps using [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk "Liferay Mobile SDK"). Mobile SDK is low-level layer on top of Liferay JSON API, so it means that, besides to your iOS or Android experience, you'll need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them (some services are trivial, but others could be a bit tricky).
If you already know some of those Liferay concepts, you may want to take a look at the [Mobile SDK documentation](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en) and check whether it's convenient for your needs.

The goal of Screens is to hide all the complexity of calling Liferay remote services, just giving to the developer a set of GUI oriented components (widgets). Its widgets will make all the server calls under the hood so you don't have to deal with remote services, JSON responses and weird parameters.
As developer, you'll be able to customize the UI, hide or extend widget's features, or even override the default behaviour to implement specific features in your project.


## iOS

The implementation of screens for iOS devices is the most advanced at the moment. It's developed using the new Swift language and modern development techniques. The goal is to distribute it using CocoaPods, but until Swift is supported in this project you'll have to include screen's source code into your project. If you have developed apps before CocoaPods appeared, this procedure will be familiar to you. As soon as CocoaPods works fine with Swift, Cocoa frameworks and ObjC-Swift interoperativity, we'll release a Pod to make it easier.

## Android

The implementation for Android is still very inmature, but we hope that it will catch up iOS in the next few months.
