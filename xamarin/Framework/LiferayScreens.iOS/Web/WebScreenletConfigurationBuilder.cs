using Foundation;
using System;

namespace LiferayScreens
{
    // @interface WebScreenletConfigurationBuilder : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface WebScreenletConfigurationBuilder
    {
        // -(instancetype _Nonnull)initWithUrl:(NSString * _Nonnull)url __attribute__((objc_designated_initializer));
        [Export("initWithUrl:")]
        [DesignatedInitializer]
        IntPtr Constructor(string url);

        // -(instancetype _Nonnull)addJsWithLocalFile:(NSString * _Nonnull)localFile __attribute__((warn_unused_result));
        [Export("addJsWithLocalFile:")]
        WebScreenletConfigurationBuilder AddJsWithLocalFile(string localFile);

        // -(instancetype _Nonnull)addCssWithLocalFile:(NSString * _Nonnull)localFile __attribute__((warn_unused_result));
        [Export("addCssWithLocalFile:")]
        WebScreenletConfigurationBuilder AddCssWithLocalFile(string localFile);

        // -(instancetype _Nonnull)addJsWithUrl:(NSString * _Nonnull)url __attribute__((warn_unused_result));
        [Export("addJsWithUrl:")]
        WebScreenletConfigurationBuilder AddJsWithUrl(string url);

        // -(instancetype _Nonnull)addCssWithUrl:(NSString * _Nonnull)url __attribute__((warn_unused_result));
        [Export("addCssWithUrl:")]
        WebScreenletConfigurationBuilder AddCssWithUrl(string url);

        // -(instancetype _Nonnull)enableCordova __attribute__((warn_unused_result));
        [Export("enableCordova")]
        WebScreenletConfigurationBuilder EnableCordova();

        // -(instancetype _Nonnull)setWithWebType:(enum WebType)webType __attribute__((warn_unused_result));
        [Export("setWithWebType:")]
        WebScreenletConfigurationBuilder SetWithWebType(WebType webType);

        // -(WebScreenletConfiguration * _Nonnull)load __attribute__((warn_unused_result));
        [Export("load")]
        WebScreenletConfiguration Load();
    }
}
