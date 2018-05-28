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

@import LiferayScreens;
#import "AssetListScreenletViewController.h"
#import "AssetTypesViewController.h"

@interface AssetTypesViewController ()

@property (nonatomic, copy) NSArray *assetClasses;
@property (nonatomic, copy) NSArray *assetClassesIds;
@property (nonatomic, copy) NSString *selectedAssetType;
@property (nonatomic) NSNumber *assetClassId;

@end

@implementation AssetTypesViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.assetClasses = @[
	  @"BlogsEntry",
	  @"BookmarksEntry",
	  @"BookmarksFolder",
	  @"CalendarBooking",
	  @"DDLRecord",
	  @"DDLFormRecord",
	  @"DLFileEntry",
	  @"DLFolder",
	  @"JournalArticle",
	  @"JournalFolder",
	  @"Layout",
	  @"LayoutRevision",
	  @"Organization",
	  @"Site",
	  @"User",
	  @"MBDiscussion",
	  @"MBMessage",
	  @"MBThread",
	  @"MicroblogsEntry",
	  @"WikiPage"
	];

	self.assetClassesIds = @[
		 @20011,
		 @28401,
		 @28402,
		 @27702,
		 @29501,
		 @31330,
		 @20015,
		 @20021,
		 @29634,
		 @29639,
		 @20047,
		 @20051,
		 @20059,
		 @20045,
		 @20087,
		 @20030,
		 @20032,
		 @20034,
		 @28701,
		 @28802
	];

}

- (void)viewWillAppear:(BOOL)animated {
	[super viewWillAppear:animated];
	
	LiferayServerContext.groupId = 20143;
}

- (void)viewWillDisappear:(BOOL)animated {
	[super viewWillDisappear:animated];

	self.title = nil;
}

#pragma mark UITableViewDataSource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return self.assetClasses.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView
		 cellForRowAtIndexPath:(NSIndexPath *)indexPath {

	UITableViewCell *cell = [[UITableViewCell alloc]
							 initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"default-cell"];

	cell.textLabel.text = self.assetClasses[indexPath.row];

	return cell;
}

#pragma mark UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	self.selectedAssetType = self.assetClasses[indexPath.row];
	self.assetClassId = self.assetClassesIds[indexPath.row];

	[self performSegueWithIdentifier:@"assetList" sender:@[self.selectedAssetType, self.assetClassId]];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {

	AssetListScreenletViewController *vc = segue.destinationViewController;
	if ([self.selectedAssetType  isEqual: @"MicroblogsEntry"] ||
		[self.selectedAssetType  isEqual: @"Organization"] ||
		[self.selectedAssetType  isEqual: @"Site"] ||
		[self.selectedAssetType  isEqual: @"User"]) {
		LiferayServerContext.groupId = 20152;
	}

	vc.assetType = self.selectedAssetType;
	vc.assetClassId = [self.assetClassId longLongValue];
}

@end
