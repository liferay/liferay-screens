//
//  WebContentListScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "WebContentDisplayScreenletViewController.h"
#import "WebContentListScreenletViewController.h"

@interface WebContentListScreenletViewController () <WebContentListScreenletDelegate>

@property (weak, nonatomic) WebContentListScreenlet *screenlet;

@end

@implementation WebContentListScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
}

- (void)screenlet:(WebContentListScreenlet *)screenlet
		onWebContentListResponse:(NSArray<WebContent *> *)contents {
    LiferayLog(contents);
}

- (void)screenlet:(WebContentListScreenlet *)screenlet onWebContentListError:(NSError *)error {
    LiferayLog(error);
}

- (void)screenlet:(WebContentListScreenlet *)screenlet onWebContentSelected:(WebContent *)content {
    LiferayLog(content);

    NSString *selectedArticleId = content.attributes[@"articleId"];
    [self performSegueWithIdentifier:@"WebContentDisplay" sender:selectedArticleId];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    WebContentDisplayScreenletViewController *vc = segue.destinationViewController;
    vc.articleId = sender;
}

@end
