using System;
using Foundation;
using UIKit;

namespace AndorraTelecomiOS.Util
{
    public static class NavItem
    {
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
            
            var LanguageButton = new UIBarButtonItem(Language, UIBarButtonItemStyle.Plain, (sender, e) => OpenMenuChangeLanguage(Controller, LanguageBundle));
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
            var LanguageAlert = new UIAlertController();

            LanguageAlert.Title = LanguageBundle.LocalizedString("Language", null);
            LanguageAlert.Message = LanguageBundle.LocalizedString("Choose your preferred language", null);
            LanguageAlert.ModalPresentationStyle = UIModalPresentationStyle.FormSheet;
            LanguageAlert.View.BackgroundColor = Colors.LightPurple;
            LanguageAlert.View.TintColor = Colors.DarkPurple;

            var AvailableLanguages = LanguageHelper.ListLanguages;
            foreach (var Value in AvailableLanguages)
            {
                var AlertActionOption = UIAlertAction.Create(Value, UIAlertActionStyle.Default, (obj) => ActionChangeLanguage(obj.Title, Controller));
                LanguageAlert.AddAction(AlertActionOption);
            }

            var AlertActionCancel = UIAlertAction.Create(LanguageBundle.LocalizedString("Cancel", null), UIAlertActionStyle.Cancel, (obj) => LanguageAlert.DismissViewController(true, null));
            LanguageAlert.AddAction(AlertActionCancel);
            Controller.PresentViewController(LanguageAlert, true, null);
        }

        static void ActionChangeLanguage(string NewLanguage, UIViewController Controller)
        {
            LanguageHelper.Language = NewLanguage.Substring(0, 2);
            Controller.ViewDidLoad();
        }
    }
}
