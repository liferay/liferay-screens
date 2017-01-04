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


@objc public protocol RatingScreenletDelegate : BaseScreenletDelegate {
	
	/// Called when the ratings are received.
	///
	/// - Parameters:
	///   - screenlet
	///   - rating: asset's rating.
	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingRetrieve rating: RatingEntry)

	/// Called when a rating is deleted.
	///
	/// - Parameters:
	///   - screenlet
	///   - rating: asset's rating.
	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingDeleted rating: RatingEntry)

	/// Called when a rating is updated.
	///
	/// - Parameters:
	///   - screenlet
	///   - rating: asset's rating.
	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingUpdated rating: RatingEntry)

	/// Called when an error occurs in the process. The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error retrieving, updating or deleting asset's rating.
	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingError error: NSError)
	
}


public class RatingScreenlet: BaseScreenlet {
	
	public static let DeleteRatingAction = "deleteRating"
	public static let UpdateRatingAction = "updateRating"
	public static let LoadRatingsAction = "loadRatings"


	//MARK: Inspectables

	@IBInspectable public var entryId: Int64 = 0
	
	@IBInspectable public var className: String = ""

	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var ratingsGroupCount: Int32 = -1
	
	@IBInspectable public var autoLoad: Bool = true
	
	@IBInspectable public var editable: Bool = false {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var ratingDisplayDelegate: RatingScreenletDelegate? {
		return delegate as? RatingScreenletDelegate
	}
	
	public var viewModel: RatingViewModel? {
		return screenletView as? RatingViewModel
	}


	//MARK: BaseScreenlet

	override public func prepareForInterfaceBuilder() {
		setCustomDefaultThemeName()
		super.prepareForInterfaceBuilder()
	}
	
	override public func onPreCreate() {
		setCustomDefaultThemeName()
	}
	
	override public func onCreated() {
		if let defaultRatingsGroupCount = viewModel?.defaultRatingsGroupCount
				where ratingsGroupCount == -1 {
			ratingsGroupCount = defaultRatingsGroupCount
		}
		screenletView?.editable = self.editable
	}
	
	override public func onShow() {
		if autoLoad {
			loadRatings()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor: ServerConnectorInteractor?

		switch name {
		case RatingScreenlet.LoadRatingsAction:
			interactor = createLoadRatingsInteractor()
		case RatingScreenlet.DeleteRatingAction:
			interactor = createDeleteRatingInteractor()
		case RatingScreenlet.UpdateRatingAction:
			let selectedScore = sender as! Double
			interactor = createUpdateRatingInteractor(selectedScore)
		default:
			return nil
		}

		interactor?.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor?.onFailure = {
			self.ratingDisplayDelegate?.screenlet?(self, onRatingError: $0)
		}

		return interactor
	}

	override public func performDefaultAction() -> Bool {
		return performAction(name: RatingScreenlet.LoadRatingsAction, sender: nil)
	}


	//MARK: Public methods

	/// Starts the request to load the asset's ratings.
	///
	/// - Returns: true if succeed, false if not.
	public func loadRatings() -> Bool {
		return self.performDefaultAction()
	}


	//MARK: Private methods

	private func setCustomDefaultThemeName() {
		if themeName == BaseScreenlet.DefaultThemeName {
			themeName = "default-thumbs"
		}
	}

	private func createLoadRatingsInteractor() -> LoadRatingsInteractor? {
		let interactor: LoadRatingsInteractor?

		if entryId != 0 {
			interactor = LoadRatingsInteractor(
				screenlet: self,
				entryId: entryId,
				ratingsGroupCount: ratingsGroupCount)
		}
		else if className != "" && classPK != 0 {
			interactor = LoadRatingsInteractor(
				screenlet: self,
				className: className,
				classPK: classPK,
				ratingsGroupCount: ratingsGroupCount)
		}
		else {
			interactor = nil
		}

		interactor?.onSuccess = {
			if let result = interactor?.resultRating {
				self.className = result.className
				self.classPK = result.classPK

				self.viewModel?.ratingEntry = result

				self.ratingDisplayDelegate?.screenlet?(self, onRatingRetrieve: result)
			}
			else {
				self.ratingDisplayDelegate?.screenlet?(self,
						onRatingError: NSError.errorWithCause(.InvalidServerResponse,
								message: "Could not load ratings."))
			}
		}

		return interactor
	}

	private func createDeleteRatingInteractor() -> DeleteRatingInteractor {
		let interactor = DeleteRatingInteractor(
				screenlet: self,
				className: className,
				classPK: classPK,
				ratingsGroupCount: ratingsGroupCount)

		interactor.onSuccess = {
			if let result = interactor.resultRating {
				self.viewModel?.ratingEntry = result

				self.ratingDisplayDelegate?.screenlet?(self, onRatingDeleted: result)
			}
			else {
				self.ratingDisplayDelegate?.screenlet?(self,
						onRatingError: NSError.errorWithCause(.InvalidServerResponse,
								message: "Could not delete the rating."))
			}
		}

		return interactor
	}

	private func createUpdateRatingInteractor(selectedScore: Double) -> UpdateRatingInteractor {
		let interactor = UpdateRatingInteractor(
				screenlet: self,
				className: className,
				classPK: classPK,
				score: selectedScore,
				ratingsGroupCount: ratingsGroupCount)

		interactor.onSuccess = {
			if let result = interactor.resultRating {
				self.viewModel?.ratingEntry = result

				self.ratingDisplayDelegate?.screenlet?(self, onRatingUpdated: result)
			}
			else {
				self.ratingDisplayDelegate?.screenlet?(self,
						onRatingError: NSError.errorWithCause(.InvalidServerResponse,
								message: "Could not update the rating."))
			}
		}

		return interactor
	}

}
