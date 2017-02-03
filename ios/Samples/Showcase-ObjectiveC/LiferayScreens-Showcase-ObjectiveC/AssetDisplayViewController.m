//
//  AssetDisplayViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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
    self.screenlet.assetEntryId = [LiferayServerContext longPropertyForKey:@"assetDisplayEntryId"];

    [self.screenlet load];
}

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onConfigureScreenlet:(BaseScreenlet *)childScreenlet onAsset:(Asset *)asset {
    LiferayLog(childScreenlet, asset);
}

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onAssetResponse:(Asset *)asset {
	LiferayLog(asset);
}

- (void)screenlet:(AssetDisplayScreenlet *)screenlet onAssetError:(NSError *)error {
	LiferayLog(error);
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
