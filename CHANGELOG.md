# Change Log
All notable changes to this project will be documented in this file.

---

# Develop

## Android

### Bugs
* Fix error in UserPortrait initials view: user lastname is required in Liferay 7.0 but is not in Liferay 6.2

## iOS

### Bugs
* Fix error in UserPortrait initials view: user lastname is required in Liferay 7.0 but is not in Liferay 6.2

### New Features
* AssetDisplayScreenlet uses FileDisplayScreenlet for ppt, xls and doc files

# 2.1.1

## Android

### Refactor
* Rename BookmarkListScreenlet comparator to obcClassName
* Allow developers to customize placeholder with user information in UserPortraitScreenlet

### Bugs
* Fix wrong UserPortraitScreenlet avatar in long lists

## iOS

### Refactor
* Allow developers to customize placeholder with user information in UserPortraitScreenlet

### Bugs
* Allow developers override supported mime types in AssetDisplayScreenlet

# 2.1.0

<!-- Possible categories for the changes: Bugs, New features, Refactor, Documentation, Samples, Deprecated, Contributions -->
## Android

### Bugs

* BaseInteractor is not handling exceptions outside its domain anymore
* List users with AssetListScreenlet
* Fixed connection timeout in DDLFormScreenlet when uploading files in hotfix/1.4.2
* Propagating connection timeout in DDLFormScreenlet when uploading files for Liferay 7.0
* Changed wrong colors in buttons and dialogs
* Used URIs instead of paths to support newer SDKs
* Fixed cache error when storing blogs or uploading portraits
* Fixed corrupted credentials in LoginScreenlet stored in SharedPreferences
* Fixed bug with camera and Android N
* Fixed minimum SDK in test application

### New features
* Integrate **Cookie** authentication
* Allow displaying AssetPublisher with portletItemName attribute in AssetDisplayScreenlet
* Allow developers to process the result when a WebContent is clicked
* Added Spanish translations

#### Other features
* Prevent NullPointerException in login
* Improvements to test-app
* Added workaround to list Users with AssetListScreenlet
* Improvements to PdfDisplayScreenlet: you can swipe between pages now
* Exposed some methods to use them in the POC of Nativescript

### Refactor
* Separated login between screens plugin and portal code
* Used connectors instead of services directly
* Automatic formatting

### Documentation
* Comment Android code

### Contributions
* GetUserScreenlet (Mounir Hallab - @mounirovic)


## iOS

### Bugs
* List users with AssetListScreenlet

### New features
* Migrated Core, Showcase-Swift and Westeros demo apps to **Swift 3**
* Integrate **Cookie** authentication
* Added Spanish translations
* Detailed error messages
* Allow displaying AssetPublisher with portletItemName attribute in AssetDisplayScreenlet


#### Other features
* Added workaround to list Users with AssetListScreenlet
* Deselect row after delegate call in BaseListTableView and BaseListCollectionView

### Refactor
* Exposed User object in SessionContext: User doesn't extend anymore from Asset
* Adapted Westeros app with the newest Liferay Screens version
* Used connectors instead of services directly

### Documentation
* Comment iOS code

### Samples
* Created Showcase-ObjectiveC
* Added file examples into Showcase-Swift: AudioDisplayScreenlet, VideoDisplayScreenlet and PdfDisplayScreenlet