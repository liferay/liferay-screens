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


public struct LiferayServerContext {

	public static var server: String {
		loadContextFile()
		return serverProperties!["server"] as String
	}

	public static var companyId: Int64 {
		loadContextFile()
		return (serverProperties!["companyId"] as NSNumber).longLongValue
	}

	public static var groupId: Int64 {
		loadContextFile()
		return (serverProperties!["groupId"] as NSNumber).longLongValue
	}

	private static var serverProperties: NSDictionary?


	//MARK: Public methods

	private static func loadContextFile() {
		if serverProperties != nil {
			return
		}

		if let propertiesPath =
				NSBundle.mainBundle().pathForResource("liferay-server-context", ofType:"plist") {

			serverProperties = NSDictionary(contentsOfFile: propertiesPath)
		}
		else {
			println("WARNING: liferay-server-context.plist file is not found. Falling back to template " +
				"liferay-server-context-sample.list")

			if let templatePath = NSBundle.mainBundle().pathForResource("liferay-server-context-sample",
					ofType:"plist") {

				serverProperties = NSDictionary(contentsOfFile: templatePath)
			}
			else {
				println("WARNING: liferay-server-context-sample.plist file is not found. " +
					"Using default values which will work in a default Liferay bundle running " +
					"on localhost:8080")
			}
		}

	}

}
