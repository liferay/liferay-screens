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


public class DDLFieldDocument : DDLField {

	public enum UploadStatus: Hashable, Equatable {
		case Uploaded([String:AnyObject])
		case Failed(NSError?)
		case Uploading(UInt,UInt)
		case Pending

		public var hashValue: Int {
		    return toInt()
		}

		private func toInt() -> Int {
			switch self {
				case .Uploaded(_):
    		        return Int.max
        		case .Failed(_):
					return Int.min
				case .Uploading(_,_):
					return 1
				default:
					return 0
			}
		}

	}


	public var uploadStatus = UploadStatus.Pending

	public var mimeType: String? {
		switch currentValue {
			case is UIImage:
				return "image/png"
			case is NSURL:
				return "video/mpeg"
			case is [String:AnyObject]:
				return nil
			default:
				return nil
		}
	}


	//MARK: DDLField

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:AnyObject?

		if let valueString = value {
			let data = valueString.dataUsingEncoding(NSUTF8StringEncoding,
					allowLossyConversion: false)
			if let jsonObject:AnyObject = NSJSONSerialization.JSONObjectWithData(data!,
					options: NSJSONReadingOptions(0), error: nil) {
				if let jsonDict = jsonObject as? NSDictionary {
					let dict = ["groupId" : jsonDict["groupId"]!,
							"uuid" : jsonDict["uuid"]!,
							"version" : jsonDict["version"]!]

					uploadStatus = UploadStatus.Uploaded(dict)
					result = dict
				}
			}
			else if valueString != "" {
				result = valueString
			}
		}

		return result
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		return nil
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		switch uploadStatus {
			case .Uploaded(let json):
				let groupId = (json["groupId"] ?? nil) as? Int
				let uuid = (json["uuid"] ?? nil) as? String
				let version = (json["version"] ?? nil) as? String

				if groupId != nil && uuid != nil && version != nil {
					result = "{\"groupId\":\(groupId!)," +
								"\"uuid\":\"\(uuid!)\"," +
								"\"version\":\"\(version!)\"}"
				}

			default: ()
		}

		return result
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		switch currentValue {
			case is UIImage:
				return LocalizedString("core", "an-image-has-been-selected", self)
			case is NSURL:
				return LocalizedString("core", "a-video-has-been-selected", self)
			case is [String:AnyObject]:
				return LocalizedString("core", "a-file-is-already-uploaded", self)
			default: ()
		}

		return nil
	}

	override internal func doValidate() -> Bool {
		var result = super.doValidate()

		if result {
			switch uploadStatus {
				case .Failed(_): result = false
				default: result = true
			}
		}

		return result
	}


	//MARK: Public methods

	public func getStream(inout size:Int64) -> NSInputStream? {
		var result: NSInputStream?

		switch currentValue {
			case let image as UIImage:
				let imageData = UIImagePNGRepresentation(image)
				size = Int64(imageData.length)
				result = NSInputStream(data: imageData)

			case let videoURL as NSURL:
				var outError:NSError?
				if let attributes = NSFileManager.defaultManager().attributesOfItemAtPath(
						videoURL.path!, error: &outError) {
					if let sizeValue = attributes[NSFileSize] as? NSNumber {
						size = sizeValue.longLongValue
					}
				}
				result = NSInputStream(URL: videoURL)

			default: ()
		}

		return result
	}

}


//MARK: Equatable

public func ==(left: DDLFieldDocument.UploadStatus, right: DDLFieldDocument.UploadStatus) ->
		Bool {

	return left.hashValue == right.hashValue
}
