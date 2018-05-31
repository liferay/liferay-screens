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

class DownloadUserPortraitInteractor: ServerReadConnectorInteractor {

	fileprivate enum DownloadMode {
		case attributes(portraitId: Int64, uuid: String, male: Bool)
		case emailAddress(companyId: Int64, emailAddress: String)
		case screenName(companyId: Int64, screenName: String)
		case userId(userId: Int64)

		var cacheKey: String {
			switch self {
			case .attributes(let portraitId, _, _):
				return "portraitId-\(portraitId)"
			case .userId(let userId):
				return "userId-\(userId)"
			case .emailAddress(let companyId, let emailAddress):
				return "emailAddress-\(companyId)-\(emailAddress)"
			case .screenName(let companyId, let screenName):
				return "screenName-\(companyId)-\(screenName)"
			}
		}

		var cacheAttributes: [String: Any] {
			switch self {
			case .attributes(let portraitId, _, _):
				return ["portraitId": NSNumber(value: portraitId)]
			case .userId(let userId):
				return ["userId": NSNumber(value: userId)]
			case .emailAddress(let companyId, let emailAddress):
				return [
					"companyId": NSNumber(value: companyId),
					"emailAddress": emailAddress]
			case .screenName(let companyId, let screenName):
				return [
					"companyId": NSNumber(value: companyId),
					"screenName": screenName]
			}
		}
	}

	var resultImage: UIImage?

	var resultUser: User?

	var userHasDefaultPortrait = false

	fileprivate let mode: DownloadMode

	// MARK: Initializers

	init(screenlet: BaseScreenlet?, portraitId: Int64, uuid: String, male: Bool) {
		mode = DownloadMode.attributes(portraitId: portraitId, uuid: uuid, male: male)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, userId: Int64) {
		mode = DownloadMode.userId(userId: userId)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, companyId: Int64, emailAddress: String) {
		mode = DownloadMode.emailAddress(companyId: companyId, emailAddress: emailAddress)

		super.init(screenlet: screenlet)
	}

	init(screenlet: BaseScreenlet?, companyId: Int64, screenName: String) {
		mode = DownloadMode.screenName(companyId: companyId, screenName: screenName)

		super.init(screenlet: screenlet)
	}

	// MARK: ServerConnectorInteractor

	override func createConnector() -> ServerConnector? {
		switch mode {
		case .attributes(let portraitId, let uuid, let male):
			return createConnectorFor(portraitId: portraitId, uuid: uuid, male: male)

		case .userId(let userId):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByUserIdConnector(userId: userId))

		case .emailAddress(let companyId, let emailAddress):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByEmailConnector(companyId: companyId,
					emailAddress: emailAddress))

