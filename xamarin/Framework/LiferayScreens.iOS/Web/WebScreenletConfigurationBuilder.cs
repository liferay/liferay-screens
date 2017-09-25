using Foundation;
using System;

namespace LiferayScreens
{
    // @interface WebScreenletConfigurationBuilder : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface WebScreenletConfigurationBuilder
    {
        // -(instancetype _Nonnull)initWithUrl:(id)url __attribute__((objc_designated_initializer));
        [Export("initWithUrl:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSObject url);

        // -(instancetype _Nonnull)addJsWithLocalFile:(id)localFile __attribute__((warn_unused_result));
        [Export("addJsWithLocalFile:")]
        WebScreenletConfigurationBuilder AddJsWithLocalFile(NSObject localFile);

        // -(instancetype _Nonnull)addCssWithLocalFile:(id)localFile __attribute__((warn_unused_result));
        [Export("addCssWithLocalFile:")]
        WebScreenletConfigurationBuilder AddCssWithLocalFile(NSObject localFile);

        // -(instancetype _Nonnull)addJsWithUrl:(id)url __attribute__((warn_unused_result));
        [Export("addJsWithUrl:")]
        WebScreenletConfigurationBuilder AddJsWithUrl(NSObject url);

        // -(instancetype _Nonnull)addCssWithUrl:(id)url __attribute__((warn_unused_result));
        [Export("addCssWithUrl:")]
        WebScreenletConfigurationBuilder AddCssWithUrl(NSObject url);

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
