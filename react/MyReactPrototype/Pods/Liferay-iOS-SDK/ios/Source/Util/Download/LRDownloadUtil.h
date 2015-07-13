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

#import "LRSession.h"

#import "LRDownloadDelegate.h"

/**
 * @author Bruno Farache
 */
@interface LRDownloadUtil : NSObject

+ (NSURLSessionDownloadTask *)downloadWithSession:(LRSession *)session
	URL:(NSString *)URL outputStream:(NSOutputStream *)os
	downloadProgress:(LRDownloadProgress)downloadProgress;

+ (NSURLSessionDownloadTask *)downloadFileWithSession:(LRSession *)session
	groupFriendlyURL:(NSString *)groupFriendlyURL
	folderPath:(NSString *)folderPath fileTitle:(NSString *)fileTitle
	outputStream:(NSOutputStream *)outputStream
	downloadProgress:(LRDownloadProgress)downloadProgress;

+ (NSString *)getDownloadURLWithSession:(LRSession *)session
	groupFriendlyURL:(NSString *)groupFriendlyURL
	folderPath:(NSString *)folderPath fileTitle:(NSString *)fileTitle;

@end