		case .screenName(let companyId, let screenName):
			return createConnectorFor(
				LiferayServerContext.connectorFactory.createGetUserByScreenNameConnector(companyId: companyId,
					screenName: screenName))
		}
	}

	override func completedConnector(_ c: ServerConnector) {
		if let httpOp = toHttpConnector(c), let resultData = httpOp.resultData {
			resultImage = UIImage(data: resultData)
			userHasDefaultPortrait = false
		}
		else if c.lastError == nil &&
			(c as? ServerConnectorChain)?.currentConnector is GetUserBaseLiferayConnector {

			// If the current connector is not a HttpConnector and its not errored
			// we are in the case that the user doesn't have a custom portrait
			userHasDefaultPortrait = true
			resultImage = nil
		}
	}

	// MARK: Cache methods

	override func writeToCache(_ c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		if let httpOp = toHttpConnector(c),
				let resultData = httpOp.resultData {

			cacheManager.setClean(
				collection: ScreenletName(UserPortraitScreenlet.self),
				key: mode.cacheKey,
				value: resultData as NSData,
				attributes: mode.cacheAttributes)
		}
		else if let user = resultUser,
			(c as? ServerConnectorChain)?.currentConnector is GetUserBaseLiferayConnector {

			let userAttributesToSave = selectUserAttrsToSave(attrs: user.attributes)
			cacheManager.setClean(
				collection: ScreenletName(UserPortraitScreenlet.self),
				key: "\(mode.cacheKey)",
				value: userAttributesToSave as NSCoding,
				attributes: mode.cacheAttributes)
		}
	}

	override func readFromCache(_ c: ServerConnector, result: @escaping (Any?) -> Void) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		func loadImageFromCache(output outputConnector: HttpConnector) {
			cacheManager.getImage(
					collection: ScreenletName(UserPortraitScreenlet.self),
					key: self.mode.cacheKey) {
				if let image = $0 {
					outputConnector.resultData = UIImagePNGRepresentation(image)
					outputConnector.lastError = nil
					result($0)
				}
				else {
					outputConnector.resultData = nil
					outputConnector.lastError = NSError.errorWithCause(.notAvailable,
						message: "Image from cache not available.")
					result(nil)
				}
			}
		}

		if (c as? ServerConnectorChain)?.currentConnector is GetUserBaseLiferayConnector {
			// asking for user attributes. if the image is cached, we'd need to skip this step

			cacheManager.getAny(collection: ScreenletName(UserPortraitScreenlet.self), key: mode.cacheKey) {
				if $0 == nil {
					// not cached: continue
					result(nil)
				}
				else {

					// if the key registry is a dictionary (user attrs) 
					// skip this step too
					if let userAttrs = $0 as? [String: AnyObject] {
						self.resultUser = User(attributes: userAttrs)
						result($0)
						return
					}

					// image cached. Skip!

					// create a dummy HttpConnector to store the result
					let dummyConnector = HttpConnector(url: URL(string: "http://dummy")!)

					// set this dummy connector to allow "completedConnector" method retrieve the result
					(c as? ServerConnectorChain)?.currentConnector = dummyConnector

					dispatch_async {
						loadImageFromCache(output: dummyConnector)
					}
				}
			}
		}
		else if let httpOp = toHttpConnector(c) {
			cacheManager.getAny(collection: ScreenletName(UserPortraitScreenlet.self), key: mode.cacheKey) {
				guard let cachedObject = $0 else {
					httpOp.resultData = nil
					httpOp.lastError = NSError.errorWithCause(.notAvailable, message: "Image from cache not available.")
					result(nil)
					return
				}

				if let data = cachedObject as? Data {
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

	// MARK: Private methods

	fileprivate func toHttpConnector(_ c: ServerConnector) -> HttpConnector? {
		return ((c as? ServerConnectorChain)?.currentConnector as? HttpConnector)
			?? (c as? HttpConnector)
	}

	fileprivate func createConnectorFor(_ loadUserCon: GetUserBaseLiferayConnector) -> ServerConnector? {
		let chain = ServerConnectorChain(head: loadUserCon)

		chain.onNextStep = { (c, seq) -> ServerConnector? in
			guard let loadUserCon = c as? GetUserBaseLiferayConnector,
				let userAttrs = loadUserCon.resultUserAttributes else {
				return nil
			}

			self.resultUser = User(attributes: userAttrs)

			// If the user has portrait image (portraitId != 0) continue the chain, 
			// otherwise stop the chain
			if self.userHasPortrait(userAttrs) {
				return self.createConnectorFor(attributes: userAttrs)
			}

			return nil
		}

		return chain
	}

	fileprivate func createConnectorFor(attributes: [String: AnyObject]?) -> ServerConnector? {
		let portraitEntry = attributes?["portraitId"]
		if let attributes = attributes,
			let portraitId = portraitEntry?.int64Value,
			let uuid = attributes["uuid"] as? String {

			return createConnectorFor(portraitId: portraitId, uuid: uuid, male: true)
		}

		return nil
	}

	fileprivate func createConnectorFor(portraitId: Int64, uuid: String, male: Bool) -> ServerConnector? {
		if let url = URLForAttributes(portraitId: portraitId, uuid: uuid, male: male) {
			return HttpConnector(url: url)
		}

		return nil
	}

	open func userHasPortrait(_ userAttrs: [String: AnyObject]?) -> Bool {
		if let portraitId = userAttrs?["portraitId"]?.int64Value, portraitId == 0 {
			return false
		}

		return true
	}

	open func selectUserAttrsToSave(attrs: [String: AnyObject]) -> [String: AnyObject] {
		return attrs
	}

	fileprivate func URLForAttributes(portraitId: Int64, uuid: String, male: Bool) -> URL? {

		let session = SessionContext.createSessionFromCurrentSession()

		if let session = session {
			let urlString = LRPortraitUtil.getPortraitURL(session, male: male, portraitId: portraitId, uuid: uuid)

			return URL(string: urlString)
		}

		return nil
	}
}
