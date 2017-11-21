using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class WebScreenletViewController : UIViewController, IWebScreenletDelegate
    {
        public WebScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            webScreenlet.AutoLoad = false;

            var url = LiferayServerContext.StringPropertyForKey("webScreenletUrl");

            var config = new WebScreenletConfigurationBuilder(url)
                    .AddJsWithLocalFile("js/js_master")
                    .AddCssWithLocalFile("css/css_master")
                    .Load();

            webScreenlet.Configuration = config;

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
