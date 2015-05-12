# Creating Screenlets in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the steps required to create your own screenlets in Liferay Screens for Android. Before proceeding, you may want to read the [Architecture Guide](architecture.md) to understand the concepts underlying screenlets. You may also want to read the guide [How to Create Your Own View](view_creation.md) to support the new screenlet from the Default or other view sets.

The steps below walk you through creating an example screenlet for bookmarks that has the following features:

- Allows the user to enter a URL and title in a text box.
- When the user touches the submit button, the URL and title are sent to the Liferay instance's Bookmark service to be saved.

Now that you know the basic ideas behind Screenlets and have a goal for the screenlet you'll create here, it's time to get started!

## Where to create your new screenlet?

If you are not going to reuse it in another application or if you don't want to redistribute it, the easiest place is inside your project, in a new package.

This way you can reference and access all the viewsets you have imported and the core of Liferay Screens.

If you want to reuse your screenlet in another application, you will need to create a new Android module of type application (steps are referenced at the end of this document).

That module will have to include liferay screens as a dependency and all the viewsets you are using.

## Creating Your Screenlet

1. Create a new interface called `AddBookmarkViewModel`. This is for adding the attributes to show in the view. In this case, the attributes are `url` and `title`. Any screenlet view must implement this interface.

	```java
	public interface AddBookmarkViewModel {
	
		public String getURL();
		public void setURL(String value);
	
		public String getTitle();
		public void setTitle(String value);
	
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
			android:hint="@string/url_address"
			style="@style/default_edit_text"
			android:inputType="textUri" />
	
		<EditText
			android:id="@+id/title"
			style="@style/default_edit_text"
			android:hint="@string/title"
			android:inputType="textAutoComplete" />
	
		<Button
			android:id="@+id/add_button"
			style="@style/default_submit_button"
			android:text="@string/add_bookmark" />
	
	</com.your.package.AddBookmarkDefaultView>
	```

    At this point, the graphical layout viewer in Android Studio should look like this:

    ![An app based on Liferay Screens.](images/addbookmark_view.png)

3. Create a new custom view class called `AddBookmarkDefaultView`, that extends `LinearLayout` and implements `AddBookmarkViewModel`. This new class is where you implement the UI using the layout XML file from the previous step.

	```java
	public class AddBookmarkDefaultView
		extends LinearLayout implements AddBookmarkViewModel {
	
		public AddBookmarkDefaultView(Context context) {
			super(context);
		}
	
		public AddBookmarkDefaultView(Context context, AttributeSet attributes) {
			super(context, attributes);
		}
	
		public AddBookmarkDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
			super(context, attributes, defaultStyle);
		}
	
		// TODO setters and getters have to set/get the data from/to the views
		public String getURL() ... 
		public void setURL(String value) ...
	
		public String getTitle() ...
		public void setTitle(String value) ...
	
	}
	```

4. In the `onFinishInflate` method, get the reference to the components. Then complete the getters and setters using the inner value of the components:

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

