using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class WebScreenletViewController : UIViewController, IWebScreenletDelegate
    {
        public WebScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            webScreenlet.AutoLoad = false;

            var config = new WebScreenletConfigurationBuilder("/web/westeros-hybrid/documents")
                    .AddJsWithLocalFile("js/js_master")
                    .AddCssWithLocalFile("css/css_master")
                    .Load();

            webScreenlet.Configuration = config;
            webScreenlet.Delegate = this;

            webScreenlet.Load();
        }

        /* WebScreenletDelegate */


        [Export("onWebLoad:url:")]
        public virtual void OnWebLoad(WebScreenlet screenlet, string url)
        {
            Debug.WriteLine($"WebScreenlet URL display successfully: {url}");
        }

        [Export("screenlet:onError:")]
        public virtual void Screenlet(WebScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"WebScreenlet URL display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onScriptMessageNamespace:onScriptMessage:")]
        public virtual void Screenlet(WebScreenlet screenlet, string namespace_, string message)
        {
            Debug.WriteLine($"WebScreenlet onScriptMessage -> namespace: {namespace_}, message: {message}");
        }
    }
}

