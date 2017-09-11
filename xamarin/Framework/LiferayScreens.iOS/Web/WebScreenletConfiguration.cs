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
        // @property (readonly, copy, nonatomic) NSString * _Nonnull url;
        [Export("url")]
        string Url { get; }

        // @property (readonly, copy, nonatomic) NSArray<id<InjectableScript>> * _Nonnull scripts;
        [Export("scripts", ArgumentSemantic.Copy)]
        IInjectableScript[] Scripts { get; }

        // @property (readonly, nonatomic) BOOL isCordovaEnabled;
        [Export("isCordovaEnabled")]
        bool IsCordovaEnabled { get; }

        // @property (readonly, nonatomic) enum WebType webType;
        [Export("webType")]
        WebType WebType { get; }

        // -(instancetype _Nonnull)initWithUrl:(NSString * _Nonnull)url scripts:(NSArray<id<InjectableScript>> * _Nonnull)scripts isCordovaEnabled:(BOOL)isCordovaEnabled webType:(enum WebType)webType __attribute__((objc_designated_initializer));
        [Export("initWithUrl:scripts:isCordovaEnabled:webType:")]
        [DesignatedInitializer]
        IntPtr Constructor(string url, IInjectableScript[] scripts, bool isCordovaEnabled, WebType webType);
    }
}
