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

#import "LRCommentmanagerjsonwsService_v70.h"

/**
 * @author Bruno Farache
 */
@implementation LRCommentmanagerjsonwsService_v70

- (void)deleteCommentWithCommentId:(long long)commentId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"commentId": @(commentId)
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/delete-comment": _params};

	[self.session invoke:_command error:error];
}

- (NSNumber *)getCommentsCountWithGroupId:(long long)groupId className:(NSString *)className classPK:(long long)classPK error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"className": [self checkNull: className],
		@"classPK": @(classPK)
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/get-comments-count": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateCommentWithGroupId:(long long)groupId className:(NSString *)className classPK:(long long)classPK commentId:(long long)commentId body:(NSString *)body error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"className": [self checkNull: className],
		@"classPK": @(classPK),
		@"commentId": @(commentId),
		@"body": [self checkNull: body]
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/update-comment": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSArray *)getCommentsWithGroupId:(long long)groupId className:(NSString *)className classPK:(long long)classPK start:(int)start end:(int)end error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"className": [self checkNull: className],
		@"classPK": @(classPK),
		@"start": @(start),
		@"end": @(end)
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/get-comments": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSDictionary *)addCommentWithGroupId:(long long)groupId className:(NSString *)className classPK:(long long)classPK body:(NSString *)body error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"className": [self checkNull: className],
		@"classPK": @(classPK),
		@"body": [self checkNull: body]
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/add-comment": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)getCommentWithGroupId:(long long)groupId commentId:(long long)commentId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"commentId": @(commentId)
	}];

	NSDictionary *_command = @{@"/screens.commentmanagerjsonws/get-comment": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

@end