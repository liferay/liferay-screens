using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class ForgotPasswordViewController : UIViewController, IForgotPasswordScreenletDelegate
    {
        public ForgotPasswordViewController(IntPtr handle) : base(handle) {}

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.forgotPasswordScreenlet.AnonymousApiUserName = LiferayServerContext.StringPropertyForKey("anonymousUsername");
            this.forgotPasswordScreenlet.AnonymousApiPassword = LiferayServerContext.StringPropertyForKey("anonymousPassword");

            this.forgotPasswordScreenlet.Delegate = this;

            SetDefaultValues();
        }

        /* IForgotPasswordScreenletDelegate */

        [Export("screenlet:onForgotPasswordError:")]
        public void OnForgotPasswordError(ForgotPasswordScreenlet screenlet, Foundation.NSError error)
        {
            Console.WriteLine($"Forgot password failed: {error.Description}");
        }

        [Export("screenlet:onForgotPasswordSent:")]
        public virtual void OnForgotPasswordSent(ForgotPasswordScreenlet screenlet, bool passwordSent)
        {
            Console.WriteLine($"Forgot password successful: {passwordSent}");
        }

        /* Private methods */

        void SetDefaultValues()
        {
            this.forgotPasswordScreenlet.ViewModel.UserName = LiferayServerContext.StringPropertyForKey("defaultUsername");
        }
    }
}

