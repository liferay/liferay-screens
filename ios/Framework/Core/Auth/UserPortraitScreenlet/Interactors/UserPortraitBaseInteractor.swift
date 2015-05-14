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


class UserPortraitBaseInteractor: Interactor {

	var resultURL: NSURL?
	var resultUserId: Int64?

	func URLForAttributes(#portraitId: Int64, uuid: String, male: Bool) -> NSURL? {

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
					"&img_id_token=\(hashedUUID)"

			return NSURL(string: url)
		}

		return nil
	}

}
