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

@objc public protocol AddBookmarkScreenletDelegate: BaseScreenletDelegate {

	optional func screenlet(screenlet: AddBookmarkScreenlet,
	                        onBookmarkAdded bookmark: Bookmark)

	optional func screenlet(screenlet: AddBookmarkScreenlet,
	                        onAddBookmarkError error: NSError)
	
}


//Screenlet used for adding bookmarks to a certain folder
public class AddBookmarkScreenlet: BaseScreenlet {


	//MARK: Actions

	static let AddBookmarkAction = "add-bookmark"
	static let GetTitleAction = "get-title"


	//MARK: Inspectables

	@IBInspectable var folderId: Int64 = 0


	///Screenlet ViewModel
	var viewModel: AddBookmarkViewModel {
		return self.screenletView as! AddBookmarkViewModel
	}

	///Screenlet Delegate
	var addBookmarkDelegate: AddBookmarkScreenletDelegate? {
		return self.delegate as? AddBookmarkScreenletDelegate
	}


	//MARK: BaseScreenlet

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		switch name {
		case AddBookmarkScreenlet.AddBookmarkAction:
			return createAddBookmarkInteractor()
		case AddBookmarkScreenlet.GetTitleAction:
			return createGetTitleInteractor()
		default:
			return nil
		}
	}


	//MARK: Private methods

	private func createAddBookmarkInteractor() -> Interactor {
		let interactor = AddBookmarkInteractor(screenlet: self,
		                                       folderId: folderId,
		                                       title: viewModel.title!,
		                                       url: viewModel.URL!)

		//Called when interactor finish succesfully
		interactor.onSuccess = {
            if let bookmark = interactor.resultBookmark {
                self.addBookmarkDelegate?.screenlet?(self, onBookmarkAdded: bookmark)
            }
		}

		//Called when interactor finish with error
		interactor.onFailure = { error in
			self.addBookmarkDelegate?.screenlet?(self, onAddBookmarkError: error)
		}

		return interactor
	}

	private func createGetTitleInteractor() -> Interactor {
        let interactor = GetWebTitleInteractor(screenlet: self, url: viewModel.URL!)

		//Called when interactor finish succesfully
		interactor.onSuccess = {
			let title = interactor.resultTitle
			self.viewModel.title = title
		}

		//Called when interactor finish with error
		interactor.onFailure = { _ in
			print("An error occurred retrieving the title")
		}

		return interactor
	}
}
