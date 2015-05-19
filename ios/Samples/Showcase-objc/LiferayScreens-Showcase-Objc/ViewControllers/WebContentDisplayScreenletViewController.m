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
#import "WebContentDisplayScreenletViewController.h"

@interface WebContentDisplayScreenletViewController ()

@property (nonatomic, weak) IBOutlet WebContentDisplayScreenlet* screenlet;

@end


@implementation WebContentDisplayScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
}

- (NSString *)screenlet:(WebContentDisplayScreenlet *)screenlet
		onWebContentResponse:(NSString *)html {
	NSLog(@"DELEGATE onWebContentResponse -> %@", html);
	return nil;
}

- (void)screenlet:(WebContentDisplayScreenlet *)screenlet
		onWebContentError:(NSError *)error {
	NSLog(@"DELEGATE onWebContentError -> %@", error);
}

@end
