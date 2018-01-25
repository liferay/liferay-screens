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

            AddLogoToNavigationItem();

            AddMenuChangeLanguage();

            LoadWebScreenlet();
        }

        public void AddLogoToNavigationItem()
        {
            UIImageView ImageView = NavItem.AddLogo();
            NavigationItem.TitleView = ImageView;
        }

        public void AddMenuChangeLanguage()
        {
            var RightButton = NavItem.AddButtonChangeLanguage(LanguageHelper.ThreeLanguageLetters, this);
            NavigationItem.SetRightBarButtonItem(RightButton, true);
        }

        public void LoadWebScreenlet()
        {
            var Url = LanguageHelper.Url(LanguageHelper.Pages.Index);

            var config = new WebScreenletConfigurationBuilder(Url)
                .SetWithWebType(WebType.Other)
                .AddJsWithLocalFile("js/menu_js")
                .AddCssWithLocalFile("css/menu_css")
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

            switch(namespace_)
            {
                case "call-me-back":
                    Console.WriteLine("Call me back popover");
                    break;
                case "click-button":
                    Console.WriteLine("Go to next forfet");
                    GoNextForfet(Message);
                    break;
                case "map":
                    Console.WriteLine("Go to map");
                    GoMap();
                    break;
                default:
                    Console.WriteLine("Invalid event");
                    break;
                    
            }
        }

        /* UIViewController */

        public override void PrepareForSegue(UIStoryboardSegue segue, NSObject sender)
        {
            if(segue.Identifier == "forfet")
            {
                var ViewController = segue.DestinationViewController as ForfetViewController;

                if(ViewController != null)
                {
                    switch(sender.ToString())
                    {
                        case "0":
                            ViewController.ForfetUrl = LanguageHelper.Pages.Mobile;
                            break;
                        case "1":
                            ViewController.ForfetUrl = LanguageHelper.Pages.Roaming;
                            break;
                        case "2":
                            ViewController.ForfetUrl = LanguageHelper.Pages.Paquete69;
                            break;
                        case "3":
                            ViewController.ForfetUrl = LanguageHelper.Pages.Optima;
                            break;
                        default:
                            Console.WriteLine("Invalid page");
                            break;
                    }
                }
            }

        }

        /* Private methods */

        void GoNextForfet(string Message)
        {
            PerformSegue("forfet", NSObject.FromObject(Message));
        }

        void GoMap()
        {
            PerformSegue("map", null);
        }
    }
}