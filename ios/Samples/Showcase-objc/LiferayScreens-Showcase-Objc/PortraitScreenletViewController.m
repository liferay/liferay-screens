//
//  PortraitScreenletViewController.m
//  LiferayScreens-Showcase-Objc
//
//  Created by jmWork on 14/01/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

#import "PortraitScreenletViewController.h"

@interface PortraitScreenletViewController ()

@property (weak, nonatomic) IBOutlet UITextField *userIdField;
@property (weak, nonatomic) IBOutlet PortraitScreenlet *screenlet;

@end


@implementation PortraitScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

	if ([SessionContext hasSession]) {
		self.userIdField.text = [[SessionContext userAttribute:@"userId"] description];
	}
}

- (IBAction)loadPortrait:(id)sender {
	[self.screenlet loadWithUserId:[self.userIdField.text longLongValue]];
}

@end
