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
		case Uploading(UInt64, UInt64)
		case Pending

		public static func CachedStatusData(cacheKey: String) -> [String:AnyObject] {
			return [
				"cached": cacheKey,
				"mimeType": "image/png"]
		}

		public var hashValue: Int {
			return toInt()
		}

		private func toInt() -> Int {
			switch self {
			case .Uploaded(_):
				return 1
			case .Failed(_):
				return 2
			case .Uploading(_,_):
				return 3
			case .Pending:
				return 4
			}
		}

	}


	public var uploadStatus = UploadStatus.Pending

	public var mimeType: String? {
		if cachedKey != nil {
			switch uploadStatus {
			case .Uploaded(let json):
				return json["mimeType"] as? String
			default: ()
			}
		}

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

	public var cachedKey: String? {
		switch uploadStatus {
		case .Uploaded(let json):
			return json["cached"] as? String
		default: ()
		}

		return nil
	}


	//MARK: DDLField

	public override init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		let uploadStatusHash = aDecoder.decodeIntegerForKey("uploadStatusHash")

		switch uploadStatusHash {
		case UploadStatus.Uploaded([:]).hashValue:
			let attributes = aDecoder.decodeObjectForKey("uploadStatusUploadedAttributes") as!  [String:AnyObject]
			uploadStatus = .Uploaded(attributes)

		case UploadStatus.Failed(nil).hashValue:
			let err = aDecoder.decodeObjectForKey("uploadStatusFailedError") as! NSError
			uploadStatus = .Failed(err)

		case UploadStatus.Uploading(0, 0).hashValue:
			let n1 = aDecoder.decodeObjectForKey("uploadStatusUploading1") as! NSNumber
			let n2 = aDecoder.decodeObjectForKey("uploadStatusUploading2") as! NSNumber
			uploadStatus = .Uploading(n1.unsignedLongLongValue, n2.unsignedLongLongValue)

		default:
			()
		}

		super.init(coder: aDecoder)
	}

	public override func encodeWithCoder(aCoder: NSCoder) {
		super.encodeWithCoder(aCoder)

		aCoder.encodeInteger(uploadStatus.hashValue, forKey: "uploadStatusHash")

		switch uploadStatus {
		case .Uploaded(let attributes):
			aCoder.encodeObject(attributes, forKey: "uploadStatusUploadedAttributes")
		case .Failed(let error):
			aCoder.encodeObject(error, forKey: "uploadStatusFailedError")
		case .Uploading(let n1, let n2):
			aCoder.encodeObject(NSNumber(unsignedLongLong: n1), forKey: "uploadStatusUploading1")
			aCoder.encodeObject(NSNumber(unsignedLongLong: n2), forKey: "uploadStatusUploading2")
		case .Pending:
			()
		}
	}


	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:AnyObject?

		if let valueString = value {
			let data = valueString.dataUsingEncoding(NSUTF8StringEncoding,
				allowLossyConversion: false)

			let jsonObject: AnyObject? = try? NSJSONSerialization.JSONObjectWithData(data!,
				options: NSJSONReadingOptions(rawValue: 0))

			if let jsonDict = jsonObject as? [String:AnyObject] {
				uploadStatus = .Uploaded(jsonDict)
				result = jsonDict
			}
			else if valueString != "" {
				uploadStatus = .Pending
				result = valueString
			}
		}

		return result
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		return nil
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		switch uploadStatus {
		case .Uploaded(let json):
			if let groupId = json["groupId"] as? NSNumber,
					uuid = json["uuid"] as? String,
					version = json["version"] as? String {
				return "{\"groupId\":\(groupId)," +
						"\"uuid\":\"\(uuid)\"," +
						"\"version\":\"\(version)\"}"
			}
			else {
				let data = try? NSJSONSerialization.dataWithJSONObject(json,
					options: [])

				if let data = data {
					return NSString(data: data, encoding: NSUTF8StringEncoding) as? String
				}
			}

		default: ()
		}

		return nil
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		switch currentValue {
		case is UIImage:
			return LocalizedString("core", key: "an-image-has-been-selected", obj: self)
		case is NSURL:
			return LocalizedString("core", key: "a-video-has-been-selected", obj: self)
		case is [String:AnyObject]:
			return LocalizedString("core", key: "a-file-is-already-uploaded", obj: self)
		default: ()
		}

		return nil
	}

	override internal func doValidate() -> Bool {
		var result = super.doValidate()

		if result {
			switch uploadStatus {
			case .Failed(_):
				result = false
			default:
				result = true
			}
		}

		return result
	}


	//MARK: Public methods

	public func getStream(inout size: Int64) -> NSInputStream? {
		var result: NSInputStream?

		switch currentValue {
		case let image as UIImage:
			if let imageData = UIImagePNGRepresentation(image) {
				size = Int64(imageData.length)
				result = NSInputStream(data: imageData)
			}

		case let videoURL as NSURL:
			let attributes = try? NSFileManager.defaultManager().attributesOfItemAtPath(
					videoURL.path!)
			if let sizeValue = attributes?[NSFileSize] as? NSNumber {
				size = sizeValue.longLongValue
			}
			result = NSInputStream(URL: videoURL)

		default: ()
		}

		return result
	}

}


//MARK: Equatable

public func ==(
		left: DDLFieldDocument.UploadStatus,
		right: DDLFieldDocument.UploadStatus)
		-> Bool {
	return left.hashValue == right.hashValue
}
