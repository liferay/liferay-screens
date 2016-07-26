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


public class ImageGalleryPageLiferayConnector : PaginationLiferayConnector {
    
    public let repositoryId: Int64
    public let folderId: Int64
    public let mimeTypes = ["image/png", "image/jpeg"]
    
    public init(startRow: Int, endRow:Int, computeRowCount:Bool, repositoryId: Int64, folderId: Int64) {
        self.repositoryId = repositoryId
        self.folderId = folderId
        
        super.init(startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
    }

	public override func validateData() -> ValidationError? {
		var error = super.validateData()

		if error == nil {
			if repositoryId < 0 {
				error = ValidationError("imagegallery-screenlet","undefined-repositoryid")
			}
			else if folderId < 0 {
				error = ValidationError("imagegallery-screenlet","undefined-folderid")
			}
		}

		return error
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