5. Create the interactor class. It's responsible for sending the bookmark to the Liferay instance (or any other backend). Note that it's a good practice to use [IoC](http://en.wikipedia.org/wiki/Inversion_of_control) in your interactor classes. This way, anyone can provide a different implementation without breaking the code. The `Interactor` base class also needs a parameter that represents the type of listener to notify. This is defined here:

	```java
	public interface AddBookmarkInteractor extends Interactor<AddBookmarkListener> {
	
		public void addBookmark(String url, String title);
	
	}
	```

	```java
	public class AddBookmarkInteractorImpl
		extends BaseRemoteInteractor<AddBookmarkListener>
		implements AddBookmarkInteractor {
	
		public AddBookmarkInteractorImpl(int targetScreenletId) {
			super(targetScreenletId);
		}
	
		public void addBookmark(String url, String title) {
			// 1. Validate input
			if (url == null || url.isEmpty()) {
				throw new IllegalArgumentException("Invalid url");
			}
	
			// 2. Call the service asynchronously.
			// Notify when the request ends using the EventBus
		}
	
		public void onEvent(BasicEvent event) {
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

    Pay special attention to the second step in the `addBookmark` method. When the request ends, make sure you post an event into the bus using `EventBusUtil.post(event)`, where `event` is a `BasicEvent` object containing the `targetScreenletId` together with either the result or the exception. Every interactor should also implement the `onEvent` method. This method is invoked by the `EventBus` and calls the registered listener.

6. Once your interactor is ready, you need to create the screenlet class. This is the cornerstone and entry point that your app developer sees and interacts with. In this example, this class is called `AddBookmarkScreenlet` and extends from `BaseScreenlet`. Again, this class needs to be parameterised with the interactor class. Since the screenlet is notified by the interactor when the asynchronous operation ends, you must implement the listener interface used by the interactor (`AddBookmarkListener`, in this case). Also, to notify the app, this class usually has another listener. This listener can be the same one you used in the interactor or a different one altogether (if you want different methods or signatures). You could even notify the app using a different mechanism such as the Event Bus, Android's `BroadcastReceiver`, or others.  Note that the implemented interface methods call the view to modify the UI and the app's listener to allow the app to perform any action:

	```java
	public class AddBookmarkScreenlet
		extends BaseScreenlet<LoginInteractor>
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
	
			AddBookmarkListener view = (AddBookmarkListener) getScreenletView();
			view.onAddBookmarkSuccess();
	
			if (_listener != null) {
				_listener.onAddBookmarkSuccess();
			}
		}
	
		public void onAddBookmarkFailure(Exception e) {
			AddBookmarkListener view = (AddBookmarkListener) getScreenletView();
			view.onAddBookmarkFailure(e);
	
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

7. You're almost finished! The next step is to implement the screenlet's abstract methods. First is the `createScreenletView` method. In this method you get attributes from the XML definition and either store them as class attributes or otherwise make use of them. Then inflate the view using the layout specified in the `liferay:layoutId` attribute. You can even configure the initial state of the view, using the attributes read.

	```java
		@Override
		protected View createScreenletView(Context context, AttributeSet attributes) {
			TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.AddBookmarkScreenlet, 0, 0);
				
			int layoutId = typedArray.getResourceId(R.styleable.AddBookmarkScreenlet_layoutId, 0);
	
			View view = LayoutInflater.from(context).inflate(layoutId, null);
			
			String defaultTitle = typedArray.getResourceString(R.styleable.AddBookmarkScreenlet_defaultTitle, 0);
			
			typedArray.recycle();
	
			AddBookmarkViewModel viewModel = (AddBookmarkViewModel) view;
			viewModel.setTitle(defaultTitle);
	
			return view;
		}
	```

    The Second abstract method to implement is `createInteractor`. This is a factory method in which you have to create the corresponding interactor for a specific action name. Note that a single screenlet may have several interactions (use cases). Each interaction should therefore be implemented in a separate interactor. In this example there is only one interactor, so the object is created in the method. Alternatively, you can retrieve the instance by using your IoC framework. Also, you need to pass the `screenletId` (a number auto generated by the `BaseScreenlet` class) to the constructor:

	```java
		protected AddBookmarkInteractor createInteractor(String actionName) {
			return new AddBookmarkInteractorImpl(getScreenletId());
		}
	```

    The third and final abstract method to implement is `onUserAction`. In this method, retrieve the data entered in the view and start the operation by using the supplied interactor and the data:

	```java
		protected void onUserAction(String userActionName, AddBookmarkInteractor interactor) {
			AddBookmarkViewModel viewModel = (AddBookmarkViewModel) getScreenletView();
			String url = viewModel.getURL();	
			String title = viewModel.getTitle();
	
			interactor.addBookmark(url, title);
		}
	```

8. The only thing left to do is to trigger the user action when the button is pressed. To do this, go back to the view and add a listener to the button:

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

Congratulations! Now you know how to create your own screenlets.

## Packaging Your Screenlets

If you want to distribute your screenlets to reuse them in different projects you should package them in a module (android library) that is then added as an app's project dependency.

To do this, you have to do several steps:

- Create a new Android module and configure the build.gradle file.
- Use your new module from your application
- [Optional] Upload the module to jcenter or mavenCentral

Let's see the steps in practice

### Create a new Android module

Luckily Android Studio has a menu option that automatically creates an Android Module and adds it to your settings.gradle automatically.

If you want to do it by hand, you will have to create a new Android Library. It is basically an Android Application with the gradle import set to *apply plugin: 'com.android.library'*. Use the gradle file of the material viewset or Westeros as an example.

### Use your need module from your application

After creation, you need to import the new module into your project by specifying its location in the [settings.gradle](https://github.com/liferay/liferay-screens/tree/master/android/samples/settings.gradle).

The new will have to include liferay screens as a dependency and all the viewsets you are using.

### Upload the module to jcenter or mavenCentral

If you want to redistribute your theme and let other people use it you can upload it to jcenter or mavenCentral. 

Use the build.gradle file of the material or Westeros viewset as an example.

After entering your bintray api key, you can execute *gradlew bintrayupload* to upload your project to jcenter. When finished you will be able to use as every other Android dependency, adding the repository, artifact, groupId and version to your gradle file.