//
//  ImageGalleryView_default.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation
import AFNetworking


public class ImageGalleryView_default : BaseListTableView {
 
    public override func onCreated() {
        super.onCreated()
        tableView?.rowHeight = 110
    }
    
    override public func createProgressPresenter() -> ProgressPresenter {
        return DefaultProgressPresenter()
    }
    
    override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
        if let entry = object as? ImageEntry {
            guard let imageCell = cell as? ImageGalleryCell else {
                return
            }
            
            imageCell.imageUrl = entry.thumbnailUrl
            imageCell.title = entry.title
        }
    }
    
    public override func doCreateCell(cellId: String) -> UITableViewCell {
        if cellId == "cellId" {
            ImageGalleryCell(style: .Default, reuseIdentifier: "cellId")
        }
        
        return super.doCreateCell(cellId)
    }
    
    override public func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
        cell.textLabel?.text = "..."
        cell.accessoryType = .None
        
        if let image = NSBundle.imageInBundles(
            name: "default-hourglass",
            currentClass: self.dynamicType) {
            
            cell.accessoryView = UIImageView(image: image)
            cell.accessoryView?.frame = CGRectMake(0, 0, image.size.width, image.size.height)
        }
    }
    
    public override func doGetCellId(row row: Int, object: AnyObject?) -> String {
        if let _ = object {
            return "cellId"
        }
        
        return super.doGetCellId(row: row, object: object)
    }
    
    public override func doRegisterCellNibs() {
        if let imageGalleryCellNib = NSBundle.nibInBundles(
            name: "ImageGalleryCell",
            currentClass: self.dynamicType) {
            
            tableView?.registerNib(imageGalleryCellNib, forCellReuseIdentifier: "cellId")
        }
    }
}