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

struct LiferayContext {

	var server:String = "http://localhost:8080"
	var companyId:Int = 10154
	var groupId:Int = 10181

	var currentSession:LRSession?

	static var instance = LiferayContext()

	init() {
        if let propertiesPath = NSBundle.mainBundle().pathForResource("liferay-context", ofType:"plist") {
			loadContextFile(propertiesPath)
		}
		else {
			println("WARNING: liferay-context.plist file is not found. Falling back to template " +
                "liferay-context-sample.list")

			if let templatePath = NSBundle.mainBundle().pathForResource("liferay-context-sample", ofType:"plist") {
				loadContextFile(templatePath)
			}
			else {
				println("WARNING: liferay-context-sample.plist file is not found. Using default values which will " +
                    "work in a default Liferay bundle installed in localhost")
			}
		}
	}

	mutating func loadContextFile(propertiesPath:String) {
		let properties = NSDictionary(contentsOfFile: propertiesPath)

        server = properties["server"] as String;
		companyId = properties["companyId"] as Int
		groupId = properties["groupId"] as Int
	}

	mutating func createSession(username:String, password:String) -> LRSession {
		self.currentSession = LRSession(server, username:username, password:password)
		return self.currentSession!
	}

	mutating func clearSession() {
		self.currentSession = nil
	}

}
