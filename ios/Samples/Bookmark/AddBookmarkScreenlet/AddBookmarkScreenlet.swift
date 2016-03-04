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


public class AddBookmarkScreenlet: BaseScreenlet {

	@IBInspectable var folderId: Int64 = 0
	@IBInspectable var allowsBrokenURL: Bool = false


	override public func createInteractor(name name: String?, sender: AnyObject?) -> Interactor? {
		switch name! {
		case "get-title":
			return createGetTitleInteractor()

		case "add-bookmark":
			return createAddBookmarkInteractor()

		default:
			return nil
		}
	}

	private func createGetTitleInteractor() -> GetSiteTitleInteractor {
		let interactor = GetSiteTitleInteractor(screenlet: self)

		// this shows the standard activity indicator in the screen...
		self.showHUDWithMessage("Getting site title...",
			closeMode: .Autoclose,
			spinnerMode: .IndeterminateSpinner)

		interactor.onSuccess = {
			self.hideHUD()

			// when the interactor is finished, set the resulting title in the title text field
			(self.screenletView as? AddBookmarkViewModel)?.title = interactor.resultTitle
		}

		interactor.onFailure = { err in
			self.showHUDWithMessage("An error occurred retrieving the title",
				closeMode: .ManualClose_TouchClosable,
				spinnerMode: .NoSpinner)
		}

		return interactor
	}

	private func createAddBookmarkInteractor() -> LiferayAddBookmarkInteractor {
		let viewModel = (self.screenletView as! AddBookmarkViewModel)

		let interactor = LiferayAddBookmarkInteractor(
			screenlet: self,
			folderId:  self.folderId,
			title: viewModel.title!,
			url: viewModel.URL!)

		self.showHUDWithMessage("Saving bookmark...",
			closeMode: .Autoclose,
			spinnerMode: .IndeterminateSpinner)

		interactor.onSuccess = {
			self.showHUDWithMessage("Bookmark saved!",
				closeMode: .Autoclose_TouchClosable,
				spinnerMode: .NoSpinner)
		}

		interactor.onFailure = { e in
			self.showHUDWithMessage("An error occurred saving the bookmark",
				closeMode: .ManualClose_TouchClosable,
				spinnerMode: .NoSpinner)
		}

		return interactor
	}

}
