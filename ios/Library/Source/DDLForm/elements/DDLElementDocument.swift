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

public class DDLElementDocument : DDLElement {

	public enum UploadStatus {
		case Uploaded([String:AnyObject])
		case Failed(NSError)
		case Pending
	}

	public var uploadStatus:UploadStatus = .Pending

	public var mimeType: String? {
		var result:String?

		switch currentValue {
			case is UIImage:
				result = "image/png"
			case is NSURL:
				result = "video/mpeg"
			default: ()
		}

		return result
	}

	public func getStream(inout size:Int64) -> NSInputStream? {
		var result: NSInputStream?

		switch currentValue {
			case let image as UIImage:
				let imageData = UIImagePNGRepresentation(image)
				size = Int64(imageData.length)
				result = NSInputStream(data: imageData)

			case let videoURL as NSURL:
				var outError:NSError?
				if let attributes = NSFileManager.defaultManager().attributesOfItemAtPath(videoURL.path, error: &outError) {
					if let sizeValue = attributes[NSFileSize] as? NSNumber {
						size = sizeValue.longLongValue
					}
				}
				result = NSInputStream(URL: videoURL)

			default: ()
		}

		return result
	}

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:String? = nil

		if value != nil && value! != "" {
			result = value!
		}

		return result
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		switch currentValue {
			case is UIImage:
				result = "Image"
			case is NSURL:
				result = "Video"
			default: ()
		}

		return result
	}

	override internal func doValidate() -> Bool {
		var result = super.doValidate()

		if result {
			switch (uploadStatus) {
				case .Failed(_): ()
					result = false
				default: ()
					result = true
			}
		}

		return result
	}


}
