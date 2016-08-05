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
	
	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingRetrieve rating: RatingEntry)

	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingDeleted rating: RatingEntry)

	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingUpdated rating: RatingEntry)

	optional func screenlet(screenlet: RatingScreenlet,
	                        onRatingError error: NSError)
	
}

@IBDesignable public class RatingScreenlet: BaseScreenlet {
	
	public static let DeleteRatingAction = "deleteRating"
	public static let UpdateRatingAction = "updateRating"
	public static let LoadRatingsAction = "loadRatings"
	
	@IBInspectable public var entryId: Int64 = 0
	
	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var ratingsGroupCount: Int32 = -1
	
	@IBInspectable public var autoLoad: Bool = true
	
	@IBInspectable public var editable: Bool = false {
		didSet {
			viewModel?.editable = self.editable
		}
	}
	
	public var ratingDisplayDelegate: RatingScreenletDelegate? {
		return delegate as? RatingScreenletDelegate
	}
	
	public var viewModel: RatingViewModel? {
		return screenletView as? RatingViewModel
	}


	//MARK: BaseScreenlet methods

	override public func prepareForInterfaceBuilder() {
		setCustomDefaultThemeName()
		super.prepareForInterfaceBuilder()
	}
	
	public override func onPreCreate() {
		setCustomDefaultThemeName()
	}
	
	public override func onCreated() {
		if let defaultRatingsGroupCount = viewModel?.defaultRatingsGroupCount
				where ratingsGroupCount == -1 {
			ratingsGroupCount = defaultRatingsGroupCount
		}
	}
	
	public override func onShow() {
		if autoLoad {
			loadRatings()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor: Interactor?

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

		interactor?.onFailure = {
			self.ratingDisplayDelegate?.screenlet?(self, onRatingError: $0)
		}

		return interactor
	}

	public override func performDefaultAction() -> Bool {
		return performAction(name: RatingScreenlet.LoadRatingsAction, sender: nil)
	}


	//MARK: Public methods

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
						onRatingError: NSError.errorWithCause(.InvalidServerResponse))
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
						onRatingError: NSError.errorWithCause(.InvalidServerResponse))
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
						onRatingError: NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		return interactor
	}

}
