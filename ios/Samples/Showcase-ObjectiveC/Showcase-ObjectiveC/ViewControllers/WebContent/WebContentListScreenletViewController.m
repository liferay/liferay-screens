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

#pragma mark WebContentListScreenlet

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
