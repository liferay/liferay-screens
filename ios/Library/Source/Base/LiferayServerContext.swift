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

	//MARK: Singleton type

	private struct StaticInstance {
		static var serverProperties: NSMutableDictionary?
	}


	//MARK: Public properties

	public class var server: String {
		get {
			loadContextFile()
			return StaticInstance.serverProperties!["server"] as String
		}
		set {
			StaticInstance.serverProperties!["server"] = newValue
		}
	}

	public class var companyId: Int64 {
		get {
			loadContextFile()
			return (StaticInstance.serverProperties!["companyId"] as NSNumber).longLongValue
		}
		set {
			StaticInstance.serverProperties!["companyId"] = NSNumber(longLong: newValue)
		}
	}

	public class var groupId: Int64 {
		get {
			loadContextFile()
			return (StaticInstance.serverProperties!["groupId"] as NSNumber).longLongValue
		}
		set {
			StaticInstance.serverProperties!["groupId"] = NSNumber(longLong: newValue)
		}
	}



	//MARK: Public methods

	private class func loadContextFile() {
		if StaticInstance.serverProperties != nil {
			return
		}

		if let propertiesPath =
				NSBundle.mainBundle().pathForResource("liferay-server-context", ofType:"plist") {

			StaticInstance.serverProperties = NSMutableDictionary(contentsOfFile: propertiesPath)
		}
		else {
			println("WARNING: liferay-server-context.plist file is not found. Falling back to template " +
				"liferay-server-context-sample.list")

			if let templatePath = NSBundle.mainBundle().pathForResource("liferay-server-context-sample",
					ofType:"plist") {

				StaticInstance.serverProperties = NSMutableDictionary(contentsOfFile: templatePath)
			}
			else {
				println("WARNING: liferay-server-context-sample.plist file is not found. " +
					"Using default values which will work in a default Liferay bundle running " +
					"on localhost:8080")
			}
		}

	}

}
