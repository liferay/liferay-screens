using System;
using AndorraTelecomiOS.Util;
using Foundation;
using LiferayScreens;
using UIKit;

namespace AndorraTelecomiOS
{
    public partial class ForfetViewController : UIViewController, IWebScreenletDelegate
    {
        protected ForfetViewController(IntPtr handle) : base(handle) { }

        public LanguageHelper.Pages ForfetUrl { get; internal set; }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            AddLogoToNavigationItem();

            LoadWebScreenlet();
        }

        public void AddLogoToNavigationItem()
        {
            UIImageView ImageView = NavItem.AddLogo();
            NavigationItem.TitleView = ImageView;
        }

        public void LoadWebScreenlet()
        {
            var Url = LanguageHelper.Url(ForfetUrl);

            var config = new WebScreenletConfigurationBuilder(Url)
                .SetWithWebType(WebType.Other)
                .AddJsWithLocalFile("js/forfet_js")
                .AddCssWithLocalFile("css/forfet_css")
                .Load();

            webScreenlet.ThemeName = "andorra";
            webScreenlet.Configuration = config;
            webScreenlet.BackgroundColor = Colors.LightPurple;
            webScreenlet.Delegate = this;
            webScreenlet.Load();
        }

        /* IWebScreenletDelegate */

        [Export("onWebLoad:url:")]
        public virtual void OnWebLoad(WebScreenlet screenlet, string url)
        {
            Console.WriteLine($"WebScreenlet URL display successfully: {url}");
        }

        [Export("screenlet:onError:")]
        public virtual void Screenlet(WebScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"WebScreenlet URL display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onScriptMessageNamespace:onScriptMessage:")]
        public virtual void Screenlet(WebScreenlet screenlet, string namespace_, string Message)
        {
            Console.WriteLine($"WebScreenlet onScriptMessage -> namespace: {namespace_}, message: {Message}");
        }
    }
}
