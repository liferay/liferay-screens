using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebScreenletConfiguration : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface WebScreenletConfiguration
    {
        // @property (readonly, copy, nonatomic) int * _Nonnull url;
        [Export("url", ArgumentSemantic.Copy)]
        unsafe IntPtr Url { get; }

        // @property (readonly, nonatomic) int isCordovaEnabled;
        [Export("isCordovaEnabled")]
        int IsCordovaEnabled { get; }

        // @property (readonly, nonatomic) enum WebType webType;
        [Export("webType")]
        WebType WebType { get; }

        // -(instancetype _Nonnull)initWithUrl:(id)url scripts:(id)scripts isCordovaEnabled:(id)isCordovaEnabled webType:(enum WebType)webType __attribute__((objc_designated_initializer));
        [Export("initWithUrl:scripts:isCordovaEnabled:webType:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSObject url, NSObject scripts, NSObject isCordovaEnabled, WebType webType);
    }
}
