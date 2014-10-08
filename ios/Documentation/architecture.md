# Liferay Screens iOS Architecture

## Important note
__This product is under heavy development and most of the features aren't ready to be used in production.
It's public just in order to allow developers to preview the technology.__

## Introduction

This document explains the internal architecture of the Liferay Screens for iOS.

## Architecture

### High level architecture

The whole system is organized in the following high level components:


![Liferay Screens Components](http://liferay.github.io/liferay-screens/ios/Library/svg/architecture-components.svg)

 - **Core**: it's the component that includes all base classes in order to allow to develop screenlets. It can be defined as a *micro-framework intended to allow programmers to write their own screenlets, themes and server operation classes*.
 - **Screenlets**: it's the library of all available screenlets. Each screenlet is a Swift class (can be used from Objective-C also) that can be inserted in a UIView. It renders the selected theme (both in runtime and in Interface Builder) and it reacts to user interface events, starting server operations if neccessary. Screenlets also define a set of `@IBInspectable` properties, allowing programmer or designed to configure them from Interface Builder.
 - **Server Operations**: it's a collection of *NSOperation* classes that interacts with data sources (remote or not). In case of Liferay, we have our own sey of *Liferay Operations* that use the [Mobile SDK](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/mobile-sdk-to-call-services-liferay-portal-6-2-dev-guide-en). Given that all Server Operations uses the [NSOperation framework](https://developer.apple.com/library/mac/documentation/General/Conceptual/ConcurrencyProgrammingGuide/OperationObjects/OperationObjects.html#//apple_ref/doc/uid/TP40008091-CH101-SW1), they can be run concurrently and it's very easy to define priorities and dependencies between operations, so you can build your own graph of operations that would be resolved by the framework.
 - **Themes**: it's a set of `xib` files and compaing UIView classes that presents the screenlet to the user.

 ### Core layer
 
Core layer is the *micro-framework* intended to allow programmers to write their own screenlets, themes and server operation classes in a structured and isolated way. Each component (screenlet, theme and operation) have a clear aim and communication API, so it may be programmed even by different people.
 
![Liferay Screens Core Layer](http://liferay.github.io/liferay-screens/ios/Library/svg/architecture-core.svg)
 
- **Server Operation**: it's the base class for all operations started by screenlets. The operation is responsible of getting the data from its data source in a asynchronous way. Despite of its name, the data source could be a server backend or just a simple file. Screenlet classes instantiate and start operations, and eventually receive the responses which changes the state of the view classes.
- **BaseScreenlet**: it's the base class for all screenlet classes. The main responsability of the screenlet class is to receive event from UI (user actions), start server operations and update view data when operation result arrives. It constains a set of [templated methods](http://www.oodesign.com/template-method-pattern.html) (called *onWhatever*) intended to be overwritten in children classes.
- **BaseScreenletView**: it's the base class for all screenlet's view classes. These children classes belong to theme layer. The main responsability of the view classes is to render a specific UI (using standard `xib` files) and update its UI when data changes. It constains a set of templated methods (called *onWhatever*) intended to be overwritten in children classes.
- **SessionContext**: it's a singleton class that holds the session of the user logged into the app. Apps could have implicit login (invisible to the user) to explict one (using a [LoginScreenlet](LoginScreenlet.md) in order to allow user to create the session.
- **LiferayServerContext**: it's a singleton class that holds some server configuration parameters. It's loaded from `liferay-server-context.plist` file. Most screenlets uses these parameters as default values.

### Screenlet layer

![Liferay Screens Core Layer](http://liferay.github.io/liferay-screens/ios/Library/svg/architecture-screenlets.svg)

- **MyScreenletData**: it's an interface that defines the attributes of the screenlet. It typically includes the input values (i.e. user name and password for Login screenlet). The operation could validate this values and read them in order to configure the operation. The screenlet could change these values based on operation's result and default values.
- **MyScreenlet**: it's the actual class that represents the screenlet component. It includes:
	- A set of inspectable parameters, in order to allow to configure its behaviour (and optionally, set initial state in screenlet's data)
	- A reference to its actual view, depending on the theme selected. All themes must implement de *Data* interface in order to meet the screenlet's requirements.
	- Zero or more methods that create and start server operations (could be public methods, like *loadMyData()* intended to be called by programmer, or UI events coded received on *onUserAction()*
	- Optionally (but recommended), can include a [delegate object](https://developer.apple.com/library/ios/documentation/general/conceptual/DevPedia-CocoaCore/Delegation.html) intented to be called when events happen.
- **MyScreenletOperation**: related to the screenlet, but in a different layer, we have one or more server operations. Each operation is responsible of  getting a set of related values (typicalle one single requests if it's a backend call). The results will be stored in a *result* object, that can be read by the screenlet when it's notified.
- **MyScreenletView_themeX**: this class belongs to one specific theme (*ThemeX* in the diagram) and renders the UI of the screenlet using it's related `xib` file. The view object and the `xib` file *talk* using standard mechanisms like `@IBOutlet` and `@IBAction`. When an user action occurs in the `xib` it's received by BaseScreenletView and passed to the screenlet class using the *onUserAction()* method. In order to identify different events, property `restorationIdentifier` of the component is used and passed to *onUserAction()* method.
- **MyScreenletView_themeX.xib**: It's the `xib` file with the components used to render the view. Note that the name of this class is very important: by convenion, the `xib` file for screenlet called `FooScreenlet` and theme called `BarTheme` must be called `FooScreenlet_barTheme.xib`.
 
### Theme (frontend) layer

Theme layer allows developers to have more than one look and feel implementation (called *theme*) for any screenlet. The screenlet has a property called *themeName* which will be used to determine the theme to load.
One theme can implement a look & feel just for a limited set of screenlets, depending on the requirements.

![Liferay Screens Core Layer](http://liferay.github.io/liferay-screens/ios/Library/svg/architecture-themes.svg)

- **Default theme**: it's a mandatory theme, supplied by Liferay, which is used when the screenlet's themeName is not specified. It uses a neutral flat white and blue design with standard UI components. For instance, for a Login screenlet, it uses standard text boxes for user name and password.
- **Simple**: it's a theme that *inherits* from one theme (from *Default* in this case) and only changes the look and feel of the UI components. It inherits all attributes, should use all of them and the UI compoments must be the same. For instance, for a Login screenlet, if a theme want change the position and size of the standard text boxes, it just creates a theme inherited from default, and configures the `xib` files.
- **Full**: it's a complete theme that can present a different set of attributes and components. For instance, for Login screenlet, a new theme can decide to present different components for user name and password, or even just show input to password and infer user name from somewhere.


## How to create your own screenlet
Imagine you want to create a Bookmark screenlet, with next requirements

- Shows an input text box in order to type an URL
- When the URL is typed, retrieves the content of the URL in order to check if the URL is valid and also to extract the title value. 
- Shows both preview image and title for user confirmation.
- The user can modify the title
- Finally if the user confirms, the url and title is sent back to Liferay Portal's Bookmark services to be saved.
	
1. Create a new xib called `BookmarkView_default.xib`. Build your UI here using Interface Builder as usual: two text box (`UITextField`) for the URL and title. Also we add a couple of buttons to fire user actions: retrieve title and save the bookmark. Assign value for property `restorationIdentifier` in each button to discriminate each user actions.

1. Create a new interface (protocol) called `BookmarkData`. Attributes associated could be: `url` and `title`.

1. Create a new class called `BookmarkView_default` extending `BaseScreenletView` and implementing `BookmarkData`. It must wire all UI components and events from the `xib` using standard `@IBOutlet` and `@IBAction`. Getters and setters from `BookmarkData` should typically get/set data from UI components. Write also animations or frontend code here.

1. Set `BookmarkView_default` as the Custom Class of your `BookmarkView_default.xib`

1. Create a class called `BookmarkScreenlet` extending `BaseScreenlet`.

1. Override *onUserAction* to receive both actions and use `actionName` paramenter to discriminate both of them:
	- Preview
	- Save

1. Write two operation classes extending from `ServerOperation`. Override `doRun` method to perform the operation and `validateData` to check if the data stored in `BookmarkData` is valid:
	- `GetSiteTitleOperation`: retrieves the first bytes of the URL until it reaches the `<title>` tag. The result is the title extracted from the HTML.
	- `LiferaySaveBookmarkOperation`: sends title and URL to Liferay Portal using Liferay Portal bookmark services.

1. On screenlet's *onUserAction* method, create and start these operations:
	- Preview: creates and starts `GetSiteTitleOperation`. The closure specified should get the retrieved title and set it to the associated `BookmarkData` (using `screenletView` attribute). If the operation fails, show the error to the user.
	- Save: get the URL and title from the `BookmarkData` and creates a `LiferaySaveBookmarkOperation` with these values configured. Start the operation and set the closure to show the success of failure to the user.

