# Creating Screenlets in Liferay Screens for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the steps required to create your own screenlet. Before proceeding, you may want to read the [Architecture Guide](architecture.md) in order to understand the underlying concepts. You may also want to read the guide [How to Create Your Own Theme](theme_creation.md) in order to support the new screenlet from the Default or other themes.

The steps below walk you through creating an example screenlet for bookmarks that has the following features:

- Allows entry of a URL in a text box.
- Checks if the URL is valid and extract its title value. 
- Allows the user to modify the title.
- Upon user confirmation, the URL and title is sent back to the Liferay instance's Bookmark services to be saved.

You can perform these steps in your app's Xcode project. However, if you want to distribute your screenlet as a library so that other apps can use it, follow the steps described in the section *Publish Your Themes Using CocoaPods* in [Creating a Theme in Liferay Screens for iOS](theme_creation.md#publish-your-themes-using-cocoapods).
This project [in this folder](https://github.com/liferay/liferay-screens/tree/master/ios/Samples/AddBookmark-screenlet) contains the screenlet described in this tutorial. You can use it as a reference.

## Steps

* Create a new `xib` called `AddBookmarkView_default.xib`. You'll build your UI here using Interface Builder. Put in two text box fields (`UITextField`) for the URL and title. Also, add a couple of buttons to let the user retrieve the title and save the bookmark. To differentiate between these two user actions, assign a value for the `restorationIdentifier` property in each button (for example `get-title` and `add-bookmark`)

    ![The new `xib` file for the new screenlet.](Images/xcode-add-bookmark.png)

* Create a new protocol called `AddBookmarkViewModel`. The associated attributes are `URL` and `title`.

```swift
@objc protocol AddBookmarkViewModel {

	var URL: String? {get set}

	var title: String? {get set}

}
```

* Create a new class called `AddBookmarkView_default` that extends `BaseScreenletView` and conforms `AddBookmarkViewModel`. It must wire all UI components and events from the `xib` using the standard `@IBOutlet` and `@IBAction`. Getters and setters from `AddBookmarkViewModel` should, respectively, get and set the data from UI components. Also be sure to write any animations or front end code here.

```swift 
import UIKit
import LiferayScreens

class AddBookmarkView_default: BaseScreenletView, AddBookmarkViewModel {

	@IBOutlet weak var URLTextField: UITextField!
	@IBOutlet weak var titleTextField: UITextField!

	var URL: String? {
		get {
			return URLTextField.text
		}
		set {
			URLTextField.text = newValue
		}
	}

	var title: String? {
		get {
			return titleTextField.text
		}
		set {
			titleTextField.text = newValue
		}
	}

}
```

* Set `AddBookmarkView_default` as the custom class of your `AddBookmarkView_default.xib` file. If you're using CocoaPods, be careful to set the appropiate module (don't use the grayed value "Current"). For example, the Custom Class setting in this screenshot is incorrect. This is because the `xib` file is bound to the custom class name without specifying the module:

![The `xib` file is bound to the custom class name without specifying the module.](Images/xcode-theme-custom-module-wrong.png)

In the following screenshot, the setting for the custom class is correct:

![Xib file binded to custom class name specifying the module.](Images/xcode-theme-custom-module-right.png)

* Create a class called `GetSiteTitleInteractor` that extends from class `Interactor`. This will be the place where we're going to write the code to get the title of the web site. It's important to understand how an interactor works:
	* It works asynchronously. This means that the underlaying operation (to retrieve the HTML page) will be run in background and when it's completed, a closure will be called: `onSuccess` or `onFailure` depending on the result of the operation. You have to use `callOnSuccess()` and `callOnFailure(error)` methods to invoke those closures. 
	* You have to override the method `start()` and place your code there.
	* Since the interactor receives the source screenlet in the init method, you can use that reference to read input data.

