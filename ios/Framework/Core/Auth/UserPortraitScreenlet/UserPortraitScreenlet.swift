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

	/// Called when an image is received from the server. You can then apply image filters 
	/// (grayscale, for example) and return the new image. You can return the original image 
	/// supplied as the argument if you don’t want to modify it.
	///
	/// - Parameters:
	///   - screenlet
	///   - image: user portrait image.
	/// - Returns: original or modify image.
	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitResponseImage image: UIImage) -> UIImage

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving user portrait image.
	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitError error: NSError)

	/// Called when a new portrait is uploaded to the server. You receive the user 
	/// attributes as a parameter.
	///
	/// - Parameters:
	///   - screenlet
	///   - attributes: user portrait attributes.
	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitUploaded attributes: [String:AnyObject])

	/// Called when an error occurs in the upload process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while uploading the user portrait image.
	optional func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitUploadError error: NSError)
}


public class UserPortraitScreenlet: BaseScreenlet {


	//MARK: Inspectables

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
			screenletView?.editable = self.editable
		}
	}

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var userPortraitDelegate: UserPortraitScreenletDelegate? {
		return self.delegate as? UserPortraitScreenletDelegate
	}

	public var viewModel: UserPortraitViewModel {
		return screenletView as! UserPortraitViewModel
	}

	public var userId: Int64? {
		return loadedUserId
	}

	private var loadedUserId: Int64?


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()

		viewModel.borderWidth = self.borderWidth
		viewModel.borderColor = self.borderColor
		screenletView?.editable = self.editable
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
					self.userPortraitDelegate?.screenlet?(self,
					                                      onUserPortraitError: NSError.errorWithCause(.InvalidServerResponse,
															message: "Could not load user portrait image."))

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


	//MARK: Public methods

	/// Loads the user portrait that correspond to the user logged.
	///
	/// - Returns: true if suceed, false otherwise.
	public func loadLoggedUserPortrait() -> Bool {
		guard let userId = SessionContext.currentContext?.user.userId else {
			return false
		}

		let interactor = DownloadUserPortraitInteractor(
			screenlet: self,
			userId: userId)

		loadedUserId = userId

		return performAction(name: "load-portrait", sender: interactor)
	}

	/// Loads the user portrait image.
	///
	/// - Parameters:
	///   - portraitId: portrait identifier.
	///   - uuid: user portrait unique identifier.
	///   - male: true if the user is male, false otherwise.
	/// - Returns: true if succeed, false otherwise.
	public func load(portraitId portraitId: Int64, uuid: String, male: Bool = true) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	/// Loads the user portrait image throught user identifier.
	///
	/// - Parameter userId: user identifier.
	/// - Returns: true if succeed, false otherwise.
	public func load(userId userId: Int64) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				userId: userId)

		loadedUserId = userId

		return performAction(name: "load-portrait", sender: interactor)
	}

	/// Loads the user portrait throught company identifier and email address.
	///
	/// - Parameters:
	///   - companyId: company identifier.
	///   - emailAddress: user email.
	/// - Returns: true if succeed, false otherwise.
	public func load(companyId companyId: Int64, emailAddress: String) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				companyId: companyId,
				emailAddress: emailAddress)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	/// Loads the user portrait throught company identifier and user screen name.
	///
	/// - Parameters:
	///   - companyId: company identifier.
	///   - screenName: user screen name.
	/// - Returns: <#return value description#>
	public func load(companyId companyId: Int64, screenName: String) -> Bool {
		let interactor = DownloadUserPortraitInteractor(
				screenlet: self,
				companyId: companyId,
				screenName: screenName)

		loadedUserId = nil

		return performAction(name: "load-portrait", sender: interactor)
	}

	/// Loads the user portrait placeholder when the user portrait is empty.
	public func loadPlaceholder() {
		viewModel.image = nil
	}


	//MARK: Private methods

	private func setPortraitImage(image: UIImage?) {
		viewModel.image = image

		if image == nil {
			let error = NSError.errorWithCause(.AbortedDueToPreconditions,
					message: "Could not set user portrait image.")
			userPortraitDelegate?.screenlet?(self, onUserPortraitError: error)
		}
	}

}
