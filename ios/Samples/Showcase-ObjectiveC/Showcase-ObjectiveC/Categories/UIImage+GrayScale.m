//
//  UIImage+UIImage_GrayScale.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import "UIImage+GrayScale.h"

@implementation UIImage (GrayScale)

- (UIImage *)grayScaleImage {
    CGRect imageRect = CGRectMake(0, 0, self.size.width, self.size.height);
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceGray();

    CGContextRef context = CGBitmapContextCreate(nil, self.size.width,
    		self.size.height, 8, 0, colorSpace, kCGImageAlphaNone);

    CGContextDrawImage(context, imageRect, [self CGImage]);
    CGImageRef imageRef = CGBitmapContextCreateImage(context);

    UIImage *newImage = [UIImage imageWithCGImage:imageRef];

    CGColorSpaceRelease(colorSpace);
    CGContextRelease(context);
    CFRelease(imageRef);

    return newImage;
}

@end
