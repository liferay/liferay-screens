//
//  ImageEntry.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation


public class ImageEntry : Asset {
    
    public var thumbnailUrl: String {
        return createThumbnailUrl()
    }
    
    override public init(attributes: [String:AnyObject]) {
        super.init(attributes: attributes)
    }

    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    private func createThumbnailUrl() -> String {
        return "\(createImageUrl())?version=\(attributes["version"])&imageThumbnail=1"
    }
    
    private func createImageUrl() -> String {
        return "\(LiferayServerContext.server)/documents/\(attributes["groupId"])/)" +
            "\(attributes["folderId"])/\(encodeUrlString(title))/\(attributes["uuid"]))"
    }
    
    private func encodeUrlString(originalString: String) -> String {
        return originalString.stringByAddingPercentEncodingWithAllowedCharacters(
            .URLHostAllowedCharacterSet()) ?? ""
    }
}