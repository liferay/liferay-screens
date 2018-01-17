using System;
using UIKit;
using LiferayScreens;
using Foundation;
using AndorraTelecomiOS.Util;

namespace AndorraTelecomiOS
{
    public partial class MenuViewController : UIViewController, IWebScreenletDelegate
    {
        protected MenuViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            LoadWebScreenlet();
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        public void LoadWebScreenlet()
        {
            
            var url = "https://www.andorratelecom.ad/particulars/inici";

            var config = new WebScreenletConfigurationBuilder(url)
                .SetWithWebType(WebType.Other)
                .AddJsWithLocalFile("js/menu_js")
                .AddCssWithLocalFile("css/menu_css")
                .Load();

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
        public virtual void Screenlet(WebScreenlet screenlet, string namespace_, string message)
        {
            Console.WriteLine($"WebScreenlet onScriptMessage -> namespace: {namespace_}, message: {message}");
        }
    }
}