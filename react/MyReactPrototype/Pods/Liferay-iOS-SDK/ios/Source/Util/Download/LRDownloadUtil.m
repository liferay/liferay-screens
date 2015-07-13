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

#import "LRDownloadUtil.h"

#import "LRHttpUtil.h"

/**
 * @author Bruno Farache
 */
@implementation LRDownloadUtil

+ (NSURLConnection *)downloadWithSession:(LRSession *)session
		URL:(NSString *)URL outputStream:(NSOutputStream *)outputStream
		downloadProgress:(LRDownloadProgress)downloadProgress {

	LRBasicAuthentication *auth = [LRDownloadUtil _getAuthentication:session];

	LRDownloadDelegate *delegate = [[LRDownloadDelegate alloc]
		initWithSession:auth outputStream:outputStream
		downloadProgress:downloadProgress];

	NSMutableURLRequest *request = [LRHttpUtil getRequestWithSession:session
		URL:[NSURL URLWithString:URL]];

	return [[NSURLConnection alloc] initWithRequest:request delegate:delegate
		startImmediately:YES];
}

+ (NSURLSessionDownloadTask *)downloadFileWithSession:(LRSession *)session
		groupFriendlyURL:(NSString *)groupFriendlyURL
		folderPath:(NSString *)folderPath fileTitle:(NSString *)fileTitle
		outputStream:(NSOutputStream *)outputStream
		downloadProgress:(LRDownloadProgress)downloadProgress {

	NSString *URL = [LRDownloadUtil getDownloadURLWithSession:session
		groupFriendlyURL:groupFriendlyURL folderPath:folderPath
		fileTitle:fileTitle];

	return [LRDownloadUtil downloadWithSession:session URL:URL
		outputStream:outputStream downloadProgress:downloadProgress];
}

+ (NSString *)getDownloadURLWithSession:(LRSession *)session
		groupFriendlyURL:(NSString *)groupFriendlyURL
		folderPath:(NSString *)folderPath fileTitle:(NSString *)fileTitle {

	NSString *webdavPath = [NSString stringWithFormat:@"%@/%@", folderPath,
		fileTitle];

	NSString *webdavPathEscaped = [LRHttpUtil
		escape:webdavPath include:@":?#[]@!$ &'()*+,;=\"<>%{}|\\^~`"
		ignore:@"/"];

	return [NSString
		stringWithFormat:@"%@/webdav%@/document_library%@",
		session.server, groupFriendlyURL, webdavPathEscaped];
}

+ (LRBasicAuthentication *)_getAuthentication:(LRSession *)session {
	id<LRAuthentication> authentication = session.authentication;

	if (!authentication) {
		[NSException raise:@"" format:@"Session authentication can't be null"];
	}

	if (![authentication isKindOfClass:[LRBasicAuthentication class]]) {
		[NSException raise:@"" format:@"Can't download if authentication " \
			"implementation is not BasicAuthentication"];
	}

	return (LRBasicAuthentication *)authentication;
}

@end