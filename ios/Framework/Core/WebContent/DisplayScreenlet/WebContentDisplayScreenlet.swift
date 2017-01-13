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

	///  Called when the web content’s HTML is received.
	///
	/// - Parameters:
	///   - screenlet
	///   - html: web content html.
	/// - Returns: original or modify html.
	optional func screenlet(screenlet: WebContentDisplayScreenlet,
			onWebContentResponse html: String) -> String?

	/// Called when a web content record is received.
	///
	/// - Parameters:
	///   - screenlet
	///   - record: web content record.
	optional func screenlet(screenlet: WebContentDisplayScreenlet,
		onRecordContentResponse record: DDLRecord)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving web content or record.
	optional func screenlet(screenlet: WebContentDisplayScreenlet,
			onWebContentError error: NSError)

}


public class WebContentDisplayScreenlet: BaseScreenlet {


	//MARK: Inspectables

	@IBInspectable public var groupId: Int64 = 0

	@IBInspectable public var articleId: String = ""

	// only for html web contents
	@IBInspectable public var templateId: Int64 = 0

	// only for structured web contents
	@IBInspectable public var structureId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var webContentDisplayDelegate: WebContentDisplayScreenletDelegate? {
		return delegate as? WebContentDisplayScreenletDelegate
	}


	//MARK: BaseScreenlet

	override public func onShow() {
		if autoLoad && articleId != "" {
			loadWebContent()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor = WebContentDisplayLoadInteractor(
			screenlet: self,
			groupId: self.groupId,
			articleId: self.articleId,
			structureId: self.structureId,
			templateId: self.templateId)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultHtml = interactor.resultHTML {
				let modifiedHtml = self.webContentDisplayDelegate?.screenlet?(self,
					onWebContentResponse: resultHtml)

				(self.screenletView as! WebContentDisplayViewModel).htmlContent =
					modifiedHtml ?? resultHtml
			}
			else if let resultRecord = interactor.resultRecord {
				self.webContentDisplayDelegate?.screenlet?(self,
					onRecordContentResponse: resultRecord)

				(self.screenletView as! WebContentDisplayViewModel).recordContent = resultRecord
			}
		}

		interactor.onFailure = {
			self.webContentDisplayDelegate?.screenlet?(self, onWebContentError: $0)
		}

		return interactor
	}


	//MARK: Public methods

	/// Loads a web content in the screenlet.
	///
	/// - Returns: true if default use case has been perform, false otherwise.
	public func loadWebContent() -> Bool {
		return self.performDefaultAction()
	}

}
