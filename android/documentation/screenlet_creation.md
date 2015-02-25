# Creating Screenlets in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the steps required to create your own screenlet. Before proceeding, you may want to read the [Architecture Guide](architecture.md) in order to understand the underlying concepts. You may also want to read the guide [How to Create Your Own View](view_creation.md) in order to support the new screenlet from the Default or other view sets.

The steps below walk you through creating an example screenlet for bookmarks that has the following features:

- Allows the user to enter an URL and title in a text box.
- When the user touches the submit button, the URL and title entered are sent to the Liferay instance's Bookmark service to be saved.

Steps:

- Create a new interface called `AddBookmarkViewModel`. This will include all attributes that will be presented in the view. In our case, these are `url` and `title`. Any screenlet view must implement this interface.

```java
public interface AddBookmarkViewModel {

	public String getURL();
	public void setURL(String value);

	public String getTitle();
	public void setTitle(String value);

}
```

- Build your UI here using a layout XML. Put in two `EditText` for the URL and title. Also, add button to let the user save the bookmark. Note the root element is a custom class. We'll cover that class later.

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

- The Android Studio's previewer will look like this:

![App based on Liferay Screens](images/addbookmark_view.png)

- Create a new custom view class called `AddBookmarkDefaultView` extending from `LinearLayout` and implementing `AddBookmarkViewModel`. This class will implement the UI together with the previous layout XML.

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

- In the `onFinishInflate` method, get the reference to the components. Then complete the getters and setters using the inner value of the components:

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

- Now, create the interactor class. It will be responsible for sending the bookmark to the Liferay Portal (or any other backend). Note that it's a good practice to use [IoC](http://en.wikipedia.org/wiki/Inversion_of_control) in your interactor classes. This way, anyone could provide a different implementation without breaking the rest of the code. `Interactor` base class also needs as a parameter the type of listener to notify to. We'll define this type below:

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

- Pay special attention to the second step in the `addBookmark` method. When the request ends, make sure you post an event into the bus using `EventBusUtil.post(event)`, where 'event' is a `BasicEvent` object containing the `targetScreenletId` together with either the result or the exception. Every interactor should also implement the `onEvent` method. It will be invoked by the EventBus and it will call the registered listener.

- Once you have your interactor ready, you need to create the screenlet class. This is the cornerstone and the entry point that your app developer will see and interact with. Let's call our class `AddBookmarkScreenlet` and extend it from `BaseScreenlet`. Again, this class have to be parametrized with the interactor class. Given that the screenlet will be notified by the interactor when the asynchronous operation ends, we have to implement the listener interface used by the interactor (`AddBookmarkListener` in our case). Also, in order to notify the app, this class usually has another listener. This new listener may be the same you used in the interactor, or a different one (if you want different methods or signatures) or even you can notify the app using a different mechanism (event bus, Android's BroadcastReceiver, etc.).  Note that the implemented interface methods, call both the view (to modify the UI) and the app's listener (to allow the app to perform any action):

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
- We're getting close. The next step is to implement the abstract methods of the screenlets. Let's do it one by one: first is the `createScreenletView` method. In there you get attributes from the XML definition (and in turn store them as class attributes or use it somehow) and finally inflate the view using the layout specified in the `liferay:layoutId` attribute. You can even configure the initial state of the view, using the attributes read.

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

- Second abstract method is the `createInteractor` one. This is a factory method where you have to create the corresponding interactor for one specific action name. Note that one screenlet may have several "interactions" (or use cases), so each interaction should be implemented in a separated interactor. In our case, we have only one interactor, so we create the object (or get the instance using your IoC framework) in there. You'll have to pass the `screenletId` (a number autogenerated by the `BaseScreenlet` class in the constructor:

```java
	protected AddBookmarkInteractor createInteractor(String actionName) {
		return new AddBookmarkInteractorImpl(getScreenletId());
	}
```

- And finally, the third abstract method is the `onUserAction` one. In there, you get the data entered in the view, and starts the operation using the supplied interactor and the data.

```java
	protected void onUserAction(String userActionName, AddBookmarkInteractor interactor) {
		AddBookmarkViewModel viewModel = (AddBookmarkViewModel) getScreenletView();
		String url = viewModel.getURL();	
		String title = viewModel.getTitle();

		interactor.addBookmark(url, title);
	}
```

- And at the end, the only missing thing is to trigger the user action when the button is touched. So we go back to the view, and add a listener to the button:

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
