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


@objc public protocol WebContentDisplayScreenletDelegate {

	optional func onWebContentResponse(html:String) -> String?
	optional func onWebContentError(error: NSError)

}


@IBDesignable public class WebContentDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var articleId: String = ""
	@IBInspectable public var autoLoad: Bool = true

	@IBOutlet public var delegate: WebContentDisplayScreenletDelegate?


	//MARK: Public methods

	override func onShow() {
		if autoLoad && articleId != "" {
			loadWebContent()
		}
	}

	override internal func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		let interactor = WebContentDisplayLoadInteractor(screenlet: self)

		interactor.onSuccess = {
			let modifiedHtml = self.delegate?.onWebContentResponse?(interactor.resultHTML!)

			(self.screenletView as WebContentDisplayViewModel).htmlContent =
					(modifiedHtml != nil) ? modifiedHtml! : interactor.resultHTML!
		}

		interactor.onFailure = {
			self.delegate?.onWebContentError?($0)
			return
		}

		return interactor
	}

	public func loadWebContent() -> Bool {
		return self.performDefaultAction()
	}

}
