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
		0: ["Assets", "AssetListScreenlet", "AssetDisplayScreenlet", "AssetDisplayScreenletWithPortletItemName"],
		1: [NSLocalizedString("comment-screenlets", comment: "Comments"), "CommentListScreenlet", "CommentDisplayScreenlet", "CommentAddScreenlet"],
		2: ["DDL", "DDLListScreenlet", "DDLFormScreenlet"],
		3: [NSLocalizedString("files-screenlets", comment: "Files"), "AudioDisplayScreenlet", "ImageDisplayScreenlet", "PdfDisplayScreenlet",
			"VideoDisplayScreenlet", "FileDisplayScreenlet"],
		4: [NSLocalizedString("others-screenlets", comment: "Others"), "UserPortraitScreenlet", "RatingScreenlet", "ImageGalleryScreenlet",
			"BlogDisplayScreenlet"],
		5: ["WebContent", "WebContentDisplayScreenlet", "WebContentListScreenlet"]
	]
	
	
	//MARK: UIViewController
	
	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		self.navigationItem.title = NSLocalizedString("screenlets-available", comment: "Screenlets Available")
	}
	
	override func viewWillDisappear(_ animated: Bool) {
		super.viewWillDisappear(animated)
		self.navigationItem.title = nil
	}
	
	
	//MARK: UITableViewDataSource
	
    override func numberOfSections(in tableView: UITableView) -> Int {
		return data.count
	}

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return data[section]!.count - 1
	}
	
	override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = UITableViewCell(style: .default, reuseIdentifier: "default-cell");
		
		cell.textLabel?.text = data[indexPath.section]![indexPath.row + 1]
		
		return cell
	}

    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
		return data[section]![0]
	}
	
	
	//MARK: UITableViewDelegate
	
	override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		let name = data[indexPath.section]![indexPath.row + 1]
		if Bundle.main.path(forResource: name, ofType: "storyboardc") != nil {
			let storyboard = UIStoryboard(name: name, bundle: Bundle.main)
			
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

