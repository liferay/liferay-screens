using System;

using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class WebContentViewController : UIViewController
    {
		public WebContentViewController(IntPtr handle) : base(handle) { }

		public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            this.webContentDisplayScreenlet.ArticleId = "57343";
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }
    }
}