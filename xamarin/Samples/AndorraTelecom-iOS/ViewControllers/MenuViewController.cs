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

            LoadCallMeBackPopOver();
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

            this.viewCallMeBack.Hidden = true;
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

        public void LoadCallMeBackPopOver()
        {
            var CmbView = CallMeBackView.Create();
            CmbView.Frame = callMeBackView.Bounds;
            callMeBackView.AddSubview(CmbView);
        }

        /* IWebScreenletDelegate */

        [Export("onWebLoad:url:")]
        public virtual void OnWebLoad(WebScreenlet screenlet, string url)
        {
            Console.WriteLine($"WebScreenlet URL display successfully: {url}");
            AttachClickHeaderCallMeBack();
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
                    CreatePopOverCallMeBack(Message);
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
                var LanguageBundle = NSBundle.FromPath(NSBundle.MainBundle.PathForResource(LanguageHelper.Language, "lproj"));
                var BackItem = new UIBarButtonItem();
                BackItem.Title = LanguageBundle.LocalizedString("Back", null);
                BackItem.TintColor = UIColor.White;
                NavigationItem.BackBarButtonItem = BackItem;

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

        async void CreatePopOverCallMeBack(string Message)
        {
            this.labelCallMeBack.Text = Message;
            await UIView.AnimateAsync(0, () => AnimateCallMeBack());
        }

        void AnimateCallMeBack()
        {
            this.heightCallMeBack.Constant = 50;
            this.viewCallMeBack.Superview.LayoutIfNeeded();
            this.viewCallMeBack.Hidden = false;
        }

        void AttachClickHeaderCallMeBack()
        {
            Action CallMeBackActions = () =>
            {
                UIView.Animate(0.5, () =>
                {
                    var bigSize = 400;
                    var smallSize = 50;

                    if (this.heightCallMeBack.Constant.Equals(bigSize))
                    {
                        this.heightCallMeBack.Constant = smallSize;
                        this.viewCallMeBack.Superview.LayoutIfNeeded();
                    }
                    else
                    {
                        this.heightCallMeBack.Constant = bigSize;
                        this.viewCallMeBack.Superview.LayoutIfNeeded();
                    }
                });
            };

            var headerGesture = new UITapGestureRecognizer(CallMeBackActions);
            this.headerCallMeBack.AddGestureRecognizer(headerGesture);
        }
    }
}