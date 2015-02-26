# DDLListScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `DDLListScreenlet` enables the following features:

- Shows a scrollable collection of DDL records.
- Implements [fluent pagination](http://www.iosnomad.com/blog/2014/4/21/fluent-pagination) with configurable page size.
- Allows filtering of records by creator.
- Supports i18n in record values.

## Module

- DDL

## Views

- The Default view uses a standard `RecyclerView` to show the scrollable list. Other views may use a different component, such as `ViewPager` or others, to show the items.

TODO image
![The DDLList screenlet using the Default theme.](Images/ddllist.png)

## Portal Configuration

Dynamic Data Lists (DDL) and Data Types should be properly configured in the portal. For more details, please refer to the Liferay User Guide sections [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-) and [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists).

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `layoutId` | `@layout` | The layout to be used to show the view.|
|  `autoLoad` | `boolean` | Whether or not the list should be loaded when it's presented in the screen. Default value is `true`.|
|  `firstPageSize` | `number` | The number of items to be retrieved from the server in the first page. Default value is `50`.|
|  `pageSize` | `number` | The number of items to be retrieved from the server in the second page and next. Default value is `25`.|
| `recordSetId` | `number` | The identifier of the DDL being called. To find the identifiers for your DDLs, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists*. The identifier of each DDL is in the ID column of the table that appears. |
| `userId` | `number` | The user identifier to filter records on. Records won't be filtered if the `userId` is `0`. Default value is `0 `|
| `labelFields` | `string` | The names of the DDL fields to show, separated by a comma. Refer to the list's data definition to find the field names. To do so, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists* and click the *Manage Data Definitions* button. You can view the fields by clicking on any of the data definitions in the table that appears. Note that the appearance of these values depends on the `layoutId` set. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadPage(pageNumber)` | `void` | Starts the request to load the specified page of records. The page is shown when the response is received.|

## Listener

The `DDLListScreenlet` delegates some events to an object that implements the `DDLListListener` interface. This interface extends from `BaseListListener` and let you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onListPageReceived(<br/>      BaseListScreenlet source, <br/>      int page,<br/>      List<DDLEntry> entries,<br/>      int rowCount)</pre> | Called when a page of records is received. Note that this method may be called more than once; once for each page received.|
|  <pre>onListPageFailed(<br/>      BaseListScreenlet source, <br/>      int page,<br/>      Exception e)</pre> | Called when an error occurs in the process.|
|  <pre>onListItemSelected(<br/>      BaseListScreenlet source, <br/>      DDLEntry entry)</pre> | Called when an item in the list is selected.|

