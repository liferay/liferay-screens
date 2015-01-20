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

	@IBInspectable public var borderWidth: CGFloat = 1.0 {
		didSet {
			(screenletView as? PortraitData)?.borderWidth = self.borderWidth
		}
	}
	@IBInspectable public var borderColor: UIColor? {
		didSet {
			(screenletView as? PortraitData)?.borderColor = self.borderColor
		}
	}

	private var portraitView: PortraitData {
		return screenletView as PortraitData
	}


	//MARK: BaseScreenlet

	override internal func onCreated() {
		super.onCreated()

		portraitView.borderWidth = self.borderWidth
		portraitView.borderColor = self.borderColor
	}

	public func loadLoggedUserPortrait() -> Bool {
		portraitView.portraitURL = getLoggedUserPortraitURL()

		return SessionContext.hasSession
	}

	public func load(#portraitId: Int64, uuid: String, male: Bool = true) {
		if portraitId == 0 {
			portraitView.portraitURL = nil
		}
		else {
			portraitView.portraitURL = getUserPortraitURL(
					male: male,
					portraitId: portraitId,
					uuid: uuid)
		}
	}

	public func load(#userId: Int64) {
		let operation = GetUserByUserIdOperation(
				screenlet: self,
				userId: userId)

		loadUserOrGetFromSession(
				userAttribute: "userId",
				value: NSNumber(longLong: userId),
				operation: operation)
	}

	public func load(#companyId: Int64, emailAddress: String) {
		let operation = GetUserByEmailOperation(
				screenlet: self,
				companyId: companyId,
				emailAddress: emailAddress)

		loadUserOrGetFromSession(
				userAttribute: "emailAddress",
				value: emailAddress,
				operation: operation)
	}

	public func load(#companyId: Int64, screenName: String) {
		let operation = GetUserByScreenNameOperation(
				screenlet: self,
				companyId: companyId,
				screenName: screenName)

		loadUserOrGetFromSession(
				userAttribute: "screenName",
				value: screenName,
				operation: operation)
	}


	//MARK: Private methods

	private func loadUserOrGetFromSession(
			#userAttribute: String,
			value: AnyObject,
			operation: GetUserBaseOperation) {

		func onUserLoaded(operation: ServerOperation) {
			let userOperation = operation as GetUserBaseOperation

			if let userAttributes = userOperation.resultUserAttributes {
				load(
					portraitId:(userAttributes["portraitId"] as NSNumber).longLongValue,
					uuid: userAttributes["uuid"] as String)
			}
			else {
				portraitView.portraitURL = nil
			}
		}

		if let url = getLoggedUserPortraitURLByAttribute(
				key: userAttribute,
				value: value) {
			portraitView.portraitURL = url
		}
		else {
			if operation.validateAndEnqueue(onUserLoaded) {
				screenletView?.onStartOperation()
			}
			else {
				portraitView.portraitURL = nil
			}
		}
	}

	private func getLoggedUserPortraitURLByAttribute(
			#key: String,
			value: AnyObject) -> NSURL? {

		if let loggedUserAttributeValue:AnyObject = SessionContext.userAttribute(key) {
			if loggedUserAttributeValue.isEqual(value) {
				return getLoggedUserPortraitURL()
			}
		}

		return nil
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

	private func getUserPortraitURL(#male: Bool, portraitId: Int64, uuid: String) -> NSURL {
		let maleString = male ? "male" : "female"

		let URL = "\(LiferayServerContext.server)/image/user_\(maleString)/_portrait" +
				"?img_id=\(portraitId)" +
				"&img_id_token=\(encodedSHA1(uuid))"

		return NSURL(string: URL)!
	}

	private func encodedSHA1(input: String) -> String {
		var result = [Byte](count: Int(CC_SHA1_DIGEST_LENGTH), repeatedValue: 0)

		CC_SHA1(input, CC_LONG(countElements(input)), &result)

		let data = NSData(bytes: result, length: result.count)

		let encodedString = data.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(0))

		return LRHttpUtil.encodeURL(encodedString)
	}

}
