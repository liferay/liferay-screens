using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using WebKit;

namespace LiferayScreens
{
    // @interface PdfDisplayView_default : BaseScreenletView <FileDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface PdfDisplayView_default : IFileDisplayViewModel
    {
        // @property (nonatomic, strong) WKWebView * _Nullable webView;
        [NullAllowed, Export("webView", ArgumentSemantic.Strong)]
        WKWebView WebView { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable title;
        [NullAllowed, Export("title")]
        string Title { get; set; }

        // @property (copy, nonatomic) NSURL * _Nullable url;
        [NullAllowed, Export("url", ArgumentSemantic.Copy)]
        NSUrl Url { get; set; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)addWebView;
        [Export("addWebView")]
        void AddWebView();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
