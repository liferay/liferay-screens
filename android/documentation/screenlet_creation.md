# Creating Screenlets in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the steps required to create your own screenlets in Liferay Screens for Android. Before proceeding, you may want to read the [Architecture Guide](architecture.md) to understand the concepts underlying screenlets. You may also want to read the guide [How to Create Your Own View](view_creation.md) to support the new screenlet from the Default or other view sets.

The steps below walk you through creating an example screenlet for bookmarks that has the following features:

- Allows the user to enter a URL and title in a text box.
- When the user touches the submit button, the URL and title are sent to the Liferay instance's Bookmark service to be saved.

Now that you know the basic ideas behind Screenlets and have a goal for the screenlet you'll create here, it's time to get started!

## Where Should You Create Your New Screenlet?

If you don't plan to reuse your screenlet in another app, or if you don't want to redistribute it, the best place to create it is in a new package inside your project. This way you can reference and access all the viewsets you've imported and the core of Liferay Screens. 

If you want to reuse your screenlet in another app, you need to create it in a new Android application module. The steps for creating such a module are referenced at the end of this document. This module needs to include Liferay Screens as a dependency, as well as the viewsets you're using. 

## Creating Your Screenlet

1. Create a new interface called `AddBookmarkViewModel`. This is for adding the attributes to show in the view. In this case, the attributes are `url` and `title`. Any screenlet view must implement this interface.

	```java
	public interface AddBookmarkViewModel extends BaseViewModel {
		String getURL();

		void setURL(String value);

		String getTitle();

		void setTitle(String value);
	}
	```

2. Build your UI using a layout XML file. Put in two `EditText` tags: one for the URL and another for the title. Also, add a `Button` tag to let the user save the bookmark. Note that the root element is a custom class. You'll create this class in the next step.

	```xml
	<?xml version="1.0" encoding="utf-8"?>
	<com.your.package.AddBookmarkDefaultView 
		xmlns:android="http://schemas.android.com/apk/res/android"
		style="@style/default_screenlet">

		<EditText
			android:id="@+id/url"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_marginBottom="15dp"
			android:hint="URL Address"
			android:inputType="textUri"/>

		<EditText
			android:id="@+id/title"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_marginBottom="15dp"
			android:hint="Title"/>

		<Button
			android:id="@+id/add_button"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:text="Add Bookmark"/>
	
	</com.your.package.AddBookmarkDefaultView>
	```

    At this point, the graphical layout viewer in Android Studio should look like this:

    ![An app based on Liferay Screens.](images/addbookmark_view.png)

3. Create a new custom view class called `AddBookmarkView`, that extends `LinearLayout` and implements `AddBookmarkViewModel`. This new class is where you implement the UI using the layout XML file from the previous step.

	```java
	public class AddBookmarkView
		extends LinearLayout implements AddBookmarkViewModel {
	
		public AddBookmarkView(Context context) {
			super(context);
		}
	
		public AddBookmarkView(Context context, AttributeSet attributes) {
			super(context, attributes);
		}
	
		public AddBookmarkView(Context context, AttributeSet attributes, int defaultStyle) {
			super(context, attributes, defaultStyle);
		}

		@Override
		public void showStartOperation(String actionName) {
		}

		@Override
		public void showFinishOperation(String actionName) {
		}

		@Override
		public void showFailedOperation(String actionName, Exception e) {
		}

		private EditText _urlText;
		private EditText _titleText;
	
	}
	```

4. In the `onFinishInflate` method, get the reference to the components. Then create the getters and setters using the inner value of the components:

	```java
		@Override
		protected void onFinishInflate() {
			super.onFinishInflate();
	
			_urlText = (EditText) findViewById(R.id.url);
			_titleText = (EditText) findViewById(R.id.title);
		}
		
		public String getURL() {
			return _urlText.getText().toString();
		}
	
		public void setURL(String value) {
			_urlText.setText(value);
		}
	
		public String getTitle() {
			return _titleText.getText().toString();
		}
	
		public void setTitle(String value) {
			_titleText.setText(value);
		}	
	```

