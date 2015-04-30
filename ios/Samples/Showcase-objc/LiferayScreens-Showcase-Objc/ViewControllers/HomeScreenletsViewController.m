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
#import "HomeScreenletsViewController.h"

@interface HomeScreenletsViewController ()

@property (nonatomic, retain) NSDictionary* data;

@end

@implementation HomeScreenletsViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.data = @{
		@0 : @[@"Auth Module", @"LoginScreenlet", @"SignUpScreenlet", @"ForgotPasswordScreenlet"],
		@1 : @[@"DDL Module", @"DDLListScreenlet", @"DDLFormScreenlet"],
		@2 : @[@"Others", @"AssetListScreenlet", @"UserPortraitScreenlet", @"WebContentDisplayScreenlet"]
	};
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return [self.data count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	NSArray *screenlets = self.data[@(section)];

	return !screenlets ? 0 : screenlets.count - 1;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
	NSArray *screenlets = self.data[@(section)];

	return !screenlets ? nil : screenlets[0];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"default-cell"];

	if (!cell) {
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault
                                   reuseIdentifier:@"default-cell"];
	}

	NSArray *screenlets = self.data[@(indexPath.section)];

	cell.textLabel.text = screenlets[indexPath.row + 1];

	return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	NSArray *screenlets = self.data[@(indexPath.section)];

	[self performSegueWithIdentifier:screenlets[indexPath.row + 1] sender:self];
}


@end
