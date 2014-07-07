# Liferay iOS Mobile Widget Library

## Important note
__This product is under heavy development and most of the features aren't ready to be used in production.
It's public just in order to allow developers to preview the technology.__

## Introduction

This is the library which contains all available widgets for iOS. It also includes the infrastructure classes to develop and contribute new widgets.

Note that this library is developed using iOS 8, so you'll need XCode6 in order to develop your own widgets.

## Architecture

### View and classes hierarchy

Widgets for iOS follow the architecture shown in the following diagram:

![Architecture](http://liferay.github.io/liferay-mobile-widgets/ios/Library/svg/message-hierarchy.svg)

From top to down in the view hierarchy, we have:

  - _`BarViewController`_: this is your regular view controller, based on Storyboards or xib, where you insert your widgets into. It belongs to your iOS project.
  - _`FooWidget`_: this is the view that you insert into previous view controller, using Interface Builder or programatically. Since it's based on `BaseWidget` (and this is a direct descendant of `UIControl`), it inherits all the business logic code that handles the remote services requets and responses. 
  - _`FooView`_: the framework will insert this view into `FooWidget` automatically. Its UI is defined in `FooView.xib` and can be overwritten in `FooView-ext.xib`. It defines IBOutlets and IBActions to connect UI components to code.


### Events and messages passing

When the widget is initilized, event handlers are added to all components in your component. Only `TouchUpInside` even is wired automatically, although you can wire other events using Interface Builder.
When this automatic event is fired, the flow goes as next:

![Messages](http://liferay.github.io/liferay-mobile-widgets/ios/Library/svg/message-hierarchy.svg)

The steps are:

  1. _TouchUpInside even is fired_: because all components are automatically wired with this event, this will be received for all TouchUpInside even in every component inside the widget. In order to distinguish one event from the other, an action name parameter is supplied. This parameter is filled with sender component's [restorationIdentifier](https://developer.apple.com/library/ios/documentation/uikit/reference/uiview_class/uiview/uiview.html#//apple_ref/occ/instp/UIView/restorationIdentifier) property. This property value could be filled from Interface Builder's Identity Inspector
  1. _Received event is delegated to `onCustomAction`_: `FooWidget` registerd the closure `onCustomAction` so it will receive the event there. In that callback, it will check the `actionName` parameter in order to identify the actual action and then call the remote service (using Mobile SDK)  or do any other action. If a remote service is called, it will eventually receive server's response in `onServerResult` (or an error in `onServerError`).
  1. _Both `onServerResult` and `onServerError` are delegated to view controller's closures_: the view controller may implement both methods and use them as closures in order to receive the events.
  1. Any widget may define other closures to notify other state changes to view controller's


## How to create your own widgets
If you want to develop one new widget, lets call it `Foo`, follow next steps:

1. Create a new xib called `FooView.xib`. Build your UI there as usual.
1. Create a new class called `FooView` inherited from `BaseView`.
1. Change the class of your view in `FooView.xib` to `FooView` using Interface Builder's Custom Class.
1. Declare your `IBOutlets` in `FooView` class and connect to your xib components.
1. Declare your `IBActions` in `FooView` class and connect to your xib events. Note that TouchUpInside events are automatically handled by the framework, but you can define other, or overwrite framework's automatic handling.
1. Create a new class called `FooWidget` inherited from `BaseView`.
1. Overrride next methods in `FooWidget`
   - `onCreate`: called when the widget is created. You can initialize your outlets' properties here.
   - `onShow`: guess...
   - `onHide`: guess even more...
   - `onCustomAction`: called when a TouchUpInside event is triggered. Here you can check the `actionName` parameter and perform any operation. The typical implementation here is to call one Liferay's remote service using MobileSDK. See Architecture section for more details. 
   - `onServerResult`: if you called a remote service, the response will be received here.
   - `onServerError`: called if the remote service request failed.
1. Define your widget's delegate protocol called `FooWidgetDelegate`. Make sure the protocol is annotated with `@obj`. This will be the way to notify events to holder view controller.
1. Define delegate's `IBOutlet`:
   - `@IBOutlet var delegate: FooWidgetDelegate?`
1. You may define other `IBOutlets` also.
1. Call your delegate's methods when appropriate (when result or error is received from server is the typical one).


    
    
