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

class HomeScreenletsViewController: UITableViewController {

	private let data: [Int: [String]] = [
		0: ["DDL Module", "DDLListScreenlet", "DDLFormScreenlet"],
		1: ["Web Content Module", "WebContentDisplayScreenlet", "WebContentListScreenlet"],
		2: ["Assets", "AssetListScreenlet", "AssetDisplayScreenlet"],
		3: ["Comments", "CommentListScreenlet", "CommentDisplayScreenlet"],
		4: ["Others", "UserPortraitScreenlet", "RatingScreenlet", "ImageGalleryScreenlet", "BlogDisplayScreenlet"]
	]
	
	
	//MARK: UIViewController
	
	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		self.navigationItem.title = "Screenlets Available"
	}
	
	override func viewWillDisappear(animated: Bool) {
		super.viewWillDisappear(animated)
		self.navigationItem.title = nil
	}
	
	
	//MARK: UITableViewDataSource
	
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
		return data.count
	}

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return data[section]!.count - 1
	}
	
	override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
		let cell = UITableViewCell(style: .Default, reuseIdentifier: "default-cell");
		
		cell.textLabel?.text = data[indexPath.section]![indexPath.row + 1]
		
		return cell
	}

    override func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
		return data[section]![0]
	}
	
	
	//MARK: UITableViewDelegate
	
	
	override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
		self.performSegueWithIdentifier(data[indexPath.section]![indexPath.row + 1], sender: self)
	}

}

