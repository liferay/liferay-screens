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
#import "AssetDisplayViewController.h"
#import "LiferayLogger.h"
#import "AssetListScreenletViewController.h"

@interface AssetListScreenletViewController () <AssetListScreenletDelegate>

@property (weak, nonatomic) IBOutlet AssetListScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UILabel *assetTypeLabel;

@end

@implementation AssetListScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.classNameId = self.assetClassId;
	self.assetTypeLabel.text = self.assetType;
}

#pragma mark AssetDisplayScreenletDelegate

- (void)screenlet:(AssetListScreenlet *)screenlet onAssetListResponse:(NSArray<Asset *> *)assets {
	LiferayLog(assets);
}

- (void)screenlet:(AssetListScreenlet *)screenlet onAssetListError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(AssetListScreenlet *)screenlet onAssetSelected:(Asset *)asset {
	LiferayLog(asset);

	[self performSegueWithIdentifier:@"assetDisplay" sender:asset];
}

#pragma mark UIViewController

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	AssetDisplayViewController *vc = segue.destinationViewController;
	vc.entryId = ((Asset *)sender).entryId;
}

@end
