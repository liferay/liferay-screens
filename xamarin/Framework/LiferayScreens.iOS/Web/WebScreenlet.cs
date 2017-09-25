using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface WebScreenlet
    {
        // @property (nonatomic) int autoLoad;
        [Export("autoLoad")]
        int AutoLoad { get; set; }

        // @property (nonatomic) int loggingEnabled;
        [Export("loggingEnabled")]
        int LoggingEnabled { get; set; }

        // @property (nonatomic) int isScrollEnabled;
        [Export("isScrollEnabled")]
        int IsScrollEnabled { get; set; }

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

        // -(void)handleJsCallWithNamespace:(id)namespace_ message:(id)message;
        [Export("handleJsCallWithNamespace:message:")]
        void HandleJsCallWithNamespace(NSObject namespace_, NSObject message);

        // -(void)injectWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Export("injectWithInjectableScript:")]
        void InjectWithInjectableScript(IInjectableScript injectableScript);

        // -(void)load;
        [Export("load")]
        void Load();

        // -(instancetype _Nonnull)initWithFrame:(id)frame themeName:(id)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSObject frame, NSObject themeName);
    }
}
