//
//  NativeUserPortraitScreenletView.m
//  MyReactPrototype
//
//  Created by Iliyan Peychev on 7/21/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "NativeUserPortraitScreenletView.h"
#import "RCTEventDispatcher.h"
#import "UIView+React.h"

@interface NativeUserPortraitScreenletView()

@property (nonatomic, weak) RCTEventDispatcher *eventDispatcher;
@property (nonatomic, weak) UserPortraitScreenlet *screenlet;
@property (nonatomic) int uId;

@end


@implementation NativeUserPortraitScreenletView



- (instancetype)initWithFrame:(CGRect)frame eventDispatcher:(RCTEventDispatcher *)eventDispatcher {
  if (self = [super initWithFrame:frame]) {
    self.eventDispatcher = eventDispatcher;
    
    self.userId = 0;
    
    UserPortraitScreenlet *screenlet = [[UserPortraitScreenlet alloc] initWithFrame:frame];
    screenlet.delegate = self;
    [self addSubview:screenlet];
    self.screenlet = screenlet;
  }
  return self;
}

- (void)setThemeName:(NSString *)themeName {
  self.screenlet.themeName = themeName;

  if (self.uId != 0) {
    [self.screenlet create];
    [self.screenlet loadWithUserId:self.uId];
  }
}

- (void)setUserId:(int)userId {
  self.uId = userId;

  if (self.screenlet.themeName != nil) {
    [self.screenlet create];
    [self.screenlet loadWithUserId:self.uId];
  }
}


- (void)layoutSubviews
{
  [super layoutSubviews];
  self.screenlet.frame = self.bounds;
}


- (UIImage *)screenlet:(UserPortraitScreenlet *)screenlet onUserPortraitResponseImage:(UIImage *)image {
  
  NSDictionary *event = @{
    @"target": self.reactTag,
    @"image": NSStringFromCGSize([image size])
  };
  
  [self.eventDispatcher sendInputEventWithName:@"portraitLoaded" body:event];

  return image;
}

- (void)screenlet:(UserPortraitScreenlet * __nonnull)screenlet onUserPortraitError:(NSError * __nonnull)error {
  NSDictionary *event = @{
    @"target": self.reactTag,
    @"error_msg": [error localizedDescription]
  };
  
  [self.eventDispatcher sendInputEventWithName:@"portraitLoaded" body:event];

}




@end
