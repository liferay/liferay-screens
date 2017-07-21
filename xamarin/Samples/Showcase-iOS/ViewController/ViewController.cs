using LiferayScreens;
using Foundation;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class ViewController : UIViewController, ILoginScreenletDelegate
    {
        protected ViewController(IntPtr handle) : base(handle)
        {
            // Note: this .ctor should not contain any initialization logic.
        }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            // Perform any additional setup after loading the view, typically from a nib.

            this.loginScreenlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        /* LoginScreenletDelegate */

        [Export("screenlet:onLoginError:")]
        public virtual void OnLoginError(BaseScreenlet screenlet, NSError error) {
            System.Diagnostics.Debug.WriteLine($"Login failed: {error.Description}");
        }

        [Export("screenlet:onLoginResponseUserAttributes:")]
        public virtual void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes) {
            System.Diagnostics.Debug.WriteLine($"Login successful: {attributes}");
        }
    }
}
