/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit
import LiferayScreens


//Screenlet used for adding bookmarks to a certain folder
public class AddBookmarkBasicScreenlet: BaseScreenlet {


	//MARK: Inspectables

	@IBInspectable var folderId: Int64 = 0


	//MARK: BaseScreenlet

	override public func createInteractor(name name: String?, sender: AnyObject?) -> Interactor? {

		let view = self.screenletView as! AddBookmarkBasicView_default

		let interactor = AddBookmarkBasicInteractor(screenlet: self,
		                                       folderId: folderId,
		                                       title: view.title!,
		                                       url: view.URL!)

		//Called when interactor finish succesfully
		interactor.onSuccess = {
			let bookmarkName = interactor.resultBookmarkInfo!["name"] as! String
			print("Bookmark \"\(bookmarkName)\" saved!")
		}

		//Called when interactor finish with error
		interactor.onFailure = { _ in
			print("An error occurred saving the bookmark")
		}

		return interactor
	}

}
