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
import CryptoSwift


class UserPortraitBaseInteractor: Interactor {

	var resultURL: NSURL?

	func URLForAttributes(#portraitId: Int64, uuid: String, male: Bool) -> NSURL? {

		func encodedSHA1(input: String) -> String? {
			if let inputData = input.dataUsingEncoding(NSUTF8StringEncoding,
					allowLossyConversion: false) {

				if let resultData = CryptoSwift.Hash.sha1(inputData).calculate() {
					return LRHttpUtil.encodeURL(
							resultData.base64EncodedStringWithOptions(
								NSDataBase64EncodingOptions(0)))
				}
			}

			return nil
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
