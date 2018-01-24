using System;
using UIKit;

namespace AndorraTelecomiOS.Util
{
    public static class NavItem
    {
        public static UIImageView AddLogo()
        {
            var Logo = new UIImage("img/logo.png");
            var ImageView = new UIImageView(Logo);
            ImageView.ContentMode = UIViewContentMode.ScaleAspectFit;
            return ImageView;
        }

        public static UIBarButtonItem AddButtonChangeLanguage(string Language, UIViewController Controller, LanguageHelper LanguageHelper)
        {
            var LanguageButton = new UIBarButtonItem(Language, UIBarButtonItemStyle.Plain, (sender, e) => OpenMenuChangeLanguage(Controller, LanguageHelper));
            LanguageButton.TintColor = UIColor.White;

            return LanguageButton;
        }

        private static void OpenMenuChangeLanguage(UIViewController Controller, LanguageHelper LanguageHelper)
        {
            var LanguageAlert = new UIAlertController();
            LanguageAlert.Title = "Language";
            LanguageAlert.Message = "Choose your preferred language";
            LanguageAlert.ModalPresentationStyle = UIModalPresentationStyle.FormSheet;

            var AvailableLanguages = LanguageHelper.ListLanguages;
            foreach (var Value in AvailableLanguages)
            {
                var AlertActionOption = UIAlertAction.Create(Value, UIAlertActionStyle.Default, (obj) => ActionChangeLanguage(obj.Title, Controller, LanguageHelper));
                LanguageAlert.AddAction(AlertActionOption);
            }

            var AlertActionCancel = UIAlertAction.Create("Cancel", UIAlertActionStyle.Cancel, (obj) => LanguageAlert.DismissViewController(true, null));
            LanguageAlert.AddAction(AlertActionCancel);
            Controller.PresentViewController(LanguageAlert, true, null);
        }

        private static void ActionChangeLanguage(string NewLanguage, UIViewController Controller, LanguageHelper LanguageHelper)
        {
            LanguageHelper.Language = NewLanguage.Substring(0, 2);
            Controller.ViewDidLoad();
        }
    }
}
