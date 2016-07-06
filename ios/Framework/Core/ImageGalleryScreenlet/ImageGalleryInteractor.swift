//
//  ImageGalleryInteractor.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation


public class ImageGalleryInteractor : BaseListPageLoadInteractor {
    
    public let repositoryId: Int64
    public let folderId: Int64

    
    public init(screenlet: BaseListScreenlet, page: Int, computeRowCount:Bool, repositoryId: Int64, folderId: Int64) {
        self.repositoryId = repositoryId
        self.folderId = folderId
        
        super.init(screenlet: screenlet, page: page, computeRowCount: computeRowCount)
    }
    
    public override func createConnector() -> PaginationLiferayConnector {
        let pager = (self.screenlet as! BaseListScreenlet).firstRowForPage
        
        return ImageGalleryPageLiferayConnector(startRow: pager(self.page),
                                                endRow: pager(self.page + 1),
                                                computeRowCount: self.computeRowCount,
                                                repositoryId: repositoryId,
                                                folderId: folderId)
    }
    
    public override func convertResult(serverResult: [String : AnyObject]) -> AnyObject {
        return ImageEntry(attributes:serverResult)
    }
    
    public override func cacheKey(op: PaginationLiferayConnector) -> String {
        return "image-gallery-key"
    }
}