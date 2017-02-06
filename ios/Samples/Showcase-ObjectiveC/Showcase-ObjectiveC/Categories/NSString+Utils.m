//
//  NSString+Utils.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import "NSString+Utils.h"

@implementation NSString (Utils)

- (BOOL)isNumeric {
    NSScanner *scanner = [NSScanner scannerWithString:self];
    return [scanner scanInteger:NULL] && [scanner isAtEnd];
}

@end
