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
#import "UserView.h"
#import "LiferayLogger.h"
#import "AssetDisplayViewController.h"

@interface AssetDisplayViewController () <AssetDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet AssetDisplayScreenlet *screenlet;

@end

@implementation AssetDisplayViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.presentingViewController = self;

	if (self.entryId) {
		self.screenlet.assetEntryId = self.entryId;
	}
	else {
		self.screenlet.assetEntryId = [LiferayServerContext longPropertyForKey:@"assetDisplayEntryId"];
	}

	[self.screenlet load];
}

#pragma mark AssetDisplayScreenletDelegate

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onConfigureScreenlet:(BaseScreenlet *)childScreenlet onAsset:(Asset *)asset {
	LiferayLog(childScreenlet, asset.attributes);
}

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onAssetResponse:(Asset *)asset {
	LiferayLog(asset.attributes);
}

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onAssetError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

- (UIView *)screenlet:(AssetDisplayScreenlet *)screenlet onAsset:(Asset *)asset {
	NSArray *keys = [asset.attributes[@"object"] allKeys];

	if ([keys containsObject:@"user"]) {
		UserView *userView = [[UserView alloc] init];
		NSDictionary *object = asset.attributes[@"object"];

		userView.user = [[User alloc] initWithAttributes:object[@"user"]];

		return userView;
	}

	return nil;
}

@end
