//
//  GetSiteTitleInteractor.swift
//  LiferayScreensAddBookmarkScreenletSample
//
//  Created by jmWork on 18/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens

public class GetSiteTitleInteractor: Interactor {

	public var resultTitle: String?

	private var session: NSURLSession?

	override public func start() -> Bool {
		let viewModel = self.screenlet.screenletView as! AddBookmarkViewModel

		if let URL = NSURL(string: viewModel.URL!) {
			NSURLSession.sharedSession().dataTaskWithURL(URL) {
				(data, response, error) in

				if let errorValue = error {
					self.callOnFailure(errorValue)
				}
				else {
					if let html = NSString(data: data, encoding: NSUTF8StringEncoding) {
						self.resultTitle = self.parseTitle(html)
					}
					self.callOnSuccess()
				}
			}.resume()

			// return true to notify the operation is in progress
			return true
		}

		// return false if you cannot start the operation
		return false
	}

	private func parseTitle(html: NSString) -> String {
		// quick & dirty parse
		let range1 = html.rangeOfString("<title>")
		let range2 = html.rangeOfString("</title>")

		let start = range1.location + range1.length

		return html.substringWithRange(NSMakeRange(start, range2.location - start))
	}

}
