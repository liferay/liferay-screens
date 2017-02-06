//
//  RatingScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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
