using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface PortletDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface PortletDisplayScreenlet
    {
        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (nonatomic) BOOL loggingEnabled;
        [Export("loggingEnabled")]
        bool LoggingEnabled { get; set; }

        // @property (nonatomic, strong) PortletConfiguration * _Nullable configuration;
        [NullAllowed, Export("configuration", ArgumentSemantic.Strong)]
        PortletConfiguration Configuration { get; set; }

        [Wrap("WeakPortletDisplayDelegate")]
        [NullAllowed]
        PortletDisplayScreenletDelegate PortletDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<PortletDisplayScreenletDelegate> _Nullable portletDisplayDelegate;
        [NullAllowed, Export("portletDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakPortletDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<PortletDisplayViewModel> _Nonnull portletDisplayViewModel;
        [Export("portletDisplayViewModel", ArgumentSemantic.Strong)]
        IPortletDisplayViewModel PortletDisplayViewModel { get; }

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

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
