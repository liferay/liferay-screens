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

#pragma mark WebContentDisplayScreenlet

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
