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

#import "LRResponseParser.h"

#import "LRBatchSession.h"
#import "LRError.h"

const int LR_HTTP_STATUS_MOVED_PERMANENTLY = 301;
const int LR_HTTP_STATUS_MOVED_TEMPORARILY = 302;
const int LR_HTTP_STATUS_OK = 200;
const int LR_HTTP_STATUS_SEE_OTHER = 303;
const int LR_HTTP_STATUS_TEMPORARY_REDIRECT = 307;
const int LR_HTTP_STATUS_UNAUTHORIZED = 401;

/**
 * @author Bruno Farache
 */
@implementation LRResponseParser

+ (id)parse:(id)data request:(NSURLRequest *)request
		response:(NSHTTPURLResponse *)response error:(NSError **)error {

	*error = [self _checkHTTPError:request response:response];

	if (*error) {
		return nil;
	}

	if ([data isKindOfClass:[NSData class]]) {
		return [self _parse:data error:error];
	}
	else {
		*error = [self _checkPortalException:data];

		if (*error) {
			return nil;
		}

		return data;
	}
}

+ (NSError *)_checkHTTPError:(NSURLRequest *)request
		response:(NSHTTPURLResponse *)response {

	NSError *error;

	NSURL *requestURL = [request URL];
	NSURL *responseURL = [response URL];
	long statusCode = [response statusCode];

	if (statusCode == LR_HTTP_STATUS_UNAUTHORIZED) {
		error = [LRError errorWithCode:LRErrorCodeUnauthorized
			description:@"wrong-credentials"];
	}
	else if (![requestURL isEqual:responseURL] ||
			[self _isRedirect:statusCode]) {

		NSURL *location = [response.allHeaderFields objectForKey:@"Location"];

		if (!location) {
			location = responseURL;
		}

		NSDictionary *userInfo = @{
			NSURLErrorKey: location
		};

		error = [LRError errorWithCode:LRErrorCodeRedirect
			description:@"url-has-moved" userInfo:userInfo];
	}
	else if (statusCode != LR_HTTP_STATUS_OK) {
		error = [LRError errorWithCode:statusCode description:@"http-error"];
	}

	return error;
}

+ (NSError *)_checkPortalException:(id)json {

	if (![json isKindOfClass:[NSDictionary class]]) {
		return nil;
	}

	NSString *exception = [json objectForKey:@"exception"];

	if (!exception) {
		return nil;
	}

	NSString *type = [json objectForKey:@"type"];
	NSError *error;

	if (type) {
		NSDictionary *userInfo = @{
			NSLocalizedFailureReasonErrorKey: exception
		};

		error = [LRError errorWithCode:LRErrorCodePortalException
			description:type userInfo:userInfo];
	}
	else {
		error = [LRError errorWithCode:LRErrorCodePortalException
			description:exception];
	}

	return error;
}

+ (BOOL)_isRedirect:(long)statusCode {
	return ((statusCode == LR_HTTP_STATUS_MOVED_PERMANENTLY) ||
		(statusCode == LR_HTTP_STATUS_MOVED_TEMPORARILY) ||
		(statusCode == LR_HTTP_STATUS_SEE_OTHER) ||
		(statusCode == LR_HTTP_STATUS_TEMPORARY_REDIRECT));
}

+ (id)_parse:(NSData *)data error:(NSError **)error {
	NSError *parseError;
	
	id json = [NSJSONSerialization JSONObjectWithData:data options:0
		error:&parseError];

	if (parseError) {
		NSDictionary *userInfo = @{
			NSUnderlyingErrorKey: parseError
		};

		*error = [LRError errorWithCode:LRErrorCodeParse
			description:@"json-parsing-error" userInfo:userInfo];
	}
	else {
		*error = [self _checkPortalException:json];
	}

	if (*error) {
		return nil;
	}

	return json;
}

@end