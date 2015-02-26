# DDLListScreenlet for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- iOS 7 and above

## Features

The `DDLListScreenlet` enables the following features:

- Shows a scrollable collection of DDL records.
- Implements [fluent pagination](http://www.iosnomad.com/blog/2014/4/21/fluent-pagination) with configurable page size.
- Allows filtering of records by creator.
- Supports i18n in record values.

## Module

- DDL

## Themes

- The Default theme uses a standard `UITableView` to show the scrollable list. Other themes may use a different component, such as `UICollectionView` or others, to show the items.

![The DDLList screenlet using the Default theme.](Images/ddllist.png)

## Portal Configuration

Dynamic Data Lists (DDL) and Data Types should be properly configured in the portal. For more details, please refer to the Liferay User Guide sections [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-) and [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists).

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `autoLoad` | `boolean` | Whether or not the list should be loaded when it's presented in the screen. Default value is `true`.|
|  `refreshControl` | `boolean` | Whether or not an starndar [UIRefreshControl](https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIRefreshControl_class/) will be shown when the "pull to refresh" gesture is done by the user. Default value is `true`.|
|  `firstPageSize` | `number` | The number of items to be retrieved from the server in the first page. Default value is `50`.|
|  `pageSize` | `number` | The number of items to be retrieved from the server in the second page and next. Default value is `25`.|
| `recordSetId` | `number` | The identifier of the DDL being called. To find the identifiers for your DDLs, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists*. The identifier of each DDL is in the ID column of the table that appears. |
| `userId` | `number` | The user identifier to filter records on. Records won't be filtered if the `userId` is `0`. Default value is `0 `|
| `labelFields` | `string` | The names of the DDL fields to show, separated by a comma. Refer to the list's data definition to find the field names. To do so, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists* and click the *Manage Data Definitions* button. You can view the fields by clicking on any of the data definitions in the table that appears. Note that the appearance of these values depends on the theme selected by the user. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadList()` | `boolean` | Starts the request to load the list of records. The list is shown when the response is received. This method returns `true` if the request is sent. |

## Delegate

The `DDLListScreenlet` delegates some events in an object that conforms to the `DDLListScreenletDelegate` protocol. This protocol lets you implement the following methods:

- `onDDLListResponse(list of records)`: Called when a page of contents is received. Note that this method may be called more than once; once for each retrieved page.
- `onDDLListError(error)`: Called when an error occurs in the process. The `NSError` object describes the error.
- `onDDLRecordSelected(record)`: Called when an item in the list is selected. The parameter is an instance of the class `DLLRecord`.

