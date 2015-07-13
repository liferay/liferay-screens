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

#import "LRLayoutService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRLayoutService_v62

- (NSDictionary *)addLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId name:(NSString *)name title:(NSString *)title description:(NSString *)description type:(NSString *)type hidden:(BOOL)hidden friendlyURL:(NSString *)friendlyURL serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId),
		@"name": name,
		@"title": title,
		@"description": description,
		@"type": type,
		@"hidden": @(hidden),
		@"friendlyURL": friendlyURL,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/add-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)addLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId localeNamesMap:(NSDictionary *)localeNamesMap localeTitlesMap:(NSDictionary *)localeTitlesMap descriptionMap:(NSDictionary *)descriptionMap keywordsMap:(NSDictionary *)keywordsMap robotsMap:(NSDictionary *)robotsMap type:(NSString *)type hidden:(BOOL)hidden friendlyURL:(NSString *)friendlyURL serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId),
		@"localeNamesMap": localeNamesMap,
		@"localeTitlesMap": localeTitlesMap,
		@"descriptionMap": descriptionMap,
		@"keywordsMap": keywordsMap,
		@"robotsMap": robotsMap,
		@"type": type,
		@"hidden": @(hidden),
		@"friendlyURL": friendlyURL,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/add-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)addLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId localeNamesMap:(NSDictionary *)localeNamesMap localeTitlesMap:(NSDictionary *)localeTitlesMap descriptionMap:(NSDictionary *)descriptionMap keywordsMap:(NSDictionary *)keywordsMap robotsMap:(NSDictionary *)robotsMap type:(NSString *)type typeSettings:(NSString *)typeSettings hidden:(BOOL)hidden friendlyURLMap:(NSDictionary *)friendlyURLMap serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId),
		@"localeNamesMap": localeNamesMap,
		@"localeTitlesMap": localeTitlesMap,
		@"descriptionMap": descriptionMap,
		@"keywordsMap": keywordsMap,
		@"robotsMap": robotsMap,
		@"type": type,
		@"typeSettings": typeSettings,
		@"hidden": @(hidden),
		@"friendlyURLMap": friendlyURLMap,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/add-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (void)deleteLayoutWithPlid:(long long)plid serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/delete-layout": _params};

	[self.session invoke:_command error:error];
}

- (void)deleteLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/delete-layout": _params};

	[self.session invoke:_command error:error];
}

- (void)deleteTempFileEntryWithGroupId:(long long)groupId fileName:(NSString *)fileName tempFolderName:(NSString *)tempFolderName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"fileName": fileName,
		@"tempFolderName": tempFolderName
	}];

	NSDictionary *_command = @{@"/layout/delete-temp-file-entry": _params};

	[self.session invoke:_command error:error];
}

- (NSArray *)exportLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)exportLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutIds:(NSArray *)layoutIds parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutIds": layoutIds,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSDictionary *)exportLayoutsAsFileWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutIds:(NSArray *)layoutIds parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutIds": layoutIds,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-layouts-as-file": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSNumber *)exportLayoutsAsFileInBackgroundWithTaskName:(NSString *)taskName groupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutIds:(NSArray *)layoutIds parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate fileName:(NSString *)fileName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutIds": layoutIds,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate),
		@"fileName": fileName
	}];

	NSDictionary *_command = @{@"/layout/export-layouts-as-file-in-background": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSArray *)exportPortletInfoWithCompanyId:(long long)companyId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)exportPortletInfoWithPlid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSDictionary *)exportPortletInfoAsFileWithPortletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info-as-file": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)exportPortletInfoAsFileWithPlid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate)
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info-as-file": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSNumber *)exportPortletInfoAsFileInBackgroundWithTaskName:(NSString *)taskName portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate fileName:(NSString *)fileName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate),
		@"fileName": fileName
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info-as-file-in-background": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSNumber *)exportPortletInfoAsFileInBackgroundWithTaskName:(NSString *)taskName plid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap startDate:(long long)startDate endDate:(long long)endDate fileName:(NSString *)fileName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"startDate": @(startDate),
		@"endDate": @(endDate),
		@"fileName": fileName
	}];

	NSDictionary *_command = @{@"/layout/export-portlet-info-as-file-in-background": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSArray *)getAncestorLayoutsWithPlid:(long long)plid error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid)
	}];

	NSDictionary *_command = @{@"/layout/get-ancestor-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSNumber *)getDefaultPlidWithGroupId:(long long)groupId scopeGroupId:(long long)scopeGroupId portletId:(NSString *)portletId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"scopeGroupId": @(scopeGroupId),
		@"portletId": portletId
	}];

	NSDictionary *_command = @{@"/layout/get-default-plid": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSNumber *)getDefaultPlidWithGroupId:(long long)groupId scopeGroupId:(long long)scopeGroupId privateLayout:(BOOL)privateLayout portletId:(NSString *)portletId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"scopeGroupId": @(scopeGroupId),
		@"privateLayout": @(privateLayout),
		@"portletId": portletId
	}];

	NSDictionary *_command = @{@"/layout/get-default-plid": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSDictionary *)getLayoutByUuidAndGroupIdWithUuid:(NSString *)uuid groupId:(long long)groupId privateLayout:(BOOL)privateLayout error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"uuid": uuid,
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout)
	}];

	NSDictionary *_command = @{@"/layout/get-layout-by-uuid-and-group-id": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSString *)getLayoutNameWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId languageId:(NSString *)languageId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"languageId": languageId
	}];

	NSDictionary *_command = @{@"/layout/get-layout-name": _params};

	return (NSString *)[self.session invoke:_command error:error];
}

