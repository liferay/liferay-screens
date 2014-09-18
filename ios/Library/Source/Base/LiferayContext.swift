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
import Foundation


public class LiferayContext {

	public var server = "http://localhost:8080"
	public var companyId = 10154
	public var groupId = 10181

	public var currentSession:LRSession?

	//MARK: Singleton

	class var instance: LiferayContext {
		struct Singleton {
			static var instance: LiferayContext? = nil
			static var onceToken: dispatch_once_t = 0
		}

		dispatch_once(&Singleton.onceToken) {
			Singleton.instance = self()
		}

		return Singleton.instance!
	}

	public required init() {
		if let propertiesPath =
				NSBundle.mainBundle().pathForResource("liferay-context", ofType:"plist") {
			loadContextFile(propertiesPath)
		}
		else {
			println("WARNING: liferay-context.plist file is not found. Falling back to template " +
				"liferay-context-sample.list")

			if let templatePath = NSBundle.mainBundle().pathForResource("liferay-context-sample",
					ofType:"plist") {
				loadContextFile(templatePath)
			}
			else {
				println("WARNING: liferay-context-sample.plist file is not found. " +
					"Using default values which will work in a default Liferay bundle installed " +
					"in localhost")
			}
		}
	}


	//MARK: Public methods

	public func loadContextFile(propertiesPath:String) {
		let properties = NSDictionary(contentsOfFile: propertiesPath)

		server = properties["server"] as String;
		companyId = properties["companyId"] as Int
		groupId = properties["groupId"] as Int
	}

	public func createSession(username:String, password:String) -> LRSession {
		currentSession = LRSession(server:server, username:username, password:password)
		return currentSession!
	}

	public func clearSession() {
		currentSession = nil
	}

}
