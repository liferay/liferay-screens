using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class SignUpViewController : UIViewController, ISignUpScreenletDelegate
    {
        public SignUpViewController(IntPtr handle) : base(handle)
        {
            // Note: this .ctor should not contain any initialization logic.
        }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            // Perform any additional setup after loading the view, typically from a nib.

            this.signUpScreenlet.AnonymousApiUserName = "anonymous1@liferay.com";
            this.signUpScreenlet.AnonymousApiPassword = "anonymous1";

            this.signUpScreenlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }

        /* ISignUpScreenletDelegate */

        [Export("screenlet:onSignUpError:")]
        public void OnSignUpError(SignUpScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Sign up failed: {error.Description}");
        }

        [Export("screenlet:onSignUpResponseUserAttributes:")]
        public void OnSignUpResponseUserAttributes(SignUpScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            System.Diagnostics.Debug.WriteLine($"Sign up successful: {attributes}");
        }

    }
}

