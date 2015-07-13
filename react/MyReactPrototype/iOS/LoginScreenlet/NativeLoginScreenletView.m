//
//  MyCompoView.m
//  MyReactCompo
//
//  Created by jmWork on 10/07/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "NativeLoginScreenletView.h"
#import "MBProgressHUD.h"

@interface NativeLoginScreenletView()

@property (weak, nonatomic) IBOutlet UITextField *usernameField;
@property (weak, nonatomic) IBOutlet UITextField *passwordField;

@end


@implementation NativeLoginScreenletView

- (instancetype)init {
	if (self = [super init]) {
	}

	return self;
}

- (void) setUsername:(NSString *)value {
	self.usernameField.text = value;
}

- (void) setPassword:(NSString *)value {
	self.passwordField.text = value;
}

- (IBAction)loginButtonAction:(id)sender {
	[MBProgressHUD showHUDAddedTo:self animated:YES];

	dispatch_after(dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * 2), dispatch_get_main_queue(), ^(void){
		// FAKE login!
		if (self.onLoginSuccess) {
			self.onLoginSuccess();
		}

		[MBProgressHUD hideHUDForView:self animated:YES];
	});
}


@end
