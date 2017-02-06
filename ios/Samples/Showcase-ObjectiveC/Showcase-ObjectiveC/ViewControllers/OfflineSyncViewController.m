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
#import "OfflineSyncViewController.h"

@interface OfflineSyncViewController () <SyncManagerDelegate>

@property (weak, nonatomic) IBOutlet UIButton *button;
@property (weak, nonatomic) IBOutlet UITextView *log;
@property (nonatomic) SyncManager *syncManager;

@end

@implementation OfflineSyncViewController

- (void)viewDidAppear:(BOOL)animated {
	if (SessionContext.currentContext.cacheManager) {
		self.syncManager = [[SyncManager alloc]
							initWithCacheManager:SessionContext.currentContext.cacheManager];
		self.syncManager.delegate = self;
		self.button.enabled = YES;
	}
	else {
		self.button.enabled = NO;
	}
}

- (IBAction)startSync:(id)sender {
	self.log.text = @"";
	[self.syncManager startSync];
}

- (IBAction)clear:(id)sender {
	[self.syncManager clear];
	self.log.text = [self.log.text stringByAppendingString:@"Cleared\n\n"];
}

#pragma mark SyncManagerDelegate

- (void)syncManager:(SyncManager *)manager itemsCount:(NSUInteger)itemsCount {
	NSString *logText = [NSString stringWithFormat:@"Start sync... %lx items", itemsCount];
	self.log.text = [self.log.text stringByAppendingString:logText];
}

- (void)syncManager:(SyncManager *)manager onItemSyncScreenlet:(NSString *)screenlet
		   startKey:(NSString *)startKey attributes:(NSDictionary<NSString *,id> *)attributes {

	NSString *logText = [NSString
						 stringWithFormat:@"[o] Start item. screenlet=%@ key=%@ attrs=%@\n\n",
						 screenlet, startKey, attributes];

	self.log.text = [self.log.text stringByAppendingString:logText];
}

- (void)syncManager:(SyncManager *)manager onItemSyncScreenlet:(NSString *)screenlet
	   completedKey:(NSString *)completedKey attributes:(NSDictionary<NSString *,id> *)attributes {

	NSString *logText = [NSString
						 stringWithFormat:@"[o] Item completed. screenlet=%@ key=%@ attrs=%@\n\n",
						 screenlet, completedKey, attributes];

	self.log.text = [self.log.text stringByAppendingString:logText];
}

- (void)syncManager:(SyncManager *)manager onItemSyncScreenlet:(NSString *)screenlet
		  failedKey:(NSString *)failedKey attributes:(NSDictionary<NSString *,id> *)attributes error:(NSError *)error {

	NSString *logText = [NSString
						 stringWithFormat:@"[o] Item failed. screenlet=%@ key=%@ attrs=%@ error=%@\n\n",
						 screenlet, failedKey, attributes, error];

	self.log.text = [self.log.text stringByAppendingString:logText];
}

- (void)syncManager:(SyncManager *)manager
		onItemSyncScreenlet:(NSString *)screenlet conflictedKey:(NSString *)conflictedKey
		remoteValue:(id)remoteValue localValue:(id)localValue
			resolve:(void (^)(enum SyncConflictResolution))resolve {

	NSString *logText = [NSString
						 stringWithFormat:@"[o] Item conflicted. screenlet=%@ key=%@ remote=%@ local=%@\nProcessing...",
						 screenlet, conflictedKey, remoteValue, localValue];

	self.log.text = [self.log.text stringByAppendingString:logText];

	UIAlertController *alert = [UIAlertController
								alertControllerWithTitle:@"Conflicted"
								message:@"Choose resolve action" preferredStyle:UIAlertControllerStyleActionSheet];


	[alert addAction:[UIAlertAction actionWithTitle:@"Use local"
											  style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
              resolve(SyncConflictResolutionUseLocal);
											  }]];

	[alert addAction:[UIAlertAction actionWithTitle:@"Use remote"
											  style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
              resolve(SyncConflictResolutionUseRemote);
											  }]];

	[alert addAction:[UIAlertAction actionWithTitle:@"Discard"
											  style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
              resolve(SyncConflictResolutionDiscard);
											  }]];

	[alert addAction:[UIAlertAction actionWithTitle:@"Ignore"
											  style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
              resolve(SyncConflictResolutionIgnore);
											  }]];

	[self presentViewController:alert animated:YES completion:nil];
}

@end
