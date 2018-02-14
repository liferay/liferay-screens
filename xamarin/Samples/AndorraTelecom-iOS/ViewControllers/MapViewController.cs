using System;
using UIKit;
using AndorraTelecomiOS.Util;
using LiferayScreens;
using Foundation;

namespace AndorraTelecomiOS
{
    public partial class MapViewController : UIViewController, IWebScreenletDelegate
    {
        public MapViewController (IntPtr handle) : base (handle) { }

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
            var Url = LanguageHelper.Url(LanguageHelper.Pages.Map);

            var config = new WebScreenletConfigurationBuilder(Url)
                .SetWithWebType(WebType.Other)
                .AddJsWithLocalFile("js/map_js")
                .AddCssWithLocalFile("css/map_css")
                .Load();

            webScreenlet.ThemeName = "andorra";
            webScreenlet.Configuration = config;
            webScreenlet.IsScrollEnabled = false;
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