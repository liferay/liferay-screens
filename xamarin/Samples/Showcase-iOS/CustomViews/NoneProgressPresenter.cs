using Foundation;
using System;
using LiferayScreens;
using UIKit;

namespace ShowcaseiOS
{
    public class NoneProgressPresenter : MBProgressHUDPresenter, IProgressPresenter
    {
        public NoneProgressPresenter() { }

        public override void HideHUDFromView(UIView view, string message, Interactor interactor, NSError error) { }

        public override void ShowHUDInView(UIView view, string message, Interactor interactor) { }
    }
}
