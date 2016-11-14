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

	//An storyboard that matches these names must be created
	private let data: [Int: [String]] = [
		0: ["Assets", "AssetListScreenlet", "AssetDisplayScreenlet"],
		1: ["Comments", "CommentListScreenlet", "CommentDisplayScreenlet", "CommentAddScreenlet"],
		2: ["DDL Module", "DDLListScreenlet", "DDLFormScreenlet"],
		3: ["Files", "AudioDisplayScreenlet", "ImageDisplayScreenlet", "PdfDisplayScreenlet",
			"VideoDisplayScreenlet"],
		4: ["Others", "UserPortraitScreenlet", "RatingScreenlet", "ImageGalleryScreenlet",
			"BlogDisplayScreenlet"],
		5: ["Web Content Module", "WebContentDisplayScreenlet", "WebContentListScreenlet"]
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
		let name = data[indexPath.section]![indexPath.row + 1]
		if NSBundle.mainBundle().pathForResource(name, ofType: "storyboardc") != nil {
			let storyboard = UIStoryboard(name: name, bundle: NSBundle.mainBundle())
			
			let viewController = storyboard.instantiateInitialViewController()
			
			if let viewController = viewController {
				viewController.title = name
				self.navigationController?.pushViewController(viewController, animated: true)
			}
		} else {
			print("//////")
			print("ERROR: no Storyboard named \"\(name)\"")
			print("//////")
		}
	}
}

