//
//  MyCompoView.m
//  MyReactCompo
//
//  Created by jmWork on 10/07/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "NativeLoginScreenletView.h"
#import "RCTEventDispatcher.h"
#import "UIView+React.h"


@interface NativeLoginScreenletView()

@property (nonatomic, strong) RCTEventDispatcher *eventDispatcher;

@end


@implementation NativeLoginScreenletView

- (instancetype)initWithFrame:(CGRect)frame eventDispatcher:(RCTEventDispatcher *)eventDispatcher {
	if (self = [super initWithFrame:frame]) {
		self.eventDispatcher = eventDispatcher;

		LoginScreenlet *screenlet = [[LoginScreenlet alloc] initWithFrame:frame];
		screenlet.delegate = self;
		[self addSubview:screenlet];
	}
	return self;
}

- (void)screenlet:(BaseScreenlet * __nonnull)screenlet onLoginResponseUserAttributes:(NSDictionary * __nonnull)attributes {

		NSDictionary *event = @{
	    @"target": self.reactTag,
			@"attributes": attributes
	  };

		// events??
		// https://github.com/facebook/react-native/blob/master/React/Modules/RCTUIManager.m#L1566
		[self.eventDispatcher sendInputEventWithName:@"topSubmitEditing" body:event];
}

- (void)screenlet:(BaseScreenlet * __nonnull)screenlet onLoginError:(NSError * __nonnull)error {
}


@end
