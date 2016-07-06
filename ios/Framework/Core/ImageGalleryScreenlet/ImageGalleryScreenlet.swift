//
//  ImageGalleryScreenlet.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation


@IBDesignable public class ImageGalleryScreenlet : BaseListScreenlet {
    
    @IBInspectable public var repositoryId: Int64 = 0
    @IBInspectable public var folderId: Int64 = 0
    
    @IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue
    
    public override func createPageLoadInteractor(page page: Int, computeRowCount: Bool) -> BaseListPageLoadInteractor {
        
        return ImageGalleryInteractor(screenlet: self,
                                      page: page,
                                      computeRowCount: computeRowCount,
                                      repositoryId: repositoryId,
                                      folderId: folderId)
    }
    
}