```swift
import UIKit
import LiferayScreens

public class GetSiteTitleInteractor: Interactor {

	public var resultTitle: String?

	override public func start() -> Bool {
		let viewModel = self.screenlet as! AddBookmarkViewModel

		if let URL = viewModel.URL {
			// 1. use NSURLSession to retrieve the HTML
			// 2. When the response arrives, extract the title from the HTML
			// 3. Save the extracted title in the property 'resultTitle'
			// 4. invoke callOnSuccess() or callOnFailure(error) when everything is done

			// return true to notify the operation is in progress
			return true
		}

		// return false if you cannot start the operation
		return false
	}
   
}
```

* Now, do the same with the second interactor: `LiferayAddBookmarkInteractor`. This will be responsible for sending the URL and title to the Liferay portal to be stored there. We use `Liferay` as class name prefix to denote the interactor will use Liferay's services.

```swift
import UIKit
import LiferayScreens

public class LiferayAddBookmarkInteractor: Interactor {

	public var resultBookmarkInfo: [String:AnyObject]?

	override public func start() -> Bool {
		let viewModel = self.screenlet as! AddBookmarkViewModel

		if let URL = viewModel.URL {
			// 1. use MobileSDK's services to send the bookmark to the portal
			// 3. Save the response in the property 'resultBookmarkInfo'
			// 4. invoke callOnSuccess() or callOnFailure(error) when everything is done

			// return true to notify the operation is in progress
			return true
		}

		// return false if you cannot start the operation
		return false
	}
   
}
```

* Now it's time to glue everything together. Let's create a class called `AddBookmarkScreenlet ` that extends `BaseScreenlet`.

* Optionally, you can add any `@IBInspectable` property to configure the behavior. For example, you could use a boolean property to configure whether the user can save broken URLs.

```swift
import UIKit
import LiferayScreens

class AddBookmarkScreenlet: BaseScreenlet {


}
```

* Override the `createInteractor` method so you return an instance of your interactor depending on the parameter `name`: `GetSiteTitleInteractor` for name `get-title` and `LiferayAddBookmarkInteractor` for name `add-bookmark`. Let's call separated methods for create each interactor:

```swift
override public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
	switch name! {
	case "get-title":
		return createGetTitleInteractor()

	case "add-bookmark":
		return createAddBookmarkInteractor()

	default:
		return nil
	}
}
```

* The method to create the `GetSiteTitleInteractor` should looks like the following:

```swift
private func createGetTitleInteractor() -> GetSiteTitleInteractor {
	let interactor = GetSiteTitleInteractor(screenlet: self)

	// this shows the standard activity indicator in the screen...
	self.showHUDWithMessage("Getting site title...", details: nil)

	interactor.onSuccess = {
		self.hideHUD()

		// when the interactor is finished, set the resulting title in the title text field
		(self.screenletView as? AddBookmarkViewModel)?.title = interactor.resultTitle
	}

	interactor.onFailure = { err in
		self.showHUDWithMessage("An error occurred retrieving the title",
				details: nil,
				closeMode: .ManualClose(true),
				spinnerMode: .NoSpinner)
	}

	return interactor
}
```

* And the method to create the `LiferayAddBookmarkInteractor` should looks like the following:

```swift
private func createAddBookmarkInteractor() -> LiferayAddBookmarkInteractor {
	let interactor = LiferayAddBookmarkInteractor(screenlet: self)

	self.showHUDWithMessage("Saving bookmark...", details: nil)

	interactor.onSuccess = {
		self.hideHUDWithMessage("Bookmark saved!")
	}

	interactor.onFailure = { e in
		self.showHUDWithMessage("An error occurred saving the bookmark",
				details: nil,
				closeMode: .ManualClose(true),
				spinnerMode: .NoSpinner)
	}

	return interactor
}
```

* You're done! Now you can add your new screenlet to your storyboard as usual and use it as a ready-to-use component. When the user presses any button, the BaseScreenlet's code will create and run the interactor.

* If you want to see the final code, you have the project with this screenlet in [this folder](https://github.com/liferay/liferay-screens/tree/master/ios/Samples/AddBookmark-screenlet).
