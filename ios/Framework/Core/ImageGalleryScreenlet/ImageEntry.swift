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


public class ImageEntry : Asset {
    
    public var thumbnailUrl: String {
        return createThumbnailUrl()
    }

	public var imageUrl: String {
		return createImageUrl()
	}

	public var imageEntryId: Int64 {
		return attributes["fileEntryId"]?.longLongValue ?? 0
	}
    
    override public init(attributes: [String:AnyObject]) {
        super.init(attributes: attributes)
    }

    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    private func createThumbnailUrl() -> String {
        return "\(createImageUrl())?version=\(attributes["version"]!)&imageThumbnail=1"
    }
    
    private func createImageUrl() -> String {
        return "\(LiferayServerContext.server)/documents/\(attributes["groupId"]!)/" +
            "\(attributes["folderId"]!)/\(encodeUrlString(title))/\(attributes["uuid"]!)"
    }
    
    private func encodeUrlString(originalString: String) -> String {
        return originalString.stringByAddingPercentEncodingWithAllowedCharacters(
            .URLHostAllowedCharacterSet()) ?? ""
    }
}


public func ==(lhs: ImageEntry, rhs: ImageEntry) -> Bool {
	return lhs.imageEntryId == rhs.imageEntryId
}