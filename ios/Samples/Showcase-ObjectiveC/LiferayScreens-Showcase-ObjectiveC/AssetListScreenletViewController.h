//
//  AssetListScreenletViewController.h
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AssetListScreenletViewController : UIViewController

@property (nonatomic, copy) NSString *assetType;
@property (nonatomic) int64_t assetClassId;

@end
