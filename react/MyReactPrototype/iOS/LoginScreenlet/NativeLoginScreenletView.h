//
//  MyCompoView.h
//  MyReactCompo
//
//  Created by jmWork on 10/07/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "RCTView.h"
#import "MyReactPrototype-Swift.h"


@class RCTEventDispatcher;

@interface NativeLoginScreenletView : RCTView<LoginScreenletDelegate>

- (instancetype)initWithFrame:(CGRect)frame eventDispatcher:(RCTEventDispatcher *)eventDispatcher;

@end
