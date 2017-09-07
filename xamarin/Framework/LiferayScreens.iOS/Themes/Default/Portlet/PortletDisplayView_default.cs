using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface PortletDisplayView_default : BaseScreenletView <PortletDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface PortletDisplayView_default : IPortletDisplayViewModel
    {
        // @property (nonatomic) BOOL isThemeEnabled;
        [Export("isThemeEnabled")]
        bool IsThemeEnabled { get; set; }

        // @property (nonatomic) BOOL isLoggingEnabled;
        [Export("isLoggingEnabled")]
        bool IsLoggingEnabled { get; set; }

        // @property (nonatomic, strong) id<ScreensWebView> _Nullable screensWebView;
        [NullAllowed, Export("screensWebView", ArgumentSemantic.Strong)]
        IScreensWebView ScreensWebView { get; set; }

        // @property (readonly, nonatomic, strong) PortletDisplayScreenlet * _Nonnull portletDisplayScreenlet;
        [Export("portletDisplayScreenlet", ArgumentSemantic.Strong)]
        PortletDisplayScreenlet PortletDisplayScreenlet { get; }

        // -(void)configureViewWith:(BOOL)cordovaEnabled;
        [Export("configureViewWith:")]
        void ConfigureViewWith(bool cordovaEnabled);

        // -(void)addWithInjectableScripts:(NSArray<id<InjectableScript>> * _Nonnull)injectableScripts;
        [Export("addWithInjectableScripts:")]
        void AddWithInjectableScripts(IInjectableScript[] injectableScripts);

        // -(void)addWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Export("addWithInjectableScript:")]
        void AddWithInjectableScript(IInjectableScript injectableScript);

        // -(void)injectWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Export("injectWithInjectableScript:")]
        void InjectWithInjectableScript(IInjectableScript injectableScript);

        // -(void)loadWithRequest:(NSURLRequest * _Nonnull)request;
        [Export("loadWithRequest:")]
        void LoadWithRequest(NSUrlRequest request);

        // -(void)loadWithHtmlString:(NSString * _Nonnull)htmlString;
        [Export("loadWithHtmlString:")]
        void LoadWithHtmlString(string htmlString);

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onDestroy;
        [Export("onDestroy")]
        void OnDestroy();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(void)addWebView;
        [Export("addWebView")]
        void AddWebView();

        // -(void)onPageLoadFinishedWithUrl:(NSString * _Nonnull)url with:(NSError * _Nullable)error;
        [Export("onPageLoadFinishedWithUrl:with:")]
        void OnPageLoadFinishedWithUrl(string url, [NullAllowed] NSError error);

        // -(void)handleJsCallWithNamespace:(NSString * _Nonnull)namespace_ message:(NSString * _Nonnull)message;
        [Export("handleJsCallWithNamespace:message:")]
        void HandleJsCallWithNamespace(string namespace_, string message);

        // -(void)showHud;
        [Export("showHud")]
        void ShowHud();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
