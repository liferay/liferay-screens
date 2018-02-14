using System;
using Foundation;
using UIKit;

namespace AndorraTelecomiOS.Util
{
    public static class NavItem
    {
        public static UIBarButtonItem LanguageButton;

        public static UIImageView AddLogo()
        {
            var Logo = new UIImage("img/logo-navbar.png");
            var ImageView = new UIImageView(Logo);
            ImageView.ContentMode = UIViewContentMode.ScaleAspectFit;
            return ImageView;
        }

        public static UIBarButtonItem AddButtonChangeLanguage(string Language, UIViewController Controller)
        {
            var LanguageBundle = RetrieveLanguageBundle(LanguageHelper.Language);
            
            LanguageButton = new UIBarButtonItem(Language, UIBarButtonItemStyle.Plain, (sender, e) => OpenMenuChangeLanguage(Controller, LanguageBundle));
            LanguageButton.TintColor = UIColor.White;

            return LanguageButton;
        }

        static NSBundle RetrieveLanguageBundle(string Language)
        {
            var Path = NSBundle.MainBundle.PathForResource(Language, "lproj");
            return NSBundle.FromPath(Path);
        }

        static void OpenMenuChangeLanguage(UIViewController Controller, NSBundle LanguageBundle)
        {
            var Title = LanguageBundle.LocalizedString("Language", null);
            var Message = LanguageBundle.LocalizedString("Choose your preferred language", null);
            var LanguageAlert = UIAlertController.Create(Title, Message, UIAlertControllerStyle.ActionSheet);
            LanguageAlert.View.Layer.CornerRadius = 15;
            LanguageAlert.View.TintColor = Colors.DarkPurple;

            var Subview = LanguageAlert.View.Subviews[0].Subviews[0] as UIView;
            foreach (var V in Subview.Subviews)
            {
                V.BackgroundColor = Colors.Pink;
            }

            var AvailableLanguages = LanguageHelper.ListLanguages;
            foreach (var Value in AvailableLanguages)
            {
                var AlertActionOption = UIAlertAction.Create(Value, UIAlertActionStyle.Default, (obj) => ActionChangeLanguage(obj.Title, Controller));
                LanguageAlert.AddAction(AlertActionOption);
            }

            var AlertActionCancel = UIAlertAction.Create(LanguageBundle.LocalizedString("Cancel", null), UIAlertActionStyle.Cancel, (obj) => LanguageAlert.DismissViewController(true, null));
            LanguageAlert.AddAction(AlertActionCancel);

            if(LanguageAlert.PopoverPresentationController != null)
            {
                LanguageAlert.PopoverPresentationController.BarButtonItem = LanguageButton;
            }

            Controller.PresentViewController(LanguageAlert, true, null);
        }

        static void ActionChangeLanguage(string NewLanguage, UIViewController Controller)
        {
            LanguageHelper.Language = NewLanguage.Substring(0, 2);
            Controller.ViewDidLoad();
        }
    }
}
