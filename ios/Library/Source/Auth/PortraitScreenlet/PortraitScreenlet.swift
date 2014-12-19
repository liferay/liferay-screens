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


public class PortraitScreenlet: BaseScreenlet {

	private var portraitView: PortraitData {
		return screenletView as PortraitData
	}


	public func loadLoggedUserPortrait() -> Bool {
		loadPortrait(url: getLoggedUserPortraitURL())

		return SessionContext.hasSession
	}

	public func load(#portraitId: Int64, uuid: String, male: Bool = true) {

		if portraitId == 0 {
			portraitView.loadPlaceholder()
		}
		else {
			loadPortrait(
					url: getUserPortraitURL(
							male: male,
							portraitId: portraitId,
							uuid: uuid))
		}
	}

	public func load(#userId: Int64) {
		if let url = getLoggedUserPortraitURLByAttribute(
				key: "userId",
				value: NSNumber(longLong: userId)) {
			portraitView.loadPortrait(url)
		}
		else {
			let operation = GetUserByUserIdOperation(
						screenlet: self,
						userId: userId)

			if !operation.validateAndEnqueue(loadedUser) {
				portraitView.loadPlaceholder()
			}
			else {
				screenletView?.onStartOperation()
			}
		}
	}

	public func load(#companyId: Int64, emailAddress: String) {
		if let url = getLoggedUserPortraitURLByAttribute(
				key: "emailAddress",
				value: emailAddress) {
			portraitView.loadPortrait(url)
		}
		else {
			let operation = GetUserByEmailOperation(
						screenlet: self,
						companyId: companyId,
						emailAddress: emailAddress)

			if !operation.validateAndEnqueue(loadedUser) {
				portraitView.loadPlaceholder()
			}
			else {
				screenletView?.onStartOperation()
			}
		}
	}


	public func load(#companyId: Int64, screenName: String) {
		if let url = getLoggedUserPortraitURLByAttribute(
				key: "screenName",
				value: screenName) {
			portraitView.loadPortrait(url)
		}
		else {
			let operation = GetUserByScreenNameOperation(
						screenlet: self,
						companyId: companyId,
						screenName: screenName)

			if !operation.validateAndEnqueue(loadedUser) {
				portraitView.loadPlaceholder()
			}
			else {
				screenletView?.onStartOperation()
			}
		}
	}

	private func getLoggedUserPortraitURLByAttribute(
			#key: String,
			value: AnyObject) -> NSURL? {

		var url: NSURL?

		if let loggedUserAttributeValue:AnyObject = SessionContext.userAttribute(key) {
			if loggedUserAttributeValue.isEqual(value) {
				url = getLoggedUserPortraitURL()
			}
		}

		return url
	}

	private func loadedUser(operation: ServerOperation) {
		let userOperation = operation as GetUserBaseOperation

		if let userAttributes = userOperation.resultUserAttributes {
			self.load(portraitId:(userAttributes["portraitId"] as NSNumber).longLongValue,
					uuid: userAttributes["uuid"] as String)
		}
		else {
			portraitView.loadPlaceholder()
		}
	}

	private func getLoggedUserPortraitURL() -> NSURL? {
		if let portraitId = SessionContext.userAttribute("portraitId") as? NSNumber {
			if let uuid = SessionContext.userAttribute("uuid") as? String {
				let portraitIdLong = portraitId.longLongValue

				return getUserPortraitURL(male: true, portraitId: portraitIdLong, uuid: uuid)
			}
		}

		return nil
	}

	private func loadPortrait(#url: NSURL?) {
		if let urlValue = url {
			portraitView.loadPortrait(urlValue)
		}
		else {
			portraitView.loadPlaceholder()
		}
	}

	private func getUserPortraitURL(#male: Bool, portraitId: Int64, uuid: String) -> NSURL? {
		let maleString = male ? "male" : "female"
		var URL: String = String()

		URL = LiferayServerContext.server
		URL = URL + "/image/user_\(maleString)/_portrait?img_id="
		URL = URL + "\(portraitId)"
		URL = URL + "&img_id_token=" + getSHA1(uuid)

		return NSURL(string: URL)
	}

	private func getSHA1(input: String) -> String {
		var digestLength: Int = Int(CC_SHA1_DIGEST_LENGTH)

		var result: [UInt8] = [Byte](count: digestLength, repeatedValue: 0)

		CC_SHA1(input, CC_LONG(countElements(input)), &result)

		var data: NSData = NSData(bytes: result, length: result.count)

		var encodedString: String = data.base64EncodedStringWithOptions(
			NSDataBase64EncodingOptions(0))

		var SHA1 = LRHttpUtil.encodeURL(encodedString)

		return SHA1
	}

}
