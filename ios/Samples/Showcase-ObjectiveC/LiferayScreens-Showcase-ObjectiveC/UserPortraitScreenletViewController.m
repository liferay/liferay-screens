//
//  UserPortraitScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "NSString+Utils.h"
#import "UIImage+GrayScale.h"
#import "LiferayLogger.h"
#import "UserPortraitScreenletViewController.h"

@interface UserPortraitScreenletViewController () <UserPortraitScreenletDelegate>

@property (weak, nonatomic) IBOutlet UITextField *userIdField;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenletWithDelegate;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *editableScreenlet;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@end

@implementation UserPortraitScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenletWithDelegate.delegate = self;
    self.editableScreenlet.presentingViewController = self;

    self.userIdField.text = [NSString
    		stringWithFormat:@"%lld",SessionContext.currentContext.user.userId];
}

- (IBAction)loadPortrait:(id)sender {
    if (self.userIdField.text) {
        NSString *userIdFieldText = self.userIdField.text;
        int64_t companyId = LiferayServerContext.companyId;

        if ([userIdFieldText isNumeric]) {
            [self.screenlet loadWithUserId:userIdFieldText.longLongValue];
            [self.screenletWithDelegate loadWithUserId:userIdFieldText.longLongValue];
            [self.editableScreenlet loadWithUserId:userIdFieldText.longLongValue];
        }
        else if ([userIdFieldText containsString:@"@"]) {
            [self.screenlet loadWithCompanyId:companyId emailAddress:userIdFieldText];
            [self.screenletWithDelegate loadWithCompanyId:companyId emailAddress:userIdFieldText];
            [self.editableScreenlet loadWithCompanyId:companyId emailAddress:userIdFieldText];
        }
        else {
            [self.screenlet loadWithCompanyId:companyId screenName:userIdFieldText];
            [self.screenletWithDelegate loadWithCompanyId:companyId screenName:userIdFieldText];
            [self.editableScreenlet loadWithCompanyId:companyId screenName:userIdFieldText];
        }
    }
    else {
        [self.screenlet loadLoggedUserPortrait];
        [self.screenletWithDelegate loadLoggedUserPortrait];
        [self.editableScreenlet loadLoggedUserPortrait];
    }
}

- (UIImage *)screenlet:(UserPortraitScreenlet *)screenlet
		onUserPortraitResponseImage:(UIImage *)image {
	LiferayLog(image);

    return [image grayScaleImage];
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet
		onUserPortraitUploaded:(NSDictionary<NSString *,id> *)attributes {

	LiferayLog(attributes);
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet onUserPortraitUploadError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet onUserPortraitError:(NSError *)error {
    LiferayLog(error);
}

@end
