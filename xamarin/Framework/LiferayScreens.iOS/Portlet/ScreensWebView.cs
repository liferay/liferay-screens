using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    interface IScreensWebView { }

    // @protocol ScreensWebView
    [Protocol, Model]
    interface ScreensWebView
    {
        // @required @property (readonly, nonatomic, strong) UIView * _Nonnull view;
        [Abstract]
        [Export("view", ArgumentSemantic.Strong)]
        UIView View { get; }

        // @required -(instancetype _Nonnull)initWithJsCallHandler:(void (^ _Nonnull)(NSString * _Nonnull, NSString * _Nonnull))jsCallHandler jsErrorHandler:(void (^ _Nonnull (^ _Nonnull)(NSString * _Nonnull))(id _Nullable, NSError * _Nullable))jsErrorHandler onPageLoadFinished:(void (^ _Nonnull)(NSString * _Nonnull, NSError * _Nullable))onPageLoadFinished;
        [Abstract]
        [Export("initWithJsCallHandler:jsErrorHandler:onPageLoadFinished:")]
        IntPtr Constructor(Action<NSString, NSString> jsCallHandler, Func<NSString, Action<NSObject, NSError>> jsErrorHandler, Action<NSString, NSError> onPageLoadFinished);

        // @required -(void)addWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Abstract]
        [Export("addWithInjectableScript:")]
        void AddWithInjectableScript(IInjectableScript injectableScript);

        // @required -(void)injectWithInjectableScript:(id<InjectableScript> _Nonnull)injectableScript;
        [Abstract]
        [Export("injectWithInjectableScript:")]
        void InjectWithInjectableScript(IInjectableScript injectableScript);

        // @required -(void)loadWithRequest:(NSURLRequest * _Nonnull)request;
        [Abstract]
        [Export("loadWithRequest:")]
        void LoadWithRequest(NSUrlRequest request);

        // @required -(void)loadWithHtmlString:(NSString * _Nonnull)htmlString;
        [Abstract]
        [Export("loadWithHtmlString:")]
        void LoadWithHtmlString(string htmlString);

        // @optional -(void)onDestroy;
        [Export("onDestroy")]
        void OnDestroy();
    }
}
