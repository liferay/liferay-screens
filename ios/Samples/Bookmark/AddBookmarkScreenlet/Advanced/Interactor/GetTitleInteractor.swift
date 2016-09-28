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
import LiferayScreens

public class GetSiteTitleInteractor: Interactor {

	public var resultTitle: String?

	var url: String


	//MARK: Initializer

	public init(url: String) {
		self.url = url
		super.init(screenlet: nil)
	}

	override public func start() -> Bool {
		if let URL = NSURL(string: url) {

			// Use the NSURLSession class to retrieve the HTML
			NSURLSession.sharedSession().dataTaskWithURL(URL) {
				(data, response, error) in

				if let errorValue = error {
					self.callOnFailure(errorValue)
				}
				else {
					if let data = data, html = NSString(data: data, encoding: NSUTF8StringEncoding) {
						self.resultTitle = self.parseTitle(html)
					}

					self.callOnSuccess()
				}
			}.resume()

			return true
		}

		return false
	}

	///Parse the title from a webpage HTML
	private func parseTitle(html: NSString) -> String {
		let range1 = html.rangeOfString("<title>")
		let range2 = html.rangeOfString("</title>")

		let start = range1.location + range1.length

		return html.substringWithRange(NSMakeRange(start, range2.location - start))
	}

}
