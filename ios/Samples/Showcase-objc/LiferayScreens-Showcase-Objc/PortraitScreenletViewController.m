//
//  PortraitScreenletViewController.m
//  LiferayScreens-Showcase-Objc
//
//  Created by jmWork on 14/01/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

#import "PortraitScreenletViewController.h"
#import "UIImage+Grayscale.h"

@interface PortraitScreenletViewController ()

@property (weak, nonatomic) IBOutlet UITextField *userIdField;
@property (weak, nonatomic) IBOutlet PortraitScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet PortraitScreenlet *screenletWithDelegate;

@end


@implementation PortraitScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

	if ([SessionContext hasSession]) {
		self.userIdField.text = [[SessionContext userAttribute:@"userId"] description];
	}

	self.screenletWithDelegate.delegate = self;
}

- (IBAction)loadPortrait:(id)sender {
	[self.screenlet loadWithUserId:[self.userIdField.text longLongValue]];
	[self.screenletWithDelegate loadWithUserId:[self.userIdField.text longLongValue]];
}

- (UIImage *)onPortraitResponse:(UIImage *)image {
	NSLog(@"DELEGATE onPortraitResponse -> (%d x %d)", (int)image.size.width, (int)image.size.height);

	UIImage *converted = [image convertToGrayscale];
	return converted ? converted : image;
}

- (void)onPortraitError:(NSError *)error {
	NSLog(@"DELEGATE onPortraitError -> %@", error);
}


@end
