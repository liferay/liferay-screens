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
#import "CommentListScreenletViewController.h"

@interface CommentListScreenletViewController () <CommentListScreenletDelegate>

@property (weak, nonatomic) IBOutlet CommentListScreenlet *listScreenlet;
@property (strong, nonatomic) CommentEditViewController_default *editViewController;

@end

@implementation CommentListScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.listScreenlet.delegate = self;
	self.listScreenlet.presentingViewController = self;
	self.listScreenlet.className = [LiferayServerContext stringPropertyForKey:@"commentScreenletClassName"];
	self.listScreenlet.classPK = [LiferayServerContext longPropertyForKey:@"commentScreenletClassPK"];

	self.definesPresentationContext = true;
}

- (IBAction)insertButtonPressed:(NSObject *) sender {
	if (!self.editViewController) {
		self.editViewController = [[CommentEditViewController_default alloc]initWithBody:@""];
		self.editViewController.modalPresentationStyle = UIModalPresentationOverCurrentContext;
		self.editViewController.confirmButton.titleLabel.text = @"Add comment";

		__weak typeof(self) weakSelf = self;
		self.editViewController.confirmBodyClosure = ^void (NSString *body) {
			[weakSelf.editViewController dismissViewControllerAnimated:YES completion:nil];

			if (body) {
				CommentAddInteractor *interactor = [[CommentAddInteractor alloc]
						initWithClassName:weakSelf.listScreenlet.className
						classPK:weakSelf.listScreenlet.classPK body:body];

				interactor.onSuccess = ^{
					if (interactor.resultComment) {
						[weakSelf.listScreenlet addComment:interactor.resultComment];
					}
				};

				interactor.onFailure = ^(NSError *error) {
					LiferayLog(error);
				};

				[interactor start];
			}
		};
	}

	[self presentViewController:self.editViewController animated:YES completion:nil];
}

#pragma mark CommentListScreenletDelegate

- (void)screenlet:(CommentListScreenlet *)screenlet onListResponseComments:(NSArray<Comment *> *)comments {
	LiferayLog(comments);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onCommentListError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onSelectedComment:(Comment *)comment {
	LiferayLog(comment.attributes);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onDeletedComment:(Comment *)comment {
	LiferayLog(comment.attributes);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onCommentDelete:(Comment *)comment onError:(NSError *)error {
	LiferayLog(comment.attributes, error.debugDescription);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onUpdatedComment:(Comment *)comment {
	LiferayLog(comment.attributes);
}

- (void)screenlet:(CommentListScreenlet *)screenlet onCommentUpdate:(Comment *)comment onError:(NSError *)error {

	LiferayLog(comment.attributes, error.debugDescription);
}

@end
