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
#import "BlogDisplayScreenletViewController.h"

@interface BlogDisplayScreenletViewController () <BlogsEntryDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet BlogsEntryDisplayScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *blogClassPKLabel;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@end

@implementation BlogDisplayScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;

	self.blogClassPKLabel.text = [LiferayServerContext stringPropertyForKey:@"blogDisplayClassPK"];
}

- (IBAction)loadBlog:(id)sender {
	if (self.blogClassPKLabel.text.length > 0) {
		self.screenlet.classPK = self.blogClassPKLabel.text.longLongValue;
		[self.screenlet load];
	}
}

#pragma mark BlogEntryDisplayScreenletDelegate

- (void)screenlet:(BlogsEntryDisplayScreenlet *)screenlet
	onBlogEntryResponse:(BlogsEntry *)blogEntry {

	screenlet.hidden = NO;
	LiferayLog(blogEntry.attributes);
}

- (void)screenlet:(BlogsEntryDisplayScreenlet *)screenlet onBlogEntryError:(NSError *)error {
	screenlet.hidden = YES;
	LiferayLog(error.debugDescription);
}

@end
