using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class ForgotPasswordViewController : UIViewController, IForgotPasswordScreenletDelegate
    {
        public ForgotPasswordViewController(IntPtr handle) : base(handle)
        {
            // Note: this .ctor should not contain any initialization logic.
        }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            // Perform any additional setup after loading the view, typically from a nib.

            this.forgotPasswordScreenlet.AnonymousApiUserName = "anonymous1@liferay.com";
            this.forgotPasswordScreenlet.AnonymousApiPassword = "anonymous1";

            this.forgotPasswordScreenlet.Delegate = this;

            SetDefaultValues();
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        /* IForgotPasswordScreenletDelegate */

        [Export("screenlet:onForgotPasswordError:")]
        public void OnForgotPasswordError(ForgotPasswordScreenlet screenlet, Foundation.NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Forgot password failed: {error.Description}");
        }

        [Export("screenlet:onForgotPasswordSent:")]
        public virtual void OnForgotPasswordSent(ForgotPasswordScreenlet screenlet, bool passwordSent)
        {
            System.Diagnostics.Debug.WriteLine($"Forgot password successful: {passwordSent}");
        }

        /* Private methods */

        void SetDefaultValues()
        {
            this.forgotPasswordScreenlet.ViewModel.UserName = "test@liferay.com";
        }
    }
}

