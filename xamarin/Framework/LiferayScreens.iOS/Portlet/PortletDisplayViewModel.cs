using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IPortletDisplayViewModel {}

    // @protocol PortletDisplayViewModel
    [Protocol, Model]
    interface PortletDisplayViewModel
    {
        // @required @property (nonatomic) BOOL isThemeEnabled;
        [Abstract]
        [Export("isThemeEnabled")]
        bool IsThemeEnabled { get; set; }

        // @required @property (nonatomic) BOOL isLoggingEnabled;
        [Abstract]
        [Export("isLoggingEnabled")]
        bool IsLoggingEnabled { get; set; }

        // @required -(void)configureViewWith:(BOOL)cordovaEnabled;
        [Abstract]
        [Export("configureViewWith:")]
        void ConfigureViewWith(bool cordovaEnabled);

        // @required -(void)addWithInjectableScripts:(NSArray<id<InjectableScript>> * _Nonnull)injectableScripts;
        [Abstract]
        [Export("addWithInjectableScripts:")]
        void AddWithInjectableScripts(IInjectableScript[] injectableScripts);

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
    }
}