- (NSArray *)getLayoutReferencesWithCompanyId:(long long)companyId portletId:(NSString *)portletId preferencesKey:(NSString *)preferencesKey preferencesValue:(NSString *)preferencesValue error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"portletId": portletId,
		@"preferencesKey": preferencesKey,
		@"preferencesValue": preferencesValue
	}];

	NSDictionary *_command = @{@"/layout/get-layout-references": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)getLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout)
	}];

	NSDictionary *_command = @{@"/layout/get-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)getLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId)
	}];

	NSDictionary *_command = @{@"/layout/get-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)getLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId incomplete:(BOOL)incomplete start:(int)start end:(int)end error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId),
		@"incomplete": @(incomplete),
		@"start": @(start),
		@"end": @(end)
	}];

	NSDictionary *_command = @{@"/layout/get-layouts": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSNumber *)getLayoutsCountWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId)
	}];

	NSDictionary *_command = @{@"/layout/get-layouts-count": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSArray *)getTempFileEntryNamesWithGroupId:(long long)groupId tempFolderName:(NSString *)tempFolderName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"tempFolderName": tempFolderName
	}];

	NSDictionary *_command = @{@"/layout/get-temp-file-entry-names": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (void)importLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parameterMap:(NSDictionary *)parameterMap bytes:(NSData *)bytes error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parameterMap": parameterMap,
		@"bytes": [self toString:bytes]
	}];

	NSDictionary *_command = @{@"/layout/import-layouts": _params};

	[self.session invoke:_command error:error];
}

- (NSOperation *)importLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-layouts": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)importLayoutsInBackgroundWithTaskName:(NSString *)taskName groupId:(long long)groupId privateLayout:(BOOL)privateLayout parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-layouts-in-background": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)importPortletInfoWithPortletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-portlet-info": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)importPortletInfoWithPlid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-portlet-info": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)importPortletInfoInBackgroundWithTaskName:(NSString *)taskName portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-portlet-info-in-background": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)importPortletInfoInBackgroundWithTaskName:(NSString *)taskName plid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"taskName": taskName,
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/import-portlet-info-in-background": _params};

	return [self.session upload:_command error:error];
}

- (void)schedulePublishToLiveWithSourceGroupId:(long long)sourceGroupId targetGroupId:(long long)targetGroupId privateLayout:(BOOL)privateLayout layoutIdMap:(NSDictionary *)layoutIdMap parameterMap:(NSDictionary *)parameterMap scope:(NSString *)scope startDate:(long long)startDate endDate:(long long)endDate groupName:(NSString *)groupName cronText:(NSString *)cronText schedulerStartDate:(long long)schedulerStartDate schedulerEndDate:(long long)schedulerEndDate description:(NSString *)description error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"sourceGroupId": @(sourceGroupId),
		@"targetGroupId": @(targetGroupId),
		@"privateLayout": @(privateLayout),
		@"layoutIdMap": layoutIdMap,
		@"parameterMap": parameterMap,
		@"scope": scope,
		@"startDate": @(startDate),
		@"endDate": @(endDate),
		@"groupName": groupName,
		@"cronText": cronText,
		@"schedulerStartDate": @(schedulerStartDate),
		@"schedulerEndDate": @(schedulerEndDate),
		@"description": description
	}];

	NSDictionary *_command = @{@"/layout/schedule-publish-to-live": _params};

	[self.session invoke:_command error:error];
}

