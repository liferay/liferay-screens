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

@import LiferayScreens;
#import "LiferayLogger.h"
#import "ImageGalleryScreenletController.h"

@interface ImageGalleryScreenletController () <ImageGalleryScreenletDelegate>

@property (weak, nonatomic) IBOutlet ImageGalleryScreenlet *screenlet;

@end

@implementation ImageGalleryScreenletController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.mimeTypes = [LiferayServerContext stringPropertyForKey:@"imageGalleryMimeType"];
	self.screenlet.repositoryId = [LiferayServerContext longPropertyForKey:@"imageGalleryRepositoryId"];
	self.screenlet.folderId = [LiferayServerContext longPropertyForKey:@"imageGalleryFolderId"];
}

- (IBAction)segmentedControlValueChanged:(UISegmentedControl *)sender {

	switch (sender.selectedSegmentIndex) {
		case 0:
			self.screenlet.themeName = @"default";
			break;
		case 1:
			self.screenlet.themeName = @"default-slideshow";
			break;
		case 2:
			self.screenlet.themeName = @"default-list";
			break;

		default:
			break;
	}

	[self.screenlet loadList];
}

- (IBAction)startUpload {
	[self.screenlet startMediaSelectorAndUpload];
}

#pragma mark ImageGalleryScreenletDelegate

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageEntriesResponse:(NSArray<ImageEntry *> *)imageEntries {

	LiferayLog(imageEntries);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageEntriesError:(NSError *)error {

	LiferayLog(error);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageEntrySelected:(ImageEntry *)imageEntry {
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageEntryDeleted:(ImageEntry *)imageEntry {

	LiferayLog(imageEntry);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageEntryDeleteError:(NSError *)error {

	LiferayLog(error);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageUploadStart:(ImageEntryUpload *)imageEntryUpload {

	LiferayLog(imageEntryUpload);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageUploadProgress:(ImageEntryUpload *)imageEntryUpload
   totalBytesSent:(uint64_t)totalBytesSent totalBytesToSend:(uint64_t)totalBytesToSend {

	LiferayLog(imageEntryUpload);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageUploadError:(NSError *)error {

	LiferayLog(error);
}

- (void)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageUploaded:(ImageEntry *)image {

	LiferayLog(image);
}

- (BOOL)screenlet:(ImageGalleryScreenlet *)screenlet
		onImageUploadDetailViewCreated:(ImageUploadDetailViewBase *)view {

	return NO;
}

@end
