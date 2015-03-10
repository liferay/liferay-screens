# AssetListScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `AssetListScreenlet` can be used to show [asset](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en) lists from a Liferay instance. For example, you can use the screenlet to show a scrollable list of assets. It also implements [fluent pagination](http://www.iosnomad.com/blog/2014/4/21/fluent-pagination) with configurable page size. The `AssetListScreenlet` can show assets belonging to the following classes:

- `Group`
- `Layout`
- `Organization`
- `User`
- `UserGroup`
- `BlogsEntry`
- `BookmarksEntry`
- `BookmarksFolder`
- `CalendarEvent`
- `DLFileEntry`
- `DLFileEntryMetadata`
- `DLFileEntryType`
- `DLFileRank`
- `DLFileShortcut`
- `DLFileVersion`
- `DDLRecord`
- `DDLRecordSet`
- `JournalArticle` (Web Content)
- `JournalFolder`
- `MBMessage`
- `MBThread`
- `MBCategory`
- `MBDiscussion`
- `MBMailingList`
- `WikiPage`
- `WikiPageResource`
- `WikiNode`

The `AssetListScreenlet` also supports i18n in asset values.

## Module

- None

## Views

The Default views use a standard `RecyclerView` to show the scrollable list. Other views may use a different component, such as `ViewPager` or others, to show the items.

![`AssetListScreenlet` using the Default and Material viewset](images/assetlist.png)

## Portal Configuration

Dynamic Data Lists (DDL) and Data Types should be configured properly in the portal. Refer to the [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-) and [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists) sections of the User Guide for more details.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
| `layoutId` | `@layout` | The layout to use to show the view.|
| `autoLoad` | `boolean` | Whether the list should be loaded when it's presented on the screen. The default value is `true`. |
|  `firstPageSize` | `number` | The number of items to retrieve from the server for display on the list's first page. The default value is `50`. |
| `pageSize` | `number` | The number of items to retrieve from the server for display on the second and subsequent pages. The default value is `25`. |
| `groupId` | `number` | The asset's group (site) ID. If this value is `0`, the `groupId` specified in `LiferayServerContext` is used. The default value is `0 `. |
| `classNameId` | `number` | The asset class name's ID. Use values from the `AssetClassNameId` enumeration or the `classname_` database table. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
| `loadPage(pageNumber)` | `void` | Starts the request to load the specified page of assets. The page is shown when the response is received. |

## Listener

The `AssetListScreenlet` delegates some events to an object that implements the `AssetListListener` interface. This interface extends from `BaseListListener` and lets you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onListPageReceived(<br/>      BaseListScreenlet source, <br/>      int page,<br/>      List<AssetEntry> entries,<br/>      int rowCount)</pre> | Called when a page of assets is received. Note that this method may be called more than once; once for each page received. |
|  <pre>onListPageFailed(<br/>      BaseListScreenlet source, <br/>      int page,<br/>      Exception e)</pre> | Called when an error occurs in the process. |
|  <pre>onListItemSelected(<br/>      BaseListScreenlet source, <br/>      AssetEntry entry)</pre> | Called when an item in the list is selected. |
