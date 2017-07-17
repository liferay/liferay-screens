// http://stackoverflow.com/questions/3032192/networkactivityindicatorvisible

#import "NetworkActivityIndicator.h"
#import <Cordova/CDV.h>

static NSInteger activityCount = 0;

@implementation NetworkActivityIndicator

- (void)showNetworkActivityIndicator:(CDVInvokedUrlCommand*)command {

    CDVPluginResult* pluginResult = nil;

    if([[UIApplication sharedApplication] isStatusBarHidden]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Statusbar hidden"];
    }

    @synchronized([UIApplication sharedApplication]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"NetworkActivityIndicator already shown"];

        if(activityCount == 0) {
            [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:YES];

            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"NetworkActivityIndicator shown"];
        }

        activityCount++;
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)hideNetworkActivityIndicator:(CDVInvokedUrlCommand*)command {

    CDVPluginResult* pluginResult = nil;

    if([[UIApplication sharedApplication] isStatusBarHidden]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Statusbar hidden"];
    }

    @synchronized([UIApplication sharedApplication]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Another instance of NetworkActivityIndicator running"];

        activityCount--;

        if(activityCount <= 0) {
            [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:NO];

            activityCount = 0;

            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"NetworkActivityIndicator hidden"];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end