#import <Cordova/CDV.h>

@interface NetworkActivityIndicator : CDVPlugin

  - (void)showNetworkActivityIndicator:(CDVInvokedUrlCommand*)command;
  - (void)hideNetworkActivityIndicator:(CDVInvokedUrlCommand*)command;

@end