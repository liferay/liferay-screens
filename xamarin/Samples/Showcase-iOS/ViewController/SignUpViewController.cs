using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class SignUpViewController : UIViewController, ISignUpScreenletDelegate
    {
        public SignUpViewController(IntPtr handle) : base(handle) {}

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.signUpScreenlet.AnonymousApiUserName = LiferayServerContext.StringPropertyForKey("anonymousUsername");
            this.signUpScreenlet.AnonymousApiPassword = LiferayServerContext.StringPropertyForKey("anonymousPassword");

            this.signUpScreenlet.Delegate = this;
        }

        /* ISignUpScreenletDelegate */

        [Export("screenlet:onSignUpError:")]
        public void OnSignUpError(SignUpScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Sign up failed: {error.Description}");
        }

        [Export("screenlet:onSignUpResponseUserAttributes:")]
        public void OnSignUpResponseUserAttributes(SignUpScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            Console.WriteLine($"Sign up successful: {attributes}");
        }
    }
}

