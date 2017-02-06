//
//  WebContentDisplayScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "WebContentDisplayScreenletViewController.h"

@interface WebContentDisplayScreenletViewController () <WebContentDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet WebContentDisplayScreenlet *screenlet;

@end

@implementation WebContentDisplayScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;

    if (self.articleId) {
        self.screenlet.articleId = self.articleId;
    }
    else {
        self.screenlet.articleId = [LiferayServerContext stringPropertyForKey:@"webContentDisplayArticleId"];
    }
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    self.articleId = nil;
}

- (NSString *)screenlet:(WebContentDisplayScreenlet *)screenlet onWebContentResponse:(NSString *)html {
	LiferayLog(html);
    
    return nil;
}

- (void)screenlet:(WebContentDisplayScreenlet *)screenlet onWebContentError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(WebContentDisplayScreenlet *)screenlet onRecordContentResponse:(DDLRecord *)record {
    LiferayLog(record);
}

@end
