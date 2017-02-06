//
//  CommentDisplayScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//
@import LiferayScreens;
#import "LiferayLogger.h"
#import "CommentDisplayScreenletViewController.h"

@interface CommentDisplayScreenletViewController () <CommentDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet CommentDisplayScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *commentIdText;

@end

@implementation CommentDisplayScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.screenlet.delegate = self;
}

- (IBAction)loadComment:(id)sender {
    if (self.commentIdText.text) {
        self.screenlet.commentId = self.commentIdText.text.longLongValue;
        [self.screenlet load];
    }
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentLoaded:(Comment *)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onLoadCommentError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentDeleted:(Comment *)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentUpdated:(Comment *)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet
		onUpdateComment:(Comment *)comment onError:(NSError *)error {
    LiferayLog(comment, error);
}

@end
