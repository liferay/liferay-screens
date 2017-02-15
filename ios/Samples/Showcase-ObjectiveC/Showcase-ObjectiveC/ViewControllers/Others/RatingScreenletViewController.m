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
#import "RatingScreenletViewController.h"

@interface RatingScreenletViewController () <RatingScreenletDelegate>

@property (weak, nonatomic) IBOutlet RatingScreenlet *screenlet;

@end

@implementation RatingScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.entryId = [LiferayServerContext longPropertyForKey:@"ratingThumbsEntryId"];
}

- (IBAction)segmentedControlChanged:(UISegmentedControl *)sender {
	switch (sender.selectedSegmentIndex) {
		case 1:
			self.screenlet.entryId = [LiferayServerContext longPropertyForKey:@"ratingLikeEntryId"];
			self.screenlet.themeName = @"default-like";
			self.screenlet.ratingsGroupCount = 1;
			break;
		case 2:
			self.screenlet.entryId = [LiferayServerContext longPropertyForKey:@"ratingStarsEntryId"];
			self.screenlet.themeName = @"default-stars";
			self.screenlet.ratingsGroupCount = 5;
			break;
		case 3:
			self.screenlet.entryId = [LiferayServerContext longPropertyForKey:@"ratingEmojisEntryId"];
			self.screenlet.themeName = @"default-emojis";
			self.screenlet.ratingsGroupCount = 5;
			break;

		default:
			self.screenlet.entryId = [LiferayServerContext longPropertyForKey:@"ratingThumbsEntryId"];
			self.screenlet.themeName = @"default-thumbs";
			self.screenlet.ratingsGroupCount = 2;
			break;
	}

	[self.screenlet loadRatings];
}

- (IBAction)switchChange:(UISwitch *)sender {
	self.screenlet.editable = [sender isOn];
}

#pragma mark RatingScreenletDelegate

- (void)screenlet:(RatingScreenlet *)screenlet onRatingRetrieve:(RatingEntry *)rating {
	LiferayLog(rating);
}

- (void)screenlet:(RatingScreenlet *)screenlet onRatingError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(RatingScreenlet *)screenlet onRatingDeleted:(RatingEntry *)rating {
	LiferayLog(rating);
}

- (void)screenlet:(RatingScreenlet *)screenlet onRatingUpdated:(RatingEntry *)rating {
	LiferayLog(rating);
}

@end
