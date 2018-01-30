using System;
using UIKit;
using LiferayScreens;
using Foundation;
using AndorraTelecomiOS.Util;
using CoreGraphics;

namespace AndorraTelecomiOS
{
    public partial class MenuViewController : UIViewController, IWebScreenletDelegate, ICallMeBackDelegate
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
            CallMeBackView.Delegate = this; 

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

        /* ICallMeBackDelegate */

        public void ShowLegalConditions(CallMeBackView CallMeBackView)
        {
            Console.WriteLine("Show Legal Conditions");

            var AlertController = new UIAlertController();
            AlertController.Title = "\n\n\n\n\n\n\n\n\n\n\n\n\n";
            AlertController.ModalPresentationStyle = UIModalPresentationStyle.PageSheet;
            AlertController.View.Layer.CornerRadius = 15;
            AlertController.View.TintColor = Colors.DarkPurple;
            var Subview = AlertController.View.Subviews[0].Subviews[0] as UIView;
            foreach (var V in Subview.Subviews)
            {
                V.BackgroundColor = Colors.Pink;
            }

            var LegalView = LegalConditionsView.Create();

            var Margin = 5;
            var Rect = new CGRect(Margin, Margin, AlertController.View.Bounds.Size.Width - Margin * 2, AlertController.View.Bounds.Size.Height - Margin * 26);
            LegalView.Frame = Rect;
            AlertController.View.AddSubview(LegalView);

            var LanguageBundle = RetrieveLanguageBundle(LanguageHelper.Language);

            var AlertActionAccept = UIAlertAction.Create(LanguageBundle.LocalizedString("Accept", null), UIAlertActionStyle.Default, (obj) => this.callMeBackView.LegalConditionsChange(CallMeBackView, true));
            var AlertActionCancel = UIAlertAction.Create(LanguageBundle.LocalizedString("Cancel", null), UIAlertActionStyle.Cancel, (obj) => this.callMeBackView.LegalConditionsChange(CallMeBackView, false));
            AlertController.AddAction(AlertActionAccept);
            AlertController.AddAction(AlertActionCancel);
            this.PresentViewController(AlertController, true, null);
        }

        public void ShowAlertLegalNotAccepted(CallMeBackView CallMeBackView, string Title, string Message)
        {
            Console.WriteLine("Show Alert Legal Not Accepted");
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

        static NSBundle RetrieveLanguageBundle(string Language)
        {
            var Path = NSBundle.MainBundle.PathForResource(Language, "lproj");
            return NSBundle.FromPath(Path);
        }
    }
}