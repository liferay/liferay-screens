//
//  ImageGalleryPageLiferayConnector.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation


public class ImageGalleryPageLiferayConnector : PaginationLiferayConnector {
    
    public let repositoryId: Int64
    public let folderId: Int64
    public let mimeTypes = ["image/png", "image/jpeg"]
    
    public init(startRow: Int, endRow:Int, computeRowCount:Bool, repositoryId: Int64, folderId: Int64) {
        self.repositoryId = repositoryId
        self.folderId = folderId
        
        super.init(startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
    }
    
    override public func doAddPageRowsServiceCall(session session: LRBatchSession, startRow: Int, endRow: Int) {
        let service = LRDLAppService_v7(session: session)
        
        do {
            try service.getFileEntriesWithRepositoryId(repositoryId,
                                                       folderId: folderId,
                                                       mimeTypes: mimeTypes,
                                                       start: Int32(startRow),
                                                       end: Int32(endRow),
                                                       obc: nil)
        } catch {}
    }
    
    override public func doAddRowCountServiceCall(session session: LRBatchSession) {
        let service = LRDLAppService_v7(session: session)
        
        do {
            try service.getFileEntriesCountWithRepositoryId(repositoryId,
                                                   folderId: folderId,
                                                   mimeTypes: mimeTypes)
        } catch {}
    }
}