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


@objc public protocol UserPortraitScreenletDelegate {

	optional func onUserPortraitResponse(image: UIImage) -> UIImage
	optional func onUserPortraitError(error: NSError)

}


public class UserPortraitScreenlet: BaseScreenlet {

	@IBInspectable public var borderWidth: CGFloat = 1.0 {
		didSet {
			(screenletView as? UserPortraitViewModel)?.borderWidth = self.borderWidth
		}
	}
	@IBInspectable public var borderColor: UIColor? {
		didSet {
			(screenletView as? UserPortraitViewModel)?.borderColor = self.borderColor
		}
	}

	@IBOutlet public weak var delegate: UserPortraitScreenletDelegate?


	public var viewModel: UserPortraitViewModel {
		return screenletView as! UserPortraitViewModel
	}


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()

		viewModel.borderWidth = self.borderWidth
		viewModel.borderColor = self.borderColor
		viewModel.portraitLoaded = onPortraitLoaded
	}

	public func loadLoggedUserPortrait() -> Bool {
		let interactor = UserPortraitLoadLoggedUserInteractor(screenlet: self)

		return startInteractor(interactor)
	}

	public func load(#portraitId: Int64, uuid: String, male: Bool = true) -> Bool {
		let interactor = UserPortraitAttributesLoadInteractor(
				screenlet: self,
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		return startInteractor(interactor)
	}

	public func load(#userId: Int64) -> Bool {
		let interactor = UserPortraitLoadByUserIdInteractor(
				screenlet: self,
				userId: userId)

		return startInteractor(interactor)
	}

	public func load(#companyId: Int64, emailAddress: String) -> Bool {
		let interactor = UserPortraitLoadByEmailAddressInteractor(
				screenlet: self,
				companyId: companyId,
				emailAddress: emailAddress)

		return startInteractor(interactor)
	}

	public func load(#companyId: Int64, screenName: String) -> Bool {
		let interactor = UserPortraitLoadByScreenNameInteractor(
				screenlet: self,
				companyId: companyId,
				screenName: screenName)

		return startInteractor(interactor)
	}


	//MARK: Private methods

	private func startInteractor(interactor: UserPortraitBaseInteractor) -> Bool {
		interactor.onSuccess = {
			self.setPortraitURL(interactor.resultURL)
		}

		return interactor.start()
	}

	private func setPortraitURL(url: NSURL?) {
		viewModel.portraitURL = url

		if url == nil {
			screenletView?.onFinishOperation()
			delegate?.onUserPortraitError?(createError(cause: .AbortedDueToPreconditions))
		}
	}

	private func onPortraitLoaded(image: UIImage?, error: NSError?) -> UIImage? {
		var finalImage = image

		if let errorValue = error {
			delegate?.onUserPortraitError?(errorValue)
		}
		else if let imageValue = image {
			finalImage = delegate?.onUserPortraitResponse?(imageValue)
		}

		screenletView?.onFinishOperation()

		return finalImage
	}

}
