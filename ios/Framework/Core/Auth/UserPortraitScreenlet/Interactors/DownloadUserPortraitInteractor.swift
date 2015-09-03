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

#if LIFERAY_SCREENS_FRAMEWORK
	import CryptoSwift
#endif


class DownloadUserPortraitInteractor: ServerOperationInteractor {

	private enum DownloadMode {
		case Attributes(portraitId: Int64, uuid: String, male: Bool)
		case EmailAddress(companyId: Int64, emailAddress: String)
		case ScreenName(companyId: Int64, screenName: String)
		case UserId(userId: Int64)

		var cacheKey: String {
			switch self {
			case .Attributes(let portraitId, let uuid, let male):
				return "portraitId-\(portraitId)"
			case .UserId(let userId):
				return "portraitUserId-\(userId)"
			case .EmailAddress(let companyId, let emailAddress):
				return "portraitEmailAddress-\(companyId)-\(emailAddress)"
			case .ScreenName(let companyId, let screenName):
				return "portraitScreenName-\(companyId)-\(screenName)"
			}
		}
	}

	var resultImage: UIImage?
	var resultUserId: Int64?

	private let mode: DownloadMode

	init(screenlet: BaseScreenlet, portraitId: Int64, uuid: String, male: Bool) {
		mode = DownloadMode.Attributes(portraitId: portraitId, uuid: uuid, male: male)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet, userId: Int64) {
		mode = DownloadMode.UserId(userId: userId)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet, companyId: Int64, emailAddress: String) {
		mode = DownloadMode.EmailAddress(companyId: companyId, emailAddress: emailAddress)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet, companyId: Int64, screenName: String) {
		mode = DownloadMode.ScreenName(companyId: companyId, screenName: screenName)

		super.init(screenlet: screenlet)
	}

	override func createOperation() -> ServerOperation? {
		switch mode {
		case .Attributes(let portraitId, let uuid, let male):
			return createOperationFor(
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		case .UserId(let userId):
			let currentUserId = SessionContext.userAttribute("userId") as? NSNumber

			if userId == currentUserId?.longLongValue {
				return createOperationForLogged()
			}
			else {
				return createOperationFor(GetUserByUserIdOperation(userId: userId))
			}

		case .EmailAddress(let companyId, let emailAddress):
			let currentCompanyId = SessionContext.userAttribute("companyId") as? NSNumber
			let currentEmailAddress = SessionContext.userAttribute("emailAddress") as? NSString

			if companyId == currentCompanyId?.longLongValue
					&& emailAddress == currentEmailAddress {
				return createOperationForLogged()
			}
			else {
				return createOperationFor(
					GetUserByEmailOperation(
						companyId: companyId,
						emailAddress: emailAddress))
			}

		case .ScreenName(let companyId, let screenName):
			let currentCompanyId = SessionContext.userAttribute("companyId") as? NSNumber
			let currentScreenName = SessionContext.userAttribute("screenName") as? NSString

			if companyId == currentCompanyId?.longLongValue
					&& screenName == currentScreenName {
				return createOperationForLogged()
			}
			else {
				return createOperationFor(
					GetUserByScreenNameOperation(
						companyId: companyId,
						screenName: screenName))
			}
		}
	}

	override func completedOperation(op: ServerOperation) {
		if let httpOp = toHttpOperation(op),
				resultData = httpOp.resultData
				where httpOp.lastError == nil {
			resultImage = UIImage(data: resultData)
		}
	}

	override func writeToCache(op: ServerOperation) {
		if let httpOp = toHttpOperation(op),
				resultData = httpOp.resultData {
			// use "usedSession" to get the session used to retrieve the content
			SessionCacheManager(session: httpOp.usedSession).set(
				key: mode.cacheKey,
				value: resultData)
		}
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		if let httpOp = toHttpOperation(op) {
			// use "createSession" because this may happen before the request is done
			SessionCacheManager(session: httpOp.createSession()).getAny(
					key: mode.cacheKey) {
				httpOp.resultData = $0 as? NSData
				httpOp.lastError = nil
				result($0)
			}
		}
		else {
			result(nil)
		}
	}


	//MARK: Private methods

	private func toHttpOperation(op: ServerOperation) -> HttpOperation? {
		return ((op as? ServerOperationChain)?.headOperation as? HttpOperation)
			?? (op as? HttpOperation)
	}

	private func createOperationForLogged() -> ServerOperation? {
		if let portraitId = SessionContext.userAttribute("portraitId") as? NSNumber,
				uuid = SessionContext.userAttribute("uuid") as? String {
				resultUserId = SessionContext.currentUserId

			return createOperationFor(
				portraitId: portraitId.longLongValue,
				uuid: uuid,
				male: true)
		}

		return nil
	}

	private func createOperationFor(loadUserOp: GetUserBaseOperation) -> ServerOperation? {
		let chain = ServerOperationChain()

		chain.headOperation = loadUserOp

		chain.onNextStep = { (op, seq) -> ServerOperation? in
			if let loadUserOp = op as? GetUserBaseOperation {
				return self.createOperationFor(attributes: loadUserOp.resultUserAttributes)
			}

			return nil
		}

		return chain
	}

	private func createOperationFor(#attributes: [String:AnyObject]?) -> ServerOperation? {
		if let attributes = attributes,
				portraitId = attributes["portraitId"] as? NSNumber,
				uuid = attributes["uuid"] as? String,
				userId = attributes["userId"] as? NSNumber {

			resultUserId = userId.longLongValue

			return createOperationFor(
				portraitId: portraitId.longLongValue,
				uuid: uuid,
				male: true)
		}

		return nil
	}

	private func createOperationFor(#portraitId: Int64, uuid: String, male: Bool) -> ServerOperation? {
		if let url = URLForAttributes(
				portraitId: portraitId,
				uuid: uuid,
				male: male) {
			return HttpOperation(url: url)
		}

		return nil
	}

	private func URLForAttributes(#portraitId: Int64, uuid: String, male: Bool) -> NSURL? {

		func encodedSHA1(input: String) -> String? {
			var result: String?
#if LIFERAY_SCREENS_FRAMEWORK
			if let inputData = input.dataUsingEncoding(NSUTF8StringEncoding,
					allowLossyConversion: false) {

				if let resultData = CryptoSwift.Hash.sha1(inputData).calculate() {
					result = LRHttpUtil.encodeURL(
						resultData.base64EncodedStringWithOptions(
							NSDataBase64EncodingOptions(0)))
				}
			}
#else
			var buffer = [UInt8](count: Int(CC_SHA1_DIGEST_LENGTH), repeatedValue: 0)

			CC_SHA1(input, CC_LONG(count(input)), &buffer)
			let data = NSData(bytes: buffer, length: buffer.count)
			let encodedString = data.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(0))

			result = LRHttpUtil.encodeURL(encodedString)
#endif
			return result
		}

		if let hashedUUID = encodedSHA1(uuid) {
			let maleString = male ? "male" : "female"

			let url = "\(LiferayServerContext.server)/image/user_\(maleString)/_portrait" +
				"?img_id=\(portraitId)" +
				"&img_id_token=\(hashedUUID)" +
			"&t=\(NSDate.timeIntervalSinceReferenceDate())"

			return NSURL(string: url)
		}
		
		return nil
	}

}
