//
//  AudioDisplayViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights


@import LiferayScreens;
#import "LiferayLogger.h"
#import "AudioDisplayViewController.h"

@interface AudioDisplayViewController () <FileDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet AudioDisplayScreenlet *screenlet;

@end

@implementation AudioDisplayViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
    self.screenlet.presentingViewController = self;
    self.screenlet.classPK = [LiferayServerContext longPropertyForKey:@"audioDisplayClassPK"];
}

- (void)screenlet:(FileDisplayScreenlet *)screenlet onFileAssetError:(NSError *)error {
    LiferayLog(error);
}

- (void)screenlet:(FileDisplayScreenlet *)screenlet onFileAssetResponse:(NSURL *)url {
	LiferayLog(url);
}

@end
