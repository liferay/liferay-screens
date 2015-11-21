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


class DownloadUserPortraitInteractor: ServerReadOperationInteractor {

	private enum DownloadMode {
		case Attributes(portraitId: Int64, uuid: String, male: Bool)
		case EmailAddress(companyId: Int64, emailAddress: String)
		case ScreenName(companyId: Int64, screenName: String)
		case UserId(userId: Int64)

		var cacheKey: String {
			switch self {
			case .Attributes(let portraitId, _, _):
				return "portraitId-\(portraitId)"
			case .UserId(let userId):
				return "userId-\(userId)"
			case .EmailAddress(let companyId, let emailAddress):
				return "emailAddress-\(companyId)-\(emailAddress)"
			case .ScreenName(let companyId, let screenName):
				return "screenName-\(companyId)-\(screenName)"
			}
		}

		var cacheAttributes: [String:AnyObject] {
			switch self {
			case .Attributes(let portraitId, _, _):
				return ["portraitId": NSNumber(longLong: portraitId)]
			case .UserId(let userId):
				return ["userId": NSNumber(longLong: userId)]
			case .EmailAddress(let companyId, let emailAddress):
				return [
					"companyId": NSNumber(longLong: companyId),
					"emailAddress": emailAddress]
			case .ScreenName(let companyId, let screenName):
				return [
					"companyId": NSNumber(longLong: companyId),
					"screenName": screenName]
			}
		}
	}

	var resultImage: UIImage?
	var resultUserId: Int64?

	private let mode: DownloadMode


	init(screenlet: BaseScreenlet?, portraitId: Int64, uuid: String, male: Bool) {
		mode = DownloadMode.Attributes(portraitId: portraitId, uuid: uuid, male: male)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, userId: Int64) {
		mode = DownloadMode.UserId(userId: userId)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, companyId: Int64, emailAddress: String) {
		mode = DownloadMode.EmailAddress(companyId: companyId, emailAddress: emailAddress)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, companyId: Int64, screenName: String) {
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


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let httpOp = toHttpOperation(op),
				resultData = httpOp.resultData {

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(UserPortraitScreenlet),
				key: mode.cacheKey,
				value: resultData,
				attributes: mode.cacheAttributes)
		}
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		if let httpOp = toHttpOperation(op) {
			let cacheManager = SessionContext.currentCacheManager!

			cacheManager.getAny(
					collection: ScreenletName(UserPortraitScreenlet),
					key: mode.cacheKey) {
				if let data = $0 as? NSData {
					httpOp.resultData = data
					httpOp.lastError = nil
					result($0)
				}
				else {
					dispatch_async {
						cacheManager.getImage(
								collection: ScreenletName(UserPortraitScreenlet),
								key: self.mode.cacheKey) {
							if let image = $0 {
								httpOp.resultData = UIImagePNGRepresentation(image)
								httpOp.lastError = nil
								result($0)
							}
							else {
								httpOp.resultData = nil
								httpOp.lastError = NSError.errorWithCause(.NotAvailable)
								result(nil)
							}
						}
					}
				}
			}
		}
		else {
			result(nil)
		}
	}


	//MARK: Private methods

	private func toHttpOperation(op: ServerOperation) -> HttpOperation? {
		return ((op as? ServerOperationChain)?.currentOperation as? HttpOperation)
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
		let chain = ServerOperationChain(head: loadUserOp)

		chain.onNextStep = { (op, seq) -> ServerOperation? in
			if let loadUserOp = op as? GetUserBaseOperation {
				return self.createOperationFor(attributes: loadUserOp.resultUserAttributes)
			}

			return nil
		}

		return chain
	}

	private func createOperationFor(attributes attributes: [String:AnyObject]?) -> ServerOperation? {
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

	private func createOperationFor(portraitId portraitId: Int64, uuid: String, male: Bool) -> ServerOperation? {
		if let url = URLForAttributes(
				portraitId: portraitId,
				uuid: uuid,
				male: male) {
			return HttpOperation(url: url)
		}

		return nil
	}

	private func URLForAttributes(portraitId portraitId: Int64, uuid: String, male: Bool) -> NSURL? {

		func encodedSHA1(input: String) -> String? {
			var result: String?
#if LIFERAY_SCREENS_FRAMEWORK
			if let inputData = input.dataUsingEncoding(NSUTF8StringEncoding,
					allowLossyConversion: false) {

				let resultBytes = CryptoSwift.Hash.sha1(inputData.arrayOfBytes()).calculate()
				let resultData = NSData(bytes: resultBytes)
				result = LRHttpUtil.encodeURL(resultData.base64EncodedStringWithOptions([]))
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
