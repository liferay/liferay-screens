//
//  ImageGalleryScreenletController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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
