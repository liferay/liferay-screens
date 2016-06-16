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


class DownloadUserPortraitInteractor: ServerReadConnectorInteractor {

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

	override func createConnector() -> ServerConnector? {
		switch mode {
		case .Attributes(let portraitId, let uuid, let male):
			return createConnectorFor(
				portraitId: portraitId,
				uuid: uuid,
				male: male)

		case .UserId(let userId):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByUserIdConnector(
					userId: userId))

		case .EmailAddress(let companyId, let emailAddress):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByEmailConnector(
					companyId: companyId,
					emailAddress: emailAddress))

		case .ScreenName(let companyId, let screenName):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByScreenNameConnector(
					companyId: companyId,
					screenName: screenName))
		}
	}

	override func completedConnector(op: ServerConnector) {
		if let httpOp = toHttpConnector(op),
				resultData = httpOp.resultData {
			resultImage = UIImage(data: resultData)
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		if let httpOp = toHttpConnector(op),
				resultData = httpOp.resultData {

			cacheManager.setClean(
				collection: ScreenletName(UserPortraitScreenlet),
				key: mode.cacheKey,
				value: resultData,
				attributes: mode.cacheAttributes)
		}
	}

	override func readFromCache(op: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		func loadImageFromCache(output outputConnector: HttpConnector) {
			cacheManager.getImage(
					collection: ScreenletName(UserPortraitScreenlet),
					key: self.mode.cacheKey) {
				if let image = $0 {
					outputConnector.resultData = UIImagePNGRepresentation(image)
					outputConnector.lastError = nil
					result($0)
				}
				else {
					outputConnector.resultData = nil
					outputConnector.lastError = NSError.errorWithCause(.NotAvailable)
					result(nil)
				}
			}
		}


		if (op as? ServerConnectorChain)?.currentConnector is GetUserBaseLiferayConnector {
			// asking for user attributes. if the image is cached, we'd need to skip this step

			cacheManager.getAny(
					collection: ScreenletName(UserPortraitScreenlet),
					key: mode.cacheKey) {
				if $0 == nil {
					// not cached: continue
					result(nil)
				}
				else {
					// cached. Skip!

					// create a dummy HttpConnector to store the result
					let dummyConnector = HttpConnector(url: NSURL(string: "http://dummy")!)

					// set this dummy connector to allow "completedConnector" method retrieve the result
					(op as? ServerConnectorChain)?.currentConnector = dummyConnector

					dispatch_async {
						loadImageFromCache(output: dummyConnector)
					}
				}
			}
		}
		else if let httpOp = toHttpConnector(op) {
			cacheManager.getAny(
					collection: ScreenletName(UserPortraitScreenlet),
					key: mode.cacheKey) {
				guard let cachedObject = $0 else {
					httpOp.resultData = nil
					httpOp.lastError = NSError.errorWithCause(.NotAvailable)
					result(nil)
					return
				}

				if let data = cachedObject as? NSData {
					httpOp.resultData = data
					httpOp.lastError = nil
					result(httpOp)
				}
				else {
					dispatch_async {
						loadImageFromCache(output: httpOp)
					}
				}
			}
		}
		else {
			result(nil)
		}
	}


	//MARK: Private methods

	private func toHttpConnector(op: ServerConnector) -> HttpConnector? {
		return ((op as? ServerConnectorChain)?.currentConnector as? HttpConnector)
			?? (op as? HttpConnector)
	}

	private func createConnectorFor(loadUserOp: GetUserBaseLiferayConnector) -> ServerConnector? {
		let chain = ServerConnectorChain(head: loadUserOp)

		chain.onNextStep = { (op, seq) -> ServerConnector? in
			guard let loadUserOp = op as? GetUserBaseLiferayConnector else {
				return nil
			}

			return self.createConnectorFor(attributes: loadUserOp.resultUserAttributes)
		}

		return chain
	}

	private func createConnectorFor(attributes attributes: [String:AnyObject]?) -> ServerConnector? {
		if let attributes = attributes,
				portraitId = attributes["portraitId"]?.description.asLong,
				uuid = attributes["uuid"] as? String,
				userId = attributes["userId"]?.description.asLong {

			resultUserId = userId

			return createConnectorFor(
				portraitId: portraitId,
				uuid: uuid,
				male: true)
		}

		return nil
	}

	private func createConnectorFor(portraitId portraitId: Int64, uuid: String, male: Bool) -> ServerConnector? {
		if let url = URLForAttributes(
				portraitId: portraitId,
				uuid: uuid,
				male: male) {
			return HttpConnector(url: url)
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

			let url = "\(LiferayServerContext.server)/image/user_\(maleString)_portrait" +
				"?img_id=\(portraitId)" +
				"&img_id_token=\(hashedUUID)" +
				"&t=\(NSDate.timeIntervalSinceReferenceDate())"

			return NSURL(string: url)
		}
		
		return nil
	}

}
