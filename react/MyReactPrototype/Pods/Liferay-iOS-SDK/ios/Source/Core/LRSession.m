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

#import "LRHttpUtil.h"
#import "LRUploadUtil.h"
#import "LRValidator.h"

static const int _DEFAULT_CONNECTION_TIMEOUT = 15;
static NSOperationQueue *_DEFAULT_QUEUE;

/**
 * @author Bruno Farache
 */
@implementation LRSession

+ (void)initialize {
	if (self == [LRSession self]) {
		_DEFAULT_QUEUE = [[NSOperationQueue alloc] init];

		[_DEFAULT_QUEUE setName:@"com.liferay.mobile.LRSessionQueue"];
		[_DEFAULT_QUEUE setMaxConcurrentOperationCount:1];
	}
}

- (id)initWithServer:(NSString *)server {
	return [self initWithServer:server callback:nil];
}

- (id)initWithServer:(NSString *)server callback:(id<LRCallback>)callback {
	return [self initWithServer:server authentication:nil callback:callback];
}

- (id)initWithServer:(NSString *)server
		authentication:(id<LRAuthentication>)authentication {

	return [self initWithServer:server authentication:authentication
		callback:nil];
}

- (id)initWithServer:(NSString *)server
		authentication:(id<LRAuthentication>)authentication
		callback:(id<LRCallback>)callback {

	return [self initWithServer:server authentication:authentication
		connectionTimeout:_DEFAULT_CONNECTION_TIMEOUT callback:callback];
}

- (id)initWithServer:(NSString *)server
		authentication:(id<LRAuthentication>)authentication
		connectionTimeout:(int)connectionTimeout
		callback:(id<LRCallback>)callback {

	return [self initWithServer:server authentication:authentication
		connectionTimeout:connectionTimeout callback:callback queue:nil];
}

- (id)initWithServer:(NSString *)server
		authentication:(id<LRAuthentication>)authentication
		connectionTimeout:(int)connectionTimeout
		callback:(id<LRCallback>)callback queue:(NSOperationQueue *)queue {

	self = [super init];

	if (self) {
		self.server = server;
		self.authentication = authentication;
		self.connectionTimeout = connectionTimeout;
		self.callback = callback;
		self.queue = queue;
	}

	return self;
}

- (id)initWithSession:(LRSession *)session {
	return [self initWithServer:session.server
		authentication:session.authentication
		connectionTimeout:session.connectionTimeout
		callback:session.callback];
}

- (id)invoke:(NSDictionary *)command error:(NSError **)error {
	NSArray *json = [LRHttpUtil post:self command:command error:error];

	return [json objectAtIndex:0];
}

- (void)onSuccess:(LRSuccessBlock)success onFailure:(LRFailureBlock)failure {
	id<LRCallback> callback = [[LRBlockCallback alloc] initWithSuccess:success
		failure:failure];

	[self setCallback:callback];
}

- (NSOperationQueue *)queue {
	if (_queue) {
		return _queue;
	}

	return _DEFAULT_QUEUE;
}

- (NSOperation *)upload:(NSDictionary *)command error:(NSError **)error {
	if (!self.callback) {
		[NSException raise:@"Upload Exception"
			format:@"Set a callback to the session before uploading files"];
	}

	return [LRUploadUtil upload:self command:command error:error];
}

@end