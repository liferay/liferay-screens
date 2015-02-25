# Architecture of Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the internal architecture of Liferay Screens for Android. It begins with an overview of the high level components that make up the system, such as the core, screenlets, and views. A description of these components is then presented in the sections that follow.

## High Level Architecture

Liferay Screens separates presentation and business-logic code, inspired by and following ideas taken from [Model View Presenter](http://en.wikipedia.org/wiki/Model-view-presenter), [Model View ViewModel](http://en.wikipedia.org/wiki/Model_View_ViewModel) or [VIPER](http://www.objc.io/issue-13/viper.html) architectures. However, it can't be considered a canonical implementation of none of them because they are architectures designed for apps, and Liferay Screens isn't a app but a suite of visual components.

Liferay Screens for Android contains the following high level components:

![The high level components of Liferay Screens for Android](http://liferay.github.io/liferay-screens/android/library/svg/architecture-components.svg)

- **Core**: This is the component that includes all the base classes needed to develop other components. It can be defined as a micro-framework that allows developers to write their own screenlets, views, and interactor classes.

- **Screenlets**: This is the library that contains all the available screenlets. Each screenlet is a Java View class that can be inserted in any activity or fragment view hierarchy. Screenlets render the selected layout both in the runtime and Android Studio's visual editor. They also react to user interface events, sending server requests if necessary. Screenlets also define a set of configurable properties, which can be set from both from XML's layout and your Java code.

- **Interactors**: This is a collection of classes that implement one specific user interaction (or use case). These classses can interact with both remote and local data sources. Most of the interactors need to send/receive data to/from Liferay Portal, so in such case these interactors use the [Mobile SDK](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/invoking-liferay-services-in-your-ios-app).

- Views: This is a set of layout and accompanying custom view classes that present screenlets to the user.

## Core Layer
 
The core layer is the micro-framework that lets developers write their own screenlets in a structured and isolated way. This is due to the fact that each screenlet has a clear purpose and communication API. Therefore, even components developed by different people share a common structure.
 
![The core layer of Liferay Screens for Android](http://liferay.github.io/liferay-screens/android/library/svg/architecture-core.svg)
 
- **Interactor**: This is the base class for all interactions or use cases supported by the screenlet. These actions could be just an algorithm, or an operation to retrieve data asynchronously from any data store (a local database or a server). One screenlet can have more than one interactor, typically one for each kind of operation supported. The interactor may call Mobile SDK Services and it will receive the responses asynchronously through the EventBus, and eventually change the state of the view classes.

- **BaseScreenlet**: This is the base class for all screenlet classes. The main task of a screenlet class is to receive user action events from the screenlet's view, instantiate and call interactors, and update any view data with the result. This class contains a set of [templated methods](http://www.oodesign.com/template-method-pattern.html) that are intended to be overriden by children classes:
	- *createScreenletView*: it typically inflates the screenlet's view and gets the attribute's values from the XML definition.
	- *createInteractor*: instantiate one kind of interactor depending on the action name supplied. Different action names may have different interactors. If one screenlet only supports one interactor, then the actionName may be ignored.
	- *onUserAction*: runs the interactor intended to be used for an action name. 

- **ScreenletView**: This is the specific class that implements the screenlet UI. This object is created in the screenlet's method *createScreenletView*. The main task of the view classes is to render a specific UI using standard layout files and then update the UI when the data is changed.

- **InteractorAsyncTaskCallback**: This object allows to receive asynchronous responses from Mobile SDK Service's AsyncTask.

- **[EventBus](https://github.com/greenrobot/EventBus)**: This bus implementation is used to notify interactor when the async operations are completed. This way, the AsyncTask is decoupled from the activity lifecycle and most of the typical problems of AsyncTasks are fixed.

- **[MobileSDK](https://www.liferay.com/community/liferay-projects/liferay-mobile-sdk/overview)**: MobileSDK is used to call Liferay's remote services in a typesafe and transparent way.

- **SessionContext**: This is a singleton object that holds the session of the user that is logged into the app. Apps can use an implicit login, which is invisible to the user, or a login that allows the user to manually create the session. User logins can be implemented with [`LoginScreenlet`](LoginScreenlet.md).

- **LiferayServerContext**: This is a singleton object that holds some server configuration parameters. It's loaded from the `server-context.xml` file or from any other XML file that overrides the keys defined in that file.

- **LiferayScreensContext**: This is a singleton object that holds a reference to the application context. It's used internally where the context is necessary.

## Screenlet Layer

The screenlet layer contains the screenlets available in Liferay Screens for Android. The following diagram shows the screenlet layer in relation to the core, views, and interactors components. The screenlet classes detailed in the diagram are explained in this section.

![The screenlet layer in relation to the other components of Liferay Screens](http://liferay.github.io/liferay-screens/android/library/svg/architecture-screenlets.svg)

- **MyScreenletViewModel**: This is an interface that defines the attributes that are shown in the UI. It typically includes all the input and output values presented to the user. For instance, the `LoginScreenletViewModel` includes attributes like the user name and password. The screenlet will read those values to start interactor's operations and eventually it may change those values based on the interactor's result.

- **MyScreenlet**: This is the class that represents the screenlet component and the component that the app developer use to interact with. It includes:
	- A set of attributes that allows the configuration of its behavior. Those attribute's values are read in the screenlet's *createScreenletView* and optionally, the default values can be also set there.
	- A reference to its view, depending on the value supplied in the `liferay:layoutId` attribute. To meet the screenlet's requirements, all screenlet's views must implement the curresponding `ViewModel` interface.
	- Zero or more methods that start interactor's operations. These can be public methods like `loadMyData()`, which is intended to be called by a developer, or UI events received on the View class through a regular listener (like Android's `OnClickListener`) and forwarded to the screenlet using `performUserAction()` method.
	- A listener object, provided by the app, intented to be called when any event occurs. This is optional, but recommended.

- **MyScreenletInteractor**: Any screenlet may have zero, one or more interactors, depending on the number of use cases it supports. For instance, the `LoginScreenlet` only supports one use case (to log the user in) so it just have only one interactor. However, the `DDLFormScreenlet` supports several use cases (load the form, load one record, submit the form, etc.), so it includes one interactor for each use case. The interactor implements an end-to-end operation to achieve a result, so it may perform several steps at turn. For instance, first it sends a request to server A, then it computes a value locally based on server's response and finally it sends the computed value to server B. When the interaction is finished, the interactor must notify to its listener (which typically is the screenlet itself).

- **MyScreenletDefaultView**: In the diagram this class belongs to the Default "view set". The class renders the UI of the screenlet using its associated layout `myscreenlet_default.xml`. The view object and the layout file communicate using standard mechanisms like `findViewById` and listeners. When a user action occurs in the UI, it's received by the specified listener (i.e. `OnClickListener`) and then passed to the screenlet object using the `performUserAction()` method.

- **myscreenlet_default.xml**: This is the layout file with the definition of the components used to render the view for this screenlet. Tipically, the XML will looks like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.your.package.MyScreenletView 
	xmlns:android="http://schemas.android.com/apk/res/android">

	<!-- 
		put your regular components here: EditText, Button...
	-->

</com.your.package.MyScreenletView>
```

For more details refer the [How to Create Your Own Screenlet Guide](screenlet_creation.md)
 
## View Layer

This layer lets developers use more than one look and feel for any screenlet. Screenlets have an attribute called `liferay:layoutId`, which is used to determine the view which is responsible for rendering the UI.

![The view layer of Liferay Screens for Android](http://liferay.github.io/liferay-screens/android/library/svg/architecture-themes.svg)

You have different types of views:

- **Default views**: This is a mandatory "view set" that is supplied by Liferay. It's used by default when the screenlet's `layoutId` isn't specified or is invalid. The Default view set uses a neutral, flat white and blue design with standard UI components. For instance, it uses standard but styled text boxes for the user name and password in the `LoginScreenlet`. You can change at any point the styles associated to this view set to customize the colors, positions and sizes. For that, check the file `styles.xml`

- **Full views**: These views can show a different set of attributes and components. Using the `LoginScreenlet` as an example, the Full view can be used to present different components for the user name and password fields. For instance, you can show only the password field and infer the user name from somewhere else. The Default views are a kind of Full view.

- **Child views**: This kind of view inherits only the behaviour from another view, without including any code. It just contains a new layout file with a different UI, colors, positions, and any other visual changes. Therefore, the UI components of the two themes must be exactly the same, and the identifiers used in these components must be the same also. In the diagram, Child inherits from the Default view. As an example of implementing child view, you can change the position and size of the standard text boxes in `LoginScreenlet` by creating a view inherited from Default, and then configure the new layout file. This is a good alternative to implement a different UI for one specific scenario.

- **Extended**: This view inherits from other parent view (may be the Default one), but provides an extended implementation. In the diagram, the Extended view extends the Full one, but provides an specific view class for the screenlet (extending from the corresponding parent's view class). This is useful to implement new behaviour in the view, like showing new components to the user or changing the parent's behaviour somehow.
For more details, see the guide [How to Create Your Own Theme](theme_creation.md).
