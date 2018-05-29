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
#import "WebScreenletViewController.h"

@interface WebScreenletViewController () <WebScreenletDelegate>

@property (weak, nonatomic) IBOutlet WebScreenlet *screenlet;

@end

@implementation WebScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.screenlet.delegate = self;

	NSString *url = [LiferayServerContext stringPropertyForKey:@"webUrl"];
	
	WebScreenletConfigurationBuilder *builder = [[WebScreenletConfigurationBuilder alloc] initWithUrl:url];
	
	[builder setWithWebType:WebTypeLiferayAuthenticated];
	[builder addJsWithLocalFile:@"gallery"];
	[builder addCssWithLocalFile:@"gallery"];
	[builder addCssWithLocalFile:@"bigger_pagination"];
	
	WebScreenletConfiguration *configuration = [builder load];
	
	self.screenlet.configuration = configuration;
	[self.screenlet load];
}

- (void)onWebLoad:(WebScreenlet *)screenlet url:(NSString *)url {
	LiferayLog(url)
}

- (void)screenlet:(WebScreenlet *)screenlet onError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

- (void)screenlet:(WebScreenlet *)screenlet onScriptMessageNamespace:(NSString *)namespace_ onScriptMessage:(NSString *)message {
	LiferayLog(namespace_)
	LiferayLog(message)
}

@end
