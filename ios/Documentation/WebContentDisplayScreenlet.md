# WebContentDisplayScreenlet for iOS

## Important Note

_This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology._

## Features
- Shows Web Content elements rendering the inner HTML
- Supports i18n, rendering different contents depending on the current device locale.

##Module
None

## Themes
- Default: uses a standard `UIWebView` to render the HTML. Other themes may use a different component (like iOS 8's `WKWebView`).

![WebContentDisplay screenlet using Default theme](Images/webcontent.png)

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatiblity

- iOS 7 and above

## Portal configuration

Web content should be created in the portal.

Refer to [Web Content Management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/web-content-management-liferay-portal-6-2-user-guide-02-en) sections for more details.


## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `groupId` | `number` | The site (group) identifier where the asset will be stored. If this value is 0, the `groupId` specified in `LiferayServerContext` will be used|
|  `articleId` | `string` | The identifier of the Web Content. ![](Images/portal-webcontent.png) |


## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadWebContent()` | `boolean` | Starts the request to load the web content. When the response is received, the HTML is rendered. Returns `true` if the request could be sent. |


## Delegate

This screenlet delegates some events in an object that conforms `WebContentDisplayScreenletDelegate` protocol.
This protocol allows to implement the following methods:

- `onWebContentDisplayResponse(html)`: called when the web content's html is received.
- `onWebContentDisplayError(error)`: called when an error happened in the process. The NSError object describes the error occurred.



    
    