- (void)schedulePublishToRemoteWithSourceGroupId:(long long)sourceGroupId privateLayout:(BOOL)privateLayout layoutIdMap:(NSDictionary *)layoutIdMap parameterMap:(NSDictionary *)parameterMap remoteAddress:(NSString *)remoteAddress remotePort:(int)remotePort remotePathContext:(NSString *)remotePathContext secureConnection:(BOOL)secureConnection remoteGroupId:(long long)remoteGroupId remotePrivateLayout:(BOOL)remotePrivateLayout startDate:(long long)startDate endDate:(long long)endDate groupName:(NSString *)groupName cronText:(NSString *)cronText schedulerStartDate:(long long)schedulerStartDate schedulerEndDate:(long long)schedulerEndDate description:(NSString *)description error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"sourceGroupId": @(sourceGroupId),
		@"privateLayout": @(privateLayout),
		@"layoutIdMap": layoutIdMap,
		@"parameterMap": parameterMap,
		@"remoteAddress": remoteAddress,
		@"remotePort": @(remotePort),
		@"remotePathContext": remotePathContext,
		@"secureConnection": @(secureConnection),
		@"remoteGroupId": @(remoteGroupId),
		@"remotePrivateLayout": @(remotePrivateLayout),
		@"startDate": @(startDate),
		@"endDate": @(endDate),
		@"groupName": groupName,
		@"cronText": cronText,
		@"schedulerStartDate": @(schedulerStartDate),
		@"schedulerEndDate": @(schedulerEndDate),
		@"description": description
	}];

	NSDictionary *_command = @{@"/layout/schedule-publish-to-remote": _params};

	[self.session invoke:_command error:error];
}

- (void)setLayoutsWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parentLayoutId:(long long)parentLayoutId layoutIds:(NSArray *)layoutIds serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parentLayoutId": @(parentLayoutId),
		@"layoutIds": layoutIds,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/set-layouts": _params};

	[self.session invoke:_command error:error];
}

- (void)unschedulePublishToLiveWithGroupId:(long long)groupId jobName:(NSString *)jobName groupName:(NSString *)groupName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"jobName": jobName,
		@"groupName": groupName
	}];

	NSDictionary *_command = @{@"/layout/unschedule-publish-to-live": _params};

	[self.session invoke:_command error:error];
}

- (void)unschedulePublishToRemoteWithGroupId:(long long)groupId jobName:(NSString *)jobName groupName:(NSString *)groupName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"jobName": jobName,
		@"groupName": groupName
	}];

	NSDictionary *_command = @{@"/layout/unschedule-publish-to-remote": _params};

	[self.session invoke:_command error:error];
}

