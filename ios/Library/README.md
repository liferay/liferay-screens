# Liferay iOS Mobile Widget Library

## Introduction

This is the library which contains all available widgets for iOS. It also includes the infrastructure classes to develop and contribute new widgets.

Note that this library is developed using iOS 8, so you'll need XCode6 in order to develop your own widgets.

## Architecture

### View and classes hierarchy

Widgets for iOS follow the architecture shown in the following diagram:

<svg width="500" height="500" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
 <!-- Created with SVG-edit - http://svg-edit.googlecode.com/ -->
 <g>
  <title>viewcontroller</title>
  <rect stroke="#000000" id="svg_11" height="500" width="500" stroke-width="2" fill="#ffffff"/>
  <text font-style="italic" id="svg_12" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="268.98993" x="611.44483" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">[UI in regular Storyboard or xib]</text>
  <text id="svg_1" stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="196" x="440.43677" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">BarViewController</text>
  <text id="svg_3" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="223.68345" x="597.70116" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">(UIViewController child)</text>
 </g>
 <g display="inline">
  <title>widget</title>
  <rect stroke="#000000" id="svg_8" height="450" width="250" y="25" x="25" stroke-width="2" fill="#218dbf"/>
  <text id="svg_9" stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="408" x="161.03448" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">FooWidget</text>
  <text id="svg_10" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="515.34392" x="207.72444" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">(BaseWidget child)</text>
 </g>
 <g display="inline">
  <title>view</title>
  <rect stroke="#000000" id="svg_2" height="300" width="200" y="50" x="50" stroke-width="2" fill="#51bfe5"/>
  <text font-style="italic" id="svg_5" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="383.67196" x="199.13463" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">[custom UI in FooView-ext.xib]</text>
  <text id="svg_7" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="223.68345" x="202.57055" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">(BaseWidgetView child)</text>
  <text stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" id="svg_6" y="196" x="157.48276" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">FooView</text>
  <text font-style="italic" id="svg_4" stroke="#000000" transform="matrix(0.5820860127806878,0,0,0.7063007265607624,35.586917139384624,66.69722673033888) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="353.93958" x="199.13463" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">[standard UI in FooView.xib]</text>
 </g>
</svg>

From top to down in the view hierarchy, we have:

  - _`BarViewController`_: this is your regular view controller, based on Storyboards or xib, where you insert your widgets into. It belongs to your iOS project.
  - _`FooWidget`_: this is the view that you insert into previous view controller, using Interface Builder or programatically. Since it's based on `BaseWidget` (and this is a direct descendant of `UIView`), it inherits all the business logic code that handles the remote services requets and responses. 
  - _`FooView`_: the framework will insert this view into `FooWidget` automatically. Its UI is defined in `FooView.xib` and can be overwritten in `FooView-ext.xib`. It defines IBOutlets and IBActions to connect UI components to code.


### Events and messages passing

When the widget is initilized, event handlers are added to all components in your component. Only `TouchUpInside` even is wired automatically, although you can wire other events using Interface Builder.
When this automatic event is fired, the flow goes as next:

<svg width="500" height="500" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
 <!-- Created with SVG-edit - http://svg-edit.googlecode.com/ -->
 <defs>
  <marker refY="50" refX="50" markerHeight="5" markerWidth="5" viewBox="0 0 100 100" se_type="leftarrow_o" orient="auto" markerUnits="strokeWidth" id="se_marker_start_svg_17">
   <path stroke-width="10" stroke="#000000" fill="none" d="m0,50l100,40l-30,-40l30,-40z"/>
  </marker>
  <marker refY="50" refX="50" markerHeight="5" markerWidth="5" viewBox="0 0 100 100" se_type="rightarrow_o" orient="auto" markerUnits="strokeWidth" id="se_marker_end_svg_22">
   <path stroke-width="10" stroke="#000000" fill="none" d="m100,50l-100,40l30,-40l-30,-40z"/>
  </marker>
  <marker refY="50" refX="50" markerHeight="5" markerWidth="5" viewBox="0 0 100 100" se_type="rightarrow_o" orient="auto" markerUnits="strokeWidth" id="se_marker_end_svg_23">
   <path stroke-width="10" stroke="#000000" fill="none" d="m100,50l-100,40l30,-40l-30,-40z"/>
  </marker>
  <marker refY="50" refX="50" markerHeight="5" markerWidth="5" viewBox="0 0 100 100" se_type="rightarrow_o" orient="auto" markerUnits="strokeWidth" id="se_marker_end_svg_26">
   <path stroke-width="10" stroke="#000000" fill="none" d="m100,50l-100,40l30,-40l-30,-40z"/>
  </marker>
  <marker refY="50" refX="50" markerHeight="5" markerWidth="5" viewBox="0 0 100 100" se_type="rightarrow_o" orient="auto" markerUnits="strokeWidth" id="se_marker_end_svg_27">
   <path stroke-width="10" stroke="#000000" fill="none" d="m100,50l-100,40l30,-40l-30,-40z"/>
  </marker>
 </defs>
 <g display="inline">
  <title>viewcontroller</title>
  <rect fill-opacity="0" stroke="#000000" id="svg_11" height="500" width="500" stroke-width="2" fill="#000000"/>
  <text id="svg_1" stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="286" x="440.43677" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">BarViewController</text>
  <text id="svg_25" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Monospace" font-size="24" y="542.55282" x="634.24084" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">onServerResult</text>
  <text id="svg_24" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Monospace" font-size="24" y="513.9561" x="627.13127" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">onServerError</text>
 </g>
 <g display="inline">
  <title>widget</title>
  <rect stroke="#000000" id="svg_8" height="450" width="250" y="25" x="25" stroke-width="2" fill="#218dbf"/>
  <text id="svg_19" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Monospace" font-size="24" y="513.9561" x="212.99881" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">onServerError</text>
  <text id="svg_9" stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="288" x="161.03448" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">FooWidget</text>
  <text style="cursor: move;" id="svg_20" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Monospace" font-size="24" y="542.55282" x="220.10838" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">onServerResult</text>
  <text id="svg_4" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Monospace" font-size="24" y="445.32397" x="214.7762" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">onCustomAction</text>
 </g>
 <g display="inline">
  <title>view</title>
  <rect stroke="#000000" id="svg_2" height="200" width="200" y="50" x="50" stroke-width="2" fill="#51bfe5"/>
  <text style="cursor: move;" id="svg_16" stroke="#000000" transform="matrix(0.5626219144302587,0,0,0.6993809671125888,31.16240931137101,40.05126367639916) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" y="219.40991" x="209.44402" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">TouchUpInside event fired!</text>
  <text stroke="#000000" transform="matrix(0.844660222530365,0,0,1,17.980579242110252,0) " xml:space="preserve" text-anchor="middle" font-family="Sans-serif" font-size="24" id="svg_6" y="108" x="156.29885" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" fill="#000000">FooView</text>
 </g>
 <g>
  <title>arrows</title>
  <line stroke="#000000" id="svg_14" y2="188" x2="265" y1="188" x1="230" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line stroke="#000000" transform="rotate(90 264.5,267.50000000000006) " id="svg_15" y2="267.5" x2="344.49999" y1="267.5" x1="184.5" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line marker-start="url(#se_marker_start_svg_17)" id="svg_17" stroke="#000000" y2="347" x2="265" y1="347" x1="230" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line id="svg_18" stroke="#000000" y2="348" x2="88" y1="348" x1="53" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line stroke="#000000" id="svg_21" transform="rotate(90 53.49999999999995,382.00000000000006) " y2="382" x2="88" y1="382" x1="19" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line marker-end="url(#se_marker_end_svg_22)" stroke="#000000" id="svg_22" y2="395" x2="77" y1="395" x1="53" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line id="svg_23" marker-end="url(#se_marker_end_svg_23)" stroke="#000000" y2="416" x2="77" y1="416" x1="53" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line stroke="#000000" id="svg_26" marker-end="url(#se_marker_end_svg_26)" y2="395" x2="309" y1="395" x1="233" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
  <line id="svg_27" stroke="#000000" marker-end="url(#se_marker_end_svg_27)" y2="415" x2="309" y1="415" x1="233" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="2" fill="none"/>
 </g>
</svg>

The steps are

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


    
    
