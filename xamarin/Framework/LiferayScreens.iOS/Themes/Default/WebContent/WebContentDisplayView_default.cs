using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using WebKit;

namespace LiferayScreens
{
    // @interface WebContentDisplayView_default : BaseScreenletView <WebContentDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface WebContentDisplayView_default : IWebContentDisplayViewModel
    {
        // @property (nonatomic, strong) WKWebView * _Nullable webView;
        [NullAllowed, Export("webView", ArgumentSemantic.Strong)]
        WKWebView WebView { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable injectedCss;
        [NullAllowed, Export("injectedCss")]
        string InjectedCss { get; set; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)addWebView;
        [Export("addWebView")]
        void AddWebView();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // @property (copy, nonatomic) NSString * _Nullable htmlContent;
        [NullAllowed, Export("htmlContent")]
        string HtmlContent { get; set; }

        // @property (nonatomic, strong) DDLRecord * _Nullable recordContent;
        [NullAllowed, Export("recordContent", ArgumentSemantic.Strong)]
        DDLRecord RecordContent { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable customCssFile;
        [NullAllowed, Export("customCssFile")]
        string CustomCssFile { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
