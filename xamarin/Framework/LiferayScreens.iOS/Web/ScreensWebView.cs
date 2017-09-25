using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface IScreensWebView { }

    // @protocol ScreensWebView
    [Protocol, Model]
    interface ScreensWebView
    {
        // @required @property (readonly, nonatomic, strong) int * _Nonnull view;
        [Abstract]
        [Export("view", ArgumentSemantic.Strong)]
        unsafe IntPtr View { get; }

        // @required @property (nonatomic) int isScrollEnabled;
        [Abstract]
        [Export("isScrollEnabled")]
        int IsScrollEnabled { get; set; }

        // @required -(instancetype _Nonnull)initWithJsCallHandler:(void (^ _Nonnull)(int * _Nonnull, int * _Nonnull))jsCallHandler jsErrorHandler:(void (^ _Nonnull (^ _Nonnull)(int * _Nonnull))(id _Nullable, NSError * _Nullable))jsErrorHandler onPageLoadFinished:(void (^ _Nonnull)(int * _Nonnull, NSError * _Nullable))onPageLoadFinished;
        [Abstract]
        [Export("initWithJsCallHandler:jsErrorHandler:onPageLoadFinished:")]
        unsafe IntPtr Constructor(Action<IntPtr, IntPtr> jsCallHandler, Func<IntPtr, Action<NSObject, NSError>> jsErrorHandler, Action<IntPtr, NSError> onPageLoadFinished);

        // @required -(void)addWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Abstract]
        [Export("addWithInjectableScript:")]
        void AddWithInjectableScript(IInjectableScript injectableScript);

        // @required -(void)injectWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Abstract]
        [Export("injectWithInjectableScript:")]
        void InjectWithInjectableScript(IInjectableScript injectableScript);

        // @required -(void)loadWithRequest:(id)request;
        [Abstract]
        [Export("loadWithRequest:")]
        void LoadWithRequest(NSObject request);

        // @required -(void)loadWithHtmlString:(id)htmlString;
        [Abstract]
        [Export("loadWithHtmlString:")]
        void LoadWithHtmlString(NSObject htmlString);

        // @required -(void)clearCache;
        [Abstract]
        [Export("clearCache")]
        void ClearCache();
    }
}
