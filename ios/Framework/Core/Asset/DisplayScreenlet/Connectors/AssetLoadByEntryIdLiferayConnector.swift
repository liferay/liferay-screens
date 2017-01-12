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


public class AssetLoadByEntryIdLiferayConnector: ServerConnector, LoadAssetConnector {

	public let entryId: Int64

	public var resultAsset: Asset?


	//MARK: Initializers

	public init(entryId: Int64) {
		self.entryId = entryId

		super.init()
	}

	//MARK: ServerConnector

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if entryId == 0 {
				return ValidationError("assetdisplay-screenlet", "undefined-entryid")
			}
		}

		return error
	}
}

public class Liferay70AssetLoadByEntryIdConnector: AssetLoadByEntryIdLiferayConnector {


	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		resultAsset = nil

		let service = LRScreensassetentryService_v70(session: session)

		do {
			let result = try service.getAssetEntryWithEntryId(entryId, locale: NSLocale.currentLocaleString)

			resultAsset = Asset(attributes: result as! [String:AnyObject])
			lastError = nil
		}
		catch let error as NSError {
			lastError = error
			resultAsset = nil
		}
	}
}
