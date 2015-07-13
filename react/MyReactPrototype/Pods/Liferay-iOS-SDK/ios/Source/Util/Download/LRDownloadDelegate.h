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

#import "LRBasicAuthentication.h"

typedef void (^LRDownloadProgress)(long long totalBytes, NSError *e);

extern const int LR_DOWNLOAD_ERROR;
extern const int LR_DOWNLOAD_FINISHED;

/**
 * @author Bruno Farache
 */
@interface LRDownloadDelegate : NSObject <NSURLConnectionDelegate>

@property (nonatomic, strong) LRBasicAuthentication *auth;
@property (nonatomic, copy) LRDownloadProgress downloadProgress;
@property (nonatomic, strong) NSOutputStream *outputStream;
@property (nonatomic) long long totalBytes;

- (id)initWithSession:(LRBasicAuthentication *)auth
	outputStream:(NSOutputStream *)outputStream
	downloadProgress:(LRDownloadProgress)downloadProgress;

@end