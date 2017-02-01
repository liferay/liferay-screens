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
	@objc optional func screenlet(_ screenlet: RatingScreenlet,
	                        onRatingRetrieve rating: RatingEntry)

	/// Called when a rating is deleted.
	///
	/// - Parameters:
	///   - screenlet
	///   - rating: asset's rating.
	@objc optional func screenlet(_ screenlet: RatingScreenlet,
	                        onRatingDeleted rating: RatingEntry)

	/// Called when a rating is updated.
	///
	/// - Parameters:
	///   - screenlet
	///   - rating: asset's rating.
	@objc optional func screenlet(_ screenlet: RatingScreenlet,
	                        onRatingUpdated rating: RatingEntry)

	/// Called when an error occurs in the process. The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error retrieving, updating or deleting asset's rating.
	@objc optional func screenlet(_ screenlet: RatingScreenlet,
	                        onRatingError error: NSError)
	
}


open class RatingScreenlet: BaseScreenlet {
	
	open static let DeleteRatingAction = "deleteRating"
	open static let UpdateRatingAction = "updateRating"
	open static let LoadRatingsAction = "loadRatings"


	//MARK: Inspectables

	@IBInspectable open var entryId: Int64 = 0
	
	@IBInspectable open var className: String = ""

	@IBInspectable open var classPK: Int64 = 0

	@IBInspectable open var ratingsGroupCount: Int32 = -1
	
	@IBInspectable open var autoLoad: Bool = true
	
	@IBInspectable open var editable: Bool = false {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	open var ratingDisplayDelegate: RatingScreenletDelegate? {
		return delegate as? RatingScreenletDelegate
	}
	
	open var viewModel: RatingViewModel? {
		return screenletView as? RatingViewModel
	}


	//MARK: BaseScreenlet

	override open func prepareForInterfaceBuilder() {
		setCustomDefaultThemeName()
		super.prepareForInterfaceBuilder()
	}
	
	override open func onPreCreate() {
		setCustomDefaultThemeName()
	}
	
	override open func onCreated() {
		if let defaultRatingsGroupCount = viewModel?.defaultRatingsGroupCount, ratingsGroupCount == -1 {
			ratingsGroupCount = defaultRatingsGroupCount
		}
		screenletView?.editable = self.editable
	}
	
	override open func onShow() {
		if autoLoad {
			loadRatings()
		}
	}

	override open func createInteractor(name: String, sender: AnyObject?) -> Interactor? {
		let interactor: ServerConnectorInteractor?

		switch name {
		case RatingScreenlet.LoadRatingsAction:
			interactor = createLoadRatingsInteractor()
		case RatingScreenlet.DeleteRatingAction:
			interactor = createDeleteRatingInteractor()
		case RatingScreenlet.UpdateRatingAction:
			let selectedScore = sender!.doubleValue!
			interactor = createUpdateRatingInteractor(selectedScore)
		default:
			return nil
		}

		interactor?.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		interactor?.onFailure = {
			self.ratingDisplayDelegate?.screenlet?(self, onRatingError: $0)
		}

		return interactor
	}

	override open func performDefaultAction() -> Bool {
		return performAction(name: RatingScreenlet.LoadRatingsAction, sender: nil)
	}


	//MARK: Public methods

	/// Starts the request to load the asset's ratings.
	///
	/// - Returns: true if succeed, false if not.
	@discardableResult
	open func loadRatings() -> Bool {
		return self.performDefaultAction()
	}


	//MARK: Private methods

	fileprivate func setCustomDefaultThemeName() {
		if themeName == BaseScreenlet.DefaultThemeName {
			themeName = "default-thumbs"
		}
	}

	fileprivate func createLoadRatingsInteractor() -> LoadRatingsInteractor? {
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
						onRatingError: NSError.errorWithCause(.invalidServerResponse,
								message: "Could not load ratings."))
			}
		}

		return interactor
	}

	fileprivate func createDeleteRatingInteractor() -> DeleteRatingInteractor {
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
						onRatingError: NSError.errorWithCause(.invalidServerResponse,
								message: "Could not delete the rating."))
			}
		}

		return interactor
	}

	fileprivate func createUpdateRatingInteractor(_ selectedScore: Double) -> UpdateRatingInteractor {
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
						onRatingError: NSError.errorWithCause(.invalidServerResponse,
								message: "Could not update the rating."))
			}
		}

		return interactor
	}

}
