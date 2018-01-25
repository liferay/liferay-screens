using System;
using UIKit;
using AndorraTelecomiOS.Util;

namespace AndorraTelecomiOS
{
    public partial class MapViewController : UIViewController
    {
        public MapViewController (IntPtr handle) : base (handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            AddLogoToNavigationItem();

            AddMenuChangeLanguage();
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
    }
}