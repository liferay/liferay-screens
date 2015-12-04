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


@objc public protocol UserPortraitScreenletDelegate : BaseScreenletDelegate {

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

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var userPortraitDelegate: UserPortraitScreenletDelegate? {
		return self.delegate as? UserPortraitScreenletDelegate
	}

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
	}

	public func loadLoggedUserPortrait() -> Bool {
		if SessionContext.currentUserId == nil {
			return false
		}

		let interactor = DownloadUserPortraitInteractor(
			screenlet: self,
			userId: SessionContext.currentUserId!)

		loadedUserId = SessionContext.currentUserId

		return performAction(name: "load-portrait", sender: interactor)
	}

	public func load(portraitId portraitId: Int64, uuid: String, male: Bool = true) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	public func load(userId userId: Int64) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				userId: userId)

		loadedUserId = userId

		return performAction(name: "load-portrait", sender: interactor)
	}

	public func load(companyId companyId: Int64, emailAddress: String) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				companyId: companyId,
				emailAddress: emailAddress)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	public func load(companyId companyId: Int64, screenName: String) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				companyId: companyId,
				screenName: screenName)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	public func loadPlaceholder() {
		viewModel.image = nil
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor: Interactor?

		if isActionRunning(name) {
			cancelInteractorsForAction(name)
		}

		switch name {
		case "load-portrait":
			let loadInteractor = sender as! DownloadUserPortraitInteractor
			interactor = loadInteractor

			loadInteractor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

			loadInteractor.onSuccess = {
				if let imageValue = loadInteractor.resultImage {
					let finalImage = self.userPortraitDelegate?.screenlet?(self, onUserPortraitResponseImage: imageValue)

					self.loadedUserId = loadInteractor.resultUserId
					self.setPortraitImage(finalImage ?? imageValue)
				}
				else {
					self.userPortraitDelegate?.screenlet?(self, onUserPortraitError: NSError.errorWithCause(.InvalidServerResponse))

					self.loadedUserId = nil
					self.setPortraitImage(nil)
				}
			}

			loadInteractor.onFailure = {
				self.userPortraitDelegate?.screenlet?(self, onUserPortraitError: $0)

				self.loadedUserId = nil
				self.setPortraitImage(nil)
			}

		case "upload-portrait":
			let image = sender as! UIImage
			let userId: Int64

			if let loadedUserIdValue = loadedUserId {
				userId = loadedUserIdValue
			}
			else {
				print("ERROR: Can't change the portrait without an userId\n")

				return nil
			}

			let uploadInteractor = UploadUserPortraitInteractor(
					screenlet: self,
					userId: userId,
					image: image)
			interactor = uploadInteractor

			uploadInteractor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

			uploadInteractor.onSuccess = {
				self.userPortraitDelegate?.screenlet?(self, onUserPortraitUploaded: uploadInteractor.uploadResult!)

				self.loadedUserId = uploadInteractor.userId
				self.setPortraitImage(uploadInteractor.image)
			}

			uploadInteractor.onFailure = {
				self.userPortraitDelegate?.screenlet?(self, onUserPortraitUploadError: $0)
			}

		default:
			interactor = nil
		}

		return interactor
	}


	//MARK: Private methods

	private func setPortraitImage(image: UIImage?) {
		viewModel.image = image

		if image == nil {
			let error = NSError.errorWithCause(.AbortedDueToPreconditions)
			userPortraitDelegate?.screenlet?(self, onUserPortraitError: error)
		}
	}

}
