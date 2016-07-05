//
//  ImageGalleryView_default.swift
//  LiferayScreens
//
//  Created by liferay on 05/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import Foundation


public class ImageGalleryView_default : BaseListTableView {
 
    override public func createProgressPresenter() -> ProgressPresenter {
        return DefaultProgressPresenter()
    }
    
    override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
        if let entry = object as? ImageEntry {
            cell.textLabel?.text = entry.title
            cell.detailTextLabel?.text = entry.thumbnailUrl;
            cell.accessoryType = .DisclosureIndicator
            cell.accessoryView = nil
        }
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
}