5. Create the interactor class. It's responsible for sending the bookmark to the Liferay instance (or any other backend). Note that it's a good practice to use [IoC](http://en.wikipedia.org/wiki/Inversion_of_control) in your interactor classes. This way, anyone can provide a different implementation without breaking the code. The `Interactor` base class also needs a parameter that represents the type of listener to notify. This is defined here (we will implement the `AddBookmarkListener` later):

	```java
	public interface AddBookmarkInteractor extends Interactor<AddBookmarkListener> {
	
		void addBookmark(String url, String title, Integer folderId) throws Exception;
	
	}
	```

	```java
	public class AddBookmarkInteractorImpl
		extends BaseRemoteInteractor<AddBookmarkListener>
		implements AddBookmarkInteractor {
	
		public AddBookmarkInteractorImpl(int targetScreenletId) {
			super(targetScreenletId);
		}
	
		public void addBookmark(String url, String title, Integer folderId) {
			// 1. Validate input
			if (url == null || url.isEmpty()) {
				throw new IllegalArgumentException("Invalid url");
			}
	
			// 2. Call the service asynchronously.
			// Notify when the request ends using the EventBus
		}
	
		public void onEvent(JSONObjectEvent event) {
			if (!isValidEvent(event)) {
				return;
			}
	
			if (event.isFailed()) {
				getListener().onAddBookmarkFailure(event.getException());
			}
			else {
				getListener().onAddBookmarkSuccess();
			}
		}
	
	}
	```

    Pay special attention to the second step in the `addBookmark` method. When the request ends, make sure you post an event into the bus using `EventBusUtil.post(event)`, where `event` is a `JSONObjectEvent` object containing the `targetScreenletId` together with either the result or the exception. Every interactor should also implement the `onEvent` method. This method is invoked by the `EventBus` and calls the registered listener. A good example is the `AddBookmarkInteractorImpl`.

6. Our `AddBookmarkListener` interface will be really simple, just having two methods. For example:

	```java
	public interface AddBookmarkListener {

		void onAddBookmarkFailure(Exception exception);

		void onAddBookmarkSuccess();
	}
	```

7. Once your interactor is ready, you need to create the screenlet class. This is the cornerstone and entry point that your app developer sees and interacts with. In this example, this class is called `AddBookmarkScreenlet` and extends from `BaseScreenlet`. Again, this class needs to be parameterised with the interactor class. Since the screenlet is notified by the interactor when the asynchronous operation ends, you must implement the listener interface used by the interactor (`AddBookmarkListener`, in this case). Also, to notify the app, this class usually has another listener. This listener can be the same one you used in the interactor or a different one altogether (if you want different methods or signatures). You could even notify the app using a different mechanism such as the Event Bus, Android's `BroadcastReceiver`, or others.  Note that the implemented interface methods call the view to modify the UI and the app's listener to allow the app to perform any action:

	```java
	public class AddBookmarkScreenlet
		extends BaseScreenlet<AddBookmarkViewModel, AddBookmarkInteractor>
		implements AddBookmarkListener {
	
		public AddBookmarkScreenlet(Context context) {
			super(context);
		}
	
		public AddBookmarkScreenlet(Context context, AttributeSet attributes) {
			super(context, attributes);
		}
	
		public AddBookmarkScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
			super(context, attributes, defaultStyle);
		}
	
		public void onAddBookmarkSuccess() {
			// Invoked from the interactor:
			// Notify both the view and the app's listener
	
			getViewModel().showFinishOperation(null);
	
			if (_listener != null) {
				_listener.onAddBookmarkSuccess();
			}
		}
	
		public void onAddBookmarkFailure(Exception e) {
			getViewModel().showFinishOperation(null);
	
			if (_listener != null) {
				_listener.onAddBookmarkFailure(e);
			}
		}
	
		public void setListener(AddBookmarkListener listener) {
			_listener = listener;
		}
	
		private AddBookmarkListener _listener;
	
	}
	```

8. You're almost finished! The next step is to implement the screenlet's abstract methods. First is the `createScreenletView` method. In this method you get attributes from the XML definition and either store them as class attributes or otherwise make use of them. Then inflate the view using the layout specified in the `liferay:layoutId` attribute. You can even configure the initial state of the view, using the attributes read.

	```java
		@Override
		protected View createScreenletView(Context context, AttributeSet attributes) {
			TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.AddBookmarkScreenlet, 0, 0);
				
			int layoutId = typedArray.getResourceId(R.styleable.AddBookmarkScreenlet_layoutId, 0);
	
			View view = LayoutInflater.from(context).inflate(layoutId, null);
			
			String defaultTitle = typedArray.getString(R.styleable.AddBookmarkScreenlet_defaultTitle);
			
			_folderId = typedArray.getInteger(R.styleable.AddBookmarkScreenlet_folderId, 0);

			typedArray.recycle();
	
			AddBookmarkViewModel viewModel = (AddBookmarkViewModel) view;
			viewModel.setTitle(defaultTitle);
	
			return view;
		}

		private Integer _folderId;
	```

    The Second abstract method to implement is `createInteractor`. This is a factory method in which you have to create the corresponding interactor for a specific action name. Note that a single screenlet may have several interactions (use cases). Each interaction should therefore be implemented in a separate interactor. In this example there is only one interactor, so the object is created in the method. Alternatively, you can retrieve the instance by using your IoC framework. Also, you need to pass the `screenletId` (a number autogenerated by the `BaseScreenlet` class) to the constructor:

	```java
		protected AddBookmarkInteractor createInteractor(String actionName) {
			return new AddBookmarkInteractorImpl(getScreenletId());
		}
	```

    The third and final abstract method to implement is `onUserAction`. In this method, retrieve the data entered in the view and start the operation by using the supplied interactor and the data:

	```java
		@Override
		protected void onUserAction(String userActionName, BookmarkInteractor interactor, Object... args) {
			AddBookmarkViewModel viewModel = (AddBookmarkViewModel) getScreenletView();
			String url = viewModel.getURL();	
			String title = viewModel.getTitle();
	
			try {
				interactor.addBookmark(url, title, _folderId);
			}
			catch (Exception e) {
				onAddBookmarkFailure(e);
			}
		}
	```

	To be able to read the screenlet attributes you have to add an xml file that defines those attributes. An example for this `AddBookmarkScreenlet`:

	```xml
	<?xml version="1.0" encoding="utf-8"?>
<resources>
	<declare-styleable name="AddBookmarkScreenlet">
		<attr name="layoutId"/>
		<attr name="folderId"/>
		<attr name="defaultTitle" format="string"/>
	</declare-styleable>
</resources>
	```

9. The only thing left to do is to trigger the user action when the button is pressed. To do this, go back to the view and add a listener to the button:

	```java
		protected void onFinishInflate() {
			super.onFinishInflate();
	
			// same code as before
	
			Button addButton = (Button) findViewById(R.id.add_button);
			addButton.setOnClickListener(this);
		}
		
		public void onClick(View v) {
			AddBookmarkScreenlet screenlet = (AddBookmarkScreenlet) getParent();
	
			screenlet.performUserAction();
		}
	```
	You also have to make `AddBookmarkView` implement `OnClickListener`.

Congratulations! Now you know how to create your own screenlets.

## Packaging Your Screenlets

If you want to distribute your screenlets for use in different projects, you should package them in a module (Android library). To use the screenlet, developers then add that module as a project dependency in their app. 

Use the following steps to package your screenlets in a module: 

1. Create a new Android module and configure the `build.gradle` file.
2. Configure dependencies between each module
3. Optionally, you can distribute the module by uploading it to jCenter or Maven Central.

The next sections detail these steps.

### Create a New Android Module

Fortunately, Android Studio has a menu option that automatically creates an Android module and adds it to your `settings.gradle` file. Go to *File* &rarr; *New* &rarr; *New Module* &rarr; *Android Library* (in *More Modules*) and enter a name for your new module. You don't need a new activity for the new module, so just use *Blank Activity*. Android Studio automatically creates a new `build.gradle` file (with an Android Library configuration) and adds the new module to the `settings.gradle` file. 

If you prefer to do this manually, you need to create a new Android Library. This is essentially an Android app project with the gradle import set to `apply plugin: 'com.android.library'`. Use the [gradle file](https://github.com/liferay/liferay-screens/blob/master/android/library/viewsets/build.gradle) from the material viewset or Westeros app as an example. 

After creating the module manually, you need to import it into your project by specifying its location in [`settings.gradle`](https://github.com/liferay/liferay-screens/tree/master/android/samples/settings.gradle). Here's an example of this configuration:

```groovy
include ':YOUR_MODULE_NAME'
project(':YOUR_MODULE_NAME').projectDir = new File(settingsDir, 'RELATIVE_ROUTE_TO_YOUR_MODULE')
```

### Configure Dependencies Between Each Module

Next, you need to configure your app to use the new module. To do so, add the following `compile` statement to the `dependencies` in your `build.gradle` file:

```groovy
dependencies {
	compile project (':YOUR_MODULE_NAME')
	
	...
}
```

Your module also needs the dependencies required to override the existing screenlets or create new ones. This usually means that you need to add Liferay Screens and the view sets you currently use as dependencies. To do so, add the following `compile` statement to the `dependencies` in your `build.gradle` file: 

```groovy
dependencies {
	compile 'com.liferay.mobile:liferay-screens:0.3.+'
	
	...
}
```

### Upload the Module to jCenter or Maven Central

If you want to distribute your screenlet so that others can use it, you can upload it to jCenter or Maven Central. Use the [`build.gradle`](https://github.com/liferay/liferay-screens/blob/LMW-230-Changes-In-Westeros-App/android/viewsets/westeros/build.gradle) file of the material or Westeros viewset as an example. 

After entering your bintray api key, you can execute `gradlew bintrayupload` to upload your project to jCenter. When finished, your screenlet can be used as any other Android dependency. Developers just need to add the repository, artifact, groupId, and version to their gradle file.