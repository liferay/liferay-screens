using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface WebScreenlet
    {
        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (nonatomic) BOOL loggingEnabled;
        [Export("loggingEnabled")]
        bool LoggingEnabled { get; set; }

        // @property (nonatomic) BOOL isScrollEnabled;
        [Export("isScrollEnabled")]
        bool IsScrollEnabled { get; set; }

        // @property (nonatomic, strong) WebScreenletConfiguration * _Nullable configuration;
        [NullAllowed, Export("configuration", ArgumentSemantic.Strong)]
        WebScreenletConfiguration Configuration { get; set; }

        [Wrap("WeakWebDelegate")]
        [NullAllowed]
        WebScreenletDelegate WebDelegate { get; }

        // @property (readonly, nonatomic, strong) id<WebScreenletDelegate> _Nullable webDelegate;
        [NullAllowed, Export("webDelegate", ArgumentSemantic.Strong)]
        NSObject WeakWebDelegate { get; }

        // @property (readonly, nonatomic, strong) id<WebViewModel> _Nonnull webViewModel;
        [Export("webViewModel", ArgumentSemantic.Strong)]
        IWebViewModel WebViewModel { get; }

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)clearCache;
        [Export("clearCache")]
        void ClearCache();

        // -(void)handleJsCallWithNamespace:(NSString * _Nonnull)namespace_ message:(NSString * _Nonnull)message;
        [Export("handleJsCallWithNamespace:message:")]
        void HandleJsCallWithNamespace(string namespace_, string message);

        // -(void)injectWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Export("injectWithInjectableScript:")]
        void InjectWithInjectableScript(IInjectableScript injectableScript);

        // -(void)load;
        [Export("load")]
        void Load();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
