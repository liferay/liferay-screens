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
    }
}
