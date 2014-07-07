# Liferay Mobile Widgets

## Introduction
Mobile Widgets is the Liferay's effort to speed up and simplify the development of mobile apps that use Liferay Portal as backend.

It's currently possible to develop mobile apps using [Liferay Mobile SDK](https://github.com/liferay/liferay-mobile-sdk "Liferay Mobile SDK"). Mobile SDK is low-level layer on top of Liferay JSON API, so it means that, besides to your iOS or Android experience, you'll need to know what [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en) are and how to make specific calls to them (some services are trivial, but others could be a bit tricky).
However, if you already know some of those Liferay concepts, don't hesitate to read [Mobile SDK documentation](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en) and check whether it's convenient for your needs.

Mobile widgets aim is to hide all the complexity of calling Liferay remote services, just giving to the developer a set of GUI oriented components (widgets). These widgets will make all the server calls under the hood so you don't have to deal with remote services, JSON responses and weird parameters.
As developer, you'll be able to customize the UI, hide or extend widget's features, or even override the default behaviour to implement specific features in your project.


## iOS

Given that iOS widgets are written for in Swift, and it's currently in Beta version, the process is not as seamless as we'd wish. Instead of release the widgets as a CocoaPods (which is the standard and recommended way), you'll have to include widget's source code into your project. If you have developed apps before CocoaPods appeared, this procedure will be familiar to you. As soon as CocoaPods works fine with Swift, Cocoa frameworks and ObjC-Swift interoperativity, we'll release a Pod to make it easier.

## Android
TODO