/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
#import "UIImage+Themes.h"

@implementation UIImage (Themes)

- (BOOL)isBinaryEquals:(UIImage *)otherImage {
	if (![self isMetadataEquals:otherImage]) {
		return NO;
	}

	return [self.bytesData isEqualToData:otherImage.bytesData];
}

- (BOOL)isMetadataEquals:(UIImage *)otherImage {
	CGImageRef imageRef1 = self.CGImage;
	CGImageRef imageRef2 = otherImage.CGImage;

	size_t width1 = CGImageGetWidth(imageRef1);
	size_t width2 = CGImageGetWidth(imageRef2);
	size_t height1 = CGImageGetHeight(imageRef1);
	size_t height2 = CGImageGetHeight(imageRef1);

	if (width1 != width2 || height1 != height2) {
		return NO;
	}

	size_t bpp1 = CGImageGetBitsPerPixel(imageRef1);
	size_t bpp2 = CGImageGetBitsPerPixel(imageRef2);
	size_t bpc1 = CGImageGetBitsPerComponent(imageRef1);
	size_t bpc2 = CGImageGetBitsPerComponent(imageRef2);

	if (bpp1 != bpp2 || bpc1 != bpc2) {
		return NO;
	}

	CGBitmapInfo info1 = CGImageGetBitmapInfo(imageRef1);
	CGBitmapInfo info2 = CGImageGetBitmapInfo(imageRef2);

	return (info1 == info2);
}

- (NSData *)bytesData {

	// inspired by http://stackoverflow.com/a/6077782

	CGImageRef imageRef = self.CGImage;

	size_t rows = CGImageGetHeight(imageRef);
	size_t bytesPerRow = CGImageGetBytesPerRow(imageRef);

	NSMutableData *data = [[NSMutableData alloc] initWithCapacity:bytesPerRow * rows];

	CGDataProviderRef provider = CGImageGetDataProvider(imageRef);

	[data appendData:CFBridgingRelease(CGDataProviderCopyData(provider))];

	// It seems that 100% transparent white pixels are stored sometimes as 100% transparent black.
	// We have to normalize them
	
	return [UIImage normalizeTransparentPixels:data];
}

+ (NSData *)normalizeTransparentPixels:(NSMutableData *)data {
	uint8_t* bytes = data.mutableBytes;
	uint8_t* endMarker = bytes + data.length;

	while (bytes < endMarker) {
		uint8_t* pixel = bytes;

		uint8_t r = *pixel;
		uint8_t g = *(++pixel);
		uint8_t b = *(++pixel);
		uint8_t a = *(++pixel);

		if (r == 0xff && g == 0xff && b == 0xff && a == 0x00) {
			memset(bytes, 0x00, 4);
		}

		bytes += 4;
	}

	return data;
}

@end