- (NSDictionary *)updateLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId parentLayoutId:(long long)parentLayoutId localeNamesMap:(NSDictionary *)localeNamesMap localeTitlesMap:(NSDictionary *)localeTitlesMap descriptionMap:(NSDictionary *)descriptionMap keywordsMap:(NSDictionary *)keywordsMap robotsMap:(NSDictionary *)robotsMap type:(NSString *)type hidden:(BOOL)hidden friendlyURL:(NSString *)friendlyURL iconImage:(LRJSONObjectWrapper *)iconImage iconBytes:(NSData *)iconBytes serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"parentLayoutId": @(parentLayoutId),
		@"localeNamesMap": localeNamesMap,
		@"localeTitlesMap": localeTitlesMap,
		@"descriptionMap": descriptionMap,
		@"keywordsMap": keywordsMap,
		@"robotsMap": robotsMap,
		@"type": type,
		@"hidden": @(hidden),
		@"friendlyURL": friendlyURL,
		@"iconBytes": [self toString:iconBytes],
	}];

	[self mangleWrapperWithParams:_params name:@"iconImage" className:@"java.lang.Boolean" wrapper:iconImage];
	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/update-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId parentLayoutId:(long long)parentLayoutId localeNamesMap:(NSDictionary *)localeNamesMap localeTitlesMap:(NSDictionary *)localeTitlesMap descriptionMap:(NSDictionary *)descriptionMap keywordsMap:(NSDictionary *)keywordsMap robotsMap:(NSDictionary *)robotsMap type:(NSString *)type hidden:(BOOL)hidden friendlyURLMap:(NSDictionary *)friendlyURLMap iconImage:(LRJSONObjectWrapper *)iconImage iconBytes:(NSData *)iconBytes serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"parentLayoutId": @(parentLayoutId),
		@"localeNamesMap": localeNamesMap,
		@"localeTitlesMap": localeTitlesMap,
		@"descriptionMap": descriptionMap,
		@"keywordsMap": keywordsMap,
		@"robotsMap": robotsMap,
		@"type": type,
		@"hidden": @(hidden),
		@"friendlyURLMap": friendlyURLMap,
		@"iconBytes": [self toString:iconBytes],
	}];

	[self mangleWrapperWithParams:_params name:@"iconImage" className:@"java.lang.Boolean" wrapper:iconImage];
	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/layout/update-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateLayoutWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId typeSettings:(NSString *)typeSettings error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"typeSettings": typeSettings
	}];

	NSDictionary *_command = @{@"/layout/update-layout": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateLookAndFeelWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId themeId:(NSString *)themeId colorSchemeId:(NSString *)colorSchemeId css:(NSString *)css wapTheme:(BOOL)wapTheme error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"themeId": themeId,
		@"colorSchemeId": colorSchemeId,
		@"css": css,
		@"wapTheme": @(wapTheme)
	}];

	NSDictionary *_command = @{@"/layout/update-look-and-feel": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateNameWithPlid:(long long)plid name:(NSString *)name languageId:(NSString *)languageId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"name": name,
		@"languageId": languageId
	}];

	NSDictionary *_command = @{@"/layout/update-name": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateNameWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId name:(NSString *)name languageId:(NSString *)languageId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"name": name,
		@"languageId": languageId
	}];

	NSDictionary *_command = @{@"/layout/update-name": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateParentLayoutIdWithPlid:(long long)plid parentPlid:(long long)parentPlid error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"parentPlid": @(parentPlid)
	}];

	NSDictionary *_command = @{@"/layout/update-parent-layout-id": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateParentLayoutIdWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId parentLayoutId:(long long)parentLayoutId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"parentLayoutId": @(parentLayoutId)
	}];

	NSDictionary *_command = @{@"/layout/update-parent-layout-id": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateParentLayoutIdAndPriorityWithPlid:(long long)plid parentPlid:(long long)parentPlid priority:(int)priority error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"parentPlid": @(parentPlid),
		@"priority": @(priority)
	}];

	NSDictionary *_command = @{@"/layout/update-parent-layout-id-and-priority": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updatePriorityWithPlid:(long long)plid priority:(int)priority error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"priority": @(priority)
	}];

	NSDictionary *_command = @{@"/layout/update-priority": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updatePriorityWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId priority:(int)priority error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"priority": @(priority)
	}];

	NSDictionary *_command = @{@"/layout/update-priority": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updatePriorityWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout layoutId:(long long)layoutId nextLayoutId:(long long)nextLayoutId previousLayoutId:(long long)previousLayoutId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"layoutId": @(layoutId),
		@"nextLayoutId": @(nextLayoutId),
		@"previousLayoutId": @(previousLayoutId)
	}];

	NSDictionary *_command = @{@"/layout/update-priority": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSOperation *)validateImportLayoutsFileWithGroupId:(long long)groupId privateLayout:(BOOL)privateLayout parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"privateLayout": @(privateLayout),
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/validate-import-layouts-file": _params};

	return [self.session upload:_command error:error];
}

- (NSOperation *)validateImportPortletInfoWithPlid:(long long)plid groupId:(long long)groupId portletId:(NSString *)portletId parameterMap:(NSDictionary *)parameterMap file:(LRUploadData *)file error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"plid": @(plid),
		@"groupId": @(groupId),
		@"portletId": portletId,
		@"parameterMap": parameterMap,
		@"file": file
	}];

	NSDictionary *_command = @{@"/layout/validate-import-portlet-info": _params};

	return [self.session upload:_command error:error];
}

@end