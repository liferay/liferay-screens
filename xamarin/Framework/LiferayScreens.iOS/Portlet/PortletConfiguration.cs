using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface PortletConfiguration : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface PortletConfiguration
    {
        // @property (readonly, copy, nonatomic) NSString * _Nonnull portletUrl;
        [Export("portletUrl")]
        string PortletUrl { get; }

        // @property (readonly, copy, nonatomic) NSArray<id<InjectableScript>> * _Nonnull scripts;
        [Export("scripts", ArgumentSemantic.Copy)]
        IInjectableScript[] Scripts { get; }

        // @property (readonly, nonatomic) BOOL isThemeEnabled;
        [Export("isThemeEnabled")]
        bool IsThemeEnabled { get; }

        // @property (readonly, nonatomic) BOOL isCordovaEnabled;
        [Export("isCordovaEnabled")]
        bool IsCordovaEnabled { get; }

        // @property (readonly, nonatomic) enum WebType webType;
        [Export("webType")]
        WebType WebType { get; }

        // -(instancetype _Nonnull)initWithPortletUrl:(NSString * _Nonnull)portletUrl scripts:(NSArray<id<InjectableScript>> * _Nonnull)scripts isThemeEnabled:(BOOL)isThemeEnabled isCordovaEnabled:(BOOL)isCordovaEnabled webType:(enum WebType)webType __attribute__((objc_designated_initializer));
        [Export("initWithPortletUrl:scripts:isThemeEnabled:isCordovaEnabled:webType:")]
        [DesignatedInitializer]
        IntPtr Constructor(string portletUrl, IInjectableScript[] scripts, bool isThemeEnabled, bool isCordovaEnabled, WebType webType);
    }
}
