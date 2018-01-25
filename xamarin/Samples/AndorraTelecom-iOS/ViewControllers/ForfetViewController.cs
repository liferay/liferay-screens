using System;
using AndorraTelecomiOS.Util;
using UIKit;

namespace AndorraTelecomiOS
{
    public partial class ForfetViewController : UIViewController
    {
        protected ForfetViewController(IntPtr handle) : base(handle) { }

        public LanguageHelper.Pages ForfetUrl { get; internal set; }

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
