# Change Log
All notable changes to this project will be documented in this file.

---

# Develop
<!-- Possible categories for the changes: Bugs, New features, Refactor, Documentation, Samples, Deprecated, Contributions -->

## Android

### New Features

* Create ThingScreenlet
* FormsScreenlet - Allow developers to handle with value changes for all fields using onChangeValueObservable
* FormsScreenlet - Show only FileName instead of the full path on DocumentField
* Create a ModalProgressBar view
* Update ThingScreenlet with ApioConsumer 0.0.8 changes

### Refactor

* FormsScreenlet - Create an abstract OptionsField type as a base class of fields that select options
* Create a ThemeUtil class to help with custom themes

# 4.0.1

## Android

Update mobile-sdk dependency to 7.1.3

## New Features

* Expose WebView in WebScreenlet

# 4.0.0

## Android

## New Features

* Add OAuth2 authentication

### Refactor

* Drop support for OAuth1
* Give a pass to the code and solve minor issues

### Bugs

* Fix inverted thumbs in rating screenlet

### Samples

* Update bankofwesteros demo app

### New features

* Add a new method to the sync service to know if there are items to synchronize
* Add Lexicon Theme
* FormsScreenlet - Add support to upload images and videos from gallery
* FormsScreenlet - Required fields are now marked with an asterisk (*)
* WebScreenlet now correctly inject the script when navigate to another page

### Samples

* Update test-app demo app

### Samples

* Update westerosemployees demo app
* Update westerosemployees-hybrid demo app

## iOS

### Bugs

* Fix Crash when trying to obtain title property in WebContent model
* Fix method not been expose in Objective-C

### Features

* Add OAuth2 authentication

### Refactor

* Remove non essentials libraries: Cosmos and Cryptoswift
* Create AsyncServerConnector and remove the semaphores
* Remove trigger action with identifier and UITextFieldDelegate in BaseScreenletView
* Drop support for OAuth1
* Give a pass to the code and solve minor issues

### Samples

* Update Showcase-swift demo app
* Update WesterosEmployees demo app
* Update WesterosBank demo app
* Update WesterosEmployees-hybrid demo app
* Update AndorraTelecomPublic demo app
* Update Showcase-ObjectiveC demo app

# 3.1.0

## Android

### Refactor
* Cookie refresh is now handled by the mobile-sdk


## iOS

* Updated to Swift 4

### New features
* Add Geolocation field support

### Refactor
* Remove swift style guide and use SwiftLint guide
* Remove deprecated `LIFERAY_SCREENS_FRAMEWORK` macro definition
* Fix Liferay Screens warnings
* Cookie refresh is now handled by the mobile-sdk

## Xamarin

### Samples
* Andorra Telecom demo developed with Xamarin.Android

# 3.0.3

## Android

### Bugs
* Fix error in WebContentDisplayScreenlet: link's content was shown as text

## iOS

### New features
* Create new delegate in WebContentDisplayScreenlet to allow or not navigation inside the WKWebView. Also, a sample of usage in Showcase-swift

## Xamarin

### Samples
* Andorra Telecom demo developed with Xamarin.iOS

# 3.0.2

## Android

### New features
* Add preview mode in DDLForm (breaking change)
* Refactor shadow activity to request media, and use it in UserPortrait and DDLFormScreenlet
* Support Geolocation field in DDLFormScreenlet

## Xamarin

### Refactor
* Xamarin.iOS demo app takes values from plist file

# 3.0.1

## Android

### Bugs
* Fix error in WebContentListScreenlet: all the html is save as a parcelable and the application crashes

# 3.0.0

## Android

### Bugs
* Fix error in UserPortrait initials view: user lastname is required in Liferay 7.0 but is not in Liferay 6.2
* Fix error in UserPortrait initials view: image doesn't show correctly because background was always on top
* Fix error in cookie login, now its possible to use this login type with UserId and Screenname
* Fix error in VideoDisplayScreenlet: video is not showing the first time its downloaded
* Fix error in material LoginView with cookie login
* All screenlets should have getter and setter for their listener property
* Disable selection in WebScreenlet
* Fix session in WebScreenlet
* Fix url in other webtype in WebScreenlet
* Fix File provider so developers can use their own
* Fix error with comment in '//' format in WebScreenlet

### New Features
* Create loadLoggedUserPortrait method in UserPortraitScreenlet
* Expose Video events in the VideoDisplayScreenlet 
* Allow developers override injected CSS in WebContentDisplayScreenlet
* Add WebScreenlet
* Support pages in forms with a ViewPager
* Add support for multiple select fields in forms
* Allow data providers in forms
* Show alerts in the WebScreenlet
* Allow developers to disable scroll in the WebScreenlet
* Allow developers to clear cache on WebScreenlet
* All screenlets should have getter/setter for their listener properties (for Xamarin projects)

### Refactor
* Remove unused attribute in RatingsScreenlet 

### Demo
* Added Westeros Employees Hybrid

## iOS
* Increased min version to iOS 9
* Updated to Swift 3.2

### Bugs
* Fix error in UserPortrait initials view: user lastname is required in Liferay 7.0 but is not in Liferay 6.2
* Fix bug in Session context, user attributes were being removed when the cookie login was refreshed.
* Fix retain cycle in BaseListScreenlet
* Update the cookie expired verification to work with new versions of the mobile sdk
* Fix error messages "Unknown class in Interface Builder file".
* Disable selection in WebScreenlet
* Fix session in WebScreenlet

### New Features
* AssetDisplayScreenlet uses FileDisplayScreenlet for ppt, xls and doc files
* Allow developers override injected CSS in WebContentDisplayScreenlet
* Add WebScreenlet
* Add name to classes annotated with @objc for Xamarin solutions
* Remove module name in default views for Xamarin solutions
* Show alerts in the WebScreenlet
* Allow developers to disable scroll on WebScreenlet
* Wait for all scripts to load before showing the WebScreenlet
* Allow developers to clear cache on WebScreenlet

### Refactor
* Migrate from UIWebView to WKWebView
* UserPortraitScreenlet: extract methods, add comments and SF

### Documentation
* Screenlets documentation improvements

### Demo
* Added Westeros Employees Hybrid
* Added Andorra Telecom

## Xamarin

### New Features
- Auth screenlets bindings: LoginScreenlet, ForgotPasswordScreenlet, SignUpScreenlet
- DDLFormScreenlet binding
- ImageGalleryScreenlet binding
- UserPortraitScreenlet binding
- WebContentDisplay and WebContentList screenlets' bindings
- Comment screenlets' bindings
- Asset screenlets' bindings
- WebScreenlet binding
- DDLListScreenlet binding
- BlogsDisplayScreenlet binding
- File screenlets' bindings
- Create demo apps: Showcase-Android and Showcase-iOS
- Add all screenlets in demo apps
- Add example about how to change screenlet theme

# 2.1.4

## Android

### New features
* Allow developers to handle Authentication challenges in Login Screenlet

### Bugs
* Fix relogin for cookie authentication

## iOS

### Bugs
* Fix relogin for cookie authentication

# 2.1.2

## iOS

### New features
* Allow developers to handle Authentication challenges in Login Screenlet

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
