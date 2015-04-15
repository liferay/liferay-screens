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


class UserPortraitBaseInteractor: Interactor {

	var resultURL: NSURL?

	func URLForAttributes(#portraitId: Int64, uuid: String, male: Bool) -> NSURL {

		func encodedSHA1(input: String) -> String {
			var result = [Byte](count: Int(CC_SHA1_DIGEST_LENGTH), repeatedValue: 0)

			CC_SHA1(input, CC_LONG(countElements(input)), &result)
			let data = NSData(bytes: result, length: result.count)
			let encodedString = data.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(0))

			return LRHttpUtil.encodeURL(encodedString)
		}

		let maleString = male ? "male" : "female"

		let URL = "\(LiferayServerContext.server)/image/user_\(maleString)/_portrait" +
				"?img_id=\(portraitId)" +
				"&img_id_token=\(encodedSHA1(uuid))"

		return NSURL(string: URL)!
	}

}
