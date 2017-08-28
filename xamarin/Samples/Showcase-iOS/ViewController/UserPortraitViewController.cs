using System;
using UIKit;
using LiferayScreens;

namespace ShowcaseiOS.ViewController
{
    public partial class UserPortraitViewController : UIViewController
    {
        public UserPortraitViewController(IntPtr handle) : base(handle) { }

		public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            this.userPortraitScreenlet.Editable = true;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }
    }
}

