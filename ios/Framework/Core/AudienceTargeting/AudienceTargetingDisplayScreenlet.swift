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


@objc public protocol AudienceTargetingDisplayScreenletDelegate {

	optional func screenlet(screenlet: AudienceTargetingDisplayScreenlet,
			onAudienceTargetingResponse value: AnyObject,
			mimeType: String?)

	optional func screenlet(screenlet: AudienceTargetingDisplayScreenlet,
			onAudienceTargetingError error: NSError)

}


@IBDesignable public class AudienceTargetingDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var appId: String = "" // should be Int64?
	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var placeholderId: String = ""
	@IBInspectable public var autoLoad: Bool = true

	@IBOutlet public weak var delegate: AudienceTargetingDisplayScreenletDelegate?


	//MARK: Public methods

	override public func onShow() {
		if autoLoad && appId != "" && placeholderId != "" {
			loadContent()
		}
	}

	override public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		// what if we pass data to be used in rules evaluation in this request?
		let interactor = AudienceTargetingLoadPlaceholderInteractor(screenlet: self)

		// force start here to avoid start-stop-start effects
		screenletView?.onStartOperation()

		interactor.onSuccess = {
			let content: AnyObject = interactor.resultCustomContent ?? (interactor.resultContent!)

			self.delegate?.screenlet?(self,
					onAudienceTargetingResponse: content,
					mimeType: interactor.resultMimeType)

			let viewModel = (self.screenletView as! AudienceTargetingDisplayViewModel)

			viewModel.setContent(content, mimeType: interactor.resultMimeType)

			self.screenletView?.onFinishOperation()
		}

		interactor.onFailure = {
			self.delegate?.screenlet?(self, onAudienceTargetingError: $0)
			self.screenletView?.onFinishOperation()
			return
		}

		return interactor
	}

	public func loadContent() -> Bool {
		return self.performDefaultAction()
	}

}
