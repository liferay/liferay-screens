//
//  CommentListScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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
                CommentAddInteractor *interactor = [[CommentAddInteractor alloc] initWithClassName:weakSelf.listScreenlet.className classPK:weakSelf.listScreenlet.classPK body:body];

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


- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onListResponseComments:(NSArray<Comment *> * _Nonnull)comments {
    LiferayLog(comments);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentListError:(NSError * _Nonnull)error {
	LiferayLog(error);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onSelectedComment:(Comment * _Nonnull)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onDeletedComment:(Comment * _Nonnull)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentDelete:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error {
	LiferayLog(comment, error);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onUpdatedComment:(Comment * _Nonnull)comment {
	LiferayLog(comment);
}

- (void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentUpdate:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error {

	LiferayLog(comment, error);
}


@end
