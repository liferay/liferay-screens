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

public struct LiferayLogger {
	
	public static func logDelegateMessage(_ function: String = #function, args: Any?...) {
		let message = "DELEGATE: \(function) called"
		
		if args.count > 0 {
			guard let error = args[0] as? NSError else {
				print("\n+++++++++++++++++++++")
				print("\(message) -> \(args)")
				print("+++++++++++++++++++++\n")
				return
			}
			
			self.error(message, error: error)
		} else {
			print("\n+++++++++++++++++++++")
			print(message)
			print("+++++++++++++++++++++\n")
		}
	}
	
	fileprivate static func error(_ message: String, error: NSError) {
		print("\n=====================")
		print(message)
		print("ERROR: \(error)")
		print("=====================\n")
	}
}
