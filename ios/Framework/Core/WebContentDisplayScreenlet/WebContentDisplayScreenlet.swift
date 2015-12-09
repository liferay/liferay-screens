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


@objc public protocol WebContentDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: WebContentDisplayScreenlet,
			onWebContentResponse html: String ) -> String?

	optional func screenlet(screenlet: WebContentDisplayScreenlet,
			onWebContentError error: NSError)

}


@IBDesignable public class WebContentDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var groupId: Int64 = 0

	// use either articleId or classPK
	@IBInspectable public var articleId: String = ""
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true
	@IBInspectable public var templateId: Int64 = 0
	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var webContentDisplayDelegate: WebContentDisplayScreenletDelegate? {
		return delegate as? WebContentDisplayScreenletDelegate
	}


	//MARK: Public methods

	override public func onShow() {
		if autoLoad && (articleId != "" || classPK != 0) {
			loadWebContent()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor = WebContentDisplayLoadInteractor(screenlet: self)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			let modifiedHtml = self.webContentDisplayDelegate?.screenlet?(self,
					onWebContentResponse: interactor.resultHTML!)

			(self.screenletView as! WebContentDisplayViewModel).htmlContent =
					modifiedHtml ?? interactor.resultHTML!
		}

		interactor.onFailure = {
			self.webContentDisplayDelegate?.screenlet?(self, onWebContentError: $0)
		}

		return interactor
	}

	public func loadWebContent() -> Bool {
		return self.performDefaultAction()
	}

}
