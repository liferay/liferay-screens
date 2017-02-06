//
//  AssetListScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    AssetDisplayViewController *vc = segue.destinationViewController;
    vc.entryId = ((Asset *)sender).entryId;
}

@end
