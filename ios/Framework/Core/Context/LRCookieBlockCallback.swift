//
//  LRCookieBlockCallback.swift
//  LiferayScreens
//
//  Created by Victor Galán on 13/02/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation

@objc public class LRCookieBlockCallback : NSObject, LRCookieCallback {

	public func onSuccess(_ session: LRSession!) {
		callback(session, nil)
	}

	public func onFailure(_ error: Error!) {
		callback(nil, error)
	}

	let callback: (LRSession?, Error?) -> Void

	init(callback: @escaping (LRSession?, Error?) -> Void) {
		self.callback = callback
	}
	
}
