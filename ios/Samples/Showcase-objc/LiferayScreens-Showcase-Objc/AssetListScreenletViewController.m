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
#import "AssetListScreenletViewController.h"
#import "AssetListScreenletInterop.h"

@interface AssetListScreenletViewController ()

@property (nonatomic, weak) IBOutlet AssetListScreenlet* screenlet;
@property (nonatomic, weak) IBOutlet UIPickerView* picker;

@property (nonatomic, assign) NSInteger selectedAsset;

@property (nonatomic, strong) NSArray *pickerData;

@end


@implementation AssetListScreenletViewController

- (IBAction)changeAssetAction:(id)sender {
	[self showPicker:NO animated:YES];
	self.screenlet.classNameId = self.selectedAsset;
	[self.screenlet loadList];
}

- (IBAction)openAssetPickerAction:(id)sender {
	[self showPicker:YES animated:YES];
}

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;

	NSMutableArray *data = [[NSMutableArray alloc] init];

	[data addObject:@[@"Group", @(AssetClassNameIdGroup)]];
	[data addObject:@[@"Layout", @(AssetClassNameIdLayout)]];
	[data addObject:@[@"Organization", @(AssetClassNameIdOrganization)]];
	[data addObject:@[@"User", @(AssetClassNameIdUser)]];
	[data addObject:@[@"UserGroup", @(AssetClassNameIdUserGroup)]];
	[data addObject:@[@"BlogsEntry", @(AssetClassNameIdBlogsEntry)]];
	[data addObject:@[@"BookmarksEntry", @(AssetClassNameIdBookmarksEntry)]];
	[data addObject:@[@"BookmarksFolder", @(AssetClassNameIdBookmarksFolder)]];
	[data addObject:@[@"CalendarEvent", @(AssetClassNameIdCalendarEvent)]];
	[data addObject:@[@"DLFileEntry", @(AssetClassNameIdDLFileEntry)]];
	[data addObject:@[@"DLFileEntryMetadata", @(AssetClassNameIdDLFileEntryMetadata)]];
	[data addObject:@[@"DLFileEntryType", @(AssetClassNameIdDLFileEntryType)]];
	[data addObject:@[@"DLFileRank", @(AssetClassNameIdDLFileRank)]];
	[data addObject:@[@"DLFileShortcut", @(AssetClassNameIdDLFileShortcut)]];
	[data addObject:@[@"DLFileVersion", @(AssetClassNameIdDLFileVersion)]];
	[data addObject:@[@"DDLRecord", @(AssetClassNameIdDDLRecord)]];
	[data addObject:@[@"DDLRecordSet", @(AssetClassNameIdDDLRecordSet)]];
	[data addObject:@[@"JournalArticle", @(AssetClassNameIdJournalArticle)]];
	[data addObject:@[@"JournalFolder", @(AssetClassNameIdJournalFolder)]];
	[data addObject:@[@"MBMessage", @(AssetClassNameIdMBMessage)]];
	[data addObject:@[@"MBThread", @(AssetClassNameIdMBThread)]];
	[data addObject:@[@"MBCategory", @(AssetClassNameIdMBCategory)]];
	[data addObject:@[@"MBDiscussion", @(AssetClassNameIdMBDiscussion)]];
	[data addObject:@[@"MBMailingList", @(AssetClassNameIdMBMailingList)]];
	[data addObject:@[@"WikiPage", @(AssetClassNameIdWikiPage)]];
	[data addObject:@[@"WikiPageResource", @(AssetClassNameIdWikiPageResource)]];
	[data addObject:@[@"WikiNode", @(AssetClassNameIdWikiNode)]];
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
	self.selectedAsset = [self.pickerData[row][1] intValue];
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
	return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
	return self.pickerData.count;
}

-(NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
	return self.pickerData[row][0];
}

- (void)onAssetListResponse:(NSArray *)entries {
	NSLog(@"DELEGATE onAssetListResponse -> %@", entries);
}

- (void)onAssetListError:(NSError *)error {
	NSLog(@"DELEGATE onAssetListError -> %@", error);
}

- (void)onAssetSelected:(AssetListScreenletEntry *)entry {
	NSLog(@"DELEGATE onAssetSelected -> %@", entry);
}

- (void)showPicker:(BOOL)show animated:(BOOL)animated {
	[UIView animateWithDuration:animated ? 0.5 : 0 animations:^{
		self.picker.superview.frame = CGRectMake(
				self.picker.superview.frame.origin.x,
				show ? 0 : self.view.frame.size.height,
				self.picker.superview.frame.size.width,
				self.picker.superview.frame.size.height);
	}];
}

@end
