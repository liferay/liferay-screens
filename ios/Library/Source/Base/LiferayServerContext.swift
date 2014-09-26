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


public class LiferayServerContext {

	public var server = "http://localhost:8080"
	public var companyId = 10154
	public var groupId = 10181

	//MARK: Singleton

	class var instance: LiferayServerContext {
		struct Singleton {
			static var instance: LiferayServerContext? = nil
			static var onceToken: dispatch_once_t = 0
		}

		dispatch_once(&Singleton.onceToken) {
			Singleton.instance = self()
		}

		return Singleton.instance!
	}

	public required init() {
		if let propertiesPath =
				NSBundle.mainBundle().pathForResource("liferay-server-context", ofType:"plist") {
			loadContextFile(propertiesPath)
		}
		else {
			println("WARNING: liferay-server-context.plist file is not found. Falling back to template " +
				"liferay-server-context-sample.list")

			if let templatePath = NSBundle.mainBundle().pathForResource("liferay-server-context-sample",
					ofType:"plist") {
				loadContextFile(templatePath)
			}
			else {
				println("WARNING: liferay-server-context-sample.plist file is not found. " +
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

}
