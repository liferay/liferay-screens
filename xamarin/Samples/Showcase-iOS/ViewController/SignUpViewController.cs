using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class SignUpViewController : UIViewController, ISignUpScreenletDelegate
    {
        public SignUpViewController(IntPtr handle) : base(handle) {}

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.signUpScreenlet.AnonymousApiUserName = "anonymous1@liferay.com";
            this.signUpScreenlet.AnonymousApiPassword = "anonymous1";

            this.signUpScreenlet.Delegate = this;
        }

        /* ISignUpScreenletDelegate */

        [Export("screenlet:onSignUpError:")]
        public void OnSignUpError(SignUpScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Sign up failed: {error.Description}");
        }

        [Export("screenlet:onSignUpResponseUserAttributes:")]
        public void OnSignUpResponseUserAttributes(SignUpScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            Debug.WriteLine($"Sign up successful: {attributes}");
        }

    }
}

