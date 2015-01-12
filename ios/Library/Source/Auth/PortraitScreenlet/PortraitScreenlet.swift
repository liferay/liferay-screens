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

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var male: Bool = true
	@IBInspectable public var portraitId: Int64 = 0
	@IBInspectable public var uuid: String = ""

	private func getLoggedUserPortraitURL() -> NSURL? {
		if let portraitId = SessionContext.userAttribute("portrait") as? NSNumber {
			if let uuid = SessionContext.userAttribute("uuid") as? String {
				let portraitIdLong = portraitId.longLongValue

				return getUserPortraitURL(male: true, portraitId: portraitIdLong, uuid: uuid)
			}
		}

		return nil
	}

	private func getUserPortraitURL(#male: Bool, portraitId: Int64, uuid: String) -> NSURL {
		let maleString = male ? "male" : "female"
		var URL: String = String()

		URL = LiferayServerContext.server
		URL = URL + "/image/user_\(maleString)/_portrait?img_id="
		URL = URL + "\(portraitId)"
		URL = URL + "&img_id_token=" + getSHA1(uuid)

		return NSURL(string: URL)!
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

	func load() -> Bool {
		var object: AnyObject

		if uuid == "" {
			object = getLoggedUserPortraitURL() as NSURL!
		}
		else if portraitId == 0 {
			object = UIImage(named: "default-portrait-placeholder") as UIImage!
		}
		else {
			object = getUserPortraitURL(male: male, portraitId: portraitId, uuid: uuid)
		}

		(screenletView as PortraitData).loadPortrait(object)

		return true
	}

	override func onShow() {
		if autoLoad {
			load()
		}
	}

}
