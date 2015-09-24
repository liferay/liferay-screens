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

	public override init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init(coder aDecoder: NSCoder) {
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
			uploadStatus = .Uploading(n1.unsignedLongValue, n2.unsignedLongValue)

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
			aCoder.encodeObject(NSNumber(unsignedLong: n1), forKey: "uploadStatusUploading1")
			aCoder.encodeObject(NSNumber(unsignedLong: n2), forKey: "uploadStatusUploading2")
		case .Pending:
			()
		}
	}


	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:AnyObject?

		if let valueString = value {
			let data = valueString.dataUsingEncoding(NSUTF8StringEncoding,
				allowLossyConversion: false)

			let jsonObject: AnyObject? = NSJSONSerialization.JSONObjectWithData(data!,
				options: NSJSONReadingOptions(0),
				error: nil)

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
			let data = NSJSONSerialization.dataWithJSONObject(json,
				options: .allZeros,
				error: nil)

			if let data = data {
				return NSString(data: data, encoding: NSUTF8StringEncoding) as? String
			}

		default: ()
		}

		return nil
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
			case .Failed(_):
				result = false
			default:
				result = true
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

public func ==(
		left: DDLFieldDocument.UploadStatus,
		right: DDLFieldDocument.UploadStatus)
		-> Bool {
	return left.hashValue == right.hashValue
}
