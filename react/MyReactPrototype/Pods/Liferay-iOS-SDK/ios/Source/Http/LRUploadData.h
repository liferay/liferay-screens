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

#import "LRProgressDelegate.h"

/**
 * @author Bruno Farache
 */
@interface LRUploadData : NSObject

@property (nonatomic, strong) NSData *data;
@property (nonatomic, strong) NSString *fileName;
@property (nonatomic, strong) NSInputStream *inputStream;
@property (nonatomic) int64_t length;
@property (nonatomic, strong) NSString *mimeType;
@property (nonatomic, strong) NSString *parameterName;
@property (nonatomic, strong) id<LRProgressDelegate> progressDelegate;

- (id)initWithData:(NSData *)data fileName:(NSString *)fileName
	mimeType:(NSString *)mimeType;

- (id)initWithInputStream:(NSInputStream *)inputStream length:(int64_t)length
	fileName:(NSString *)fileName mimeType:(NSString *)mimeType;

@end