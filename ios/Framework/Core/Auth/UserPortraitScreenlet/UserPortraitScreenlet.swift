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

	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitResponseImage image: UIImage) -> UIImage

	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitError error: NSError)

	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitUploaded attributes: [String:AnyObject])

	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitUploadError error: NSError)
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

	@IBInspectable public var editable: Bool = false {
		didSet {
			(screenletView as? UserPortraitViewModel)?.editable = self.editable
		}
	}

	@IBOutlet public weak var delegate: UserPortraitScreenletDelegate?


	public var viewModel: UserPortraitViewModel {
		return screenletView as! UserPortraitViewModel
	}

	private var loadedUserId: Int64?


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()

		viewModel.borderWidth = self.borderWidth
		viewModel.borderColor = self.borderColor
		viewModel.editable = self.editable
		viewModel.portraitLoaded = onPortraitLoaded
	}

	public func loadLoggedUserPortrait() -> Bool {
		let interactor = UserPortraitLoadLoggedUserInteractor(screenlet: self)

		loadedUserId =  SessionContext.currentUserId

		return startInteractor(interactor)
	}

	public func load(#portraitId: Int64, uuid: String, male: Bool = true) -> Bool {
		let interactor = UserPortraitAttributesLoadInteractor(
				screenlet: self,
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		loadedUserId = nil

		return startInteractor(interactor)
	}

	public func load(#userId: Int64) -> Bool {
		let interactor = UserPortraitLoadByUserIdInteractor(
				screenlet: self,
				userId: userId)

		loadedUserId = userId

		return startInteractor(interactor)
	}

	public func load(#companyId: Int64, emailAddress: String) -> Bool {
		let interactor = UserPortraitLoadByEmailAddressInteractor(
				screenlet: self,
				companyId: companyId,
				emailAddress: emailAddress)

		loadedUserId = nil

		return startInteractor(interactor)
	}

	public func load(#companyId: Int64, screenName: String) -> Bool {
		let interactor = UserPortraitLoadByScreenNameInteractor(
				screenlet: self,
				companyId: companyId,
				screenName: screenName)

		loadedUserId = nil

		return startInteractor(interactor)
	}

	override public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {

		let interactor: UploadUserPortraitInteractor?

		switch name! {
		case "upload-portrait":
			let image = sender as! UIImage
			let userId: Int64

			if let loadedUserIdValue = loadedUserId {
				userId = loadedUserIdValue
			}
			else {
				println("ERROR: Can't change the portrait without an userId")

				return nil
			}

			interactor = UploadUserPortraitInteractor(
					screenlet: self,
					userId: userId,
					image: image)

			interactor!.onSuccess = { [weak interactor] in
				self.delegate?.screenlet?(self, onUserPortraitUploaded: interactor!.uploadResult!)
				self.load(userId: userId)
			}

			interactor!.onFailure = {
				self.delegate?.screenlet?(self, onUserPortraitUploadError: $0)
				return
			}

		default:
			interactor = nil
		}

		return interactor
	}

	//MARK: Private methods

	private func startInteractor(interactor: UserPortraitBaseInteractor) -> Bool {
		interactor.onSuccess = {
			self.setPortraitURL(interactor.resultURL)
			self.loadedUserId = interactor.resultUserId
		}

		return interactor.start()
	}

	private func setPortraitURL(url: NSURL?) {
		viewModel.portraitURL = url

		if url == nil {
			screenletView?.onFinishOperation()
			delegate?.screenlet?(self,
					onUserPortraitError: NSError.errorWithCause(.AbortedDueToPreconditions))
		}
	}

	private func onPortraitLoaded(image: UIImage?, error: NSError?) -> UIImage? {
		var finalImage = image

		if let errorValue = error {
			delegate?.screenlet?(self, onUserPortraitError: errorValue)
		}
		else if let imageValue = image {
			finalImage = delegate?.screenlet?(self, onUserPortraitResponseImage: imageValue)
		}

		screenletView?.onFinishOperation()

		return finalImage
	}

}
