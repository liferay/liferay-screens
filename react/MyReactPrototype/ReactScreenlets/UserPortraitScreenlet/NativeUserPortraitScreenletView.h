//
//  NativeUserPortraitScreenletView.h
//  MyReactPrototype
//
//  Created by Iliyan Peychev on 7/21/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "RCTView.h"
#import "MyReactPrototype-Swift.h"

@class RCTEventDispatcher;

@interface NativeUserPortraitScreenletView : RCTView<UserPortraitScreenletDelegate>

- (instancetype)initWithFrame:(CGRect)frame eventDispatcher:(RCTEventDispatcher *)eventDispatcher;

@end
