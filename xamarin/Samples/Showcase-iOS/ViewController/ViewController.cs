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

            SetDefaultValues();
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

        /* Private methods */

        void SetDefaultValues() {
            this.loginScreenlet.ViewModel.UserName = "test@liferay.com";
            this.loginScreenlet.ViewModel.Password = "test1";
        }

        /* Action methods */

        partial void ForgotPasswordButton_TouchUpInside(UIButton sender)
        {
            System.Diagnostics.Debug.WriteLine($"Navigate to ForgotPasswordViewController");
            UIStoryboard board = UIStoryboard.FromName("ForgotPassword", null);
            ForgotPasswordViewController vc = (ForgotPasswordViewController) board.InstantiateViewController("ForgotPasswordViewController");
            this.PresentViewController(vc, true, null);
        }
    }
}
