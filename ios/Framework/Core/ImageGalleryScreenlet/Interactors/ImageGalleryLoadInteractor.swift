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


public class ImageGalleryLoadInteractor : BaseListPageLoadInteractor {
    
    public let repositoryId: Int64
    public let folderId: Int64
	public let mimeTypes: [String]

    
    public init(screenlet: BaseListScreenlet,
			page: Int, computeRowCount:Bool,
			repositoryId: Int64,
			folderId: Int64,
			mimeTypes: [String]) {

        self.repositoryId = repositoryId
        self.folderId = folderId
		self.mimeTypes =  mimeTypes
        
        super.init(screenlet: screenlet, page: page, computeRowCount: computeRowCount)
    }
    
    public override func createConnector() -> PaginationLiferayConnector {
        let pager = (self.screenlet as! BaseListScreenlet).firstRowForPage
        
        return ImageGalleryPageLiferayConnector(startRow: pager(self.page),
                                                endRow: pager(self.page + 1),
                                                computeRowCount: self.computeRowCount,
                                                repositoryId: repositoryId,
                                                folderId: folderId,
												mimeTypes: mimeTypes)
    }

    public override func convertResult(serverResult: [String : AnyObject]) -> AnyObject {
        return ImageEntry(attributes:serverResult)
    }
    
    public override func cacheKey(op: PaginationLiferayConnector) -> String {
        return "image-gallery-key"
    }
}