//
//  AudioDisplayViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights


@import LiferayScreens;
#import "LiferayLogger.h"
#import "VideoDisplayViewController.h"

@interface VideoDisplayViewController () <FileDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet VideoDisplayScreenlet *screenlet;

@end

@implementation VideoDisplayViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
    self.screenlet.presentingViewController = self;
    self.screenlet.classPK = [LiferayServerContext longPropertyForKey:@"videoDisplayClassPK"];
}

- (void)screenlet:(FileDisplayScreenlet *)screenlet onFileAssetError:(NSError *)error {
    LiferayLog(error);
}

- (void)screenlet:(FileDisplayScreenlet *)screenlet onFileAssetResponse:(NSURL *)url {
    LiferayLog(url);
}

@end
