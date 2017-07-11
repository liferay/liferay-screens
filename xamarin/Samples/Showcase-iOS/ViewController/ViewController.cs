using BindingLibrary;
using Foundation;
using System;
using UIKit;

namespace ShowcaseiOS
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

            this.loginScreenlet.PresentingViewController = this;
            this.loginScreenlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        [Export("screenlet:onLoginResponseUserAttributes:")]
        public void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            System.Diagnostics.Debug.WriteLine($"Login success {attributes}");
        }

        [Export("screenlet:onLoginError:")]
        public void OnLoginError(BaseScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Login error {error.Description}");
        }
    }
}
